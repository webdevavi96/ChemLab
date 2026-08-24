package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.AcidBaseEngine
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.chemistry.SolubilityEngine
import com.webdevavi.chemlabsimulator.simulation.chemistry.StoichiometryEngine
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import com.webdevavi.chemlabsimulator.simulation.model.VisualState
import com.webdevavi.chemlabsimulator.simulation.physics.ThermodynamicsEngine
import kotlin.math.max
import kotlin.math.min

object SimulationEngine {

    /**
     * Advances the simulation state for a single container by dtSeconds.
     */
    fun stepWithEvents(container: ContainerState, dtSeconds: Double = 1.0): Pair<ContainerState, List<ReactionResult>> {
        if (container.substances.isEmpty()) {
            // Apply thermal relaxation for empty container
            val thermal = ThermodynamicsEngine.stepTemperature(
                substances = emptyList(),
                currentTempC = container.temperatureCelsius,
                heatSourceWatts = container.heatSourceWatts,
                dtSeconds = dtSeconds
            )
            val currentTemp = thermal.newTemperatureCelsius

            // Evaluate Glass Physical State according to temperature thresholds:
            // 1. T <= 100°C: Normal intact glass (will NOT crack or explode)
            // 2. 150°C < T <= 300°C: Cracks in glass, glass becomes light red
            // 3. 300°C < T <= 500°C: Cracks increased, bottom of glass will be red
            // 4. 500°C < T <= 750°C: Glass melts or breaks into pieces
            // 5. T > 750°C: Glass violently explodes
            val isTempExploded = currentTemp > 750.0
            val isTempMelted = currentTemp in 500.0..750.0
            val isTempRedBottom = currentTemp in 300.0..500.0
            val isTempLightRed = currentTemp in 150.0..300.0
            val isTempSafe = currentTemp <= 100.0

            val isExplodedNow = container.isExploded || isTempExploded
            val isMeltedNow = (container.isMelted || isTempMelted) && !isExplodedNow
            val isShatteredNow = container.isShattered || isExplodedNow || isMeltedNow

            val crackLevelNow = when {
                isShatteredNow -> 2
                isTempRedBottom -> 2
                isTempLightRed -> 1
                isTempSafe -> 0
                container.isCracked -> max(1, container.visualState.crackLevel)
                else -> 0
            }
            val isCrackedNow = crackLevelNow > 0 || isShatteredNow
            val isLightRedGlassNow = (isTempLightRed || isTempRedBottom || isTempMelted) && !isTempSafe
            val isRedHotBottomNow = (isTempRedBottom || isTempMelted) && !isTempSafe

            val currentSmokeAlpha = max(0f, container.visualState.smokeScreenAlpha - (dtSeconds.toFloat() / 2.0f))

            val updated = container.copy(
                temperatureCelsius = thermal.newTemperatureCelsius,
                isCracked = isCrackedNow,
                isMelted = isMeltedNow,
                isExploded = isExplodedNow,
                isShattered = isShatteredNow,
                visualState = container.visualState.copy(
                    liquidLevel = 0f,
                    bubbleIntensity = 0f,
                    steamIntensity = 0f,
                    isBoiling = false,
                    flameActive = container.heatSourceWatts > 0.1 && !isShatteredNow,
                    isCracked = isCrackedNow,
                    crackLevel = crackLevelNow,
                    isLightRedGlass = isLightRedGlassNow,
                    isRedHotBottom = isRedHotBottomNow,
                    isMelted = isMeltedNow,
                    isExploded = isExplodedNow,
                    isShattered = isShatteredNow,
                    thermalStress = if (isShatteredNow) 1.0f else ((thermal.newTemperatureCelsius - 100.0) / 80.0).toFloat().coerceIn(0f, 1f),
                    smokeScreenAlpha = currentSmokeAlpha
                )
            )
            return Pair(updated, emptyList())
        }

        // 1. Evaluate Chemical Reactions
        val reactionResult = StoichiometryEngine.evaluateReactions(
            substances = container.substances,
            currentTemperatureC = container.temperatureCelsius,
            dtSeconds = dtSeconds
        )

        var activeSubstances = reactionResult.updatedSubstances.toMutableList()

        // 2. Evaluate Thermodynamics & Phase Transitions
        var currentTemp = container.temperatureCelsius + (reactionResult.totalHeatJoules / ThermodynamicsEngine.calculateTotalHeatCapacity(activeSubstances))

        val thermalResult = ThermodynamicsEngine.stepTemperature(
            substances = activeSubstances,
            currentTempC = currentTemp,
            heatSourceWatts = container.heatSourceWatts,
            dtSeconds = dtSeconds
        )

        currentTemp = thermalResult.newTemperatureCelsius

        // Handle boiling liquid vaporization
        if (thermalResult.vaporizedMassGrams > 0.001) {
            val waterIdx = activeSubstances.indexOfFirst { it.chemicalId == "H2O" }
            if (waterIdx >= 0) {
                val water = activeSubstances[waterIdx]
                val remainingMass = max(0.0, water.massGrams - thermalResult.vaporizedMassGrams)
                val remainingMoles = remainingMass / 18.015
                if (remainingMass <= 0.01) {
                    activeSubstances.removeAt(waterIdx)
                } else {
                    activeSubstances[waterIdx] = water.copy(
                        massGrams = remainingMass,
                        volumeMl = remainingMass,
                        moles = remainingMoles
                    )
                }
            }
        }

        // 3. Evaluate Solubility & Precipitation
        val waterVolumeMl = activeSubstances.find { it.chemicalId == "H2O" }?.volumeMl ?: 0.0
        activeSubstances = SolubilityEngine.evaluateSolubility(
            substances = activeSubstances,
            waterVolumeMl = waterVolumeMl,
            temperatureC = currentTemp
        ).toMutableList()

        // 4. Calculate Total Mass and Volume
        var totalMass = 0.0
        var totalVol = 0.0
        for (sub in activeSubstances) {
            if (sub.phase != Phase.GAS && !sub.isGasVapor) {
                totalMass += sub.massGrams
                totalVol += sub.volumeMl
            }
        }

        // 5. Calculate pH and Acid-Base Equilbria
        val calculatedPh = AcidBaseEngine.calculatePh(activeSubstances, totalVol)
        val calculatedPOh = 14.0 - calculatedPh

        // 6. Compute Visual Rendering Parameters
        val liquidFraction = (totalVol / container.maxCapacityMl).toFloat().coerceIn(0f, 1f)
        val liquidColor = AcidBaseEngine.calculateLiquidColor(activeSubstances, calculatedPh)

        val hasPrecipitate = activeSubstances.any { it.isPrecipitated }
        val precipitateColor = when {
            activeSubstances.any { it.chemicalId == "Cu_OH_2" && it.isPrecipitated } -> 0xFF38BDF8 // Sky Blue
            activeSubstances.any { it.chemicalId == "FeCl2" } -> 0xFF86EFAC // Pale Green
            else -> 0xFFF1F5F9 // White precipitate
        }

        val precipitateHeight = if (hasPrecipitate) {
            val pptMass = activeSubstances.filter { it.isPrecipitated }.sumOf { it.massGrams }
            (pptMass / 10.0).toFloat().coerceIn(0.05f, 0.35f)
        } else 0f

        val bubbleIntensity = when {
            thermalResult.isBoiling -> 0.9f
            reactionResult.totalGasMolesProduced > 1e-5 -> 0.85f
            reactionResult.events.any { it.gasMoles > 0.0 } -> 0.7f
            else -> max(0f, container.visualState.bubbleIntensity - 0.1f * dtSeconds.toFloat())
        }

        val steamIntensity = when {
            thermalResult.isBoiling -> 0.95f
            thermalResult.steamIntensity > 0f -> thermalResult.steamIntensity
            currentTemp > 60.0 -> ((currentTemp - 60.0) / 40.0 * 0.4).toFloat()
            else -> 0f
        }

        val turbidity = if (hasPrecipitate) 0.65f else 0.0f

        // Gas type detection
        val activeGasId = reactionResult.events.firstOrNull { it.gasFormedId != null }?.gasFormedId
            ?: activeSubstances.firstOrNull { it.phase == Phase.GAS || it.isGasVapor }?.chemicalId

        // Evaluate Glass Physical State according to temperature thresholds:
        // 1. T <= 100°C: Normal intact glass (will NOT crack or explode)
        // 2. 150°C < T <= 300°C: Cracks in glass, glass becomes light red
        // 3. 300°C < T <= 500°C: Cracks increased, bottom of glass will be red
        // 4. 500°C < T <= 750°C: Glass melts or breaks into pieces
        // 5. T > 750°C: Glass violently explodes
        val isTempExploded = currentTemp > 750.0
        val isTempMelted = currentTemp in 500.0..750.0
        val isTempRedBottom = currentTemp in 300.0..500.0
        val isTempLightRed = currentTemp in 150.0..300.0
        val isTempSafe = currentTemp <= 100.0

        val blastEvent = reactionResult.events.firstOrNull { it.isBlast }

        val isExplodedNow = container.isExploded || isTempExploded || (blastEvent != null && currentTemp > 100.0 && blastEvent.blastIntensity >= 0.85f)
        val isMeltedNow = (container.isMelted || isTempMelted) && !isExplodedNow
        val isShatteredNow = container.isShattered || isExplodedNow || isMeltedNow

        val crackLevelNow = when {
            isShatteredNow -> 2
            isTempRedBottom -> 2
            isTempLightRed -> 1
            isTempSafe -> 0
            container.isCracked -> max(1, container.visualState.crackLevel)
            else -> 0
        }
        val isCrackedNow = crackLevelNow > 0 || isShatteredNow
        val isLightRedGlassNow = (isTempLightRed || isTempRedBottom || isTempMelted) && !isTempSafe
        val isRedHotBottomNow = (isTempRedBottom || isTempMelted) && !isTempSafe

        // Screen smoke timer: smokeScreenAlpha is 1.0 on explosion, decays smoothly over 2 seconds (dt / 2.0)
        val currentSmokeAlpha = if (isExplodedNow || (blastEvent != null && blastEvent.isBlast && currentTemp > 100.0)) {
            1.0f
        } else {
            max(0f, container.visualState.smokeScreenAlpha - (dtSeconds.toFloat() / 2.0f))
        }

        val newBlastIntensity = if (isExplodedNow) {
            max(blastEvent?.blastIntensity ?: 0.95f, 0.90f)
        } else if (blastEvent != null && currentTemp > 100.0) {
            blastEvent.blastIntensity
        } else {
            max(0f, container.visualState.blastIntensity - 0.35f * dtSeconds.toFloat())
        }

        // If shattered from explosion/melting, contents violently spill / vaporize
        if (isShatteredNow) {
            activeSubstances.clear()
            totalVol = 0.0
            totalMass = 0.0
        }

        val surfaceRipple = when {
            thermalResult.isBoiling -> 1.0f
            container.stirrerActive -> 0.75f
            reactionResult.totalGasMolesProduced > 1e-4 -> 0.8f
            container.visualState.isPouringStreamActive -> 0.9f
            else -> max(0f, container.visualState.surfaceRippleIntensity - 0.2f * dtSeconds.toFloat())
        }

        val isRadioactiveNow = activeSubstances.any {
            ChemicalRegistry.get(it.chemicalId)?.isRadioactive == true
        }

        val sparkColors = if (blastEvent != null && blastEvent.sparkColors.isNotEmpty()) {
            blastEvent.sparkColors
        } else if (container.visualState.sparkColors.isNotEmpty() && newBlastIntensity > 0.05f) {
            container.visualState.sparkColors
        } else {
            emptyList()
        }

        val isFirecracker = (blastEvent != null && blastEvent.sparkColors.isNotEmpty()) || (container.visualState.isFirecrackerBlast && newBlastIntensity > 0.05f)

        val blastFlashColor = when {
            blastEvent?.sparkColors?.isNotEmpty() == true -> blastEvent.sparkColors.first()
            blastEvent?.reactionId == "r_mg_hcl" || blastEvent?.reactionId == "r_mg_combustion_flash" -> 0xFFFFFFFF
            blastEvent?.reactionId == "r_potassium_water_lilac" -> 0xFFA855F7
            blastEvent?.reactionId?.contains("strontium") == true -> 0xFFEF4444
            blastEvent?.reactionId?.contains("barium") == true -> 0xFF22C55E
            else -> 0xFFFF5722
        }

        val updatedVisualState = VisualState(
            liquidLevel = if (isShatteredNow) 0f else liquidFraction,
            liquidColorHex = liquidColor,
            turbidity = turbidity,
            bubbleIntensity = if (isShatteredNow) 0f else bubbleIntensity,
            precipitateColorHex = precipitateColor,
            precipitateHeight = if (isShatteredNow) 0f else precipitateHeight,
            steamIntensity = if (isShatteredNow) 0f else steamIntensity,
            isBoiling = if (isShatteredNow) false else thermalResult.isBoiling,
            flameActive = container.heatSourceWatts > 0.1 && !isShatteredNow,
            blastIntensity = newBlastIntensity,
            blastFlashColorHex = blastFlashColor,
            gasType = activeGasId,
            isPouringStreamActive = container.visualState.isPouringStreamActive,
            pourStreamProgress = container.visualState.pourStreamProgress,
            surfaceRippleIntensity = surfaceRipple,
            isShattered = isShatteredNow,
            isCracked = isCrackedNow,
            crackLevel = crackLevelNow,
            isLightRedGlass = isLightRedGlassNow,
            isRedHotBottom = isRedHotBottomNow,
            isMelted = isMeltedNow,
            isExploded = isExplodedNow,
            thermalStress = if (isShatteredNow) 1.0f else ((currentTemp - 100.0) / 80.0).toFloat().coerceIn(0f, 1f),
            smokeScreenAlpha = currentSmokeAlpha,
            isRadioactive = isRadioactiveNow,
            radioactivityIntensity = if (isRadioactiveNow) 1.0f else 0f,
            sparkColors = sparkColors,
            isFirecrackerBlast = isFirecracker
        )

        val isOverflown = totalVol > container.maxCapacityMl

        val allEvents = if (reactionResult.events.isNotEmpty()) {
            container.lastReactionEvents + reactionResult.events
        } else {
            container.lastReactionEvents
        }

        val updatedContainer = container.copy(
            temperatureCelsius = if (isShatteredNow) 20.0 else currentTemp,
            totalMassGrams = totalMass,
            totalVolumeMl = totalVol,
            pH = calculatedPh,
            pOH = calculatedPOh,
            substances = activeSubstances,
            lastReactionEvents = allEvents.takeLast(10),
            visualState = updatedVisualState,
            isOverflown = isOverflown,
            isShattered = isShatteredNow,
            isCracked = isCrackedNow,
            isMelted = isMeltedNow,
            isExploded = isExplodedNow,
            isRadioactive = isRadioactiveNow
        )

        return Pair(updatedContainer, reactionResult.events)
    }

