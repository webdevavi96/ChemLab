package com.webdevavi.chemlabsimulator.simulation

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class EquipmentApparatusTest {

    @Test
    fun testAllApparatusTypesAreDefined() {
        val types = EquipmentType.values()
        assertTrue("At least 20 apparatus types must be registered", types.size >= 20)

        // Check key apparatus
        val beaker1000 = EquipmentType.valueOf("BEAKER_1000")
        assertEquals(1000.0, beaker1000.capacityMl, 0.1)

        val burette50 = EquipmentType.valueOf("BURETTE_50")
        assertEquals(50.0, burette50.capacityMl, 0.1)

        val crucible50 = EquipmentType.valueOf("CRUCIBLE_50")
        assertEquals(50.0, crucible50.capacityMl, 0.1)

        val gasSyringe100 = EquipmentType.valueOf("GAS_SYRINGE_100")
        assertEquals(100.0, gasSyringe100.capacityMl, 0.1)
    }

    @Test
    fun testRadioactiveRayEmissionDetection() {
        val radioactiveContainer = ContainerState(
            id = "c_uranium",
            name = "Radioactive Sample Container",
            equipmentType = EquipmentType.BEAKER_250,
            substances = listOf(
                SubstanceState(chemicalId = "U", massGrams = 23.8, volumeMl = 1.25, moles = 0.1, phase = Phase.SOLID)
            )
        )

        val updated = SimulationEngine.step(radioactiveContainer, dtSeconds = 1.0)
        assertTrue("Container must be flagged as radioactive", updated.isRadioactive)
        assertTrue("VisualState must flag isRadioactive", updated.visualState.isRadioactive)
        assertTrue("Radioactivity intensity must be active for ray rendering", updated.visualState.radioactivityIntensity > 0.5f)
    }
}

