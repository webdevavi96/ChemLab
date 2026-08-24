package com.webdevavi.chemlabsimulator.simulation.model

import kotlinx.serialization.Serializable

@Serializable
enum class ReactionType(val displayName: String) {
    NEUTRALIZATION("Acid-Base Neutralization"),
    SINGLE_DISPLACEMENT("Single Displacement"),
    DOUBLE_DISPLACEMENT("Double Displacement (Precipitation)"),
    GAS_EVOLUTION("Gas Evolution"),
    DECOMPOSITION("Thermal / Catalytic Decomposition"),
    COMBUSTION("Combustion / Oxidation"),
    DISSOLUTION("Dissolution / Solvation"),
    NUCLEAR_DECAY("Nuclear Decay & Transmutation"),
    COMPLEXATION("Complex Formation & Double Salt")
}

@Serializable
data class ReactionCondition(
    val minTemperatureCelsius: Double = -273.15,
    val maxTemperatureCelsius: Double = 5000.0,
    val requiresAqueous: Boolean = false,
    val requiresHeatSource: Boolean = false,
    val catalystId: String? = null
)

@Serializable
data class ReactionRule(
    val id: String,
    val name: String,
    val type: ReactionType,
    val equationString: String,
    val reactants: Map<String, Double>, // chemicalId -> stoichiometric coefficient
    val products: Map<String, Double>,  // chemicalId -> stoichiometric coefficient
    val conditions: ReactionCondition = ReactionCondition(),
    val enthalpyDeltaH_kJ_mol: Double = 0.0, // negative = exothermic, positive = endothermic
    val gasProducedId: String? = null,
    val precipitateProducedId: String? = null,
    val isBlast: Boolean = false,
    val blastIntensity: Float = 0f,
    val sparkColors: List<Long> = emptyList(), // Hex colors of pyrotechnic sparks
    val description: String = "",
    val observation: String = "",
    val educationalNote: String = ""
)

@Serializable
data class ReactionResult(
    val reactionId: String,
    val reactionName: String,
    val equationString: String,
    val reactionType: ReactionType,
    val limitingReagentId: String,
    val limitingMolesReacted: Double,
    val extentOfReaction: Double, // moles of reaction progress
    val heatReleasedJoules: Double,
    val temperatureChangeCelsius: Double,
    val precipitateFormedId: String? = null,
    val precipitateGrams: Double = 0.0,
    val gasFormedId: String? = null,
    val gasMoles: Double = 0.0,
    val isBlast: Boolean = false,
    val blastIntensity: Float = 0f,
    val sparkColors: List<Long> = emptyList(),
    val description: String,
    val timestampSeconds: Double = 0.0
)

