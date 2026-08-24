package com.webdevavi.chemlabsimulator.simulation.chemistry

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

object AcidBaseEngine {

    const val KW = 1.0e-14
    const val KA_ACETIC = 1.74e-5
    const val KB_AMMONIA = 1.78e-5

    /**
     * Calculates the pH of an aqueous solution based on strong/weak acids, bases, and buffer systems.
     */
    fun calculatePh(substances: List<SubstanceState>, totalVolumeMl: Double): Double {
        val volumeL = max(0.001, totalVolumeMl / 1000.0) // in Liters

        var strongAcidProtons = 0.0 // moles H+
        var strongBaseHydroxides = 0.0 // moles OH-

        var aceticAcidMoles = 0.0
        var acetateMoles = 0.0

        var ammoniaMoles = 0.0
        var ammoniumMoles = 0.0

        var hasSaltsOnly = true

        for (sub in substances) {
            when (sub.chemicalId) {
                "HCl" -> {
                    strongAcidProtons += sub.moles
                    hasSaltsOnly = false
                }
                "HNO3" -> {
                    strongAcidProtons += sub.moles
                    hasSaltsOnly = false
                }
                "H2SO4" -> {
                    // Diprotic strong acid (first dissociation complete, second Ka2 ~ 0.012)
                    strongAcidProtons += sub.moles * 1.9
                    hasSaltsOnly = false
                }
                "NaOH" -> {
                    strongBaseHydroxides += sub.moles
                    hasSaltsOnly = false
                }
                "KOH" -> {
                    strongBaseHydroxides += sub.moles
                    hasSaltsOnly = false
                }
                "Ca_OH_2" -> {
                    // Limited by solubility (0.17 g / 100 mL ~ 0.023 M)
                    val maxSolubleMoles = (0.17 / 74.09) * (totalVolumeMl / 100.0)
                    val dissolvedMoles = min(sub.moles, maxSolubleMoles)
                    strongBaseHydroxides += dissolvedMoles * 2.0
                    hasSaltsOnly = false
                }
                "CH3COOH" -> {
                    aceticAcidMoles += sub.moles
                    hasSaltsOnly = false
                }
                "CH3COONa" -> {
                    acetateMoles += sub.moles
                }
                "NH3" -> {
                    ammoniaMoles += sub.moles
                    hasSaltsOnly = false
                }
                "NH4Cl" -> {
                    ammoniumMoles += sub.moles
                }
                "Na2CO3" -> {
                    // Hydrolysis: CO3^2- + H2O <-> HCO3- + OH- (moderately basic)
                    val conc = sub.moles / volumeL
                    val ohConc = sqrt(1e-4 * conc)
                    strongBaseHydroxides += ohConc * volumeL
                    hasSaltsOnly = false
                }
                "NaHCO3" -> {
                    // Amphoteric: pH ~ 1/2(pK1 + pK2) ~ 8.3
                    if (substances.size == 1 || (substances.size == 2 && substances.any { it.chemicalId == "H2O" })) {
                        return 8.34
                    }
                }
            }
        }

        // 1. Weak Acid Buffer: Acetic Acid / Sodium Acetate System
        if (aceticAcidMoles > 1e-6 && acetateMoles > 1e-6 && strongAcidProtons < 1e-6 && strongBaseHydroxides < 1e-6) {
            val ratio = acetateMoles / aceticAcidMoles
            val pKa = -log10(KA_ACETIC)
            val ph = pKa + log10(ratio)
            return ph.coerceIn(0.0, 14.0)
        }

        // 2. Weak Base Buffer: Ammonia / Ammonium Chloride System
        if (ammoniaMoles > 1e-6 && ammoniumMoles > 1e-6 && strongAcidProtons < 1e-6 && strongBaseHydroxides < 1e-6) {
            val ratio = ammoniaMoles / ammoniumMoles
            val pKb = -log10(KB_AMMONIA)
            val pOH = pKb - log10(ratio)
            val ph = 14.0 - pOH
            return ph.coerceIn(0.0, 14.0)
        }

        // 3. Weak Acid Alone: Acetic Acid
        if (aceticAcidMoles > 1e-6 && strongAcidProtons < 1e-6 && strongBaseHydroxides < 1e-6) {
            val conc = aceticAcidMoles / volumeL
            val hPlus = (-KA_ACETIC + sqrt(KA_ACETIC * KA_ACETIC + 4 * KA_ACETIC * conc)) / 2.0
            val ph = -log10(max(1e-14, hPlus))
            return ph.coerceIn(0.0, 14.0)
        }

        // 4. Weak Base Alone: Ammonia
        if (ammoniaMoles > 1e-6 && strongAcidProtons < 1e-6 && strongBaseHydroxides < 1e-6) {
            val conc = ammoniaMoles / volumeL
            val ohMinus = (-KB_AMMONIA + sqrt(KB_AMMONIA * KB_AMMONIA + 4 * KB_AMMONIA * conc)) / 2.0
            val pOH = -log10(max(1e-14, ohMinus))
            val ph = 14.0 - pOH
            return ph.coerceIn(0.0, 14.0)
        }

        // 5. Strong Acid / Strong Base Net Ionic Balance
        val netProtons = strongAcidProtons - strongBaseHydroxides

        return when {
            netProtons > 1e-8 -> {
                val hPlusConc = netProtons / volumeL
                val ph = -log10(hPlusConc)
                ph.coerceIn(0.0, 7.0)
            }
            netProtons < -1e-8 -> {
                val ohMinusConc = (-netProtons) / volumeL
                val pOH = -log10(ohMinusConc)
                val ph = 14.0 - pOH
                ph.coerceIn(7.0, 14.0)
            }
            else -> {
                // Neutral or pure water/salt solution
                7.00
            }
        }
    }

