package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.chemistry.StoichiometryEngine
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PotashAlumReactionTest {

    @Test
    fun testPotashAlumCompoundRegistered() {
        val alum = ChemicalRegistry.get("PotashAlum")
        assertNotNull("PotashAlum must be registered in ChemicalRegistry", alum)
        assertEquals("Potash Alum (Crystals)", alum?.name)
        assertEquals("KAl(SO₄)₂·12H₂O", alum?.formula)
        assertEquals(474.39, alum?.molarMass ?: 0.0, 0.1)
    }

    @Test
    fun testAluminumInKOHFormsAluminateAndHydrogenGas() {
        // 2Al + 2KOH + 6H2O -> 2KAl(OH)4 + 3H2
        val substances = listOf(
            SubstanceState(chemicalId = "Al", massGrams = 5.4, volumeMl = 2.0, moles = 0.2, phase = Phase.SOLID),
            SubstanceState(chemicalId = "KOH", massGrams = 11.22, volumeMl = 20.0, moles = 0.2, phase = Phase.AQUEOUS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0, dtSeconds = 5.0)
        assertTrue("Reaction must trigger for Al + KOH", result.events.isNotEmpty())

        val event = result.events.find { it.reactionId == "r_al_koh_potash_step1" }
        assertNotNull("r_al_koh_potash_step1 must trigger", event)

        val aluminate = result.updatedSubstances.find { it.chemicalId == "KAl_OH_4" }
        assertNotNull("KAl(OH)4 must be produced", aluminate)
        assertTrue("Aluminate moles > 0", aluminate!!.moles > 0.0)

        val h2gas = result.updatedSubstances.find { it.chemicalId == "H2_gas" }
        assertNotNull("Hydrogen gas must be produced", h2gas)
        assertTrue("H2 gas moles > 0", h2gas!!.moles > 0.0)
    }

    @Test
    fun testAluminateAcidificationFormsPotashAlumCrystals() {
        // KAl(OH)4 + 2H2SO4 -> KAl(SO4)2·12H2O (PotashAlum)
        val substances = listOf(
            SubstanceState(chemicalId = "KAl_OH_4", massGrams = 13.4, volumeMl = 15.0, moles = 0.1, phase = Phase.AQUEOUS),
            SubstanceState(chemicalId = "H2SO4", massGrams = 19.6, volumeMl = 20.0, moles = 0.2, phase = Phase.AQUEOUS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0)
        val alumEvent = result.events.find { it.reactionId == "r_potash_alum_acidification" }
        assertNotNull("Acidification must produce Potash Alum", alumEvent)
        assertEquals("PotashAlum", alumEvent?.precipitateFormedId)

        val alumSubstance = result.updatedSubstances.find { it.chemicalId == "PotashAlum" }
        assertNotNull("PotashAlum precipitate must be present in solution", alumSubstance)
        assertTrue("PotashAlum mass > 0", alumSubstance!!.massGrams > 0.0)
    }
}

