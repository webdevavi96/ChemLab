package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.HazardType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PeriodicTableTest {

    @Test
    fun testAll118ElementsAreRegistered() {
        val elements = ChemicalRegistry.getAllElements()
        assertEquals("Periodic table must have exactly 118 elements", 118, elements.size)

        // Check first element: Hydrogen (Z=1)
        val h = ChemicalRegistry.get("H")
        assertNotNull("Hydrogen must exist", h)
        assertEquals(1, h?.atomicNumber)
        assertEquals("Hydrogen", h?.name)
        assertEquals(1.008, h?.atomicWeight ?: 0.0, 0.001)

        // Check last element: Oganesson (Z=118)
        val og = ChemicalRegistry.get("Og")
        assertNotNull("Oganesson must exist", og)
        assertEquals(118, og?.atomicNumber)
        assertEquals("Oganesson", og?.name)
        assertEquals(294.0, og?.atomicWeight ?: 0.0, 0.1)

        // Check key elements
        val u = ChemicalRegistry.get("U")
        assertNotNull("Uranium must exist", u)
        assertEquals(92, u?.atomicNumber)
        assertTrue("Uranium must be radioactive", u?.isRadioactive == true)
        assertTrue("Uranium must have RADIOACTIVE hazard", u?.hazards?.contains(HazardType.RADIOACTIVE) == true)

        val fe = ChemicalRegistry.get("Fe")
        assertNotNull("Iron must exist", fe)
        assertEquals(26, fe?.atomicNumber)
        assertEquals(55.845, fe?.atomicWeight ?: 0.0, 0.01)

        val au = ChemicalRegistry.get("Au")
        assertNotNull("Gold must exist", au)
        assertEquals(79, au?.atomicNumber)
        assertEquals(196.97, au?.atomicWeight ?: 0.0, 0.01)
    }

    @Test
    fun testElementAndCompoundFiltering() {
        val allElements = ChemicalRegistry.getAllElements()
        val allCompounds = ChemicalRegistry.getAllCompounds()
        val radioactiveList = ChemicalRegistry.getRadioactive()

        assertTrue("Must have 118 elements", allElements.size == 118)
        assertTrue("Must have multiple compounds registered", allCompounds.isNotEmpty())
        assertTrue("Must have multiple radioactive elements", radioactiveList.size >= 30)

        // Search test
        val uraniumSearchResults = ChemicalRegistry.search("Uranium")
        assertTrue("Search for Uranium should find element", uraniumSearchResults.any { it.id == "U" })

        val atomicNumberSearchResults = ChemicalRegistry.search("92")
        assertTrue("Search by atomic number 92 should find Uranium", atomicNumberSearchResults.any { it.id == "U" })
    }
}

