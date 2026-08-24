package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.StoichiometryEngine
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PyrotechnicReactionsTest {

    @Test
    fun testPermanganateGlycerolSpontaneousFirecrackerIgnition() {
        // 14KMnO4 + 4Glycerol -> K2CO3 + Mn2O3 + CO2 + H2O + Violet Sparks
        val substances = listOf(
            SubstanceState(chemicalId = "KMnO4", massGrams = 22.1, volumeMl = 10.0, moles = 0.14, phase = Phase.SOLID),
            SubstanceState(chemicalId = "Glycerol", massGrams = 3.68, volumeMl = 3.0, moles = 0.04, phase = Phase.LIQUID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0)
        val event = result.events.find { it.reactionId == "r_kmno4_glycerol_fire" }
        assertNotNull("KMnO4 + Glycerol spontaneous fire reaction must trigger", event)
        assertTrue("Must be marked as blast", event!!.isBlast)
        assertTrue("Must have spark colors defined", event.sparkColors.isNotEmpty())
    }

    @Test
    fun testStrontiumCrimsonRedSparklerReaction() {
        // Sr(NO3)2 + 2Mg -> SrO + 2MgO + N2
        val substances = listOf(
            SubstanceState(chemicalId = "Sr_NO3_2", massGrams = 21.1, volumeMl = 7.0, moles = 0.1, phase = Phase.SOLID),
            SubstanceState(chemicalId = "Mg", massGrams = 4.86, volumeMl = 2.8, moles = 0.2, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 35.0)
        val event = result.events.find { it.reactionId == "r_strontium_pyrotechnic_crimson" }
        assertNotNull("Strontium pyrotechnic reaction must trigger", event)
        assertTrue("Must have crimson red spark colors", event!!.sparkColors.contains(0xFFEF4444.toLong()))
    }

    @Test
    fun testBariumEmeraldGreenSparklerReaction() {
        // Ba(NO3)2 + 2Mg -> BaO + 2MgO + N2
        val substances = listOf(
            SubstanceState(chemicalId = "Ba_NO3_2", massGrams = 26.1, volumeMl = 8.0, moles = 0.1, phase = Phase.SOLID),
            SubstanceState(chemicalId = "Mg", massGrams = 4.86, volumeMl = 2.8, moles = 0.2, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 35.0)
        val event = result.events.find { it.reactionId == "r_barium_pyrotechnic_green" }
        assertNotNull("Barium pyrotechnic reaction must trigger", event)
        assertTrue("Must have emerald green spark colors", event!!.sparkColors.contains(0xFF22C55E.toLong()))
    }

    @Test
    fun testPotassiumInWaterLilacFirecrackerBlast() {
        // 2K + 2H2O -> 2KOH + H2
        val substances = listOf(
            SubstanceState(chemicalId = "K", massGrams = 7.8, volumeMl = 9.0, moles = 0.2, phase = Phase.SOLID),
            SubstanceState(chemicalId = "H2O", massGrams = 50.0, volumeMl = 50.0, moles = 2.77, phase = Phase.LIQUID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0)
        val event = result.events.find { it.reactionId == "r_potassium_water_lilac" }
        assertNotNull("Potassium in water reaction must trigger", event)
        assertTrue("Must be marked as blast", event!!.isBlast)
    }
}