    /**
     * Advances the simulation state for a single container by dtSeconds.
     */
    fun step(container: ContainerState, dtSeconds: Double = 1.0): ContainerState =
        stepWithEvents(container, dtSeconds).first

    /**
     * Adds a chemical substance to a container and evaluates reactions.
     */
    fun addChemical(
        container: ContainerState,
        chemicalId: String,
        amount: Double,
        isVolume: Boolean,
        concentrationMolar: Double? = null
    ): Pair<ContainerState, List<ReactionResult>> {
        val chem = ChemicalRegistry.get(chemicalId) ?: return Pair(container, emptyList())

        val massGrams: Double
        val volumeMl: Double
        val moles: Double

        if (isVolume) {
            volumeMl = amount
            if (concentrationMolar != null && concentrationMolar > 0) {
                // Solution with given molarity (e.g., 1.0 M HCl)
                moles = concentrationMolar * (volumeMl / 1000.0)
                massGrams = moles * chem.molarMass + (volumeMl * 1.0) // solute + solvent
            } else {
                massGrams = volumeMl * chem.density
                moles = massGrams / chem.molarMass
            }
        } else {
            massGrams = amount
            moles = massGrams / chem.molarMass
            volumeMl = if (chem.density > 0) massGrams / chem.density else 0.0
        }

        val newSubstance = SubstanceState(
            chemicalId = chemicalId,
            massGrams = massGrams,
            volumeMl = volumeMl,
            moles = moles,
            concentrationMolar = concentrationMolar,
            temperatureCelsius = container.temperatureCelsius,
            phase = chem.defaultPhase,
            isPrecipitated = chem.defaultPhase == Phase.PRECIPITATE,
            isDissolved = chem.isSolvent || chem.defaultPhase == Phase.AQUEOUS
        )

        val updatedSubstances = container.substances.toMutableList()
        val existingIndex = updatedSubstances.indexOfFirst { it.chemicalId == chemicalId }
        if (existingIndex >= 0) {
            val existing = updatedSubstances[existingIndex]
            updatedSubstances[existingIndex] = existing.copy(
                massGrams = existing.massGrams + massGrams,
                volumeMl = existing.volumeMl + volumeMl,
                moles = existing.moles + moles
            )
        } else {
            updatedSubstances.add(newSubstance)
        }

        val intermediateContainer = container.copy(substances = updatedSubstances)
        val (steppedContainer, newEvents) = stepWithEvents(intermediateContainer, dtSeconds = 1.0)

        return Pair(steppedContainer, newEvents)
    }

