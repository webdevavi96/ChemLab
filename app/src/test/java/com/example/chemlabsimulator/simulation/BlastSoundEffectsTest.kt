package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import org.junit.Assert.assertTrue
import org.junit.Test

class BlastSoundEffectsTest {

    @Test
    fun testPotassiumInWaterTriggersBlastAndSparksAudio() {
        val container = ContainerState(id = "c1", name = "Test Beaker", equipmentType = EquipmentType.BEAKER_250)
        
        // Add Water then Potassium metal (2K + 2H2O -> 2KOH + H2 + Lilac Sparks)
        val (c1, _) = SimulationEngine.addChemical(container, "H2O", 50.0, true)
        val (c2, events) = SimulationEngine.addChemical(c1, "K", 3.9, false)

        assertTrue("Potassium water reaction event should be produced", events.isNotEmpty())
        val blastEvent = events.first()
        assertTrue("Reaction should be marked as blast", blastEvent.isBlast)
        assertTrue("Blast intensity should be high", blastEvent.blastIntensity >= 0.7f)
        assertTrue("Sparks should be present for firecracker audio", blastEvent.sparkColors.isNotEmpty())
    }

    @Test
    fun testStrontiumPyrotechnicTriggersFirecrackerAudio() {
        val container = ContainerState(
            id = "c1",
            name = "Test Dish",
            equipmentType = EquipmentType.EVAPORATING_DISH_100,
            temperatureCelsius = 35.0
        )
        
        val (c1, _) = SimulationEngine.addChemical(container, "Sr_NO3_2", 10.0, false)
        val (c2, events) = SimulationEngine.addChemical(c1, "Mg", 2.5, false)

        assertTrue("Strontium firecracker reaction should occur", events.isNotEmpty())
        val event = events.first()
        assertTrue("Pyrotechnic should trigger blast", event.isBlast)
        assertTrue("Crimson sparks should be produced", event.sparkColors.isNotEmpty())
    }

    @Test
    fun testAcidCarbonateReactionTriggersGasEffervescenceSound() {
        val container = ContainerState(id = "c1", name = "Test Beaker", equipmentType = EquipmentType.BEAKER_250)
        
        val (c1, _) = SimulationEngine.addChemical(container, "HCl", 30.0, true, concentrationMolar = 2.0)
        val (c2, events) = SimulationEngine.addChemical(c1, "NaHCO3", 5.0, false)

        assertTrue("Acid-carbonate neutralization should occur", events.isNotEmpty())
        val event = events.first()
        assertTrue("CO2 gas is produced", event.gasMoles > 0.005 || event.gasFormedId == "CO2_gas")
    }

    @Test
    fun testThermalShatterProducesVesselDamage() {
        var container = ContainerState(id = "c1", name = "Test Beaker", equipmentType = EquipmentType.BEAKER_250, temperatureCelsius = 25.0)
        
        // Heat beyond thermal threshold (e.g. 500 °C > max 450 °C for standard beaker)
        container = container.copy(temperatureCelsius = 520.0)
        val stepped = SimulationEngine.step(container, dtSeconds = 1.0)
        
        assertTrue("Thermal shock should crack or shatter beaker at 520 °C", stepped.isCracked || stepped.isShattered)
    }
}

