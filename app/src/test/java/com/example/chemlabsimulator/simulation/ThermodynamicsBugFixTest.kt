package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import com.webdevavi.chemlabsimulator.simulation.physics.ThermodynamicsEngine
import org.junit.Assert.assertTrue
import org.junit.Test

class ThermodynamicsBugFixTest {

    @Test
    fun testBurnerDoesNotClampNaturallyExothermicHeatAbove100C() {
        // Suppose an exothermic chemical reaction pushed the temperature to 135°C
        val hotReactionContainer = ContainerState(
            id = "c1",
            name = "Exothermic Reaction Vessel",
            equipmentType = EquipmentType.BEAKER_250,
            temperatureCelsius = 135.0,
            heatSourceWatts = 600.0, // Turning on the burner
            substances = listOf(
                SubstanceState(chemicalId = "H2O", massGrams = 50.0, volumeMl = 50.0, moles = 2.77, phase = Phase.LIQUID),
                SubstanceState(chemicalId = "NaCl", massGrams = 10.0, volumeMl = 4.6, moles = 0.17, phase = Phase.DISSOLVED)
            )
        )

        val updated = SimulationEngine.step(hotReactionContainer, dtSeconds = 1.0)

        // The temperature must NOT be reset or clamped back down to 100°C!
        assertTrue(
            "Temperature must remain above 135°C when burner is turned on, but was ${updated.temperatureCelsius}°C",
            updated.temperatureCelsius >= 135.0
        )
        assertTrue("Water should actively boil and release steam", updated.visualState.isBoiling || updated.visualState.steamIntensity > 0f)
    }

    @Test
    fun testThermodynamicsEngineStepDirectlyPreservesHighTemperature() {
        val substances = listOf(
            SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID)
        )

        val result = ThermodynamicsEngine.stepTemperature(
            substances = substances,
            currentTempC = 145.0,
            heatSourceWatts = 500.0,
            dtSeconds = 1.0
        )

        assertTrue(
            "ThermodynamicsEngine must not clamp 145°C down to 100°C on burner activation (was ${result.newTemperatureCelsius}°C)",
            result.newTemperatureCelsius >= 145.0
        )
    }
}