    /**
     * Pours liquid from a source container into a target container.
     */
    fun pour(
        source: ContainerState,
        target: ContainerState,
        volumeToPourMl: Double
    ): Pair<ContainerState, ContainerState> {
        if (source.totalVolumeMl <= 0.001 || volumeToPourMl <= 0.001) {
            return Pair(source, target)
        }

        val actualPourVolume = min(source.totalVolumeMl, volumeToPourMl)
        val pourFraction = actualPourVolume / source.totalVolumeMl

        val sourceRemainingSubstances = mutableListOf<SubstanceState>()
        val transferredSubstances = mutableListOf<SubstanceState>()

        for (sub in source.substances) {
            val transferredMass = sub.massGrams * pourFraction
            val transferredVol = sub.volumeMl * pourFraction
            val transferredMoles = sub.moles * pourFraction

            val remainingMass = sub.massGrams - transferredMass
            val remainingVol = sub.volumeMl - transferredVol
            val remainingMoles = sub.moles - transferredMoles

            if (remainingMass > 1e-6) {
                sourceRemainingSubstances.add(
                    sub.copy(
                        massGrams = remainingMass,
                        volumeMl = remainingVol,
                        moles = remainingMoles
                    )
                )
            }

            if (transferredMass > 1e-6) {
                transferredSubstances.add(
                    sub.copy(
                        massGrams = transferredMass,
                        volumeMl = transferredVol,
                        moles = transferredMoles
                    )
                )
            }
        }

        // Merge into target
        val targetMergedSubstances = target.substances.toMutableList()
        for (trans in transferredSubstances) {
            val idx = targetMergedSubstances.indexOfFirst { it.chemicalId == trans.chemicalId }
            if (idx >= 0) {
                val existing = targetMergedSubstances[idx]
                targetMergedSubstances[idx] = existing.copy(
                    massGrams = existing.massGrams + trans.massGrams,
                    volumeMl = existing.volumeMl + trans.volumeMl,
                    moles = existing.moles + trans.moles
                )
            } else {
                targetMergedSubstances.add(trans)
            }
        }

        val updatedSource = step(source.copy(substances = sourceRemainingSubstances), dtSeconds = 0.5)
        val updatedTarget = step(target.copy(substances = targetMergedSubstances), dtSeconds = 1.0)

        return Pair(updatedSource, updatedTarget)
    }
}