    /**
     * Maps calculated pH to Universal Indicator RGB color spectrum.
     */
    fun getUniversalIndicatorColor(pH: Double): Long {
        val clampedPh = pH.coerceIn(0.0, 14.0)
        return when {
            clampedPh <= 1.0 -> 0xFFDC2626 // Deep Red
            clampedPh <= 3.0 -> 0xFFEA580C // Red-Orange
            clampedPh <= 5.0 -> 0xFFF59E0B // Orange-Yellow
            clampedPh <= 6.5 -> 0xFFEAB308 // Yellow
            clampedPh <= 7.5 -> 0xFF22C55E // Vibrant Neutral Green
            clampedPh <= 9.0 -> 0xFF06B6D4 // Cyan / Turquoise
            clampedPh <= 11.0 -> 0xFF3B82F6 // Blue
            clampedPh <= 13.0 -> 0xFF6366F1 // Indigo
            else -> 0xFF8B5CF6 // Violet / Purple
        }
    }

    /**
     * Blends chemical intrinsic colors and indicator colors with alpha.
     */
    fun calculateLiquidColor(substances: List<SubstanceState>, pH: Double): Long {
        if (substances.isEmpty()) return 0x00000000 // Empty container

        val hasIndicator = substances.any { it.chemicalId == "UNIVERSAL_IND" }
        if (hasIndicator) {
            return getUniversalIndicatorColor(pH)
        }

        // Check for dominant colored solutes (e.g. CuSO4 = blue, FeCl2 = green)
        val cuSO4 = substances.find { it.chemicalId == "CuSO4" }
        if (cuSO4 != null && cuSO4.massGrams > 0.01) {
            val intensity = min(1.0, cuSO4.massGrams / 5.0)
            val alpha = (0x99 + (0x66 * intensity).toInt()).coerceIn(0x99, 0xFF)
            return (alpha.toLong() shl 24) or 0x000284C7
        }

        val feCl2 = substances.find { it.chemicalId == "FeCl2" }
        if (feCl2 != null && feCl2.massGrams > 0.01) {
            return 0xBB86EFAC // Pale green
        }

        // Standard translucent aqueous liquid
        return 0x6638BDF8
    }
}

