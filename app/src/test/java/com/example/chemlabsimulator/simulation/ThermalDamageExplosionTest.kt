package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ThermalDamageExplosionTest {

    @Test
    fun testTemperatureUnder100NoCrackNoExplosion() {
        // Temperature <= 100°C: glass remains intact, no cracks, no melting, no explosion
        val container = ContainerState(
            id = "c1",
            name = "Test Beaker",
            temperatureCelsius = 75.0,
            substances = listOf(
                SubstanceState(chemicalId = "H2O", massGrams = 100.0, volumeMl = 100.0, moles = 5.55, phase = Phase.LIQUID)
            )
        )

        val stepped = SimulationEngine.step(container, dtSeconds = 1.0)

        assertFalse("T <= 100°C should NOT be cracked", stepped.isCracked)
        assertFalse("T <= 100°C should NOT be shattered", stepped.isShattered)
        assertFalse("T <= 100°C should NOT be melted", stepped.isMelted)
        assertFalse("T <= 100°C should NOT be exploded", stepped.isExploded)
        assertEquals(0, stepped.visualState.crackLevel)
        assertFalse(stepped.visualState.isLightRedGlass)
        assertFalse(stepped.visualState.isRedHotBottom)
    }

    @Test
    fun testTemperature150to300CracksAndLightRedGlass() {
        // 150°C < T <= 300°C: cracks in glass (level 1) and glass becomes light red
        val container = ContainerState(
            id = "c1",
            name = "Test Beaker",
            temperatureCelsius = 220.0,
            substances = listOf(
                SubstanceState(chemicalId = "NaCl", massGrams = 20.0, volumeMl = 9.2, moles = 0.34, phase = Phase.SOLID)
            )
        )

        val stepped = SimulationEngine.step(container, dtSeconds = 1.0)

        assertTrue("150-300°C should cause glass to crack", stepped.isCracked)
        assertEquals(1, stepped.visualState.crackLevel)
        assertTrue("150-300°C should make glass light red", stepped.visualState.isLightRedGlass)
        assertFalse("150-300°C should NOT have incandescent red hot bottom", stepped.visualState.isRedHotBottom)
        assertFalse("150-300°C should NOT be melted", stepped.isMelted)
        assertFalse("150-300°C should NOT be exploded", stepped.isExploded)
    }

    @Test
    fun testTemperature350to500SevereCracksAndRedHotBottom() {
        // 350°C <= T <= 500°C: cracks increase (level 2) and bottom of glass turns glowing red
        val container = ContainerState(
            id = "c1",
            name = "Test Beaker",
            temperatureCelsius = 420.0,
            substances = listOf(
                SubstanceState(chemicalId = "FeS", massGrams = 30.0, volumeMl = 6.2, moles = 0.34, phase = Phase.SOLID)
            )
        )

        val stepped = SimulationEngine.step(container, dtSeconds = 1.0)

        assertTrue("350-500°C should be cracked", stepped.isCracked)
        assertEquals(2, stepped.visualState.crackLevel)
        assertTrue("350-500°C bottom of glass should be glowing red", stepped.visualState.isRedHotBottom)
        assertFalse("350-500°C should NOT be melted", stepped.isMelted)
        assertFalse("350-500°C should NOT be exploded", stepped.isExploded)
    }

    @Test
    fun testTemperature550to750GlassMeltsAndBreaks() {
        // 550°C <= T <= 750°C: glass softens / melts or breaks into pieces
        val container = ContainerState(
            id = "c1",
            name = "Test Beaker",
            temperatureCelsius = 650.0,
            substances = listOf(
                SubstanceState(chemicalId = "MgO", massGrams = 20.0, volumeMl = 5.5, moles = 0.5, phase = Phase.SOLID)
            )
        )

        val stepped = SimulationEngine.step(container, dtSeconds = 1.0)

        assertTrue("550-750°C glass should melt or break into pieces", stepped.isMelted)
        assertTrue("550-750°C container is shattered / broken", stepped.isShattered)
        assertFalse("550-750°C is melting stage, not violent gas explosion (>750°C)", stepped.isExploded)
        assertTrue("Container is destroyed", stepped.isDestroyed)
    }

    @Test
    fun testTemperatureOver750ViolentExplosion() {
        // Temperature > 750°C (up to 1000°C+): violent glass explosion
        val container = ContainerState(
            id = "c1",
            name = "Test Beaker",
            temperatureCelsius = 850.0,
            substances = listOf(
                SubstanceState(chemicalId = "NaCl", massGrams = 10.0, volumeMl = 4.6, moles = 0.17, phase = Phase.SOLID)
            )
        )

        val stepped = SimulationEngine.step(container, dtSeconds = 1.0)

        assertTrue("T > 750°C should violently explode", stepped.isExploded)
        assertTrue("T > 750°C container is shattered", stepped.isShattered)
        assertTrue("T > 750°C container is destroyed", stepped.isDestroyed)
        assertTrue("Blast shockwave intensity should be high", stepped.visualState.blastIntensity >= 0.85f)
        assertEquals(1.0f, stepped.visualState.smokeScreenAlpha, 0.01f)
    }

    @Test
    fun testDestroyedContainerFlag() {
        val normal = ContainerState(id = "c1", name = "Beaker", temperatureCelsius = 25.0)
        assertFalse(normal.isDestroyed)

        val melted = ContainerState(id = "c2", name = "Beaker", temperatureCelsius = 600.0, isMelted = true)
        assertTrue(melted.isDestroyed)

        val exploded = ContainerState(id = "c3", name = "Beaker", temperatureCelsius = 900.0, isExploded = true)
        assertTrue(exploded.isDestroyed)
    }
}

