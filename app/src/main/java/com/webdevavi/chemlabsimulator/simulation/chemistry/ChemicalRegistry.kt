package com.webdevavi.chemlabsimulator.simulation.chemistry

import com.webdevavi.chemlabsimulator.simulation.model.Chemical
import com.webdevavi.chemlabsimulator.simulation.model.HazardType
import com.webdevavi.chemlabsimulator.simulation.model.Phase

object ChemicalRegistry {
    private val chemicalsMap = mutableMapOf<String, Chemical>()

    init {
        registerAllElements()
        registerAllCompounds()
    }

    private fun registerAllElements() {
        register(
            Chemical(
                id = "H",
                name = "Hydrogen",
                formula = "H",
                molarMass = 1.008,
                density = 8.988e-05,
                meltingPointCelsius = -259.1,
                boilingPointCelsius = -252.9,
                specificHeatCapacity = 14.304,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 1,
                atomicWeight = 1.008,
                periodicGroup = 1,
                periodicPeriod = 1,
                isRadioactive = false,
                description = "Lightest and most abundant chemical element in the universe.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "He",
                name = "Helium",
                formula = "He",
                molarMass = 4.0026,
                density = 0.0001785,
                meltingPointCelsius = -272.2,
                boilingPointCelsius = -268.9,
                specificHeatCapacity = 5.193,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 2,
                atomicWeight = 4.0026,
                periodicGroup = 18,
                periodicPeriod = 1,
                isRadioactive = false,
                description = "Colorless, odorless inert noble gas with lowest boiling point.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Li",
                name = "Lithium",
                formula = "Li",
                molarMass = 6.94,
                density = 0.534,
                meltingPointCelsius = 180.5,
                boilingPointCelsius = 1342.0,
                specificHeatCapacity = 3.582,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFE2E8F0,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.CORROSIVE),
                category = "Elements",
                elementCategory = "Alkali Metal",
                isElement = true,
                atomicNumber = 3,
                atomicWeight = 6.94,
                periodicGroup = 1,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Lightest metal and least dense solid element. Reacts with water.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Be",
                name = "Beryllium",
                formula = "Be",
                molarMass = 9.0122,
                density = 1.85,
                meltingPointCelsius = 1287.0,
                boilingPointCelsius = 2470.0,
                specificHeatCapacity = 1.825,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Alkaline Earth Metal",
                isElement = true,
                atomicNumber = 4,
                atomicWeight = 9.0122,
                periodicGroup = 2,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Relatively rare metal in the universe, forms emeralds and beryl.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "B",
                name = "Boron",
                formula = "B",
                molarMass = 10.81,
                density = 2.34,
                meltingPointCelsius = 2076.0,
                boilingPointCelsius = 3927.0,
                specificHeatCapacity = 1.026,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF78716C,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Metalloid",
                isElement = true,
                atomicNumber = 5,
                atomicWeight = 10.81,
                periodicGroup = 13,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Low-abundance metalloid used in borosilicate glassware (Pyrex).",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "C",
                name = "Carbon",
                formula = "C",
                molarMass = 12.011,
                density = 2.26,
                meltingPointCelsius = 3550.0,
                boilingPointCelsius = 4027.0,
                specificHeatCapacity = 0.709,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF1E293B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 6,
                atomicWeight = 12.011,
                periodicGroup = 14,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Basis of all known organic life; exists as graphite and diamond.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "N",
                name = "Nitrogen",
                formula = "N",
                molarMass = 14.007,
                density = 0.00125,
                meltingPointCelsius = -210.0,
                boilingPointCelsius = -195.8,
                specificHeatCapacity = 1.04,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 7,
                atomicWeight = 14.007,
                periodicGroup = 15,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Makes up ~78% of Earth's atmosphere. Inert diatomic gas.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "O",
                name = "Oxygen",
                formula = "O",
                molarMass = 15.999,
                density = 0.00143,
                meltingPointCelsius = -218.8,
                boilingPointCelsius = -183.0,
                specificHeatCapacity = 0.918,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = listOf(HazardType.OXIDIZER),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 8,
                atomicWeight = 15.999,
                periodicGroup = 16,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Highly reactive nonmetal and oxidizing agent essential for life.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "F",
                name = "Fluorine",
                formula = "F",
                molarMass = 18.998,
                density = 0.0017,
                meltingPointCelsius = -219.7,
                boilingPointCelsius = -188.1,
                specificHeatCapacity = 0.824,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x55FEF08A,
                hazards = listOf(HazardType.CORROSIVE, HazardType.TOXIC, HazardType.OXIDIZER),
                category = "Elements",
                elementCategory = "Halogen",
                isElement = true,
                atomicNumber = 9,
                atomicWeight = 18.998,
                periodicGroup = 17,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Extremely reactive and electronegative pale yellow halogen gas.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ne",
                name = "Neon",
                formula = "Ne",
                molarMass = 20.18,
                density = 0.0009,
                meltingPointCelsius = -248.6,
                boilingPointCelsius = -246.1,
                specificHeatCapacity = 1.03,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 10,
                atomicWeight = 20.18,
                periodicGroup = 18,
                periodicPeriod = 2,
                isRadioactive = false,
                description = "Colorless noble gas that glows reddish-orange in high-voltage signs.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Na",
                name = "Sodium",
                formula = "Na",
                molarMass = 22.99,
                density = 0.968,
                meltingPointCelsius = 97.8,
                boilingPointCelsius = 883.0,
                specificHeatCapacity = 1.228,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.CORROSIVE),
                category = "Elements",
                elementCategory = "Alkali Metal",
                isElement = true,
                atomicNumber = 11,
                atomicWeight = 22.99,
                periodicGroup = 1,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Soft, silvery alkali metal that reacts vigorously with water.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Mg",
                name = "Magnesium",
                formula = "Mg",
                molarMass = 24.305,
                density = 1.738,
                meltingPointCelsius = 650.0,
                boilingPointCelsius = 1090.0,
                specificHeatCapacity = 1.023,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFE2E8F0,
                hazards = listOf(HazardType.FLAMMABLE),
                category = "Elements",
                elementCategory = "Alkaline Earth Metal",
                isElement = true,
                atomicNumber = 12,
                atomicWeight = 24.305,
                periodicGroup = 2,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Shiny gray metal that burns with a brilliant white flame in air.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Al",
                name = "Aluminium",
                formula = "Al",
                molarMass = 26.982,
                density = 2.7,
                meltingPointCelsius = 660.3,
                boilingPointCelsius = 2470.0,
                specificHeatCapacity = 0.897,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 13,
                atomicWeight = 26.982,
                periodicGroup = 13,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Abundant, lightweight, corrosion-resistant post-transition metal.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Si",
                name = "Silicon",
                formula = "Si",
                molarMass = 28.085,
                density = 2.33,
                meltingPointCelsius = 1414.0,
                boilingPointCelsius = 3265.0,
                specificHeatCapacity = 0.705,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Metalloid",
                isElement = true,
                atomicNumber = 14,
                atomicWeight = 28.085,
                periodicGroup = 14,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Semiconductor metalloid central to modern electronics and quartz.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "P",
                name = "Phosphorus",
                formula = "P",
                molarMass = 30.974,
                density = 1.82,
                meltingPointCelsius = 44.1,
                boilingPointCelsius = 280.5,
                specificHeatCapacity = 0.769,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFF87171,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 15,
                atomicWeight = 30.974,
                periodicGroup = 15,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Essential element for life (ATP, DNA); white and red allotropes.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "S",
                name = "Sulfur",
                formula = "S",
                molarMass = 32.06,
                density = 2.07,
                meltingPointCelsius = 115.2,
                boilingPointCelsius = 444.6,
                specificHeatCapacity = 0.71,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFACC15,
                hazards = listOf(HazardType.IRRITANT),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 16,
                atomicWeight = 32.06,
                periodicGroup = 16,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Bright yellow crystalline solid nonmetal forming sulfates and acid rain.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Cl",
                name = "Chlorine",
                formula = "Cl",
                molarMass = 35.45,
                density = 0.0032,
                meltingPointCelsius = -101.5,
                boilingPointCelsius = -34.0,
                specificHeatCapacity = 0.479,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x5584CC16,
                hazards = listOf(HazardType.CORROSIVE, HazardType.TOXIC, HazardType.OXIDIZER),
                category = "Elements",
                elementCategory = "Halogen",
                isElement = true,
                atomicNumber = 17,
                atomicWeight = 35.45,
                periodicGroup = 17,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Yellow-green toxic halogen gas used in disinfectants and synthesis.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ar",
                name = "Argon",
                formula = "Ar",
                molarMass = 39.948,
                density = 0.00178,
                meltingPointCelsius = -189.3,
                boilingPointCelsius = -185.8,
                specificHeatCapacity = 0.52,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 18,
                atomicWeight = 39.948,
                periodicGroup = 18,
                periodicPeriod = 3,
                isRadioactive = false,
                description = "Third most abundant atmospheric gas; inert shielding gas for welding.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "K",
                name = "Potassium",
                formula = "K",
                molarMass = 39.098,
                density = 0.862,
                meltingPointCelsius = 63.5,
                boilingPointCelsius = 759.0,
                specificHeatCapacity = 0.757,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFE2E8F0,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.CORROSIVE),
                category = "Elements",
                elementCategory = "Alkali Metal",
                isElement = true,
                atomicNumber = 19,
                atomicWeight = 39.098,
                periodicGroup = 1,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Soft silvery-white alkali metal that reacts explosively with water with violet flame.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ca",
                name = "Calcium",
                formula = "Ca",
                molarMass = 40.078,
                density = 1.55,
                meltingPointCelsius = 842.0,
                boilingPointCelsius = 1484.0,
                specificHeatCapacity = 0.647,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.IRRITANT),
                category = "Elements",
                elementCategory = "Alkaline Earth Metal",
                isElement = true,
                atomicNumber = 20,
                atomicWeight = 40.078,
                periodicGroup = 2,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Essential mineral for bones and teeth; reacts with water releasing H2.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Sc",
                name = "Scandium",
                formula = "Sc",
                molarMass = 44.956,
                density = 2.985,
                meltingPointCelsius = 1541.0,
                boilingPointCelsius = 2836.0,
                specificHeatCapacity = 0.568,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 21,
                atomicWeight = 44.956,
                periodicGroup = 3,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Light transition metal used in aerospace aluminum alloys.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ti",
                name = "Titanium",
                formula = "Ti",
                molarMass = 47.867,
                density = 4.506,
                meltingPointCelsius = 1668.0,
                boilingPointCelsius = 3287.0,
                specificHeatCapacity = 0.523,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 22,
                atomicWeight = 47.867,
                periodicGroup = 4,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "High-strength, low-density corrosion-resistant transition metal.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "V",
                name = "Vanadium",
                formula = "V",
                molarMass = 50.942,
                density = 6.11,
                meltingPointCelsius = 1910.0,
                boilingPointCelsius = 3407.0,
                specificHeatCapacity = 0.489,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 23,
                atomicWeight = 50.942,
                periodicGroup = 5,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Hard, ductile metal used in high-strength steel alloys and redox batteries.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Cr",
                name = "Chromium",
                formula = "Cr",
                molarMass = 51.996,
                density = 7.19,
                meltingPointCelsius = 1907.0,
                boilingPointCelsius = 2671.0,
                specificHeatCapacity = 0.449,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 24,
                atomicWeight = 51.996,
                periodicGroup = 6,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Lustrous, hard metal used in stainless steel and chrome plating.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Mn",
                name = "Manganese",
                formula = "Mn",
                molarMass = 54.938,
                density = 7.21,
                meltingPointCelsius = 1246.0,
                boilingPointCelsius = 2061.0,
                specificHeatCapacity = 0.479,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 25,
                atomicWeight = 54.938,
                periodicGroup = 7,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Transition metal essential in iron and steel production.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Fe",
                name = "Iron",
                formula = "Fe",
                molarMass = 55.845,
                density = 7.874,
                meltingPointCelsius = 1538.0,
                boilingPointCelsius = 2862.0,
                specificHeatCapacity = 0.449,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 26,
                atomicWeight = 55.845,
                periodicGroup = 8,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Most common element on Earth by mass; basis of steel and hemoglobin.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Co",
                name = "Cobalt",
                formula = "Co",
                molarMass = 58.933,
                density = 8.9,
                meltingPointCelsius = 1495.0,
                boilingPointCelsius = 2927.0,
                specificHeatCapacity = 0.421,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 27,
                atomicWeight = 58.933,
                periodicGroup = 9,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Ferromagnetic transition metal used in lithium-ion cathodes and magnets.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ni",
                name = "Nickel",
                formula = "Ni",
                molarMass = 58.693,
                density = 8.908,
                meltingPointCelsius = 1455.0,
                boilingPointCelsius = 2913.0,
                specificHeatCapacity = 0.444,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.IRRITANT),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 28,
                atomicWeight = 58.693,
                periodicGroup = 10,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Silvery-white lustrous metal with slight golden tinge, corrosion-resistant.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Cu",
                name = "Copper",
                formula = "Cu",
                molarMass = 63.546,
                density = 8.96,
                meltingPointCelsius = 1084.6,
                boilingPointCelsius = 2562.0,
                specificHeatCapacity = 0.385,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFB45309,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 29,
                atomicWeight = 63.546,
                periodicGroup = 11,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Soft, malleable metal with extremely high electrical and thermal conductivity.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Zn",
                name = "Zinc",
                formula = "Zn",
                molarMass = 65.38,
                density = 7.14,
                meltingPointCelsius = 419.5,
                boilingPointCelsius = 907.0,
                specificHeatCapacity = 0.388,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 30,
                atomicWeight = 65.38,
                periodicGroup = 12,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Slightly brittle transition metal used in galvanizing steel and brass.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ga",
                name = "Gallium",
                formula = "Ga",
                molarMass = 69.723,
                density = 5.91,
                meltingPointCelsius = 29.8,
                boilingPointCelsius = 2229.0,
                specificHeatCapacity = 0.371,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 31,
                atomicWeight = 69.723,
                periodicGroup = 13,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Soft metal that melts near room temperature in the palm of a hand.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ge",
                name = "Germanium",
                formula = "Ge",
                molarMass = 72.63,
                density = 5.323,
                meltingPointCelsius = 938.3,
                boilingPointCelsius = 2833.0,
                specificHeatCapacity = 0.32,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Metalloid",
                isElement = true,
                atomicNumber = 32,
                atomicWeight = 72.63,
                periodicGroup = 14,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Lustrous gray-white metalloid used in fiber optics and infrared lenses.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "As",
                name = "Arsenic",
                formula = "As",
                molarMass = 74.922,
                density = 5.776,
                meltingPointCelsius = 817.0,
                boilingPointCelsius = 614.0,
                specificHeatCapacity = 0.329,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = listOf(HazardType.TOXIC, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Elements",
                elementCategory = "Metalloid",
                isElement = true,
                atomicNumber = 33,
                atomicWeight = 74.922,
                periodicGroup = 15,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Infamous poisonous metalloid with complex allotropes.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Se",
                name = "Selenium",
                formula = "Se",
                molarMass = 78.971,
                density = 4.819,
                meltingPointCelsius = 221.0,
                boilingPointCelsius = 685.0,
                specificHeatCapacity = 0.321,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF334155,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Reactive Nonmetal",
                isElement = true,
                atomicNumber = 34,
                atomicWeight = 78.971,
                periodicGroup = 16,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Nonmetal with photovoltaic and photoconductive properties.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Br",
                name = "Bromine",
                formula = "Br",
                molarMass = 79.904,
                density = 3.1028,
                meltingPointCelsius = -7.2,
                boilingPointCelsius = 58.8,
                specificHeatCapacity = 0.474,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0xFF991B1B,
                hazards = listOf(HazardType.CORROSIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Halogen",
                isElement = true,
                atomicNumber = 35,
                atomicWeight = 79.904,
                periodicGroup = 17,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Dense reddish-brown volatile halogen liquid with pungent vapor.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Kr",
                name = "Krypton",
                formula = "Kr",
                molarMass = 83.798,
                density = 0.00375,
                meltingPointCelsius = -157.4,
                boilingPointCelsius = -153.2,
                specificHeatCapacity = 0.248,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 36,
                atomicWeight = 83.798,
                periodicGroup = 18,
                periodicPeriod = 4,
                isRadioactive = false,
                description = "Colorless noble gas used in fluorescent lamps and high-speed photography.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Rb",
                name = "Rubidium",
                formula = "Rb",
                molarMass = 85.468,
                density = 1.532,
                meltingPointCelsius = 39.3,
                boilingPointCelsius = 688.0,
                specificHeatCapacity = 0.363,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.CORROSIVE),
                category = "Elements",
                elementCategory = "Alkali Metal",
                isElement = true,
                atomicNumber = 37,
                atomicWeight = 85.468,
                periodicGroup = 1,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Soft, highly reactive alkali metal; ignites spontaneously in air.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Sr",
                name = "Strontium",
                formula = "Sr",
                molarMass = 87.62,
                density = 2.64,
                meltingPointCelsius = 777.0,
                boilingPointCelsius = 1382.0,
                specificHeatCapacity = 0.301,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.FLAMMABLE),
                category = "Elements",
                elementCategory = "Alkaline Earth Metal",
                isElement = true,
                atomicNumber = 38,
                atomicWeight = 87.62,
                periodicGroup = 2,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Soft silver-yellow metal that burns with intense crimson-red flame in fireworks.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Y",
                name = "Yttrium",
                formula = "Y",
                molarMass = 88.906,
                density = 4.472,
                meltingPointCelsius = 1526.0,
                boilingPointCelsius = 3345.0,
                specificHeatCapacity = 0.298,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 39,
                atomicWeight = 88.906,
                periodicGroup = 3,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Silvery-metallic transition metal used in LEDs and superconductors.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Zr",
                name = "Zirconium",
                formula = "Zr",
                molarMass = 91.224,
                density = 6.52,
                meltingPointCelsius = 1855.0,
                boilingPointCelsius = 4409.0,
                specificHeatCapacity = 0.278,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 40,
                atomicWeight = 91.224,
                periodicGroup = 4,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Lustrous, corrosion-resistant metal with very low neutron absorption.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Nb",
                name = "Niobium",
                formula = "Nb",
                molarMass = 92.906,
                density = 8.57,
                meltingPointCelsius = 2477.0,
                boilingPointCelsius = 4744.0,
                specificHeatCapacity = 0.265,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 41,
                atomicWeight = 92.906,
                periodicGroup = 5,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Ductile transition metal used in superconducting magnets and rocket nozzles.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Mo",
                name = "Molybdenum",
                formula = "Mo",
                molarMass = 95.95,
                density = 10.28,
                meltingPointCelsius = 2623.0,
                boilingPointCelsius = 4639.0,
                specificHeatCapacity = 0.251,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 42,
                atomicWeight = 95.95,
                periodicGroup = 6,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Refractory metal with extremely high melting point and strength.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Tc",
                name = "Technetium",
                formula = "Tc",
                molarMass = 98.0,
                density = 11.0,
                meltingPointCelsius = 2157.0,
                boilingPointCelsius = 4265.0,
                specificHeatCapacity = 0.24,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 43,
                atomicWeight = 98.0,
                periodicGroup = 7,
                periodicPeriod = 5,
                isRadioactive = true,
                description = "First artificially produced element; widely used in medical radioactive imaging.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Ru",
                name = "Ruthenium",
                formula = "Ru",
                molarMass = 101.07,
                density = 12.45,
                meltingPointCelsius = 2334.0,
                boilingPointCelsius = 4150.0,
                specificHeatCapacity = 0.238,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 44,
                atomicWeight = 101.07,
                periodicGroup = 8,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Rare platinum-group transition metal used in wear-resistant electrical contacts.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Rh",
                name = "Rhodium",
                formula = "Rh",
                molarMass = 102.91,
                density = 12.41,
                meltingPointCelsius = 1964.0,
                boilingPointCelsius = 3695.0,
                specificHeatCapacity = 0.243,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 45,
                atomicWeight = 102.91,
                periodicGroup = 9,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Extremely rare, noble metal prized for catalytic converters and corrosion resistance.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Pd",
                name = "Palladium",
                formula = "Pd",
                molarMass = 106.42,
                density = 12.023,
                meltingPointCelsius = 1554.9,
                boilingPointCelsius = 2963.0,
                specificHeatCapacity = 0.244,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 46,
                atomicWeight = 106.42,
                periodicGroup = 10,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Platinum-group metal capable of absorbing up to 900 times its volume of hydrogen.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ag",
                name = "Silver",
                formula = "Ag",
                molarMass = 107.87,
                density = 10.49,
                meltingPointCelsius = 961.8,
                boilingPointCelsius = 2162.0,
                specificHeatCapacity = 0.235,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFF1F5F9,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 47,
                atomicWeight = 107.87,
                periodicGroup = 11,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Precious metal with highest electrical conductivity, thermal conductivity, and reflectivity.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Cd",
                name = "Cadmium",
                formula = "Cd",
                molarMass = 112.41,
                density = 8.65,
                meltingPointCelsius = 321.1,
                boilingPointCelsius = 767.0,
                specificHeatCapacity = 0.232,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.TOXIC, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 48,
                atomicWeight = 112.41,
                periodicGroup = 12,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Soft, malleable bluish-white metal; toxic heavy metal.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "In",
                name = "Indium",
                formula = "In",
                molarMass = 114.82,
                density = 7.31,
                meltingPointCelsius = 156.6,
                boilingPointCelsius = 2072.0,
                specificHeatCapacity = 0.233,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 49,
                atomicWeight = 114.82,
                periodicGroup = 13,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Post-transition metal crucial for indium tin oxide (ITO) touchscreens.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Sn",
                name = "Tin",
                formula = "Sn",
                molarMass = 118.71,
                density = 7.265,
                meltingPointCelsius = 231.9,
                boilingPointCelsius = 2602.0,
                specificHeatCapacity = 0.228,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 50,
                atomicWeight = 118.71,
                periodicGroup = 14,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Malleable post-transition metal used in bronze, solder, and tin plating.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Sb",
                name = "Antimony",
                formula = "Sb",
                molarMass = 121.76,
                density = 6.697,
                meltingPointCelsius = 630.6,
                boilingPointCelsius = 1587.0,
                specificHeatCapacity = 0.207,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Metalloid",
                isElement = true,
                atomicNumber = 51,
                atomicWeight = 121.76,
                periodicGroup = 15,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Lustrous gray metalloid used in lead-acid batteries and flame retardants.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Te",
                name = "Tellurium",
                formula = "Te",
                molarMass = 127.6,
                density = 6.24,
                meltingPointCelsius = 449.5,
                boilingPointCelsius = 988.0,
                specificHeatCapacity = 0.202,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Metalloid",
                isElement = true,
                atomicNumber = 52,
                atomicWeight = 127.6,
                periodicGroup = 16,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Brittle, mildly toxic metalloid used in cadmium telluride solar panels.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "I",
                name = "Iodine",
                formula = "I",
                molarMass = 126.9,
                density = 4.933,
                meltingPointCelsius = 113.7,
                boilingPointCelsius = 184.3,
                specificHeatCapacity = 0.214,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF581C87,
                hazards = listOf(HazardType.CORROSIVE, HazardType.IRRITANT),
                category = "Elements",
                elementCategory = "Halogen",
                isElement = true,
                atomicNumber = 53,
                atomicWeight = 126.9,
                periodicGroup = 17,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Lustrous purple-black solid halogen that sublimates into violet vapor.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Xe",
                name = "Xenon",
                formula = "Xe",
                molarMass = 131.29,
                density = 0.005894,
                meltingPointCelsius = -111.8,
                boilingPointCelsius = -108.1,
                specificHeatCapacity = 0.158,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 54,
                atomicWeight = 131.29,
                periodicGroup = 18,
                periodicPeriod = 5,
                isRadioactive = false,
                description = "Heavy noble gas used in ion propulsion thrusters and strobe lamps.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Cs",
                name = "Caesium",
                formula = "Cs",
                molarMass = 132.91,
                density = 1.93,
                meltingPointCelsius = 28.5,
                boilingPointCelsius = 671.0,
                specificHeatCapacity = 0.242,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFCD34D,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.CORROSIVE),
                category = "Elements",
                elementCategory = "Alkali Metal",
                isElement = true,
                atomicNumber = 55,
                atomicWeight = 132.91,
                periodicGroup = 1,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Extremely reactive golden-silvery metal; standard for atomic clocks.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ba",
                name = "Barium",
                formula = "Ba",
                molarMass = 137.33,
                density = 3.51,
                meltingPointCelsius = 727.0,
                boilingPointCelsius = 1897.0,
                specificHeatCapacity = 0.204,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Alkaline Earth Metal",
                isElement = true,
                atomicNumber = 56,
                atomicWeight = 137.33,
                periodicGroup = 2,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Soft silvery alkaline earth metal that oxidizes rapidly in air; burns green.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "La",
                name = "Lanthanum",
                formula = "La",
                molarMass = 138.91,
                density = 6.162,
                meltingPointCelsius = 920.0,
                boilingPointCelsius = 3464.0,
                specificHeatCapacity = 0.195,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 57,
                atomicWeight = 138.91,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Prototype of the lanthanide series; used in carbon arc lighting and hybrid batteries.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ce",
                name = "Cerium",
                formula = "Ce",
                molarMass = 140.12,
                density = 6.77,
                meltingPointCelsius = 795.0,
                boilingPointCelsius = 3443.0,
                specificHeatCapacity = 0.192,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 58,
                atomicWeight = 140.12,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Most abundant rare-earth metal; component of catalytic converters and lighter flints.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Pr",
                name = "Praseodymium",
                formula = "Pr",
                molarMass = 140.91,
                density = 6.77,
                meltingPointCelsius = 935.0,
                boilingPointCelsius = 3520.0,
                specificHeatCapacity = 0.193,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 59,
                atomicWeight = 140.91,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Soft silvery lanthanide that produces intense yellow-orange glass colorant.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Nd",
                name = "Neodymium",
                formula = "Nd",
                molarMass = 144.24,
                density = 7.01,
                meltingPointCelsius = 1024.0,
                boilingPointCelsius = 3074.0,
                specificHeatCapacity = 0.19,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 60,
                atomicWeight = 144.24,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Rare earth element used to manufacture the strongest permanent magnets (NdFeB).",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Pm",
                name = "Promethium",
                formula = "Pm",
                molarMass = 145.0,
                density = 7.26,
                meltingPointCelsius = 1042.0,
                boilingPointCelsius = 3000.0,
                specificHeatCapacity = 0.18,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 61,
                atomicWeight = 145.0,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = true,
                description = "Radioactive lanthanide with no stable isotopes; used in atomic batteries.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Sm",
                name = "Samarium",
                formula = "Sm",
                molarMass = 150.36,
                density = 7.52,
                meltingPointCelsius = 1072.0,
                boilingPointCelsius = 1794.0,
                specificHeatCapacity = 0.197,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 62,
                atomicWeight = 150.36,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Lanthanide metal used in high-temperature samarium-cobalt magnets.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Eu",
                name = "Europium",
                formula = "Eu",
                molarMass = 151.96,
                density = 5.264,
                meltingPointCelsius = 826.0,
                boilingPointCelsius = 1529.0,
                specificHeatCapacity = 0.182,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 63,
                atomicWeight = 151.96,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Most reactive lanthanide; red and blue phosphors in screens and banknotes.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Gd",
                name = "Gadolinium",
                formula = "Gd",
                molarMass = 157.25,
                density = 7.9,
                meltingPointCelsius = 1312.0,
                boilingPointCelsius = 3273.0,
                specificHeatCapacity = 0.236,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 64,
                atomicWeight = 157.25,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Ferromagnetic rare earth metal used as MRI contrast agent.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Tb",
                name = "Terbium",
                formula = "Tb",
                molarMass = 158.93,
                density = 8.23,
                meltingPointCelsius = 1356.0,
                boilingPointCelsius = 3230.0,
                specificHeatCapacity = 0.182,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 65,
                atomicWeight = 158.93,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Silvery-white rare earth metal used in green phosphors and magneto-optical discs.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Dy",
                name = "Dysprosium",
                formula = "Dy",
                molarMass = 162.5,
                density = 8.54,
                meltingPointCelsius = 1407.0,
                boilingPointCelsius = 2567.0,
                specificHeatCapacity = 0.17,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 66,
                atomicWeight = 162.5,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "High magnetic susceptibility metal used in wind turbines and EV electric motors.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ho",
                name = "Holmium",
                formula = "Ho",
                molarMass = 164.93,
                density = 8.79,
                meltingPointCelsius = 1461.0,
                boilingPointCelsius = 2720.0,
                specificHeatCapacity = 0.165,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 67,
                atomicWeight = 164.93,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Has the highest magnetic permeability of any element; used in medical lasers.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Er",
                name = "Erbium",
                formula = "Er",
                molarMass = 167.26,
                density = 9.066,
                meltingPointCelsius = 1529.0,
                boilingPointCelsius = 2868.0,
                specificHeatCapacity = 0.168,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 68,
                atomicWeight = 167.26,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Pink-colored lanthanide ion used in erbium-doped optical fiber amplifiers (EDFA).",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Tm",
                name = "Thulium",
                formula = "Tm",
                molarMass = 168.93,
                density = 9.32,
                meltingPointCelsius = 1545.0,
                boilingPointCelsius = 1950.0,
                specificHeatCapacity = 0.16,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 69,
                atomicWeight = 168.93,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Second least abundant lanthanide; portable X-ray sources.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Yb",
                name = "Ytterbium",
                formula = "Yb",
                molarMass = 173.05,
                density = 6.9,
                meltingPointCelsius = 824.0,
                boilingPointCelsius = 1196.0,
                specificHeatCapacity = 0.155,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 70,
                atomicWeight = 173.05,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Soft, malleable lanthanide used in atomic clocks and stainless steel stress sensors.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Lu",
                name = "Lutetium",
                formula = "Lu",
                molarMass = 174.97,
                density = 9.841,
                meltingPointCelsius = 1663.0,
                boilingPointCelsius = 3402.0,
                specificHeatCapacity = 0.154,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Lanthanide",
                isElement = true,
                atomicNumber = 71,
                atomicWeight = 174.97,
                periodicGroup = 3,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Hardest and densest lanthanide; used in PET scan detectors.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Hf",
                name = "Hafnium",
                formula = "Hf",
                molarMass = 178.49,
                density = 13.31,
                meltingPointCelsius = 2233.0,
                boilingPointCelsius = 4603.0,
                specificHeatCapacity = 0.144,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 72,
                atomicWeight = 178.49,
                periodicGroup = 4,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Corrosion-resistant transition metal used in nuclear control rods and microchips.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ta",
                name = "Tantalum",
                formula = "Ta",
                molarMass = 180.95,
                density = 16.69,
                meltingPointCelsius = 3017.0,
                boilingPointCelsius = 5458.0,
                specificHeatCapacity = 0.14,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 73,
                atomicWeight = 180.95,
                periodicGroup = 5,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Extremely corrosion-resistant metal used in smartphone capacitors and implants.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "W",
                name = "Tungsten",
                formula = "W",
                molarMass = 183.84,
                density = 19.25,
                meltingPointCelsius = 3422.0,
                boilingPointCelsius = 5555.0,
                specificHeatCapacity = 0.132,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 74,
                atomicWeight = 183.84,
                periodicGroup = 6,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Has the highest melting point of all elements (3422°C); used in incandescent filaments.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Re",
                name = "Rhenium",
                formula = "Re",
                molarMass = 186.21,
                density = 21.02,
                meltingPointCelsius = 3186.0,
                boilingPointCelsius = 5596.0,
                specificHeatCapacity = 0.137,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 75,
                atomicWeight = 186.21,
                periodicGroup = 7,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Extremely dense, high-melting metal used in jet engine combustion chambers.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Os",
                name = "Osmium",
                formula = "Os",
                molarMass = 190.23,
                density = 22.59,
                meltingPointCelsius = 3033.0,
                boilingPointCelsius = 5012.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF334155,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 76,
                atomicWeight = 190.23,
                periodicGroup = 8,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Densest naturally occurring element (22.59 g/cm³); blue-gray platinum group metal.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Ir",
                name = "Iridium",
                formula = "Ir",
                molarMass = 192.22,
                density = 22.56,
                meltingPointCelsius = 2446.0,
                boilingPointCelsius = 4428.0,
                specificHeatCapacity = 0.131,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 77,
                atomicWeight = 192.22,
                periodicGroup = 9,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Most corrosion-resistant metal known; standard kilogram reference mass.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Pt",
                name = "Platinum",
                formula = "Pt",
                molarMass = 195.08,
                density = 21.45,
                meltingPointCelsius = 1768.3,
                boilingPointCelsius = 3825.0,
                specificHeatCapacity = 0.133,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFF1F5F9,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 78,
                atomicWeight = 195.08,
                periodicGroup = 10,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Noble, ductile precious metal widely used in vehicle catalytic converters and jewelry.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Au",
                name = "Gold",
                formula = "Au",
                molarMass = 196.97,
                density = 19.3,
                meltingPointCelsius = 1064.2,
                boilingPointCelsius = 2970.0,
                specificHeatCapacity = 0.129,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFBBF24,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 79,
                atomicWeight = 196.97,
                periodicGroup = 11,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Unreactive noble yellow metal, highly malleable and prized throughout human history.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Hg",
                name = "Mercury",
                formula = "Hg",
                molarMass = 200.59,
                density = 13.534,
                meltingPointCelsius = -38.8,
                boilingPointCelsius = 356.7,
                specificHeatCapacity = 0.14,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.TOXIC, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 80,
                atomicWeight = 200.59,
                periodicGroup = 12,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Heavy silvery metal that is liquid at room temperature; toxic vapors.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Tl",
                name = "Thallium",
                formula = "Tl",
                molarMass = 204.38,
                density = 11.85,
                meltingPointCelsius = 304.0,
                boilingPointCelsius = 1473.0,
                specificHeatCapacity = 0.129,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 81,
                atomicWeight = 204.38,
                periodicGroup = 13,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Soft gray post-transition metal; extremely toxic historically known as the poisoner's poison.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Pb",
                name = "Lead",
                formula = "Pb",
                molarMass = 207.2,
                density = 11.34,
                meltingPointCelsius = 327.5,
                boilingPointCelsius = 1749.0,
                specificHeatCapacity = 0.129,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = listOf(HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 82,
                atomicWeight = 207.2,
                periodicGroup = 14,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "Heavy, soft, malleable metal with high density used in radiation shielding and batteries.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Bi",
                name = "Bismuth",
                formula = "Bi",
                molarMass = 208.98,
                density = 9.78,
                meltingPointCelsius = 271.4,
                boilingPointCelsius = 1564.0,
                specificHeatCapacity = 0.122,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = emptyList(),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 83,
                atomicWeight = 208.98,
                periodicGroup = 15,
                periodicPeriod = 6,
                isRadioactive = false,
                description = "High-density post-transition metal that forms iridescent rainbow oxide crystals.",
                safetyInfo = "Standard laboratory chemical safety protocol."
            )
        )
        register(
            Chemical(
                id = "Po",
                name = "Polonium",
                formula = "Po",
                molarMass = 209.0,
                density = 9.196,
                meltingPointCelsius = 254.0,
                boilingPointCelsius = 962.0,
                specificHeatCapacity = 0.125,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 84,
                atomicWeight = 209.0,
                periodicGroup = 16,
                periodicPeriod = 6,
                isRadioactive = true,
                description = "Rare and highly radioactive alpha emitter discovered by Marie and Pierre Curie.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "At",
                name = "Astatine",
                formula = "At",
                molarMass = 210.0,
                density = 7.0,
                meltingPointCelsius = 302.0,
                boilingPointCelsius = 337.0,
                specificHeatCapacity = 0.14,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF1E293B,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Halogen",
                isElement = true,
                atomicNumber = 85,
                atomicWeight = 210.0,
                periodicGroup = 17,
                periodicPeriod = 6,
                isRadioactive = true,
                description = "Extremely rare radioactive halogen; less than 1 gram exists in Earth's crust at any time.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Rn",
                name = "Radon",
                formula = "Rn",
                molarMass = 222.0,
                density = 0.00973,
                meltingPointCelsius = -71.0,
                boilingPointCelsius = -61.7,
                specificHeatCapacity = 0.094,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 86,
                atomicWeight = 222.0,
                periodicGroup = 18,
                periodicPeriod = 6,
                isRadioactive = true,
                description = "Radioactive, colorless, odorless noble gas produced by the decay of radium.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Fr",
                name = "Francium",
                formula = "Fr",
                molarMass = 223.0,
                density = 2.48,
                meltingPointCelsius = 27.0,
                boilingPointCelsius = 677.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.CORROSIVE),
                category = "Elements",
                elementCategory = "Alkali Metal",
                isElement = true,
                atomicNumber = 87,
                atomicWeight = 223.0,
                periodicGroup = 1,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Extremely unstable and radioactive alkali metal; second-rarest naturally occurring element.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Ra",
                name = "Radium",
                formula = "Ra",
                molarMass = 226.0,
                density = 5.5,
                meltingPointCelsius = 700.0,
                boilingPointCelsius = 1737.0,
                specificHeatCapacity = 0.094,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFE2E8F0,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Alkaline Earth Metal",
                isElement = true,
                atomicNumber = 88,
                atomicWeight = 226.0,
                periodicGroup = 2,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Intensely radioactive alkaline earth metal that glows faintly blue in the dark.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Ac",
                name = "Actinium",
                formula = "Ac",
                molarMass = 227.0,
                density = 10.07,
                meltingPointCelsius = 1050.0,
                boilingPointCelsius = 3198.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 89,
                atomicWeight = 227.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Radioactive actinide metal that glows blue due to intense alpha radiation ionizing air.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Th",
                name = "Thorium",
                formula = "Th",
                molarMass = 232.04,
                density = 11.724,
                meltingPointCelsius = 1750.0,
                boilingPointCelsius = 4788.0,
                specificHeatCapacity = 0.118,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 90,
                atomicWeight = 232.04,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Naturally occurring weakly radioactive actinide fertile nuclear fuel source.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Pa",
                name = "Protactinium",
                formula = "Pa",
                molarMass = 231.04,
                density = 15.37,
                meltingPointCelsius = 1568.0,
                boilingPointCelsius = 4027.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 91,
                atomicWeight = 231.04,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Dense, silvery-gray actinide metal that is superconducting at very low temperatures.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "U",
                name = "Uranium",
                formula = "U",
                molarMass = 238.03,
                density = 19.1,
                meltingPointCelsius = 1132.2,
                boilingPointCelsius = 4131.0,
                specificHeatCapacity = 0.116,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 92,
                atomicWeight = 238.03,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Heavy radioactive actinide; primary fissile fuel for nuclear reactors and weapons.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Np",
                name = "Neptunium",
                formula = "Np",
                molarMass = 237.0,
                density = 20.45,
                meltingPointCelsius = 644.0,
                boilingPointCelsius = 3902.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF475569,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 93,
                atomicWeight = 237.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "First synthetic transuranic element, formed as a byproduct in nuclear reactors.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Pu",
                name = "Plutonium",
                formula = "Pu",
                molarMass = 244.0,
                density = 19.86,
                meltingPointCelsius = 639.4,
                boilingPointCelsius = 3228.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF334155,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC, HazardType.EXPLOSIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 94,
                atomicWeight = 244.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Fissile transuranic actinide used in nuclear weapons and RTG deep space power supplies.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Am",
                name = "Americium",
                formula = "Am",
                molarMass = 243.0,
                density = 12.0,
                meltingPointCelsius = 1176.0,
                boilingPointCelsius = 2607.0,
                specificHeatCapacity = 0.125,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 95,
                atomicWeight = 243.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Synthetic actinide used in commercial household smoke detector ionization chambers.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Cm",
                name = "Curium",
                formula = "Cm",
                molarMass = 247.0,
                density = 13.51,
                meltingPointCelsius = 1345.0,
                boilingPointCelsius = 3110.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 96,
                atomicWeight = 247.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Highly radioactive actinide named after Marie and Pierre Curie; heat source for space.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Bk",
                name = "Berkelium",
                formula = "Bk",
                molarMass = 247.0,
                density = 14.78,
                meltingPointCelsius = 986.0,
                boilingPointCelsius = 2627.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 97,
                atomicWeight = 247.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Synthetic actinide discovered at the University of California, Berkeley.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Cf",
                name = "Californium",
                formula = "Cf",
                molarMass = 251.0,
                density = 15.1,
                meltingPointCelsius = 900.0,
                boilingPointCelsius = 1470.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 98,
                atomicWeight = 251.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Strong neutron emitter used to start nuclear reactors and analyze coal/minerals.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Es",
                name = "Einsteinium",
                formula = "Es",
                molarMass = 252.0,
                density = 8.84,
                meltingPointCelsius = 860.0,
                boilingPointCelsius = 996.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 99,
                atomicWeight = 252.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Discovered in the debris of the first thermonuclear bomb explosion (Ivy Mike).",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Fm",
                name = "Fermium",
                formula = "Fm",
                molarMass = 257.0,
                density = 9.7,
                meltingPointCelsius = 1527.0,
                boilingPointCelsius = 2000.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 100,
                atomicWeight = 257.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Heaviest element that can be formed by neutron bombardment of lighter elements.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Md",
                name = "Mendelevium",
                formula = "Md",
                molarMass = 258.0,
                density = 10.3,
                meltingPointCelsius = 827.0,
                boilingPointCelsius = 1100.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 101,
                atomicWeight = 258.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Named in honor of Dmitri Mendeleev, father of the periodic table.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "No",
                name = "Nobelium",
                formula = "No",
                molarMass = 259.0,
                density = 9.9,
                meltingPointCelsius = 827.0,
                boilingPointCelsius = 1100.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 102,
                atomicWeight = 259.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Radioactive actinide synthesized by bombarding curium with carbon ions.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Lr",
                name = "Lawrencium",
                formula = "Lr",
                molarMass = 266.0,
                density = 14.4,
                meltingPointCelsius = 1627.0,
                boilingPointCelsius = 2000.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Actinide",
                isElement = true,
                atomicNumber = 103,
                atomicWeight = 266.0,
                periodicGroup = 3,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Final member of the actinide series, named after Ernest Lawrence (cyclotron inventor).",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Rf",
                name = "Rutherfordium",
                formula = "Rf",
                molarMass = 267.0,
                density = 23.2,
                meltingPointCelsius = 2100.0,
                boilingPointCelsius = 5500.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 104,
                atomicWeight = 267.0,
                periodicGroup = 4,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "First transactinide superheavy element; half-life of ~1.3 hours.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Db",
                name = "Dubnium",
                formula = "Db",
                molarMass = 268.0,
                density = 29.3,
                meltingPointCelsius = 2200.0,
                boilingPointCelsius = 5600.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 105,
                atomicWeight = 268.0,
                periodicGroup = 5,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Synthetic superheavy transition metal named after Dubna, Russia.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Sg",
                name = "Seaborgium",
                formula = "Sg",
                molarMass = 269.0,
                density = 35.0,
                meltingPointCelsius = 2300.0,
                boilingPointCelsius = 5700.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 106,
                atomicWeight = 269.0,
                periodicGroup = 6,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Named after Glenn T. Seaborg who discovered ten transuranic elements.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Bh",
                name = "Bohrium",
                formula = "Bh",
                molarMass = 270.0,
                density = 37.1,
                meltingPointCelsius = 2400.0,
                boilingPointCelsius = 5800.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 107,
                atomicWeight = 270.0,
                periodicGroup = 7,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Synthetic superheavy element named after Danish physicist Niels Bohr.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Hs",
                name = "Hassium",
                formula = "Hs",
                molarMass = 277.0,
                density = 40.7,
                meltingPointCelsius = 2500.0,
                boilingPointCelsius = 5900.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 108,
                atomicWeight = 277.0,
                periodicGroup = 8,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Predicted to be the densest known element (~40.7 g/cm³); volatile tetroxide.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Mt",
                name = "Meitnerium",
                formula = "Mt",
                molarMass = 278.0,
                density = 37.4,
                meltingPointCelsius = 2600.0,
                boilingPointCelsius = 6000.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 109,
                atomicWeight = 278.0,
                periodicGroup = 9,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Named after Austrian physicist Lise Meitner, discoverer of nuclear fission.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Ds",
                name = "Darmstadtium",
                formula = "Ds",
                molarMass = 281.0,
                density = 34.8,
                meltingPointCelsius = 2700.0,
                boilingPointCelsius = 6100.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 110,
                atomicWeight = 281.0,
                periodicGroup = 10,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Synthetic superheavy transition metal discovered in Darmstadt, Germany.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Rg",
                name = "Roentgenium",
                formula = "Rg",
                molarMass = 282.0,
                density = 28.7,
                meltingPointCelsius = 2800.0,
                boilingPointCelsius = 6200.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 111,
                atomicWeight = 282.0,
                periodicGroup = 11,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Named after Wilhelm Röntgen, discoverer of X-rays; group 11 coin metal homologue.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Cn",
                name = "Copernicium",
                formula = "Cn",
                molarMass = 285.0,
                density = 23.7,
                meltingPointCelsius = 283.0,
                boilingPointCelsius = 340.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0xFF94A3B8,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Transition Metal",
                isElement = true,
                atomicNumber = 112,
                atomicWeight = 285.0,
                periodicGroup = 12,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Extremely volatile superheavy element; predicted to be a metallic gas or liquid.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Nh",
                name = "Nihonium",
                formula = "Nh",
                molarMass = 286.0,
                density = 16.0,
                meltingPointCelsius = 430.0,
                boilingPointCelsius = 1130.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 113,
                atomicWeight = 286.0,
                periodicGroup = 13,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "First element discovered in Asia at RIKEN, Japan (Nihon).",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Fl",
                name = "Flerovium",
                formula = "Fl",
                molarMass = 289.0,
                density = 14.0,
                meltingPointCelsius = 200.0,
                boilingPointCelsius = 380.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 114,
                atomicWeight = 289.0,
                periodicGroup = 14,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Superheavy post-transition metal in group 14; island of stability candidate.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Mc",
                name = "Moscovium",
                formula = "Mc",
                molarMass = 290.0,
                density = 13.5,
                meltingPointCelsius = 400.0,
                boilingPointCelsius = 1100.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 115,
                atomicWeight = 290.0,
                periodicGroup = 15,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Extremely radioactive synthetic element named after Moscow Oblast.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Lv",
                name = "Livermorium",
                formula = "Lv",
                molarMass = 293.0,
                density = 12.9,
                meltingPointCelsius = 435.0,
                boilingPointCelsius = 1085.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF64748B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Post-transition Metal",
                isElement = true,
                atomicNumber = 116,
                atomicWeight = 293.0,
                periodicGroup = 16,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Synthetic superheavy chalcogen named after Lawrence Livermore National Laboratory.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Ts",
                name = "Tennessine",
                formula = "Ts",
                molarMass = 294.0,
                density = 7.2,
                meltingPointCelsius = 450.0,
                boilingPointCelsius = 610.0,
                specificHeatCapacity = 0.14,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF334155,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Halogen",
                isElement = true,
                atomicNumber = 117,
                atomicWeight = 294.0,
                periodicGroup = 17,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Superheavy halogen named after the state of Tennessee (Oak Ridge, Vanderbilt).",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
        register(
            Chemical(
                id = "Og",
                name = "Oganesson",
                formula = "Og",
                molarMass = 294.0,
                density = 4.9,
                meltingPointCelsius = 52.0,
                boilingPointCelsius = 177.0,
                specificHeatCapacity = 0.15,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF1E293B,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Elements",
                elementCategory = "Noble Gas",
                isElement = true,
                atomicNumber = 118,
                atomicWeight = 294.0,
                periodicGroup = 18,
                periodicPeriod = 7,
                isRadioactive = true,
                description = "Heaviest known element in the periodic table (Z=118); predicted semiconductor noble gas.",
                safetyInfo = "Handle radioactive material with strict radiation shielding protocol."
            )
        )
    }

    private fun registerAllCompounds() {
        register(
            Chemical(
                id = "HCl",
                name = "Hydrochloric Acid (1.0 M)",
                formula = "HCl",
                molarMass = 36.46,
                density = 1.02,
                meltingPointCelsius = -30.0,
                boilingPointCelsius = 108.5,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x55E2E8F0,
                intrinsicPh = 1.0,
                pKa = -6.3,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE, HazardType.TOXIC),
                category = "Acids",
                isSolvent = false,
                description = "Strong monoprotic mineral acid. Dissociates completely in water.",
                safetyInfo = "Causes severe skin burns and serious eye damage.",
                commonReactions = listOf("HCl + NaOH → NaCl + H₂O", "Zn + 2HCl → ZnCl₂ + H₂↑", "NaHCO₃ + HCl → NaCl + H₂O + CO₂↑")
            )
        )
        register(
            Chemical(
                id = "H2SO4",
                name = "Sulfuric Acid (1.0 M)",
                formula = "H₂SO₄",
                molarMass = 98.08,
                density = 1.06,
                meltingPointCelsius = 10.3,
                boilingPointCelsius = 337.0,
                specificHeatCapacity = 3.9,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x55CBD5E1,
                intrinsicPh = 0.3,
                pKa = -3.0,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE, HazardType.OXIDIZER),
                category = "Acids",
                isSolvent = false,
                description = "Strong diprotic acid widely used in industrial synthesis.",
                safetyInfo = "Highly corrosive and exothermic on dilution.",
                commonReactions = listOf("H₂SO₄ + 2NaOH → Na₂SO₄ + 2H₂O")
            )
        )
        register(
            Chemical(
                id = "HNO3",
                name = "Nitric Acid (1.0 M)",
                formula = "HNO₃",
                molarMass = 63.01,
                density = 1.05,
                meltingPointCelsius = -42.0,
                boilingPointCelsius = 83.0,
                specificHeatCapacity = 4.0,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x55E0E7FF,
                intrinsicPh = 1.0,
                pKa = -1.4,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE, HazardType.OXIDIZER),
                category = "Acids",
                isSolvent = false,
                description = "Strong oxidizing acid capable of dissolving noble metals with HCl.",
                safetyInfo = "Strong oxidizer; produces toxic NO2 gas when heated with metals.",
                commonReactions = listOf("HNO₃ + KOH → KNO₃ + H₂O")
            )
        )
        register(
            Chemical(
                id = "CH3COOH",
                name = "Acetic Acid (0.1 M / Vinegar)",
                formula = "CH₃COOH",
                molarMass = 60.05,
                density = 1.01,
                meltingPointCelsius = 16.6,
                boilingPointCelsius = 118.1,
                specificHeatCapacity = 4.18,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x33CBD5E1,
                intrinsicPh = 2.87,
                pKa = 4.76,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Acids",
                isSolvent = false,
                description = "Weak carboxylic organic acid with distinctive vinegar aroma.",
                safetyInfo = "Skin and respiratory irritant.",
                commonReactions = listOf("CH₃COOH + NaOH → CH₃COONa + H₂O", "CH₃COOH + NaHCO₃ → CH₃COONa + H₂O + CO₂↑")
            )
        )
        register(
            Chemical(
                id = "H3PO4",
                name = "Phosphoric Acid (1.0 M)",
                formula = "H₃PO₄",
                molarMass = 98.0,
                density = 1.08,
                meltingPointCelsius = 42.3,
                boilingPointCelsius = 158.0,
                specificHeatCapacity = 3.8,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44CBD5E1,
                intrinsicPh = 1.5,
                pKa = 2.15,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE),
                category = "Acids",
                isSolvent = false,
                description = "Triprotic mineral acid used in rust removal, fertilizers, and buffers.",
                safetyInfo = "Causes skin irritation and eye damage.",
                commonReactions = listOf("H₃PO₄ + 3NaOH → Na₃PO₄ + 3H₂O")
            )
        )
        register(
            Chemical(
                id = "NaOH",
                name = "Sodium Hydroxide (1.0 M)",
                formula = "NaOH",
                molarMass = 40.0,
                density = 1.04,
                meltingPointCelsius = 318.0,
                boilingPointCelsius = 1388.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x55E2E8F0,
                intrinsicPh = 14.0,
                pKa = null,
                pKb = 0.2,
                hazards = listOf(HazardType.CORROSIVE),
                category = "Bases",
                isSolvent = false,
                description = "Strong caustic base that dissociates completely into Na⁺ and OH⁻ ions.",
                safetyInfo = "Causes rapid chemical burns; exothermic dissolution.",
                commonReactions = listOf("HCl + NaOH → NaCl + H₂O", "CuSO₄ + 2NaOH → Cu(OH)₂↓ + Na₂SO₄", "FeCl₃ + 3NaOH → Fe(OH)₃↓ + 3NaCl")
            )
        )
        register(
            Chemical(
                id = "KOH",
                name = "Potassium Hydroxide (1.0 M)",
                formula = "KOH",
                molarMass = 56.11,
                density = 1.05,
                meltingPointCelsius = 360.0,
                boilingPointCelsius = 1327.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x55E2E8F0,
                intrinsicPh = 14.0,
                pKa = null,
                pKb = 0.1,
                hazards = listOf(HazardType.CORROSIVE),
                category = "Bases",
                isSolvent = false,
                description = "Strong alkali base widely used in alkaline batteries and biodiesel synthesis.",
                safetyInfo = "Severe burn hazard.",
                commonReactions = listOf("HNO₃ + KOH → KNO₃ + H₂O")
            )
        )
        register(
            Chemical(
                id = "NH4OH",
                name = "Ammonium Hydroxide (1.0 M)",
                formula = "NH₄OH",
                molarMass = 35.05,
                density = 0.98,
                meltingPointCelsius = -57.5,
                boilingPointCelsius = 37.7,
                specificHeatCapacity = 4.2,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44E2E8F0,
                intrinsicPh = 11.6,
                pKa = null,
                pKb = 4.75,
                hazards = listOf(HazardType.CORROSIVE, HazardType.TOXIC),
                category = "Bases",
                isSolvent = false,
                description = "Weak aqueous base formed by dissolving ammonia gas in water.",
                safetyInfo = "Pungent choking odor. Work under fume hood.",
                commonReactions = listOf("NH₄OH + HCl → NH₄Cl + H₂O")
            )
        )
        register(
            Chemical(
                id = "Ca_OH_2",
                name = "Calcium Hydroxide (Limewater)",
                formula = "Ca(OH)₂",
                molarMass = 74.09,
                density = 1.01,
                meltingPointCelsius = 580.0,
                boilingPointCelsius = 2850.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44CBD5E1,
                intrinsicPh = 12.4,
                pKa = null,
                pKb = 1.4,
                hazards = listOf(HazardType.IRRITANT, HazardType.CORROSIVE),
                category = "Bases",
                isSolvent = false,
                description = "Slightly soluble alkaline solution used for the classical carbon dioxide limewater test.",
                safetyInfo = "Irritant to skin and eyes.",
                commonReactions = listOf("Ca(OH)₂ + CO₂ → CaCO₃↓ + H₂O")
            )
        )
        register(
            Chemical(
                id = "NaCl",
                name = "Sodium Chloride",
                formula = "NaCl",
                molarMass = 58.44,
                density = 2.16,
                meltingPointCelsius = 801.0,
                boilingPointCelsius = 1465.0,
                specificHeatCapacity = 0.864,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Table salt. Forms neutral aqueous solutions.",
                safetyInfo = "Non-toxic culinary and laboratory reagent.",
                commonReactions = listOf("AgNO₃ + NaCl → AgCl↓ + NaNO₃")
            )
        )
        register(
            Chemical(
                id = "CuSO4",
                name = "Copper(II) Sulfate Pentahydrate",
                formula = "CuSO₄·5H₂O",
                molarMass = 249.68,
                density = 2.286,
                meltingPointCelsius = 110.0,
                boilingPointCelsius = 650.0,
                specificHeatCapacity = 1.15,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF0284C7,
                intrinsicPh = 4.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Bright azure-blue crystalline inorganic salt forming brilliant blue aqueous solutions.",
                safetyInfo = "Toxic to aquatic organisms.",
                commonReactions = listOf("CuSO₄ + 2NaOH → Cu(OH)₂↓ + Na₂SO₄", "Fe + CuSO₄ → FeSO₄ + Cu↓", "CuSO₄ + BaCl₂ → BaSO₄↓ + CuCl₂")
            )
        )
        register(
            Chemical(
                id = "AgNO3",
                name = "Silver Nitrate (0.1 M)",
                formula = "AgNO₃",
                molarMass = 169.87,
                density = 1.03,
                meltingPointCelsius = 212.0,
                boilingPointCelsius = 444.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44E0E7FF,
                intrinsicPh = 6.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE, HazardType.OXIDIZER, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Soluble silver salt used for halide detection via instant precipitation.",
                safetyInfo = "Causes black stains on skin; light-sensitive oxidizer.",
                commonReactions = listOf("AgNO₃ + NaCl → AgCl↓ + NaNO₃", "AgNO₃ + KBr → AgBr↓ + KNO₃", "AgNO₃ + KI → AgI↓ + KNO₃")
            )
        )
        register(
            Chemical(
                id = "BaCl2",
                name = "Barium Chloride (0.5 M)",
                formula = "BaCl₂",
                molarMass = 208.23,
                density = 1.05,
                meltingPointCelsius = 962.0,
                boilingPointCelsius = 1560.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44E0E7FF,
                intrinsicPh = 6.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC),
                category = "Salts",
                isSolvent = false,
                description = "Soluble barium salt used for sulfate anion detection forming insoluble BaSO4.",
                safetyInfo = "Highly toxic if ingested.",
                commonReactions = listOf("BaCl₂ + H₂SO₄ → BaSO₄↓ + 2HCl", "BaCl₂ + Na₂SO₄ → BaSO₄↓ + 2NaCl")
            )
        )
        register(
            Chemical(
                id = "FeCl3",
                name = "Iron(III) Chloride (0.5 M)",
                formula = "FeCl₃",
                molarMass = 162.2,
                density = 1.08,
                meltingPointCelsius = 306.0,
                boilingPointCelsius = 315.0,
                specificHeatCapacity = 4.0,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x88D97706,
                intrinsicPh = 2.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE, HazardType.TOXIC),
                category = "Salts",
                isSolvent = false,
                description = "Amber/brown acidic ferric salt forming orange-brown hydroxide precipitate.",
                safetyInfo = "Corrosive and staining.",
                commonReactions = listOf("FeCl₃ + 3NaOH → Fe(OH)₃↓ + 3NaCl", "FeCl₃ + 3KSCN → Fe(SCN)₃ + 3KCl")
            )
        )
        register(
            Chemical(
                id = "KMnO4",
                name = "Potassium Permanganate (0.05 M)",
                formula = "KMnO₄",
                molarMass = 158.03,
                density = 1.02,
                meltingPointCelsius = 240.0,
                boilingPointCelsius = 500.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0xFF701A75,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.OXIDIZER, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Deep vivid violet-magenta strong oxidizing solution used in redox titrations.",
                safetyInfo = "Potent oxidizer; stains skin deep brown with MnO2.",
                commonReactions = listOf("2KMnO4 + 5H2O2 + 3H2SO4 → K2SO4 + 2MnSO4 + 5O2↑ + 8H2O")
            )
        )
        register(
            Chemical(
                id = "K2Cr2O7",
                name = "Potassium Dichromate (0.1 M)",
                formula = "K₂Cr₂O₇",
                molarMass = 294.18,
                density = 1.04,
                meltingPointCelsius = 398.0,
                boilingPointCelsius = 500.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0xFFC2410C,
                intrinsicPh = 4.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC, HazardType.OXIDIZER, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Bright intense orange-red hexavalent chromium oxidizing solution.",
                safetyInfo = "Carcinogen and potent oxidizer. Handle with nitrile gloves.",
                commonReactions = listOf("K₂Cr₂O₇ + 3C₂H₅OH + 4H₂SO₄ → Cr₂(SO₄)₃ + 3CH₃CHO + K₂SO₄ + 7H₂O")
            )
        )
        register(
            Chemical(
                id = "KI",
                name = "Potassium Iodide (0.5 M)",
                formula = "KI",
                molarMass = 166.0,
                density = 1.06,
                meltingPointCelsius = 681.0,
                boilingPointCelsius = 1330.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44E0E7FF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Source of iodide anions; forms brilliant yellow lead iodide precipitate (golden rain).",
                safetyInfo = "Safe laboratory reagent.",
                commonReactions = listOf("Pb(NO₃)₂ + 2KI → PbI₂↓ + 2KNO₃", "2KI + Cl₂ → 2KCl + I₂")
            )
        )
        register(
            Chemical(
                id = "Pb_NO3_2",
                name = "Lead(II) Nitrate (0.2 M)",
                formula = "Pb(NO₃)₂",
                molarMass = 331.2,
                density = 1.06,
                meltingPointCelsius = 470.0,
                boilingPointCelsius = 500.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x44E0E7FF,
                intrinsicPh = 4.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Soluble lead salt used for golden rain precipitation experiments.",
                safetyInfo = "Toxic heavy metal cumulative poison.",
                commonReactions = listOf("Pb(NO₃)₂ + 2KI → PbI₂↓ + 2KNO₃")
            )
        )
        register(
            Chemical(
                id = "AgCl",
                name = "Silver Chloride (Precipitate)",
                formula = "AgCl↓",
                molarMass = 143.32,
                density = 5.56,
                meltingPointCelsius = 455.0,
                boilingPointCelsius = 1547.0,
                specificHeatCapacity = 0.354,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Precipitates",
                isSolvent = false,
                description = "Insoluble white curdy precipitate formed in halide tests. Darkens in sunlight.",
                safetyInfo = "Insoluble solid.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "BaSO4",
                name = "Barium Sulfate (Precipitate)",
                formula = "BaSO₄↓",
                molarMass = 233.39,
                density = 4.49,
                meltingPointCelsius = 1580.0,
                boilingPointCelsius = 1600.0,
                specificHeatCapacity = 0.45,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFF8FAFC,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Precipitates",
                isSolvent = false,
                description = "Dense, bright white insoluble barium precipitate.",
                safetyInfo = "Insoluble non-toxic radio-opaque compound.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Cu_OH_2",
                name = "Copper(II) Hydroxide (Precipitate)",
                formula = "Cu(OH)₂↓",
                molarMass = 97.56,
                density = 3.37,
                meltingPointCelsius = 80.0,
                boilingPointCelsius = 160.0,
                specificHeatCapacity = 0.4,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFF38BDF8,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC),
                category = "Precipitates",
                isSolvent = false,
                description = "Gelatinous sky-blue precipitate formed by adding alkali to copper salts. Decomposes to black CuO upon heating.",
                safetyInfo = "Avoid ingestion.",
                commonReactions = listOf("Cu(OH)₂ → CuO + H₂O")
            )
        )
        register(
            Chemical(
                id = "Fe_OH_3",
                name = "Iron(III) Hydroxide (Precipitate)",
                formula = "Fe(OH)₃↓",
                molarMass = 106.87,
                density = 3.4,
                meltingPointCelsius = 500.0,
                boilingPointCelsius = 600.0,
                specificHeatCapacity = 0.45,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFB45309,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Precipitates",
                isSolvent = false,
                description = "Gelatinous red-brown rust precipitate.",
                safetyInfo = "Insoluble ferric precipitate.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "PbI2",
                name = "Lead(II) Iodide (Golden Rain)",
                formula = "PbI₂↓",
                molarMass = 461.01,
                density = 6.16,
                meltingPointCelsius = 402.0,
                boilingPointCelsius = 954.0,
                specificHeatCapacity = 0.17,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFFACC15,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC),
                category = "Precipitates",
                isSolvent = false,
                description = "Brilliant shimmering golden crystalline precipitate.",
                safetyInfo = "Toxic heavy metal solid.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "CaCO3",
                name = "Calcium Carbonate (Chalk/Marble)",
                formula = "CaCO₃",
                molarMass = 100.09,
                density = 2.71,
                meltingPointCelsius = 825.0,
                boilingPointCelsius = 1339.0,
                specificHeatCapacity = 0.818,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFF1F5F9,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "White insoluble carbonate rock that fizzes vigorously in acids.",
                safetyInfo = "Non-toxic mineral.",
                commonReactions = listOf("CaCO₃ + 2HCl → CaCl₂ + H₂O + CO₂↑")
            )
        )
        register(
            Chemical(
                id = "NaHCO3",
                name = "Sodium Bicarbonate (Baking Soda)",
                formula = "NaHCO₃",
                molarMass = 84.01,
                density = 2.2,
                meltingPointCelsius = 50.0,
                boilingPointCelsius = 851.0,
                specificHeatCapacity = 1.04,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 8.3,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "White powder baking soda that neutralizes acids with bubbling CO2 release.",
                safetyInfo = "Safe food-grade buffering salt.",
                commonReactions = listOf("NaHCO₃ + HCl → NaCl + H₂O + CO₂↑")
            )
        )
        register(
            Chemical(
                id = "H2O",
                name = "Deionized Water (Solvent)",
                formula = "H₂O",
                molarMass = 18.015,
                density = 1.0,
                meltingPointCelsius = 0.0,
                boilingPointCelsius = 100.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0x4438BDF8,
                intrinsicPh = 7.0,
                pKa = 14.0,
                pKb = 14.0,
                hazards = emptyList(),
                category = "Solvents",
                isSolvent = true,
                description = "Universal chemical laboratory solvent with high specific heat capacity (4.184 J/g°C).",
                safetyInfo = "Safe pure laboratory solvent.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "C2H5OH",
                name = "Ethanol (95%)",
                formula = "C₂H₅OH",
                molarMass = 46.07,
                density = 0.789,
                meltingPointCelsius = -114.1,
                boilingPointCelsius = 78.37,
                specificHeatCapacity = 2.44,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0x33CBD5E1,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.FLAMMABLE),
                category = "Solvents",
                isSolvent = true,
                description = "Volatile flammable alcohol solvent miscible with water.",
                safetyInfo = "Flammable liquid and vapor. Keep away from Bunsen flame.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "CH3COCH3",
                name = "Acetone (Solvent)",
                formula = "CH₃COCH₃",
                molarMass = 58.08,
                density = 0.784,
                meltingPointCelsius = -94.7,
                boilingPointCelsius = 56.05,
                specificHeatCapacity = 2.15,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0x33E2E8F0,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.IRRITANT),
                category = "Solvents",
                isSolvent = true,
                description = "Highly volatile, sweet-smelling organic solvent for glassware cleaning.",
                safetyInfo = "Highly flammable liquid and vapor.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "H2O2",
                name = "Hydrogen Peroxide (30%)",
                formula = "H₂O₂",
                molarMass = 34.01,
                density = 1.11,
                meltingPointCelsius = -0.43,
                boilingPointCelsius = 150.2,
                specificHeatCapacity = 2.62,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0x44E0E7FF,
                intrinsicPh = 4.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.OXIDIZER, HazardType.CORROSIVE),
                category = "Oxidizers",
                isSolvent = false,
                description = "Colorless oxidizing liquid that decomposes catalytically to water and oxygen gas.",
                safetyInfo = "Strong oxidizer; bleach hazard.",
                commonReactions = listOf("2H₂O₂ → 2H₂O + O₂↑")
            )
        )
        register(
            Chemical(
                id = "H2_gas",
                name = "Hydrogen Gas",
                formula = "H₂ (g)",
                molarMass = 2.016,
                density = 8.988e-05,
                meltingPointCelsius = -259.1,
                boilingPointCelsius = -252.9,
                specificHeatCapacity = 14.304,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.FLAMMABLE),
                category = "Gases",
                isSolvent = false,
                description = "Extremely buoyant, light, flammable gas produced in acid-metal reactions.",
                safetyInfo = "Explosive with air when ignited.",
                commonReactions = listOf("2H₂ + O₂ → 2H₂O")
            )
        )
        register(
            Chemical(
                id = "CO2_gas",
                name = "Carbon Dioxide Gas",
                formula = "CO₂ (g)",
                molarMass = 44.01,
                density = 0.001977,
                meltingPointCelsius = -56.6,
                boilingPointCelsius = -78.5,
                specificHeatCapacity = 0.839,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x22FFFFFF,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Gases",
                isSolvent = false,
                description = "Dense, non-flammable acidic oxide gas produced from acid-carbonate reactions.",
                safetyInfo = "Simple asphyxiant in confined unventilated spaces.",
                commonReactions = listOf("CO₂ + Ca(OH)₂ → CaCO₃↓ + H₂O")
            )
        )
        register(
            Chemical(
                id = "UNIVERSAL_IND",
                name = "Universal Indicator Solution",
                formula = "pH Indicator",
                molarMass = 100.0,
                density = 1.0,
                meltingPointCelsius = 0.0,
                boilingPointCelsius = 100.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0xFF22C55E,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Indicators",
                isSolvent = false,
                description = "Colorimetric solution displaying continuous spectrum of colors across 0-14 pH.",
                safetyInfo = "Non-toxic indicator dye solution.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "ZnCl2",
                name = "Zinc Chloride",
                formula = "ZnCl₂",
                molarMass = 136.315,
                density = 2.907,
                meltingPointCelsius = 290.0,
                boilingPointCelsius = 732.0,
                specificHeatCapacity = 0.524,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 5.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE, HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Hygroscopic zinc salt formed from reaction of zinc with hydrochloric acid.",
                safetyInfo = "Causes severe skin burns and eye damage.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Na2SO4",
                name = "Sodium Sulfate",
                formula = "Na₂SO₄",
                molarMass = 142.04,
                density = 2.664,
                meltingPointCelsius = 884.0,
                boilingPointCelsius = 1429.0,
                specificHeatCapacity = 0.9,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Neutral sodium sulfate salt formed from neutralization of sulfuric acid.",
                safetyInfo = "Non-toxic salt.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "CH3COONa",
                name = "Sodium Acetate",
                formula = "CH₃COONa",
                molarMass = 82.03,
                density = 1.528,
                meltingPointCelsius = 324.0,
                boilingPointCelsius = 881.0,
                specificHeatCapacity = 1.21,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 8.9,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Mildly basic acetate buffer salt formed from acetic acid and NaOH.",
                safetyInfo = "Safe laboratory salt.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "KNO3",
                name = "Potassium Nitrate",
                formula = "KNO₃",
                molarMass = 101.1,
                density = 2.11,
                meltingPointCelsius = 334.0,
                boilingPointCelsius = 400.0,
                specificHeatCapacity = 0.95,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.OXIDIZER),
                category = "Salts",
                isSolvent = false,
                description = "Soluble potassium nitrate salt widely used in fertilizers and pyrotechnics.",
                safetyInfo = "Oxidizer.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "NH4Cl",
                name = "Ammonium Chloride",
                formula = "NH₄Cl",
                molarMass = 53.49,
                density = 1.527,
                meltingPointCelsius = 338.0,
                boilingPointCelsius = 520.0,
                specificHeatCapacity = 1.57,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 5.1,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "Sublimable white salt formed by ammonia neutralization.",
                safetyInfo = "Mild irritant.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "CaCl2",
                name = "Calcium Chloride",
                formula = "CaCl₂",
                molarMass = 110.98,
                density = 2.15,
                meltingPointCelsius = 772.0,
                boilingPointCelsius = 1935.0,
                specificHeatCapacity = 0.67,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "Deliquescent calcium salt with high water solubility.",
                safetyInfo = "Irritant.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "FeSO4",
                name = "Iron(II) Sulfate",
                formula = "FeSO₄",
                molarMass = 151.91,
                density = 2.84,
                meltingPointCelsius = 680.0,
                boilingPointCelsius = 800.0,
                specificHeatCapacity = 0.68,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF86EFAC,
                intrinsicPh = 4.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "Green vitriol; light green ferrous iron salt.",
                safetyInfo = "Harmful if swallowed.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Fe_SCN_3",
                name = "Iron(III) Thiocyanate",
                formula = "Fe(SCN)₃",
                molarMass = 230.09,
                density = 1.8,
                meltingPointCelsius = 100.0,
                boilingPointCelsius = 200.0,
                specificHeatCapacity = 1.0,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0xFF991B1B,
                intrinsicPh = 3.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Deep blood-red coordination complex used in chemical equilibrium demonstrations.",
                safetyInfo = "Safe laboratory complex.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "KCl",
                name = "Potassium Chloride",
                formula = "KCl",
                molarMass = 74.55,
                density = 1.984,
                meltingPointCelsius = 770.0,
                boilingPointCelsius = 1420.0,
                specificHeatCapacity = 0.69,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Colorless soluble potassium salt.",
                safetyInfo = "Non-toxic salt.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Cr2_SO4_3",
                name = "Chromium(III) Sulfate",
                formula = "Cr₂(SO₄)₃",
                molarMass = 392.18,
                density = 3.1,
                meltingPointCelsius = 1000.0,
                boilingPointCelsius = 1200.0,
                specificHeatCapacity = 0.7,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF15803D,
                intrinsicPh = 3.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "Dark green trivalent chromium salt.",
                safetyInfo = "Irritant.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "CH3CHO",
                name = "Acetaldehyde",
                formula = "CH₃CHO",
                molarMass = 44.05,
                density = 0.784,
                meltingPointCelsius = -123.5,
                boilingPointCelsius = 20.2,
                specificHeatCapacity = 2.18,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0x33E2E8F0,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.FLAMMABLE, HazardType.TOXIC),
                category = "Solvents",
                isSolvent = true,
                description = "Pungent volatile aldehyde formed by ethanol oxidation.",
                safetyInfo = "Flammable and toxic vapor.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "K2SO4",
                name = "Potassium Sulfate",
                formula = "K₂SO₄",
                molarMass = 174.26,
                density = 2.66,
                meltingPointCelsius = 1069.0,
                boilingPointCelsius = 1689.0,
                specificHeatCapacity = 0.75,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Neutral soluble potassium fertilizer salt.",
                safetyInfo = "Non-toxic.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "MnSO4",
                name = "Manganese(II) Sulfate",
                formula = "MnSO₄",
                molarMass = 151.0,
                density = 3.25,
                meltingPointCelsius = 710.0,
                boilingPointCelsius = 850.0,
                specificHeatCapacity = 0.67,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFBCFE8,
                intrinsicPh = 5.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.ENVIRONMENTAL_HAZARD),
                category = "Salts",
                isSolvent = false,
                description = "Pale pink manganous salt formed from permanganate reduction.",
                safetyInfo = "Toxic to aquatic life.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "NaNO3",
                name = "Sodium Nitrate",
                formula = "NaNO₃",
                molarMass = 84.99,
                density = 2.257,
                meltingPointCelsius = 308.0,
                boilingPointCelsius = 380.0,
                specificHeatCapacity = 1.1,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.OXIDIZER),
                category = "Salts",
                isSolvent = false,
                description = "Chile saltpeter; soluble sodium nitrate oxidizer.",
                safetyInfo = "Oxidizer.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "KBr",
                name = "Potassium Bromide",
                formula = "KBr",
                molarMass = 119.0,
                density = 2.75,
                meltingPointCelsius = 734.0,
                boilingPointCelsius = 1435.0,
                specificHeatCapacity = 0.44,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "White ionic salt used in photography and infrared spectroscopy.",
                safetyInfo = "Low toxicity.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "AgBr",
                name = "Silver Bromide (Precipitate)",
                formula = "AgBr↓",
                molarMass = 187.77,
                density = 6.47,
                meltingPointCelsius = 432.0,
                boilingPointCelsius = 1502.0,
                specificHeatCapacity = 0.28,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFFEF08A,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Precipitates",
                isSolvent = false,
                description = "Pale cream-yellow photosensitive halide precipitate.",
                safetyInfo = "Photosensitive solid.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "AgI",
                name = "Silver Iodide (Precipitate)",
                formula = "AgI↓",
                molarMass = 234.77,
                density = 5.675,
                meltingPointCelsius = 558.0,
                boilingPointCelsius = 1506.0,
                specificHeatCapacity = 0.24,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFFDE047,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Precipitates",
                isSolvent = false,
                description = "Bright yellow insoluble halide precipitate.",
                safetyInfo = "Insoluble solid.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "CuO",
                name = "Copper(II) Oxide",
                formula = "CuO",
                molarMass = 79.55,
                density = 6.315,
                meltingPointCelsius = 1326.0,
                boilingPointCelsius = 2000.0,
                specificHeatCapacity = 0.53,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF1E293B,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.ENVIRONMENTAL_HAZARD),
                category = "Oxides",
                isSolvent = false,
                description = "Black insoluble copper oxide formed by thermal decomposition of copper hydroxide.",
                safetyInfo = "Avoid inhalation.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "PotashAlum",
                name = "Potash Alum (Crystals)",
                formula = "KAl(SO₄)₂·12H₂O",
                molarMass = 474.39,
                density = 1.725,
                meltingPointCelsius = 92.5,
                boilingPointCelsius = 200.0,
                specificHeatCapacity = 1.2,
                defaultPhase = Phase.PRECIPITATE,
                defaultColorHex = 0xFFF8FAFC,
                intrinsicPh = 3.3,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Salts",
                isSolvent = false,
                description = "Potassium alum octahedral double salt crystals. Astringent and water clarifier.",
                safetyInfo = "Safe educational crystals.",
                commonReactions = listOf("2Al + 2KOH + 6H₂O → 2KAl(OH)₄ + 3H₂↑", "KAl(OH)₄ + 2H₂SO₄ → KAl(SO₄)₂ + 4H₂O")
            )
        )
        register(
            Chemical(
                id = "KAl_OH_4",
                name = "Potassium Tetrahydroxoaluminate (1.0 M)",
                formula = "KAl(OH)₄",
                molarMass = 134.09,
                density = 1.15,
                meltingPointCelsius = 0.0,
                boilingPointCelsius = 100.0,
                specificHeatCapacity = 4.184,
                defaultPhase = Phase.AQUEOUS,
                defaultColorHex = 0x33E2E8F0,
                intrinsicPh = 13.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE),
                category = "Salts",
                isSolvent = false,
                description = "Soluble aluminate complex solution formed by dissolving aluminum in potassium hydroxide.",
                safetyInfo = "Caustic alkaline solution.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Al2_SO4_3",
                name = "Aluminum Sulfate",
                formula = "Al₂(SO₄)₃",
                molarMass = 342.15,
                density = 2.672,
                meltingPointCelsius = 770.0,
                boilingPointCelsius = 1000.0,
                specificHeatCapacity = 0.85,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 3.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "White water-soluble aluminum salt used in alum crystal synthesis and water purification.",
                safetyInfo = "Causes eye irritation.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Glycerol",
                name = "Glycerol (Glycerin 99%)",
                formula = "C₃H₅(OH)₃",
                molarMass = 92.09,
                density = 1.261,
                meltingPointCelsius = 17.8,
                boilingPointCelsius = 290.0,
                specificHeatCapacity = 2.43,
                defaultPhase = Phase.LIQUID,
                defaultColorHex = 0x44E2E8F0,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Organics",
                isSolvent = false,
                description = "Viscous, sweet-tasting triol alcohol that ignites spontaneously with strong oxidizers like KMnO4.",
                safetyInfo = "Non-toxic viscous polyol.",
                commonReactions = listOf("14KMnO₄ + 4C₃H₅(OH)₃ → 7K₂CO₃ + 7Mn₂O₃ + 5CO₂↑ + 16H₂O")
            )
        )
        register(
            Chemical(
                id = "Sr_NO3_2",
                name = "Strontium Nitrate",
                formula = "Sr(NO₃)₂",
                molarMass = 211.63,
                density = 2.986,
                meltingPointCelsius = 570.0,
                boilingPointCelsius = 645.0,
                specificHeatCapacity = 0.69,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 7.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.OXIDIZER, HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "Inorganic strontium salt yielding brilliant crimson-red flame coloration and pyrotechnic sparks.",
                safetyInfo = "Oxidizer.",
                commonReactions = listOf("Sr(NO₃)₂ + 2Mg → SrO + 2MgO + N₂↑")
            )
        )
        register(
            Chemical(
                id = "Ba_NO3_2",
                name = "Barium Nitrate",
                formula = "Ba(NO₃)₂",
                molarMass = 261.34,
                density = 3.24,
                meltingPointCelsius = 592.0,
                boilingPointCelsius = 1000.0,
                specificHeatCapacity = 0.58,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 6.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.OXIDIZER, HazardType.TOXIC),
                category = "Salts",
                isSolvent = false,
                description = "Inorganic barium salt yielding bright emerald-green flame coloration and pyrotechnic sparks.",
                safetyInfo = "Toxic oxidizer if ingested.",
                commonReactions = listOf("Ba(NO₃)₂ + 2Mg → BaO + 2MgO + N₂↑")
            )
        )
        register(
            Chemical(
                id = "Fe2O3",
                name = "Iron(III) Oxide (Rust Powder)",
                formula = "Fe₂O₃",
                molarMass = 159.69,
                density = 5.242,
                meltingPointCelsius = 1565.0,
                boilingPointCelsius = 2000.0,
                specificHeatCapacity = 0.65,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF991B1B,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Oxides",
                isSolvent = false,
                description = "Reddish-brown ferric oxide powder. Used in exothermic thermite reactions with aluminum.",
                safetyInfo = "Non-toxic mineral powder.",
                commonReactions = listOf("Fe₂O₃ + 2Al → Al₂O₃ + 2Fe")
            )
        )
        register(
            Chemical(
                id = "Al2O3",
                name = "Aluminum Oxide (Corundum)",
                formula = "Al₂O₃",
                molarMass = 101.96,
                density = 3.987,
                meltingPointCelsius = 2072.0,
                boilingPointCelsius = 2977.0,
                specificHeatCapacity = 0.88,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Oxides",
                isSolvent = false,
                description = "Extremely hard refractory white ceramic oxide formed in thermite reaction.",
                safetyInfo = "Inert solid.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "SrO",
                name = "Strontium Oxide",
                formula = "SrO",
                molarMass = 103.62,
                density = 4.7,
                meltingPointCelsius = 2531.0,
                boilingPointCelsius = 3200.0,
                specificHeatCapacity = 0.45,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFE2E8F0,
                intrinsicPh = 12.5,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.CORROSIVE),
                category = "Oxides",
                isSolvent = false,
                description = "Basic strontium oxide powder.",
                safetyInfo = "Basic oxide.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "BaO",
                name = "Barium Oxide",
                formula = "BaO",
                molarMass = 153.33,
                density = 5.72,
                meltingPointCelsius = 1923.0,
                boilingPointCelsius = 2000.0,
                specificHeatCapacity = 0.31,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFCBD5E1,
                intrinsicPh = 13.0,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC, HazardType.CORROSIVE),
                category = "Oxides",
                isSolvent = false,
                description = "Basic barium oxide powder.",
                safetyInfo = "Toxic caustic oxide.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "MgO",
                name = "Magnesium Oxide (Magnesia)",
                formula = "MgO",
                molarMass = 40.304,
                density = 3.58,
                meltingPointCelsius = 2852.0,
                boilingPointCelsius = 3600.0,
                specificHeatCapacity = 0.87,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 10.3,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Oxides",
                isSolvent = false,
                description = "White refractory ash formed by burning magnesium in oxygen.",
                safetyInfo = "Safe mineral oxide.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Mn2O3",
                name = "Manganese(III) Oxide",
                formula = "Mn₂O₃",
                molarMass = 157.87,
                density = 4.5,
                meltingPointCelsius = 1080.0,
                boilingPointCelsius = 1200.0,
                specificHeatCapacity = 0.68,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF334155,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = emptyList(),
                category = "Oxides",
                isSolvent = false,
                description = "Dark brownish-black manganese oxide powder.",
                safetyInfo = "Insoluble solid.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "K2CO3",
                name = "Potassium Carbonate (Potash)",
                formula = "K₂CO₃",
                molarMass = 138.205,
                density = 2.43,
                meltingPointCelsius = 891.0,
                boilingPointCelsius = 1200.0,
                specificHeatCapacity = 0.83,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFFFFFFFF,
                intrinsicPh = 11.6,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.IRRITANT),
                category = "Salts",
                isSolvent = false,
                description = "White alkaline potash salt.",
                safetyInfo = "Mild alkali.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Th234",
                name = "Thorium-234 (Isotope)",
                formula = "²³⁴Th",
                molarMass = 234.04,
                density = 11.7,
                meltingPointCelsius = 1750.0,
                boilingPointCelsius = 4788.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF34D399,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Radioactive",
                isSolvent = false,
                description = "Radioactive beta-emitting thorium daughter isotope from U-238 alpha decay (t½ = 24.1 days).",
                safetyInfo = "Radioactive material. Ionizing beta radiation.",
                commonReactions = listOf("²³⁴Th → ²³⁴Pa + β⁻")
            )
        )
        register(
            Chemical(
                id = "Pa234",
                name = "Protactinium-234 (Isotope)",
                formula = "²³⁴Pa",
                molarMass = 234.04,
                density = 15.37,
                meltingPointCelsius = 1568.0,
                boilingPointCelsius = 4027.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF10B981,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Radioactive",
                isSolvent = false,
                description = "Radioactive beta-emitting protactinium daughter isotope from Th-234 decay.",
                safetyInfo = "Radioactive beta-gamma emitter.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Rn222",
                name = "Radon-222 (Radioactive Gas)",
                formula = "²²²Rn (g)",
                molarMass = 222.0,
                density = 0.00973,
                meltingPointCelsius = -71.0,
                boilingPointCelsius = -61.7,
                specificHeatCapacity = 0.094,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x334ADE80,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Radioactive",
                isSolvent = false,
                description = "Dense noble radioactive alpha-emitting gas from Ra-226 decay (t½ = 3.8 days).",
                safetyInfo = "Severe inhalation radiation hazard.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Pb206",
                name = "Lead-206 (Stable Radiogenic)",
                formula = "²⁰⁶Pb",
                molarMass = 205.97,
                density = 11.34,
                meltingPointCelsius = 327.5,
                boilingPointCelsius = 1749.0,
                specificHeatCapacity = 0.13,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF94A3B8,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.TOXIC),
                category = "Metals",
                isSolvent = false,
                description = "Stable radiogenic lead isotope - the final terminal stable product of the uranium decay chain.",
                safetyInfo = "Toxic cumulative heavy metal.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Pu239",
                name = "Plutonium-239 (Alpha Emitter)",
                formula = "²³⁹Pu",
                molarMass = 239.05,
                density = 19.86,
                meltingPointCelsius = 639.4,
                boilingPointCelsius = 3228.0,
                specificHeatCapacity = 0.14,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF059669,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Radioactive",
                isSolvent = false,
                description = "Fissile transuranic actinide alpha-emitter with intense self-heating radioactivity.",
                safetyInfo = "Severe radiological hazard.",
                commonReactions = listOf("²³⁹Pu → ²³⁵U + α")
            )
        )
        register(
            Chemical(
                id = "U235",
                name = "Uranium-235 (Fissile Isotope)",
                formula = "²³⁵U",
                molarMass = 235.04,
                density = 19.1,
                meltingPointCelsius = 1135.0,
                boilingPointCelsius = 4131.0,
                specificHeatCapacity = 0.12,
                defaultPhase = Phase.SOLID,
                defaultColorHex = 0xFF10B981,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE, HazardType.TOXIC),
                category = "Radioactive",
                isSolvent = false,
                description = "Naturally occurring fissile isotope of uranium (0.72% abundance).",
                safetyInfo = "Radioactive material.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "He4_alpha",
                name = "Alpha Particle (Helium-4 Nuclei)",
                formula = "α (⁴He²⁺)",
                molarMass = 4.0026,
                density = 0.0001786,
                meltingPointCelsius = -272.2,
                boilingPointCelsius = -268.9,
                specificHeatCapacity = 5.19,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x3367E8F9,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Radioactive",
                isSolvent = false,
                description = "Energetic doubly ionized helium-4 nucleus emitted during nuclear alpha decay.",
                safetyInfo = "Ionizing particulate radiation.",
                commonReactions = emptyList()
            )
        )
        register(
            Chemical(
                id = "Beta_minus",
                name = "Beta Particle (Fast Electron)",
                formula = "β⁻ (e⁻)",
                molarMass = 0.00055,
                density = 0.0,
                meltingPointCelsius = 0.0,
                boilingPointCelsius = 0.0,
                specificHeatCapacity = 0.0,
                defaultPhase = Phase.GAS,
                defaultColorHex = 0x3338BDF8,
                intrinsicPh = null,
                pKa = null,
                pKb = null,
                hazards = listOf(HazardType.RADIOACTIVE),
                category = "Radioactive",
                isSolvent = false,
                description = "High-speed electron ejected from a radioactive nucleus during beta decay.",
                safetyInfo = "Ionizing particulate radiation.",
                commonReactions = emptyList()
            )
        )
    }

    fun register(chemical: Chemical) {
        chemicalsMap[chemical.id] = chemical
    }

    fun get(id: String): Chemical? = chemicalsMap[id]

    fun getAll(): List<Chemical> = chemicalsMap.values.toList()

    fun getAllElements(): List<Chemical> =
        chemicalsMap.values.filter { it.isElement }.sortedBy { it.atomicNumber ?: 999 }

    fun getAllCompounds(): List<Chemical> =
        chemicalsMap.values.filter { !it.isElement }

    fun getRadioactive(): List<Chemical> =
        chemicalsMap.values.filter { it.isRadioactive }

    fun getByCategory(category: String): List<Chemical> {
        if (category.equals("Elements", ignoreCase = true)) return getAllElements()
        if (category.equals("Compounds", ignoreCase = true)) return getAllCompounds()
        if (category.equals("Radioactive", ignoreCase = true)) return getRadioactive()
        return chemicalsMap.values.filter {
            it.category.equals(category, ignoreCase = true) ||
            (it.elementCategory != null && it.elementCategory.equals(category, ignoreCase = true))
        }
    }

    fun search(query: String, filterCategory: String? = null): List<Chemical> {
        var list = if (filterCategory != null && filterCategory != "All") {
            getByCategory(filterCategory)
        } else {
            getAll()
        }
        if (query.isBlank()) return list
        val q = query.trim().lowercase()
        return list.filter {
            it.name.lowercase().contains(q) ||
            it.formula.lowercase().contains(q) ||
            it.id.lowercase().contains(q) ||
            it.category.lowercase().contains(q) ||
            (it.elementCategory != null && it.elementCategory.lowercase().contains(q)) ||
            (it.atomicNumber != null && "${it.atomicNumber}".contains(q))
        }
    }
}
