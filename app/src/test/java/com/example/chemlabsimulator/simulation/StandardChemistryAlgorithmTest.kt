package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemistryReactionAlgorithm
import com.webdevavi.chemlabsimulator.simulation.chemistry.StoichiometryEngine
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StandardChemistryAlgorithmTest {

    @Test
    fun testSodiumAndChlorineDirectSynthesis() {
        // Na (solid) + Cl (gas/element) -> NaCl (solid table salt)
        val substances = listOf(
            SubstanceState(chemicalId = "Na", massGrams = 22.99, volumeMl = 23.7, moles = 1.0, phase = Phase.SOLID),
            SubstanceState(chemicalId = "Cl", massGrams = 35.45, volumeMl = 11.0, moles = 1.0, phase = Phase.GAS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0, dtSeconds = 2.0)

        // 1. Verify reaction event occurred
        assertEquals(1, result.events.size)
        val event = result.events[0]
        assertEquals("Na + Cl → NaCl", event.equationString)
        assertEquals(ReactionType.COMBUSTION, event.reactionType)
        assertTrue("Sodium in chlorine reaction should be an energetic blast", event.isBlast)
        assertTrue("Blast intensity should be high (> 0.7)", event.blastIntensity >= 0.7f)
        assertTrue("Spark colors should contain sodium yellow/amber", event.sparkColors.isNotEmpty())

        // 2. Verify NaCl formed
        val nacl = result.updatedSubstances.find { it.chemicalId == "NaCl" }
        assertNotNull("NaCl should be formed in products", nacl)
        assertTrue("NaCl moles produced should be > 0", nacl!!.moles > 0.0)

        // 3. Verify high exothermic heat release
        assertTrue("Heat released should be large (> 200,000 Joules)", result.totalHeatJoules > 200000.0)
    }

    @Test
    fun testMagnesiumOxygenCombustion() {
        // Mg + O -> MgO
        val substances = listOf(
            SubstanceState(chemicalId = "Mg", massGrams = 24.31, volumeMl = 14.0, moles = 1.0, phase = Phase.SOLID),
            SubstanceState(chemicalId = "O", massGrams = 16.00, volumeMl = 11.0, moles = 1.0, phase = Phase.GAS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0, dtSeconds = 2.0)
        assertEquals(1, result.events.size)
        val event = result.events[0]
        assertEquals("Mg + O → MgO", event.equationString)
        val mgo = result.updatedSubstances.find { it.chemicalId == "MgO" }
        assertNotNull("MgO product must exist", mgo)
        assertTrue("MgO formed", mgo!!.moles > 0.0)
    }

    @Test
    fun testIronSulfurSynthesis() {
        // Fe + S -> FeS
        val substances = listOf(
            SubstanceState(chemicalId = "Fe", massGrams = 55.85, volumeMl = 7.1, moles = 1.0, phase = Phase.SOLID),
            SubstanceState(chemicalId = "S", massGrams = 32.06, volumeMl = 15.5, moles = 1.0, phase = Phase.SOLID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0, dtSeconds = 2.0)
        assertEquals(1, result.events.size)
        val event = result.events[0]
        assertEquals("Fe + S → FeS", event.equationString)
        val fes = result.updatedSubstances.find { it.chemicalId == "FeS" }
        assertNotNull("FeS product must exist", fes)
        assertTrue("FeS formed", fes!!.moles > 0.0)
    }

    @Test
    fun testDynamicAlgorithmBinarySynthesis() {
        // Dynamic reaction between K (Potassium) and Br (Bromine) -> KBr
        val substances = listOf(
            SubstanceState(chemicalId = "K", massGrams = 39.10, volumeMl = 45.0, moles = 1.0, phase = Phase.SOLID),
            SubstanceState(chemicalId = "Br", massGrams = 79.90, volumeMl = 25.6, moles = 1.0, phase = Phase.LIQUID)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0, dtSeconds = 2.0)
        assertTrue("Dynamic reaction should be detected", result.events.isNotEmpty())
        val kbr = result.updatedSubstances.find { it.chemicalId == "KBr" }
        assertNotNull("KBr must be synthesized dynamically", kbr)
        assertTrue(kbr!!.moles > 0.0)
    }

    @Test
    fun testReactivityAssessmentNobleGasInert() {
        val he = ChemicalRegistry.get("He")!!
        val o = ChemicalRegistry.get("O")!!
        val assessment = ChemistryReactionAlgorithm.assessReactivity(he, o)

        assertFalse("Noble gas must be assessed as non-reactive", assessment.isReactive)
        assertTrue(assessment.explanation.contains("Noble Gas") || assessment.explanation.contains("octet"))
    }

    @Test
    fun testReactivityAssessmentActiveMetalAndHalogen() {
        val na = ChemicalRegistry.get("Na")!!
        val cl = ChemicalRegistry.get("Cl")!!
        val assessment = ChemistryReactionAlgorithm.assessReactivity(na, cl)

        assertTrue("Na and Cl must be reactive", assessment.isReactive)
        assertEquals(ReactionType.COMBUSTION, assessment.reactionType)
        assertEquals("NaCl", assessment.primaryProduct)
        assertTrue(assessment.isBlast)
    }
}

