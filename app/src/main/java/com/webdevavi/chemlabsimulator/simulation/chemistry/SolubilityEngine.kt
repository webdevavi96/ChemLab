package com.webdevavi.chemlabsimulator.simulation.chemistry

import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import kotlin.math.max
import kotlin.math.min

object SolubilityEngine {

    /**
     * Evaluates dissolution and precipitation based on solubility limits at current temperature.
     */
    fun evaluateSolubility(
        substances: List<SubstanceState>,
        waterVolumeMl: Double,
        temperatureC: Double
    ): List<SubstanceState> {
        val effectiveWaterMl = max(1.0, waterVolumeMl)
        val updated = mutableListOf<SubstanceState>()

        for (sub in substances) {
            val chem = ChemicalRegistry.get(sub.chemicalId)
            if (chem == null || chem.isSolvent || sub.phase == Phase.GAS) {
                updated.add(sub)
                continue
            }

            // Solubility in g/100 mL at T
            val baseSolubility = chem.solubility
            if (baseSolubility.isInfinite() || baseSolubility >= 999.0) {
                // Highly soluble (e.g. miscible liquids or highly soluble acids/bases)
                updated.add(sub.copy(isPrecipitated = false, isDissolved = true))
                continue
            }

            // Temperature adjustment for solubility
            val tempFactor = max(0.2, 1.0 + 0.015 * (temperatureC - 20.0))
            val adjustedSolubilityGramsPer100Ml = baseSolubility * tempFactor
            val maxSolubleGrams = adjustedSolubilityGramsPer100Ml * (effectiveWaterMl / 100.0)

            if (sub.massGrams > maxSolubleGrams) {
                // Saturated solution with precipitate
                val pptMass = sub.massGrams - maxSolubleGrams
                val pptFraction = (pptMass / sub.massGrams).coerceIn(0.0, 1.0)
                updated.add(
                    sub.copy(
                        isPrecipitated = true,
                        isDissolved = maxSolubleGrams > 0.001
                    )
                )
            } else {
                // Completely dissolved
                updated.add(
                    sub.copy(
                        isPrecipitated = false,
                        isDissolved = true
                    )
                )
            }
        }

        return updated
    }
}

