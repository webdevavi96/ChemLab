package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import com.webdevavi.chemlabsimulator.simulation.physics.ThermodynamicsEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ThermodynamicsEngineTest {

    @Test
    fun testHeatingWaterBelowBoiling() {
        // 100g water (c = 4.184 J/g C) starting at 20°C heated with 500W burner for 5 seconds
        // q = 500 * 0.75 * 5 = 1875 J
        // C_total = 100 * 4.184 + 30 = 448.4 J/C
        // ΔT = 1875 / 448.4 ≈ 4.18 °C -> new Temp ≈ 24.18 °C
        val substances = listOf(
            SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID)
        )
        val result = ThermodynamicsEngine.stepTemperature(
            substances = substances,
            currentTempC = 20.0,
            heatSourceWatts = 500.0,
            dtSeconds = 5.0
        )
        assertTrue("Temperature should increase", result.newTemperatureCelsius > 20.0)
        assertTrue("Temperature should be ~24°C", result.newTemperatureCelsius in 23.0..26.0)
        assertEquals(false, result.isBoiling)
    }

    @Test
    fun testBoilingPointPlateauAndSteam() {
        // Water at 100°C heated further should plateau at 100°C and vaporize mass
        val substances = listOf(
            SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID)
        )
        val result = ThermodynamicsEngine.stepTemperature(
            substances = substances,
            currentTempC = 100.0,
            heatSourceWatts = 500.0,
            dtSeconds = 1.0
        )
        assertEquals(100.0, result.newTemperatureCelsius, 0.01)
        assertEquals(true, result.isBoiling)
        assertTrue("Steam intensity should be high", result.steamIntensity > 0.5f)
        assertTrue("Water should vaporize", result.vaporizedMassGrams > 0.0)
    }

    @Test
    fun testAmbientCooling() {
        // Hot water at 80°C without heat source should cool towards 20°C
        val substances = listOf(
            SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID)
        )
        val result = ThermodynamicsEngine.stepTemperature(
            substances = substances,
            currentTempC = 80.0,
            heatSourceWatts = 0.0,
            ambientTempC = 20.0,
            dtSeconds = 10.0
        )
        assertTrue("Temperature should drop below 80°C", result.newTemperatureCelsius < 80.0)
        assertTrue("Temperature should stay above 20°C", result.newTemperatureCelsius > 20.0)
    }
}

