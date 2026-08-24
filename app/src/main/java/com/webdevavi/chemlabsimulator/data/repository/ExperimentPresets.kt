package com.webdevavi.chemlabsimulator.data.repository

import com.webdevavi.chemlabsimulator.data.model.Experiment
import com.webdevavi.chemlabsimulator.data.model.ExperimentMaterial
import com.webdevavi.chemlabsimulator.data.model.ExperimentStep
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType

object ExperimentPresets {

    val experiments: List<Experiment> = listOf(
        // Experiment 1: Acid-Base Neutralization
        Experiment(
            id = "exp_neutralization",
            title = "Acid-Base Neutralization",
            subtitle = "Titration of Hydrochloric Acid with Sodium Hydroxide",
            category = "Acids & Bases",
            difficulty = "Beginner",
            durationMinutes = 10,
            objective = "Observe the exothermic neutralization reaction between a strong acid (HCl) and a strong base (NaOH), reaching a neutral pH 7.0.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "H2O", amount = 50.0, isVolume = true, label = "50 mL Distilled Water"),
                ExperimentMaterial(chemicalId = "HCl", amount = 25.0, isVolume = true, concentrationMolar = 1.0, label = "25 mL 1.0 M HCl"),
                ExperimentMaterial(chemicalId = "NaOH", amount = 25.0, isVolume = true, concentrationMolar = 1.0, label = "25 mL 1.0 M NaOH"),
                ExperimentMaterial(chemicalId = "UNIVERSAL_IND", amount = 2.0, isVolume = true, label = "Universal Indicator")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 50 mL of Distilled Water into the 250 mL beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "H2O",
                    targetAmount = 50.0,
                    hint = "Tap 'Add Chemical' in the inventory bar and select H₂O (50 mL).",
                    completionMessage = "Water added. The pH is neutral at 7.0."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 25 mL of 1.0 M Hydrochloric Acid (HCl).",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "HCl",
                    targetAmount = 25.0,
                    hint = "Select HCl and set volume to 25 mL.",
                    completionMessage = "The solution is now strongly acidic (pH ~ 1.5)."
                ),
                ExperimentStep(
                    stepNumber = 3,
                    instruction = "Add 2 mL of Universal Indicator to visualize the pH shift.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "UNIVERSAL_IND",
                    targetAmount = 2.0,
                    hint = "Select Universal Indicator and add 2 mL. Watch the liquid turn bright red.",
                    completionMessage = "Indicator added. The liquid turns vivid red indicating low pH."
                ),
                ExperimentStep(
                    stepNumber = 4,
                    instruction = "Slowly add 25 mL of 1.0 M Sodium Hydroxide (NaOH) to neutralize the acid.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "NaOH",
                    targetAmount = 25.0,
                    hint = "Add 25 mL of NaOH to neutralize the equal moles of HCl.",
                    completionMessage = "Neutralization complete! The solution turned green (pH 7.0) and temperature increased."
                )
            ),
            chemicalEquation = "HCl(aq) + NaOH(aq) → NaCl(aq) + H₂O(l)  [ΔH = -57.3 kJ/mol]",
            expectedObservation = "The indicator transitions from red (acidic) to green (neutral pH 7). The temperature of the beaker rises measurably due to exothermic reaction enthalpy.",
            educationalSummary = "When equimolar quantities of hydrogen ions (H⁺) and hydroxide ions (OH⁻) combine, they form neutral water molecules and a neutral salt (NaCl). The standard enthalpy of neutralization for strong acids and bases is -57.3 kJ/mol."
        ),

        // Experiment 2: Zinc & Acid Gas Evolution
        Experiment(
            id = "exp_zinc_acid",
            title = "Zinc in Hydrochloric Acid",
            subtitle = "Single Displacement & Hydrogen Gas Evolution",
            category = "Redox & Metals",
            difficulty = "Beginner",
            durationMinutes = 8,
            objective = "Investigate the single replacement reaction of zinc metal with hydrochloric acid to produce zinc chloride and flammable hydrogen gas.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "HCl", amount = 50.0, isVolume = true, concentrationMolar = 1.0, label = "50 mL 1.0 M HCl"),
                ExperimentMaterial(chemicalId = "Zn", amount = 3.0, isVolume = false, label = "3.0 g Zinc Granules")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 50 mL of 1.0 M Hydrochloric Acid (HCl) into the beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "HCl",
                    targetAmount = 50.0,
                    hint = "Select HCl and pour 50 mL.",
                    completionMessage = "Acid solution is ready in the beaker."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 3.0 grams of Zinc metal granules (Zn).",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "Zn",
                    targetAmount = 3.0,
                    hint = "Select Zinc granules (3.0 g) from the Metals category.",
                    completionMessage = "Reaction initiated! Rapid effervescence of hydrogen bubbles appears."
                )
            ),
            chemicalEquation = "Zn(s) + 2HCl(aq) → ZnCl₂(aq) + H₂(g)↑  [ΔH = -152.4 kJ/mol]",
            expectedObservation = "Vigorous bubbling (effervescence) of colorless hydrogen gas. Zinc granules slowly shrink and dissolve as zinc chloride forms. The solution warms up.",
            educationalSummary = "Zinc is higher than hydrogen in the activity series of metals. Zinc atoms lose 2 electrons (oxidation) to become Zn²⁺ ions, while H⁺ ions gain electrons (reduction) to form H₂ diatomic gas molecules."
        ),

        // Experiment 3: Silver Chloride Precipitation
        Experiment(
            id = "exp_silver_precipitation",
            title = "Silver Chloride Precipitation",
            subtitle = "Double Displacement & Insoluble Salt Formation",
            category = "Precipitation",
            difficulty = "Intermediate",
            durationMinutes = 10,
            objective = "Demonstrate the rapid double replacement reaction between silver nitrate and sodium chloride to form a distinct curdy white precipitate of AgCl.",
            initialEquipment = EquipmentType.TEST_TUBE_50,
            materials = listOf(
                ExperimentMaterial(chemicalId = "H2O", amount = 20.0, isVolume = true, label = "20 mL Distilled Water"),
                ExperimentMaterial(chemicalId = "AgNO3", amount = 1.0, isVolume = false, label = "1.0 g Silver Nitrate"),
                ExperimentMaterial(chemicalId = "NaCl", amount = 1.0, isVolume = false, label = "1.0 g Sodium Chloride")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 20 mL of Distilled Water into the test tube.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "H2O",
                    targetAmount = 20.0,
                    hint = "Add 20 mL water as the solvent.",
                    completionMessage = "Water added."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 1.0 g of Silver Nitrate (AgNO₃) and allow it to dissolve.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "AgNO3",
                    targetAmount = 1.0,
                    hint = "Select AgNO₃ from the Salts category.",
                    completionMessage = "Clear aqueous silver nitrate solution prepared."
                ),
                ExperimentStep(
                    stepNumber = 3,
                    instruction = "Add 1.0 g of Sodium Chloride (NaCl).",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "NaCl",
                    targetAmount = 1.0,
                    hint = "Add NaCl to trigger precipitation.",
                    completionMessage = "Instant precipitation! A dense white curdy solid (AgCl) forms and settles."
                )
            ),
            chemicalEquation = "AgNO₃(aq) + NaCl(aq) → AgCl(s)↓ + NaNO₃(aq)",
            expectedObservation = "The transparent liquid turns instantly cloudy, forming a dense white curd-like precipitate that slowly settles to the bottom of the container.",
            educationalSummary = "Silver chloride has an extremely low solubility product constant (Ksp = 1.8 × 10⁻¹⁰ M²). When Ag⁺ and Cl⁻ ion concentrations exceed this threshold, solid crystals immediately precipitate out of solution."
        ),

        // Experiment 4: Copper Sulfate & NaOH
        Experiment(
            id = "exp_copper_hydroxide",
            title = "Copper Sulfate & Base Reaction",
            subtitle = "Synthesis of Gelatinous Copper(II) Hydroxide",
            category = "Precipitation",
            difficulty = "Intermediate",
            durationMinutes = 10,
            objective = "Synthesize bright blue gelatinous copper(II) hydroxide precipitate by reacting copper sulfate solution with sodium hydroxide.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "H2O", amount = 40.0, isVolume = true, label = "40 mL Water"),
                ExperimentMaterial(chemicalId = "CuSO4", amount = 2.0, isVolume = false, label = "2.0 g Copper Sulfate"),
                ExperimentMaterial(chemicalId = "NaOH", amount = 20.0, isVolume = true, concentrationMolar = 1.0, label = "20 mL 1.0 M NaOH")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 40 mL of Distilled Water to the beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "H2O",
                    targetAmount = 40.0,
                    completionMessage = "Water added."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 2.0 g of Copper(II) Sulfate (CuSO₄) to create an azure blue solution.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "CuSO4",
                    targetAmount = 2.0,
                    completionMessage = "Bright blue copper(II) sulfate solution formed."
                ),
                ExperimentStep(
                    stepNumber = 3,
                    instruction = "Add 20 mL of 1.0 M Sodium Hydroxide (NaOH).",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "NaOH",
                    targetAmount = 20.0,
                    completionMessage = "Thick, gelatinous sky-blue precipitate of Cu(OH)₂ formed!"
                )
            ),
            chemicalEquation = "CuSO₄(aq) + 2NaOH(aq) → Cu(OH)₂(s)↓ + Na₂SO₄(aq)",
            expectedObservation = "The clear royal blue copper solution instantly precipitates a thick, gelatinous sky-blue mass of copper(II) hydroxide.",
            educationalSummary = "Hydroxide ions (OH⁻) displace sulfate ions from copper complexes to form insoluble Cu(OH)₂. If heated, Cu(OH)₂ will thermally decompose into black copper(II) oxide (CuO) and water."
        ),

        // Experiment 5: Heating Water to Boiling
        Experiment(
            id = "exp_heating_water",
            title = "Heating Water to Boiling",
            subtitle = "Thermodynamics, Phase Transitions & Latent Heat",
            category = "Thermodynamics",
            difficulty = "Beginner",
            durationMinutes = 6,
            objective = "Model the thermodynamic heating of liquid water, verifying temperature rise (q = mcΔT) and the boiling temperature plateau at 100°C with steam evolution.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "H2O", amount = 100.0, isVolume = true, label = "100 mL Distilled Water")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 100 mL of Distilled Water to the beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "H2O",
                    targetAmount = 100.0,
                    completionMessage = "100 mL water ready at room temperature (~20°C)."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Ignite the Bunsen Burner to start heating the water.",
                    expectedAction = "HEAT",
                    hint = "Tap the Bunsen Burner tool in the Lab Tools panel to turn on the flame.",
                    completionMessage = "Burner is active! Observe the temperature steadily climb toward 100°C."
                )
            ),
            chemicalEquation = "H₂O(l) + Heat → H₂O(g)  [ΔH_vap = 40.7 kJ/mol]",
            expectedObservation = "As temperature approaches 100°C, convection currents and rising vapor bubbles appear. At 100°C, the water boils vigorously and temperature plateaus while steam billows out.",
            educationalSummary = "Sensible heat increases temperature up to the boiling point according to q = mcΔT. At 100°C, thermal energy is consumed as latent heat of vaporization (2260 J/g) to overcome hydrogen bonding without increasing temperature."
        ),

        // Experiment 6: Dissolving NaCl
        Experiment(
            id = "exp_dissolving_nacl",
            title = "Dissolving Table Salt in Water",
            subtitle = "Solvation Dynamics & Saturation Limits",
            category = "Solutions",
            difficulty = "Beginner",
            durationMinutes = 5,
            objective = "Explore physical dissolution, mass and volume conservation, and verify the saturation threshold of sodium chloride in water.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "H2O", amount = 100.0, isVolume = true, label = "100 mL Distilled Water"),
                ExperimentMaterial(chemicalId = "NaCl", amount = 30.0, isVolume = false, label = "30.0 g Sodium Chloride")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 100 mL of Distilled Water into the beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "H2O",
                    targetAmount = 100.0,
                    completionMessage = "Water added."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 30.0 g of Sodium Chloride (NaCl) crystals.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "NaCl",
                    targetAmount = 30.0,
                    completionMessage = "NaCl dissolves completely into hydrated Na⁺ and Cl⁻ ions."
                )
            ),
            chemicalEquation = "NaCl(s) + H₂O(l) → Na⁺(aq) + Cl⁻(aq)",
            expectedObservation = "Solid crystals rapidly dissolve into the solvent. Total mass increases to 130 g while volume increases slightly due to ion packing in water.",
            educationalSummary = "Water molecules orient their partial negative oxygen dipoles toward Na⁺ cations and partial positive hydrogen dipoles toward Cl⁻ anions, breaking the crystalline ionic lattice via hydration enthalpy."
        ),

        // Experiment 7: Sodium Bicarbonate & Acid (Endothermic Volcano)
        Experiment(
            id = "exp_bicarbonate_volcano",
            title = "Sodium Bicarbonate & Acid Effervescence",
            subtitle = "Rapid Carbon Dioxide Gas & Endothermic Cooling",
            category = "Gas Evolution",
            difficulty = "Beginner",
            durationMinutes = 6,
            objective = "Observe rapid effervescence of carbon dioxide gas and note the temperature drop resulting from the endothermic bicarbonate reaction.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "HCl", amount = 50.0, isVolume = true, concentrationMolar = 1.0, label = "50 mL 1.0 M HCl"),
                ExperimentMaterial(chemicalId = "NaHCO3", amount = 5.0, isVolume = false, label = "5.0 g Baking Soda (NaHCO₃)")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 50 mL of 1.0 M Hydrochloric Acid (HCl) into the beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "HCl",
                    targetAmount = 50.0,
                    completionMessage = "Acid ready."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 5.0 g of Sodium Bicarbonate (NaHCO₃).",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "NaHCO3",
                    targetAmount = 5.0,
                    completionMessage = "Intense foaming and effervescence of CO₂ gas! Temperature drops slightly."
                )
            ),
            chemicalEquation = "NaHCO₃(s) + HCl(aq) → NaCl(aq) + H₂O(l) + CO₂(g)↑  [ΔH = +28.0 kJ/mol]",
            expectedObservation = "Immediate bubbling and dense foaming as carbon dioxide gas erupts. The container temperature drops noticeably as heat is absorbed from the solution.",
            educationalSummary = "The acid protonates bicarbonate into carbonic acid (H₂CO₃), which rapidly dissociates into liquid water and gaseous CO₂. Because this reaction is endothermic (ΔH > 0), thermal energy is absorbed from the liquid."
        ),

        // Experiment 8: Pyrotechnic Firecracker Sparks & Flame Colors (Kracker Simulation)
        Experiment(
            id = "exp_pyrotechnic_kracker",
            title = "Pyrotechnic Firecracker Sparks & Flame Colors",
            subtitle = "Spontaneous Combustion & Colorful Metal Starburst Sparklers",
            category = "Pyrotechnics & Redox",
            difficulty = "Advanced",
            durationMinutes = 10,
            objective = "Simulate pyrotechnic firecracker combustion by triggering spontaneous oxidation of glycerol by potassium permanganate, generating dazzling lilac-purple and golden starburst sparks.",
            initialEquipment = EquipmentType.CRUCIBLE_50,
            materials = listOf(
                ExperimentMaterial(chemicalId = "KMnO4", amount = 10.0, isVolume = false, label = "10.0 g Potassium Permanganate (KMnO₄)"),
                ExperimentMaterial(chemicalId = "Glycerol", amount = 4.0, isVolume = true, label = "4.0 mL Glycerol"),
                ExperimentMaterial(chemicalId = "Sr_NO3_2", amount = 5.0, isVolume = false, label = "5.0 g Strontium Nitrate"),
                ExperimentMaterial(chemicalId = "Mg", amount = 2.0, isVolume = false, label = "2.0 g Magnesium Powder")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 10.0 g of Potassium Permanganate (KMnO₄) into the ceramic crucible.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "KMnO4",
                    targetAmount = 10.0,
                    completionMessage = "KMnO₄ oxidizer loaded in crucible."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 4.0 mL of Glycerol to initiate spontaneous exothermic firecracker ignition.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "Glycerol",
                    targetAmount = 4.0,
                    completionMessage = "💥 Spontaneous firecracker ignition! Brilliant purple and golden starburst sparks erupt with dense billowing smoke!"
                ),
                ExperimentStep(
                    stepNumber = 3,
                    instruction = "Add 5.0 g of Strontium Nitrate (Sr(NO₃)₂) to the crucible.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "Sr_NO3_2",
                    targetAmount = 5.0,
                    completionMessage = "Strontium pyrotechnic oxidizer added."
                ),
                ExperimentStep(
                    stepNumber = 4,
                    instruction = "Add 2.0 g of Magnesium powder (Mg) to ignite a vivid crimson-red firecracker starburst!",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "Mg",
                    targetAmount = 2.0,
                    completionMessage = "✨ Blinding crimson-red firecracker sparklers burst outward with crackling starburst particles!"
                )
            ),
            chemicalEquation = "14KMnO₄ + 4C₃H₅(OH)₃ → 7K₂CO₃ + 7Mn₂O₃ + 5CO₂↑ + 16H₂O  |  Sr(NO₃)₂ + 2Mg → SrO + 2MgO + N₂↑",
            expectedObservation = "Intense exothermic eruption with dazzling purple, gold, diamond-white, and crimson-red pyrotechnic starburst sparks. Glassware shudders with crackling audio-visual flares.",
            educationalSummary = "Pyrotechnic compositions combine strong oxidizing agents with organic fuels and metal colorants. Excited metal ions (e.g. Sr²⁺, K⁺, Ba²⁺) emit discrete spectral photons upon thermal excitation."
        ),

        // Experiment 9: Preparation of Potash Alum Crystals
        Experiment(
            id = "exp_potash_alum",
            title = "Preparation of Potash Alum Crystals",
            subtitle = "Synthesis of Potassium Aluminum Sulfate Dodecahydrate Double Salt",
            category = "Inorganic Synthesis",
            difficulty = "Intermediate",
            durationMinutes = 12,
            objective = "Synthesize pure octahedral potash alum crystals from aluminum metal via intermediate potassium tetrahydroxoaluminate followed by sulfuric acid acidification.",
            initialEquipment = EquipmentType.BEAKER_250,
            materials = listOf(
                ExperimentMaterial(chemicalId = "KOH", amount = 25.0, isVolume = true, concentrationMolar = 1.0, label = "25 mL 1.0 M KOH"),
                ExperimentMaterial(chemicalId = "Al", amount = 2.7, isVolume = false, label = "2.7 g Aluminum Metal (Al)"),
                ExperimentMaterial(chemicalId = "H2SO4", amount = 50.0, isVolume = true, concentrationMolar = 1.0, label = "50 mL 1.0 M H₂SO₄")
            ),
            steps = listOf(
                ExperimentStep(
                    stepNumber = 1,
                    instruction = "Add 25 mL of 1.0 M Potassium Hydroxide (KOH) into the 250 mL beaker.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "KOH",
                    targetAmount = 25.0,
                    completionMessage = "Caustic alkaline solution prepared."
                ),
                ExperimentStep(
                    stepNumber = 2,
                    instruction = "Add 2.7 g of Aluminum metal (Al). Watch it dissolve vigorously with hydrogen gas bubbles.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "Al",
                    targetAmount = 2.7,
                    completionMessage = "Aluminum dissolves completely into potassium tetrahydroxoaluminate [KAl(OH)₄] with rapid H₂ gas evolution."
                ),
                ExperimentStep(
                    stepNumber = 3,
                    instruction = "Slowly add 50 mL of 1.0 M Sulfuric Acid (H₂SO₄) to acidify the solution and form potash alum.",
                    expectedAction = "ADD_CHEMICAL",
                    targetChemicalId = "H2SO4",
                    targetAmount = 50.0,
                    completionMessage = "✨ Exothermic neutralization occurs followed by rapid crystallization of sparkling octahedral Potash Alum crystals [KAl(SO₄)₂·12H₂O]!"
                )
            ),
            chemicalEquation = "2Al + 2KOH + 6H₂O → 2KAl(OH)₄ + 3H₂↑  then  KAl(OH)₄ + 2H₂SO₄ + 8H₂O → KAl(SO₄)₂·12H₂O↓",
            expectedObservation = "Effervescence of hydrogen gas as aluminum dissolves in KOH. Upon adding sulfuric acid, clear octahedral crystals of potash alum precipitate out as the solution cools.",
            educationalSummary = "Potash alum KAl(SO₄)₂·12H₂O is an isomorphous double salt containing equal proportions of K₂SO₄ and Al₂(SO₄)₃ with 24 waters of hydration per unit formula. Widely used as a styptic, mordant in dyeing, and water clarifying coagulant."
        )
    )

    fun getById(id: String): Experiment? = experiments.find { it.id == id }
}

