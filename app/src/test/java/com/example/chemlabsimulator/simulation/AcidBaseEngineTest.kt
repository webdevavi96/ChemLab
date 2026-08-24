package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.AcidBaseEngine
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AcidBaseEngineTest {

    @Test
    fun testStrongAcidPh() {
        // 0.01 moles HCl in 100 mL (0.1 L) -> [H+] = 0.1 M -> pH = -log10(0.1) = 1.0
        val substances = listOf(
            SubstanceState(chemicalId = "HCl", massGrams = 0.3646, volumeMl = 100.0, moles = 0.01, phase = Phase.AQUEOUS)
        )
        val ph = AcidBaseEngine.calculatePh(substances, totalVolumeMl = 100.0)
        assertEquals(1.0, ph, 0.05)
    }

    @Test
    fun testStrongBasePh() {
        // 0.01 moles NaOH in 100 mL (0.1 L) -> [OH-] = 0.1 M -> pOH = 1.0 -> pH = 13.0
        val substances = listOf(
            SubstanceState(chemicalId = "NaOH", massGrams = 0.40, volumeMl = 100.0, moles = 0.01, phase = Phase.AQUEOUS)
        )
        val ph = AcidBaseEngine.calculatePh(substances, totalVolumeMl = 100.0)
        assertEquals(13.0, ph, 0.05)
    }

    @Test
    fun testNeutralSolutionPh() {
        // Pure water with NaCl
        val substances = listOf(
            SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID),
            SubstanceState(chemicalId = "NaCl", massGrams = 5.84, volumeMl = 2.7, moles = 0.1, phase = Phase.SOLID, isDissolved = true)
        )
        val ph = AcidBaseEngine.calculatePh(substances, totalVolumeMl = 100.0)
        assertEquals(7.0, ph, 0.01)
    }

    @Test
    fun testAceticAcidBufferHendersonHasselbalch() {
        // Equimolar Acetic Acid (0.05 mol) and Sodium Acetate (0.05 mol)
        // pH = pKa + log(1) = 4.76
        val substances = listOf(
            SubstanceState(chemicalId = "CH3COOH", massGrams = 3.0, volumeMl = 50.0, moles = 0.05, phase = Phase.AQUEOUS),
            SubstanceState(chemicalId = "CH3COONa", massGrams = 4.1, volumeMl = 50.0, moles = 0.05, phase = Phase.AQUEOUS)
        )
        val ph = AcidBaseEngine.calculatePh(substances, totalVolumeMl = 100.0)
        assertEquals(4.76, ph, 0.1)
    }

    @Test
    fun testUniversalIndicatorColors() {
        assertEquals(0xFFDC2626.toLong(), AcidBaseEngine.getUniversalIndicatorColor(1.0)) // Red
        assertEquals(0xFF22C55E.toLong(), AcidBaseEngine.getUniversalIndicatorColor(7.0)) // Green
        assertEquals(0xFF8B5CF6.toLong(), AcidBaseEngine.getUniversalIndicatorColor(14.0)) // Violet
    }
}

