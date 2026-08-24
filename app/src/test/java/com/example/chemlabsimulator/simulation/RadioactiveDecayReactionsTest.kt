package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.chemistry.StoichiometryEngine
import com.webdevavi.chemlabsimulator.simulation.model.HazardType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RadioactiveDecayReactionsTest {

    @Test
    fun testAtLeastFourRadioactiveDecayRulesRegistered() {
        val decayRules = StoichiometryEngine.rules.filter { it.type == ReactionType.NUCLEAR_DECAY }
        assertTrue("Must have at least 4 radioactive decay reactions registered (found ${decayRules.size})", decayRules.size >= 4)
    }

    @Test
    fun testUranium238AlphaDecay() {
        // U -> Th234 + He4_alpha
        val substances = listOf(
            SubstanceState(chemicalId = "U", massGrams = 238.0, volumeMl = 12.0, moles = 1.0, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0, dtSeconds = 2.0)
        val event = result.events.find { it.reactionId == "r_u238_alpha_decay" }
        assertNotNull("Uranium alpha decay must trigger", event)

        val th = result.updatedSubstances.find { it.chemicalId == "Th234" }
        assertNotNull("Thorium-234 daughter isotope must be produced", th)
        assertTrue(th!!.moles > 0.0)

        val alpha = result.updatedSubstances.find { it.chemicalId == "He4_alpha" }
        assertNotNull("Alpha particle must be emitted", alpha)
    }

    @Test
    fun testThorium234BetaDecay() {
        // Th234 -> Pa234 + Beta_minus
        val substances = listOf(
            SubstanceState(chemicalId = "Th234", massGrams = 23.4, volumeMl = 2.0, moles = 0.1, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0, dtSeconds = 2.0)
        val event = result.events.find { it.reactionId == "r_th234_beta_decay" }
        assertNotNull("Thorium beta decay must trigger", event)

        val pa = result.updatedSubstances.find { it.chemicalId == "Pa234" }
        assertNotNull("Protactinium-234 must be produced", pa)
    }

    @Test
    fun testRadium226AlphaDecayToRadonGas() {
        // Ra -> Rn222 + He4_alpha
        val substances = listOf(
            SubstanceState(chemicalId = "Ra", massGrams = 22.6, volumeMl = 4.0, moles = 0.1, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0, dtSeconds = 2.0)
        val event = result.events.find { it.reactionId == "r_ra226_alpha_decay" }
        assertNotNull("Radium alpha decay must trigger", event)

        val rn = result.updatedSubstances.find { it.chemicalId == "Rn222" }
        assertNotNull("Radioactive Radon gas must be produced", rn)
    }

    @Test
    fun testPolonium210AlphaDecayToLead206() {
        // Po -> Pb206 + He4_alpha
        val substances = listOf(
            SubstanceState(chemicalId = "Po", massGrams = 21.0, volumeMl = 2.0, moles = 0.1, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 25.0, dtSeconds = 2.0)
        val event = result.events.find { it.reactionId == "r_po210_alpha_decay" }
        assertNotNull("Polonium alpha decay must trigger", event)

        val pb = result.updatedSubstances.find { it.chemicalId == "Pb206" }
        assertNotNull("Lead-206 must be produced", pb)
    }
}

