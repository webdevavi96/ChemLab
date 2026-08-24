package com.webdevavi.chemlabsimulator.simulation.physics

import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min

object ThermodynamicsEngine {

    const val DEFAULT_AMBIENT_TEMP_C = 20.0
    const val WATER_LATENT_HEAT_VAPORIZATION_J_G = 2260.0 // J/g
    const val BURNER_EFFICIENCY = 0.75
    const val COOLING_COEFFICIENT = 0.006 // s^-1 for glass beakers

    data class ThermalStepResult(
        val newTemperatureCelsius: Double,
        val vaporizedMassGrams: Double,
        val isBoiling: Boolean,
        val steamIntensity: Float
    )

    /**
     * Calculates total thermal heat capacity C = sum(m_i * c_p,i) in Joules / °C
     */
    fun calculateTotalHeatCapacity(substances: List<SubstanceState>): Double {
        var totalCapacity = 0.0
        for (sub in substances) {
            val chem = ChemicalRegistry.get(sub.chemicalId)
            val c = chem?.specificHeatCapacity ?: 4.184
            totalCapacity += max(0.01, sub.massGrams) * c
        }
        // Add small thermal mass of borosilicate glass vessel (~30 J/°C)
        return max(5.0, totalCapacity + 30.0)
    }

    /**
     * Applies heating power, ambient cooling, and phase transition vaporization over dtSeconds.
     */
    fun stepTemperature(
        substances: List<SubstanceState>,
        currentTempC: Double,
        heatSourceWatts: Double,
        ambientTempC: Double = DEFAULT_AMBIENT_TEMP_C,
        dtSeconds: Double = 1.0
    ): ThermalStepResult {
        if (substances.isEmpty()) {
            val cooledTemp = ambientTempC + (currentTempC - ambientTempC) * exp(-COOLING_COEFFICIENT * 2 * dtSeconds)
            return ThermalStepResult(
                newTemperatureCelsius = cooledTemp,
                vaporizedMassGrams = 0.0,
                isBoiling = false,
                steamIntensity = 0f
            )
        }

        val totalCapacity = calculateTotalHeatCapacity(substances)

        // Find lowest boiling point among active volatile liquids/solvents
        val boilingPoints = substances.mapNotNull { sub ->
            val chem = ChemicalRegistry.get(sub.chemicalId)
            if (chem != null && (chem.isSolvent || sub.phase == Phase.LIQUID || sub.phase == Phase.AQUEOUS)) {
                chem.boilingPointCelsius
            } else null
        }
        val minBoilingPointC = if (boilingPoints.isNotEmpty()) boilingPoints.minOrNull() ?: 100.0 else 100.0

        var temp = currentTempC
        var vaporizedGrams = 0.0
        var isBoiling = false
        var steamIntensity = 0f

        if (heatSourceWatts > 0.1) {
            val heatJoules = heatSourceWatts * BURNER_EFFICIENCY * dtSeconds
            val hasVolatileSolvent = substances.any { sub ->
                val chem = ChemicalRegistry.get(sub.chemicalId)
                chem?.isSolvent == true || sub.chemicalId == "H2O"
            }

            if (!hasVolatileSolvent || temp < minBoilingPointC) {
                // Below boiling point or dry/non-aqueous mixture: temperature rises directly
                val deltaT = heatJoules / totalCapacity
                temp += deltaT
            } else {
                // Active boiling of volatile solvent: latent heat consumed for vaporization
                // Preserves existing superheated/exothermic reaction temperature without resetting to 100°C
                isBoiling = true
                vaporizedGrams = heatJoules / WATER_LATENT_HEAT_VAPORIZATION_J_G
                steamIntensity = (heatSourceWatts / 500.0).toFloat().coerceIn(0.4f, 1.0f)
                temp = max(temp, minBoilingPointC)
            }
        } else {
            // Ambient cooling towards room temperature
            temp = ambientTempC + (temp - ambientTempC) * exp(-COOLING_COEFFICIENT * dtSeconds)
            if (temp > 60.0) {
                steamIntensity = ((temp - 60.0) / 40.0 * 0.4).toFloat()
            }
        }

        return ThermalStepResult(
            newTemperatureCelsius = temp,
            vaporizedMassGrams = vaporizedGrams,
            isBoiling = isBoiling,
            steamIntensity = steamIntensity
        )
    }
}

