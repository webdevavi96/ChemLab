package com.webdevavi.chemlabsimulator.simulation.model

import kotlinx.serialization.Serializable

@Serializable
data class VisualState(
    val liquidLevel: Float = 0f, // 0.0 to 1.0 filling fraction
    val liquidColorHex: Long = 0x664A90E2, // ARGB
    val turbidity: Float = 0f, // 0.0 (crystal clear) to 1.0 (opaque cloudy)
    val bubbleIntensity: Float = 0f, // 0.0 to 1.0
    val precipitateColorHex: Long = 0xFFFFFFFF,
    val precipitateHeight: Float = 0f, // 0.0 to 1.0
    val steamIntensity: Float = 0f, // 0.0 to 1.0
    val isBoiling: Boolean = false,
    val flameActive: Boolean = false,
    val blastIntensity: Float = 0f, // 0.0 to 1.0 for explosion / energetic shockwave
    val blastFlashColorHex: Long = 0xFFFF5722,
    val gasType: String? = null, // "H2_gas", "CO2_gas", "O2_gas"
    val isPouringStreamActive: Boolean = false,
    val pourStreamProgress: Float = 0f,
    val surfaceRippleIntensity: Float = 0f,
    val isShattered: Boolean = false, // Beaker exploded into shards from blast
    val isCracked: Boolean = false, // Beaker cracked from extreme temperature
    val thermalStress: Float = 0f, // 0.0 to 1.0
    val smokeScreenAlpha: Float = 0f, // 0.0 to 1.0 full screen smoke overlay lasting 2 seconds
    val isRadioactive: Boolean = false,
    val radioactivityIntensity: Float = 0f,
    val sparkColors: List<Long> = emptyList(),
    val isFirecrackerBlast: Boolean = false
)

@Serializable
data class ContainerState(
    val id: String,
    val name: String,
    val equipmentType: EquipmentType = EquipmentType.BEAKER_250,
    val temperatureCelsius: Double = 20.0,
    val pressureAtm: Double = 1.0,
    val totalMassGrams: Double = 0.0,
    val totalVolumeMl: Double = 0.0,
    val pH: Double = 7.0,
    val pOH: Double = 7.0,
    val substances: List<SubstanceState> = emptyList(),
    val precipitates: List<SubstanceState> = emptyList(),
    val gases: List<SubstanceState> = emptyList(),
    val heatSourceWatts: Double = 0.0,
    val stirrerActive: Boolean = false,
    val lastReactionEvents: List<ReactionResult> = emptyList(),
    val visualState: VisualState = VisualState(),
    val isOverflown: Boolean = false,
    val isShattered: Boolean = false,
    val isCracked: Boolean = false,
    val isRadioactive: Boolean = false
) {
    val maxCapacityMl: Double get() = equipmentType.capacityMl
    val fillPercentage: Float get() = (totalVolumeMl / maxCapacityMl).toFloat().coerceIn(0f, 1f)

    fun getFormulaDisplayString(maxItems: Int = 4): String {
        val active = substances.filter { it.moles > 1e-6 }
        if (active.isEmpty()) return "Empty"
        val names = active.map { sub ->
            val chem = com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry.get(sub.chemicalId)
            chem?.formula ?: sub.chemicalId
        }
        return if (names.size <= maxItems) {
            names.joinToString(" + ")
        } else {
            names.take(maxItems).joinToString(" + ") + " + ..."
        }
    }
}

@Serializable
data class SimulationState(
    val containers: List<ContainerState> = listOf(ContainerState(id = "c1", name = "Beaker 1", equipmentType = EquipmentType.BEAKER_250)),
    val activeContainerId: String = "c1",
    val secondaryContainerId: String? = null,
    val simulationTimeSeconds: Double = 0.0,
    val isRunning: Boolean = true,
    val timeSpeedMultiplier: Float = 1.0f,
    val timelineEvents: List<ReactionResult> = emptyList()
) {
    val activeContainer: ContainerState?
        get() = containers.find { it.id == activeContainerId } ?: containers.firstOrNull()
}

