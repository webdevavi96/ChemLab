package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.chemistry.StoichiometryEngine
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class StoichiometryEngineTest {

    @Test
    fun testNeutralizationEquimolarStoichiometry() {
        // 1.0 mol HCl (36.46 g) + 1.0 mol NaOH (40.0 g) -> 1.0 mol NaCl + 1.0 mol H2O
        val substances = listOf(
            SubstanceState(chemicalId = "HCl", massGrams = 36.46, volumeMl = 35.0, moles = 1.0, phase = Phase.AQUEOUS),
            SubstanceState(chemicalId = "NaOH", massGrams = 40.00, volumeMl = 38.0, moles = 1.0, phase = Phase.AQUEOUS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0)

        // Verify reaction occurred
        assertEquals(1, result.events.size)
        val event = result.events[0]
        assertEquals(ReactionType.NEUTRALIZATION, event.reactionType)
        assertEquals("HCl + NaOH → NaCl + H₂O", event.equationString)

        // Verify products formed
        val nacl = result.updatedSubstances.find { it.chemicalId == "NaCl" }
        assertNotNull(nacl)
        assertEquals(1.0, nacl!!.moles, 0.01)

        val h2o = result.updatedSubstances.find { it.chemicalId == "H2O" }
        assertNotNull(h2o)
        assertEquals(1.0, h2o!!.moles, 0.01)

        // Verify reactants fully consumed
        val remainingHcl = result.updatedSubstances.find { it.chemicalId == "HCl" }
        val remainingNaoh = result.updatedSubstances.find { it.chemicalId == "NaOH" }
        assertTrue(remainingHcl == null || remainingHcl.moles < 1e-5)
        assertTrue(remainingNaoh == null || remainingNaoh.moles < 1e-5)

        // Verify exothermic heat released: 57.3 kJ
        assertEquals(57300.0, result.totalHeatJoules, 100.0)
    }

    @Test
    fun testLimitingReagentZincHydrochloricAcid() {
        // Zn + 2HCl -> ZnCl2 + H2
        // Given 1.0 mol Zn (65.38 g) and 1.0 mol HCl (36.46 g)
        // Required: 2 moles HCl per 1 mole Zn. Therefore HCl is the limiting reagent!
        // Expected: 0.5 mol Zn reacts (0.5 mol Zn remains), 1.0 mol HCl reacts, 0.5 mol ZnCl2 produced, 0.5 mol H2 gas produced.
        val substances = listOf(
            SubstanceState(chemicalId = "Zn", massGrams = 65.38, volumeMl = 9.15, moles = 1.0, phase = Phase.SOLID),
            SubstanceState(chemicalId = "HCl", massGrams = 36.46, volumeMl = 35.0, moles = 1.0, phase = Phase.AQUEOUS)
        )

        // For solid-liquid reaction, test multiple ticks or single step extent
        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0, dtSeconds = 4.0)

        assertEquals(1, result.events.size)
        val event = result.events[0]
        assertEquals("HCl", event.limitingReagentId)
        assertEquals(ReactionType.SINGLE_DISPLACEMENT, event.reactionType)

        val h2gas = result.updatedSubstances.find { it.chemicalId == "H2_gas" }
        assertNotNull(h2gas)
        assertTrue("Hydrogen gas should be produced", h2gas!!.moles > 0.0)

        val znCl2 = result.updatedSubstances.find { it.chemicalId == "ZnCl2" }
        assertNotNull(znCl2)
        assertTrue("Zinc chloride should be produced", znCl2!!.moles > 0.0)
    }

    @Test
    fun testPrecipitationSilverChloride() {
        // AgNO3 + NaCl -> AgCl (ppt) + NaNO3
        val substances = listOf(
            SubstanceState(chemicalId = "AgNO3", massGrams = 16.98, volumeMl = 10.0, moles = 0.1, phase = Phase.AQUEOUS),
            SubstanceState(chemicalId = "NaCl", massGrams = 5.84, volumeMl = 10.0, moles = 0.1, phase = Phase.AQUEOUS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0)
        assertEquals(1, result.events.size)
        assertEquals("AgCl", result.events[0].precipitateFormedId)

        val agCl = result.updatedSubstances.find { it.chemicalId == "AgCl" }
        assertNotNull(agCl)
        assertTrue("AgCl should be precipitated", agCl!!.isPrecipitated)
        assertEquals(0.1, agCl.moles, 0.01)
    }

    @Test
    fun testGasEvolutionBicarbonateAcid() {
        // NaHCO3 + HCl -> NaCl + H2O + CO2 (g)
        val substances = listOf(
            SubstanceState(chemicalId = "NaHCO3", massGrams = 8.40, volumeMl = 4.0, moles = 0.1, phase = Phase.SOLID),
            SubstanceState(chemicalId = "HCl", massGrams = 3.65, volumeMl = 20.0, moles = 0.1, phase = Phase.AQUEOUS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0)
        assertEquals(1, result.events.size)
        assertEquals("CO2_gas", result.events[0].gasFormedId)
        assertTrue("Gas moles should be greater than 0", result.totalGasMolesProduced > 0.0)
    }

    @Test
    fun testViolentBlastReactionMagnesiumAcid() {
        // Mg + 2HCl -> MgCl2 + H2 (High enthalpy ΔH = -467 kJ/mol -> triggers blast effect)
        val substances = listOf(
            SubstanceState(chemicalId = "Mg", massGrams = 24.31, volumeMl = 14.0, moles = 1.0, phase = Phase.SOLID),
            SubstanceState(chemicalId = "HCl", massGrams = 72.92, volumeMl = 70.0, moles = 2.0, phase = Phase.AQUEOUS)
        )

        val result = StoichiometryEngine.evaluateReactions(substances, currentTemperatureC = 20.0, dtSeconds = 2.0)
        assertEquals(1, result.events.size)
        val event = result.events[0]
        assertTrue("High enthalpy reaction should trigger blast event", event.isBlast)
        assertTrue("Blast intensity should be significant", event.blastIntensity > 0.5f)
        assertEquals("H2_gas", event.gasFormedId)
    }
}

