package com.webdevavi.chemlabsimulator.simulation.model

import kotlinx.serialization.Serializable

@Serializable
enum class EquipmentType(
    val displayName: String,
    val capacityMl: Double,
    val accuracyMl: Double,
    val maxTempLimitCelsius: Double,
    val description: String,
    val shapeRatio: Float // width to height ratio for rendering
) {
    BEAKER_100("Beaker 100 mL", 100.0, 5.0, 450.0, "Standard borosilicate glass beaker for mixing and heating small volumes.", 0.8f),
    BEAKER_250("Beaker 250 mL", 250.0, 10.0, 450.0, "General-purpose 250 mL laboratory beaker with volume graduation markings.", 0.75f),
    BEAKER_500("Beaker 500 mL", 500.0, 25.0, 450.0, "Large volume borosilicate laboratory beaker for bulk solutions.", 0.7f),
    BEAKER_1000("Beaker 1000 mL", 1000.0, 50.0, 450.0, "Extra-large 1 Liter laboratory beaker for high capacity reactions.", 0.65f),

    ERLENMEYER_125("Erlenmeyer Flask 125 mL", 125.0, 5.0, 450.0, "Compact conical flask for titrations and swirling without splashing.", 0.85f),
    ERLENMEYER_250("Erlenmeyer Flask 250 mL", 250.0, 10.0, 450.0, "Standard 250 mL conical flask for titrations and heating volatile solutions.", 0.85f),
    ERLENMEYER_500("Erlenmeyer Flask 500 mL", 500.0, 25.0, 450.0, "Large 500 mL conical flask for analytical chemistry titrations.", 0.80f),

    FLORENCE_FLASK_250("Round-Bottom Flask 250 mL", 250.0, 10.0, 500.0, "Spherical Florence boiling flask designed for uniform heating in distillations.", 0.9f),
    FLORENCE_FLASK_500("Round-Bottom Flask 500 mL", 500.0, 20.0, 500.0, "500 mL spherical distillation flask.", 0.88f),

    VOLUMETRIC_FLASK_100("Volumetric Flask 100 mL", 100.0, 0.1, 100.0, "High-precision volumetric flask calibrated for exact standard solution preparation.", 0.65f),
    VOLUMETRIC_FLASK_250("Volumetric Flask 250 mL", 250.0, 0.15, 100.0, "Precision analytical 250 mL volumetric flask.", 0.65f),

    GRADUATED_CYLINDER_10("Graduated Cylinder 10 mL", 10.0, 0.1, 150.0, "Micro-measuring cylinder with sub-milliliter calibration markings.", 0.38f),
    GRADUATED_CYLINDER_50("Graduated Cylinder 50 mL", 50.0, 0.5, 200.0, "Narrow cylinder calibrated for precise liquid volume measurement.", 0.35f),
    GRADUATED_CYLINDER_100("Graduated Cylinder 100 mL", 100.0, 1.0, 200.0, "Precision 100 mL volumetric measuring cylinder.", 0.32f),
    GRADUATED_CYLINDER_250("Graduated Cylinder 250 mL", 250.0, 2.0, 200.0, "Heavy-duty 250 mL graduated cylinder.", 0.30f),

    TEST_TUBE_15("Test Tube 15 mL", 15.0, 0.5, 500.0, "Standard qualitative test tube for small scale chemical reactions.", 0.22f),
    TEST_TUBE_20("Test Tube 20 mL", 20.0, 1.0, 500.0, "Medium test tube for qualitative inorganic salt analysis.", 0.25f),
    TEST_TUBE_30("Test Tube 30 mL", 30.0, 1.5, 500.0, "Wide test tube for gas evolution reactions.", 0.26f),
    TEST_TUBE_50("Boiling Tube 50 mL", 50.0, 2.0, 550.0, "Heavy-wall boiling tube for direct flame heating of substances.", 0.28f),

    BURETTE_50("Burette 50 mL", 50.0, 0.05, 100.0, "High-precision 50 mL graduated burette with Teflon stopcock for acid-base titrations.", 0.22f),
    PIPETTE_10("Volumetric Pipette 10 mL", 10.0, 0.02, 80.0, "Calibrated class-A volumetric pipette for precise aliquot delivery.", 0.16f),
    DROPPER_5("Pasteur Dropper 5 mL", 5.0, 0.1, 80.0, "Dispensing tool for dropwise chemical additions.", 0.15f),

    SEPARATORY_FUNNEL_250("Separatory Funnel 250 mL", 250.0, 5.0, 120.0, "Conical funnel with stopcock for liquid-liquid immiscible extractions.", 0.75f),
    EVAPORATING_DISH_100("Evaporating Dish 100 mL", 100.0, 10.0, 800.0, "Shallow porcelain basin with spout for concentrating solutions by evaporation.", 1.2f),
    CRUCIBLE_50("Porcelain Crucible 50 mL", 50.0, 5.0, 1050.0, "High-temperature ceramic vessel for extreme heat calcination and gravimetric analysis.", 0.9f),
    WATCH_GLASS_50("Watch Glass 50 mL", 50.0, 5.0, 250.0, "Circular concave glass dish for observing crystallization and covering beakers.", 1.4f),
    PETRI_DISH_60("Petri Dish 60 mL", 60.0, 5.0, 120.0, "Shallow cylindrical transparent glass dish for observing chemical surface patterns.", 1.3f),
    BUCHNER_FUNNEL_250("Büchner Vacuum Flask 250 mL", 250.0, 10.0, 300.0, "Heavy-wall conical flask with side-arm nozzle for vacuum suction filtration.", 0.85f),
    GAS_SYRINGE_100("Gas Syringe 100 mL", 100.0, 1.0, 100.0, "Ground-glass airtight gas collection syringe with low-friction plunger.", 0.4f)
}
