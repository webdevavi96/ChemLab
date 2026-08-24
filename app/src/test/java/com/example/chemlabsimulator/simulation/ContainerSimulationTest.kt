package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ContainerSimulationTest {

    @Test
    fun testAddChemicalAndReactionPipeline() {
        val initialContainer = ContainerState(
            id = "c1",
            name = "Main Beaker",
            equipmentType = EquipmentType.BEAKER_250,
            temperatureCelsius = 20.0
        )

        // 1. Add 50 mL water
        val (containerWithWater, _) = SimulationEngine.addChemical(
            container = initialContainer,
            chemicalId = "H2O",
            amount = 50.0,
            isVolume = true
        )
        assertEquals(50.0, containerWithWater.totalVolumeMl, 0.5)
        assertEquals(50.0, containerWithWater.totalMassGrams, 0.5)
        assertEquals(7.0, containerWithWater.pH, 0.1)

        // 2. Add 25 mL 1.0 M HCl
        val (containerWithAcid, _) = SimulationEngine.addChemical(
            container = containerWithWater,
            chemicalId = "HCl",
            amount = 25.0,
            isVolume = true,
            concentrationMolar = 1.0
        )
        assertTrue("pH should drop significantly with HCl", containerWithAcid.pH < 2.0)

        // 3. Add 25 mL 1.0 M NaOH (Neutralization)
        val (containerNeutralized, events) = SimulationEngine.addChemical(
            container = containerWithAcid,
            chemicalId = "NaOH",
            amount = 25.0,
            isVolume = true,
            concentrationMolar = 1.0
        )

        assertTrue("Neutralization event should be registered", events.isNotEmpty() || containerNeutralized.lastReactionEvents.isNotEmpty())
        assertTrue("Temperature should rise from exothermic neutralization", containerNeutralized.temperatureCelsius > 20.0)
        assertEquals(7.0, containerNeutralized.pH, 0.2)
    }

    @Test
    fun testPourBetweenContainers() {
        val source = ContainerState(
            id = "src",
            name = "Source Beaker",
            equipmentType = EquipmentType.BEAKER_250,
            substances = listOf(
                SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID)
            ),
            totalVolumeMl = 100.0,
            totalMassGrams = 100.0
        )

        val target = ContainerState(
            id = "tgt",
            name = "Target Beaker",
            equipmentType = EquipmentType.BEAKER_250
        )

        // Pour 40 mL from source to target
        val (updatedSource, updatedTarget) = SimulationEngine.pour(source, target, volumeToPourMl = 40.0)

        assertEquals(60.0, updatedSource.totalVolumeMl, 1.0)
        assertEquals(40.0, updatedTarget.totalVolumeMl, 1.0)
    }

    @Test
    fun testCapacityOverflowCheck() {
        val testTube = ContainerState(
            id = "tt",
            name = "Test Tube",
            equipmentType = EquipmentType.TEST_TUBE_20
        )

        // Add 30 mL to 20 mL test tube
        val (overflownContainer, _) = SimulationEngine.addChemical(
            container = testTube,
            chemicalId = "H2O",
            amount = 30.0,
            isVolume = true
        )

        assertTrue("Container should flag overflow when exceeding capacity", overflownContainer.isOverflown)
    }

    @Test
    fun testChemicalFormulaDisplayString() {
        val container = ContainerState(
            id = "c1",
            name = "Main Beaker",
            substances = listOf(
                SubstanceState(chemicalId = "HCl", massGrams = 3.65, volumeMl = 10.0, moles = 0.1, phase = Phase.AQUEOUS),
                SubstanceState(chemicalId = "NaOH", massGrams = 4.0, volumeMl = 10.0, moles = 0.1, phase = Phase.AQUEOUS),
                SubstanceState(chemicalId = "H2O", massGrams = 50.0, volumeMl = 50.0, moles = 2.77, phase = Phase.LIQUID)
            )
        )

        val formulaStr = container.getFormulaDisplayString()
        assertTrue("Formula string should contain HCl", formulaStr.contains("HCl"))
        assertTrue("Formula string should contain NaOH", formulaStr.contains("NaOH"))
        assertTrue("Formula string should contain '+' separator", formulaStr.contains(" + "))
    }

    @Test
    fun testBeakerExplosionAndSmokeScreenOnBlast() {
        val container = ContainerState(
            id = "c1",
            name = "Test Beaker",
            substances = listOf(
                SubstanceState(chemicalId = "Mg", massGrams = 24.31, volumeMl = 14.0, moles = 1.0, phase = Phase.SOLID),
                SubstanceState(chemicalId = "HCl", massGrams = 72.92, volumeMl = 70.0, moles = 2.0, phase = Phase.AQUEOUS)
            )
        )

        val updated = SimulationEngine.step(container, dtSeconds = 1.0)
        assertTrue("Beaker should be flagged as shattered upon violent blast", updated.isShattered)
        assertTrue("Smoke screen alpha should be active for full screen smoke", updated.visualState.smokeScreenAlpha > 0.5f)
        assertEquals("Substances should spill out on shatter", 0.0, updated.totalVolumeMl, 0.01)

        // After 2.0 seconds, smoke decays
        val after2Secs = SimulationEngine.step(updated, dtSeconds = 2.0)
        assertEquals("Smoke screen alpha should decay to 0 after 2 seconds", 0f, after2Secs.visualState.smokeScreenAlpha, 0.05f)
    }

    @Test
    fun testBeakerThermalCrackingOnExtremeHeat() {
        val hotContainer = ContainerState(
            id = "c1",
            name = "Hot Beaker",
            temperatureCelsius = 195.0, // Exceeds 180°C threshold
            heatSourceWatts = 800.0,
            substances = listOf(
                SubstanceState(chemicalId = "H2SO4", massGrams = 98.0, volumeMl = 50.0, moles = 1.0, phase = Phase.LIQUID)
            )
        )

        val updated = SimulationEngine.step(hotContainer, dtSeconds = 0.5)
        assertTrue("Beaker should crack due to extreme heat", updated.isCracked)
        assertTrue("VisualState should flag isCracked", updated.visualState.isCracked)
    }
}

