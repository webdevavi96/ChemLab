package com.webdevavi.chemlabsimulator.simulation.model

import kotlinx.serialization.Serializable

@Serializable
enum class Phase {
    SOLID,
    LIQUID,
    GAS,
    AQUEOUS,
    PRECIPITATE,
    DISSOLVED
}

@Serializable
enum class HazardType(
    val displayName: String,
    val description: String,
    val iconSymbol: String,
    val colorHex: Long
) {
    CORROSIVE("Corrosive", "Causes severe skin burns and eye damage", "⚠️", 0xFFEF4444),
    FLAMMABLE("Flammable", "Catches fire easily if exposed to heat/sparks", "🔥", 0xFFF97316),
    OXIDIZER("Oxidizer", "Can intensify fire and ignite combustibles", "⭕", 0xFFEAB308),
    TOXIC("Toxic", "Hazardous or fatal if swallowed, inhaled, or absorbed", "☠️", 0xFFA855F7),
    IRRITANT("Irritant", "Causes redness or mild irritation to skin/eyes/lungs", "⚡", 0xFFF59E0B),
    EXPLOSIVE("Explosive", "Risk of rapid explosion under heat or shock", "💥", 0xFFDC2626),
    ENVIRONMENTAL_HAZARD("Environmental", "Toxic to aquatic life with long lasting effects", "🐟", 0xFF10B981),
    RADIOACTIVE("Radioactive", "Emits ionizing radiation (alpha, beta, gamma rays)", "☢️", 0xFF22C55E)
}

@Serializable
data class Chemical(
    val id: String,
    val name: String,
    val formula: String,
    val molarMass: Double, // g/mol
    val density: Double, // g/mL or g/cm^3
    val meltingPointCelsius: Double,
    val boilingPointCelsius: Double,
    val specificHeatCapacity: Double, // J / (g * °C)
    val defaultPhase: Phase,
    val defaultColorHex: Long, // ARGB 0xAARRGGBB
    val intrinsicPh: Double? = null, // pH at 1.0 M aqueous solution if applicable
    val solubility: Double = Double.POSITIVE_INFINITY, // g / 100g H2O at 20°C
    val solubilityUnit: String = "g/100 mL H₂O",
    val pKa: Double? = null, // Acid dissociation constant
    val pKb: Double? = null, // Base dissociation constant
    val flammability: Int = 0, // NFPA 0-4
    val toxicityLevel: Int = 0, // NFPA 0-4
    val hazards: List<HazardType> = emptyList(),
    val category: String = "General",
    val description: String = "",
    val safetyInfo: String = "",
    val isSolvent: Boolean = false,
    val commonReactions: List<String> = emptyList(),
    val isElement: Boolean = false,
    val atomicNumber: Int? = null,
    val atomicWeight: Double? = null,
    val periodicGroup: Int? = null,
    val periodicPeriod: Int? = null,
    val elementCategory: String? = null,
    val isRadioactive: Boolean = false
)
