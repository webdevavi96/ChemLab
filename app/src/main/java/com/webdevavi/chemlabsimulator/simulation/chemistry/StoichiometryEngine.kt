package com.webdevavi.chemlabsimulator.simulation.chemistry

import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionCondition
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import com.webdevavi.chemlabsimulator.simulation.model.ReactionRule
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import kotlin.math.max
import kotlin.math.min

object StoichiometryEngine {

    val rules: List<ReactionRule> = listOf(
        // 1. HCl + NaOH -> NaCl + H2O
        ReactionRule(
            id = "r_hcl_naoh",
            name = "Hydrochloric Acid & Sodium Hydroxide Neutralization",
            type = ReactionType.NEUTRALIZATION,
            equationString = "HCl + NaOH → NaCl + H₂O",
            reactants = mapOf("HCl" to 1.0, "NaOH" to 1.0),
            products = mapOf("NaCl" to 1.0, "H2O" to 1.0),
            enthalpyDeltaH_kJ_mol = -57.3,
            description = "Strong acid-strong base neutralization yielding neutral table salt and water with significant exothermic heat generation.",
            observation = "Temperature rises rapidly; solution reaches neutral pH (7.0).",
            educationalNote = "Neutralization reaction: H⁺(aq) + OH⁻(aq) → H₂O(l) releasing 57.3 kJ/mol of energy."
        ),

        // 2. H2SO4 + 2NaOH -> Na2SO4 + 2H2O
        ReactionRule(
            id = "r_h2so4_naoh",
            name = "Sulfuric Acid Neutralization",
            type = ReactionType.NEUTRALIZATION,
            equationString = "H₂SO₄ + 2NaOH → Na₂SO₄ + 2H₂O",
            reactants = mapOf("H2SO4" to 1.0, "NaOH" to 2.0),
            products = mapOf("Na2SO4" to 1.0, "H2O" to 2.0),
            enthalpyDeltaH_kJ_mol = -114.6,
            description = "Diprotic sulfuric acid neutralization requiring 2 moles of base per mole of acid.",
            observation = "Exothermic temperature jump; neutral sulfate solution formed.",
            educationalNote = "Sulfuric acid provides two replaceable hydrogen ions per molecule."
        ),

        // 3. CH3COOH + NaOH -> CH3COONa + H2O
        ReactionRule(
            id = "r_ch3cooh_naoh",
            name = "Acetic Acid Titration with Sodium Hydroxide",
            type = ReactionType.NEUTRALIZATION,
            equationString = "CH₃COOH + NaOH → CH₃COONa + H₂O",
            reactants = mapOf("CH3COOH" to 1.0, "NaOH" to 1.0),
            products = mapOf("CH3COONa" to 1.0, "H2O" to 1.0),
            enthalpyDeltaH_kJ_mol = -55.8,
            description = "Weak acid-strong base reaction creating an acetate buffer mixture and mildly basic salt at equivalence.",
            observation = "Gradual pH rise; forms sodium acetate solution.",
            educationalNote = "Equivalence point occurs at pH > 7 (~8.9) due to the hydrolysis of acetate ions."
        ),

        // 4. HNO3 + KOH -> KNO3 + H2O
        ReactionRule(
            id = "r_hno3_koh",
            name = "Nitric Acid & Potassium Hydroxide Neutralization",
            type = ReactionType.NEUTRALIZATION,
            equationString = "HNO₃ + KOH → KNO₃ + H₂O",
            reactants = mapOf("HNO3" to 1.0, "KOH" to 1.0),
            products = mapOf("KNO3" to 1.0, "H2O" to 1.0),
            enthalpyDeltaH_kJ_mol = -57.3,
            description = "Direct neutralization forming soluble potassium nitrate.",
            observation = "Exothermic heat release with clear solution.",
            educationalNote = "Standard strong acid + strong base enthalpy."
        ),

        // 5. HCl + NH3 -> NH4Cl
        ReactionRule(
            id = "r_hcl_nh3",
            name = "Ammonia Neutralization with Hydrochloric Acid",
            type = ReactionType.NEUTRALIZATION,
            equationString = "HCl + NH₃ → NH₄Cl",
            reactants = mapOf("HCl" to 1.0, "NH3" to 1.0),
            products = mapOf("NH4Cl" to 1.0),
            enthalpyDeltaH_kJ_mol = -52.2,
            description = "Acid-base reaction forming mildly acidic ammonium chloride salt.",
            observation = "Slight warming; pH shifts toward acidic range (~5.1).",
            educationalNote = "Ammonium ion NH₄⁺ is the conjugate acid of weak base NH₃."
        ),

        // 6. Zn + 2HCl -> ZnCl2 + H2 (g)
        ReactionRule(
            id = "r_zn_hcl",
            name = "Zinc Metal in Hydrochloric Acid",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "Zn + 2HCl → ZnCl₂ + H₂↑",
            reactants = mapOf("Zn" to 1.0, "HCl" to 2.0),
            products = mapOf("ZnCl2" to 1.0, "H2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -152.4,
            gasProducedId = "H2_gas",
            description = "Single replacement reaction where electropositive zinc reduces hydrogen ions to hydrogen gas.",
            observation = "Vigorous effervescence of hydrogen gas bubbles, zinc metal slowly dissolves, temperature rises.",
            educationalNote = "Redox reaction: Zn⁰ is oxidized to Zn²⁺ while 2H⁺ are reduced to H₂(g)."
        ),

        // 7. Mg + 2HCl -> MgCl2 + H2 (g)
        ReactionRule(
            id = "r_mg_hcl",
            name = "Magnesium Metal in Hydrochloric Acid",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "Mg + 2HCl → MgCl₂ + H₂↑",
            reactants = mapOf("Mg" to 1.0, "HCl" to 2.0),
            products = mapOf("MgCl2" to 1.0, "H2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -467.0,
            gasProducedId = "H2_gas",
            description = "Violent single displacement reaction generating dense hydrogen gas and substantial heat.",
            observation = "Rapid and furious bubbling; magnesium ribbon rapidly disappears; high heat evolution.",
            educationalNote = "Highly exothermic due to magnesium's low electronegativity and standard reduction potential (-2.37 V)."
        ),

        // 8. Fe + 2HCl -> FeCl2 + H2 (g)
        ReactionRule(
            id = "r_fe_hcl",
            name = "Iron in Hydrochloric Acid",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "Fe + 2HCl → FeCl₂ + H₂↑",
            reactants = mapOf("Fe" to 1.0, "HCl" to 2.0),
            products = mapOf("FeCl2" to 1.0, "H2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -87.9,
            gasProducedId = "H2_gas",
            description = "Iron dissolves in acid to form pale green iron(II) chloride and hydrogen gas.",
            observation = "Moderate bubbling; solution takes on a pale greenish tinge.",
            educationalNote = "Iron forms ferrous (+2) chloride under non-oxidizing acid conditions."
        ),

        // 9. 2Al + 6HCl -> 2AlCl3 + 3H2 (g)
        ReactionRule(
            id = "r_al_hcl",
            name = "Aluminum in Hydrochloric Acid",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "2Al + 6HCl → 2AlCl₃ + 3H₂↑",
            reactants = mapOf("Al" to 2.0, "HCl" to 6.0),
            products = mapOf("AlCl3" to 2.0, "H2_gas" to 3.0),
            enthalpyDeltaH_kJ_mol = -1004.0,
            gasProducedId = "H2_gas",
            description = "Reaction of aluminum foil with acid to generate aluminum chloride and hydrogen.",
            observation = "Delay while oxide dissolves, followed by intense effervescence and steaming.",
            educationalNote = "Aluminum transfers 3 electrons per atom during oxidation."
        ),

        // 10. AgNO3 + NaCl -> AgCl (ppt) + NaNO3
        ReactionRule(
            id = "r_agno3_nacl",
            name = "Silver Chloride Precipitation",
            type = ReactionType.DOUBLE_DISPLACEMENT,
            equationString = "AgNO₃ + NaCl → AgCl↓ + NaNO₃",
            reactants = mapOf("AgNO3" to 1.0, "NaCl" to 1.0),
            products = mapOf("AgCl" to 1.0, "NaNO3" to 1.0),
            enthalpyDeltaH_kJ_mol = -65.7,
            precipitateProducedId = "AgCl",
            description = "Classic double displacement precipitation test for chloride ions.",
            observation = "Immediate formation of dense, white curdy precipitate (AgCl) that turns cloudy then settles.",
            educationalNote = "Net ionic equation: Ag⁺(aq) + Cl⁻(aq) → AgCl(s) with very low Ksp (1.8 × 10⁻¹⁰)."
        ),

        // 11. CuSO4 + 2NaOH -> Cu(OH)2 (ppt) + Na2SO4
        ReactionRule(
            id = "r_cuso4_naoh",
            name = "Copper(II) Hydroxide Precipitation",
            type = ReactionType.DOUBLE_DISPLACEMENT,
            equationString = "CuSO₄ + 2NaOH → Cu(OH)₂↓ + Na₂SO₄",
            reactants = mapOf("CuSO4" to 1.0, "NaOH" to 2.0),
            products = mapOf("Cu_OH_2" to 1.0, "Na2SO4" to 1.0),
            enthalpyDeltaH_kJ_mol = -48.2,
            precipitateProducedId = "Cu_OH_2",
            description = "Formation of characteristic gelatinous blue copper(II) hydroxide.",
            observation = "Blue solution immediately yields a thick, jelly-like bright cyan precipitate.",
            educationalNote = "Net ionic equation: Cu²⁺(aq) + 2OH⁻(aq) → Cu(OH)₂(s)."
        ),

        // 12. CaCl2 + Na2CO3 -> CaCO3 (ppt) + 2NaCl
        ReactionRule(
            id = "r_cacl2_na2co3",
            name = "Calcium Carbonate Precipitation",
            type = ReactionType.DOUBLE_DISPLACEMENT,
            equationString = "CaCl₂ + Na₂CO₃ → CaCO₃↓ + 2NaCl",
            reactants = mapOf("CaCl2" to 1.0, "Na2CO3" to 1.0),
            products = mapOf("CaCO3" to 1.0, "NaCl" to 2.0),
            enthalpyDeltaH_kJ_mol = -12.6,
            precipitateProducedId = "CaCO3",
            description = "Double replacement reaction producing insoluble calcium carbonate (chalk).",
            observation = "Clear solution turns milky white with fine chalky precipitate.",
            educationalNote = "Net ionic equation: Ca²⁺(aq) + CO₃²⁻(aq) → CaCO₃(s)."
        ),

        // 13. NaHCO3 + HCl -> NaCl + H2O + CO2 (g)
        ReactionRule(
            id = "r_nahco3_hcl",
            name = "Sodium Bicarbonate & Acid Effervescence",
            type = ReactionType.GAS_EVOLUTION,
            equationString = "NaHCO₃ + HCl → NaCl + H₂O + CO₂↑",
            reactants = mapOf("NaHCO3" to 1.0, "HCl" to 1.0),
            products = mapOf("NaCl" to 1.0, "H2O" to 1.0, "CO2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = 28.0, // Endothermic reaction!
            gasProducedId = "CO2_gas",
            description = "Endothermic acid-carbonate reaction that rapidly liberates carbon dioxide gas bubbles.",
            observation = "Vigorous fizzing and foaming of CO₂ gas; beaker feels noticeably cooler.",
            educationalNote = "Bicarbonate ions react with H⁺ to form unstable carbonic acid H₂CO₃, which spontaneously decomposes into H₂O and CO₂."
        ),

        // 14. Na2CO3 + 2HCl -> 2NaCl + H2O + CO2 (g)
        ReactionRule(
            id = "r_na2co3_hcl",
            name = "Sodium Carbonate & Acid Effervescence",
            type = ReactionType.GAS_EVOLUTION,
            equationString = "Na₂CO₃ + 2HCl → 2NaCl + H₂O + CO₂↑",
            reactants = mapOf("Na2CO3" to 1.0, "HCl" to 2.0),
            products = mapOf("NaCl" to 2.0, "H2O" to 1.0, "CO2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -26.0,
            gasProducedId = "CO2_gas",
            description = "Carbonate neutralization with rapid release of carbon dioxide gas.",
            observation = "Vigorous bubbling and effervescence of CO₂ gas.",
            educationalNote = "Two protons required per carbonate ion: CO₃²⁻ + 2H⁺ → H₂O + CO₂↑."
        ),

        // 15. CaCO3 + 2HCl -> CaCl2 + H2O + CO2 (g)
        ReactionRule(
            id = "r_caco3_hcl",
            name = "Calcium Carbonate Dissolution in Acid",
            type = ReactionType.GAS_EVOLUTION,
            equationString = "CaCO₃ + 2HCl → CaCl₂ + H₂O + CO₂↑",
            reactants = mapOf("CaCO3" to 1.0, "HCl" to 2.0),
            products = mapOf("CaCl2" to 1.0, "H2O" to 1.0, "CO2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -15.3,
            gasProducedId = "CO2_gas",
            description = "Chalk dissolves in hydrochloric acid releasing carbon dioxide gas.",
            observation = "White solid slowly dissolves while releasing steady streams of CO₂ bubbles.",
            educationalNote = "Geological model for acid rain dissolving limestone and marble."
        ),

        // 16. 2H2O2 -> 2H2O + O2 (g)
        ReactionRule(
            id = "r_h2o2_decomp",
            name = "Hydrogen Peroxide Decomposition",
            type = ReactionType.DECOMPOSITION,
            equationString = "2H₂O₂ → 2H₂O + O₂↑",
            reactants = mapOf("H2O2" to 2.0),
            products = mapOf("H2O" to 2.0, "O2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -98.2,
            conditions = ReactionCondition(minTemperatureCelsius = 35.0),
            gasProducedId = "O2_gas",
            description = "Thermal or catalytic decomposition of hydrogen peroxide into water and pure oxygen gas.",
            observation = "Steady bubbling of oxygen gas and warming upon gentle heating.",
            educationalNote = "Disproportionation reaction where oxygen is simultaneously oxidized and reduced."
        ),

        // 17. Potash Alum Step 1: 2Al + 2KOH + 6H2O -> 2KAl(OH)4 + 3H2↑
        ReactionRule(
            id = "r_al_koh_potash_step1",
            name = "Aluminum Dissolution in Potassium Hydroxide",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "2Al + 2KOH + 6H₂O → 2KAl(OH)₄ + 3H₂↑",
            reactants = mapOf("Al" to 2.0, "KOH" to 2.0),
            products = mapOf("KAl_OH_4" to 2.0, "H2_gas" to 3.0),
            enthalpyDeltaH_kJ_mol = -415.0,
            gasProducedId = "H2_gas",
            description = "Aluminum metal dissolves vigorously in potassium hydroxide releasing effervescent hydrogen gas and creating soluble potassium aluminate.",
            observation = "Rapid bubbling of hydrogen gas, heat generation, aluminum dissolves into clear liquid.",
            educationalNote = "First step in the classic industrial and educational synthesis of alum crystals from aluminum foil."
        ),

        // 18. Potash Alum Step 2: KAl(OH)4 + 2H2SO4 -> KAl(SO4)2 + 4H2O (Yields Potash Alum)
        ReactionRule(
            id = "r_potash_alum_acidification",
            name = "Potash Alum Double Salt Synthesis",
            type = ReactionType.COMPLEXATION,
            equationString = "KAl(OH)₄ + 2H₂SO₄ + 8H₂O → KAl(SO₄)₂·12H₂O↓",
            reactants = mapOf("KAl_OH_4" to 1.0, "H2SO4" to 2.0),
            products = mapOf("PotashAlum" to 1.0),
            enthalpyDeltaH_kJ_mol = -135.0,
            precipitateProducedId = "PotashAlum",
            description = "Acidification of aluminate with sulfuric acid precipitates shimmering transparent octahedral potash alum crystals.",
            observation = "Exothermic neutralization followed by rapid precipitation and growth of sparkling potash alum crystals.",
            educationalNote = "Potash alum KAl(SO₄)₂·12H₂O is an isomorphous dodecahydrate double salt with extensive uses in water purification and mordanting."
        ),

        // 19. Potash Alum Direct Double Salt Combination: K2SO4 + Al2(SO4)3 -> 2 KAl(SO4)2·12H2O
        ReactionRule(
            id = "r_potash_alum_combination",
            name = "Potash Alum Double Salt Crystallization",
            type = ReactionType.COMPLEXATION,
            equationString = "K₂SO₄ + Al₂(SO₄)₃ + 24H₂O → 2KAl(SO₄)₂·12H₂O↓",
            reactants = mapOf("K2SO4" to 1.0, "Al2_SO4_3" to 1.0),
            products = mapOf("PotashAlum" to 2.0),
            enthalpyDeltaH_kJ_mol = -28.0,
            precipitateProducedId = "PotashAlum",
            description = "Equimolar combination of potassium sulfate and aluminum sulfate yields crystalline potash alum double salt.",
            observation = "Formation of clear, well-defined octahedral alum crystals.",
            educationalNote = "Alums are characterized by the general formula M⁺M³⁺(SO₄)₂·12H₂O."
        ),

        // 20. Pyrotechnic Spontaneous Volcano Ignition: 14KMnO4 + 4 Glycerol -> 7 K2CO3 + 7 Mn2O3 + 5 CO2 + 16 H2O
        ReactionRule(
            id = "r_kmno4_glycerol_fire",
            name = "Permanganate-Glycerol Spontaneous Firecracker Ignition",
            type = ReactionType.COMBUSTION,
            equationString = "14KMnO₄ + 4C₃H₅(OH)₃ → 7K₂CO₃ + 7Mn₂O₃ + 5CO₂↑ + 16H₂O",
            reactants = mapOf("KMnO4" to 14.0, "Glycerol" to 4.0),
            products = mapOf("K2CO3" to 7.0, "Mn2O3" to 7.0, "CO2_gas" to 5.0, "H2O" to 16.0),
            enthalpyDeltaH_kJ_mol = -2150.0,
            gasProducedId = "CO2_gas",
            isBlast = true,
            blastIntensity = 0.95f,
            sparkColors = listOf(0xFF8B5CF6, 0xFFA855F7, 0xFFEC4899, 0xFFFDE047, 0xFFFFFFFF), // Violet, magenta, gold, diamond white
            description = "Spontaneous redox combustion where concentrated potassium permanganate oxidizes glycerol with an eruption of dazzling lilac and purple firecracker sparks.",
            observation = "Brief induction delay followed by intense purple flames, dense smoke plume, and popping firecracker sparks.",
            educationalNote = "Classic high-energy demonstration of spontaneous chemical ignition without an external match or burner."
        ),

        // 21. Thermite Reaction: Fe2O3 + 2Al -> Al2O3 + 2Fe
        ReactionRule(
            id = "r_thermite_al_fe2o3",
            name = "Thermite Reaction (Molten Iron Starburst)",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "Fe₂O₃ + 2Al → Al₂O₃ + 2Fe",
            reactants = mapOf("Fe2O3" to 1.0, "Al" to 2.0),
            products = mapOf("Al2O3" to 1.0, "Fe" to 2.0),
            enthalpyDeltaH_kJ_mol = -851.5,
            conditions = ReactionCondition(minTemperatureCelsius = 30.0),
            isBlast = true,
            blastIntensity = 1.0f,
            sparkColors = listOf(0xFFF97316, 0xFFFDE047, 0xFFFFFFFF, 0xFFEF4444), // Golden orange, yellow, bright white, red
            description = "Extremely exothermic aluminothermic reduction producing liquid molten iron at temperatures exceeding 2500°C and a cascade of dazzling starburst sparks.",
            observation = "Violent blinding eruption of molten metal sparks, extreme white-hot light, and superheated iron.",
            educationalNote = "Aluminum has an immense affinity for oxygen (high formation enthalpy of Al₂O₃), freeing molten elemental iron."
        ),

        // 22. Magnesium Burning Flash: 2Mg + O2 -> 2MgO
        ReactionRule(
            id = "r_mg_combustion_flash",
            name = "Magnesium Ribbon Blinding White Flash",
            type = ReactionType.COMBUSTION,
            equationString = "2Mg + O₂ → 2MgO",
            reactants = mapOf("Mg" to 2.0, "O2_gas" to 1.0),
            products = mapOf("MgO" to 2.0),
            enthalpyDeltaH_kJ_mol = -1203.4,
            conditions = ReactionCondition(minTemperatureCelsius = 30.0),
            isBlast = true,
            blastIntensity = 0.85f,
            sparkColors = listOf(0xFFFFFFFF, 0xFFE0F2FE, 0xFFBAE6FD, 0xFFFDE047), // Intense blinding diamond white sparks
            description = "Combustion of magnesium ribbon emitting brilliant, blinding UV-rich white light and diamond firecracker sparkle showers.",
            observation = "Intense blinding white flash accompanied by crackling white sparklers and fine white magnesium oxide smoke.",
            educationalNote = "Used historically in photographic flash powder and modern pyrotechnic white starburst flares."
        ),

        // 23. Strontium Crimson Red Firecracker Sparkler: Sr(NO3)2 + 2Mg -> SrO + 2MgO + N2
        ReactionRule(
            id = "r_strontium_pyrotechnic_crimson",
            name = "Strontium Crimson-Red Pyrotechnic Starburst",
            type = ReactionType.COMBUSTION,
            equationString = "Sr(NO₃)₂ + 2Mg → SrO + 2MgO + N₂↑",
            reactants = mapOf("Sr_NO3_2" to 1.0, "Mg" to 2.0),
            products = mapOf("SrO" to 1.0, "MgO" to 2.0),
            enthalpyDeltaH_kJ_mol = -980.0,
            conditions = ReactionCondition(minTemperatureCelsius = 30.0),
            isBlast = true,
            blastIntensity = 0.90f,
            sparkColors = listOf(0xFFEF4444, 0xFFDC2626, 0xFFB91C1C, 0xFFF87171, 0xFFFFFFFF), // Vivid crimson red sparks
            description = "Pyrotechnic combustion generating vivid crimson-red firecracker starburst particles from atomic strontium emission.",
            observation = "Vigorous crackling burst with intense crimson-red glowing spark trails and white flashes.",
            educationalNote = "Sr²⁺ ions emit intense characteristic red photons at 650-680 nm used in fireworks and flares."
        ),

        // 24. Barium Emerald Green Firecracker Sparkler: Ba(NO3)2 + 2Mg -> BaO + 2MgO + N2
        ReactionRule(
            id = "r_barium_pyrotechnic_green",
            name = "Barium Emerald-Green Pyrotechnic Starburst",
            type = ReactionType.COMBUSTION,
            equationString = "Ba(NO₃)₂ + 2Mg → BaO + 2MgO + N₂↑",
            reactants = mapOf("Ba_NO3_2" to 1.0, "Mg" to 2.0),
            products = mapOf("BaO" to 1.0, "MgO" to 2.0),
            enthalpyDeltaH_kJ_mol = -920.0,
            conditions = ReactionCondition(minTemperatureCelsius = 30.0),
            isBlast = true,
            blastIntensity = 0.90f,
            sparkColors = listOf(0xFF22C55E, 0xFF16A34A, 0xFF15803D, 0xFF4ADE80, 0xFFFFFFFF), // Vivid emerald green sparks
            description = "Pyrotechnic combustion generating luminous emerald-green firecracker sparks from atomic barium emission.",
            observation = "Brilliant emerald-green glowing starbursts and crackling sparks.",
            educationalNote = "Excited BaCl/BaO species in pyrotechnic flames emit strong characteristic green bands at 524-532 nm."
        ),

        // 25. Potassium Metal in Water: 2K + 2H2O -> 2KOH + H2
        ReactionRule(
            id = "r_potassium_water_lilac",
            name = "Potassium in Water (Lilac Firecracker Blast)",
            type = ReactionType.SINGLE_DISPLACEMENT,
            equationString = "2K + 2H₂O → 2KOH + H₂↑",
            reactants = mapOf("K" to 2.0, "H2O" to 2.0),
            products = mapOf("KOH" to 2.0, "H2_gas" to 1.0),
            enthalpyDeltaH_kJ_mol = -392.0,
            gasProducedId = "H2_gas",
            isBlast = true,
            blastIntensity = 0.90f,
            sparkColors = listOf(0xFFA855F7, 0xFFC084FC, 0xFF9333EA, 0xFFFDE047, 0xFFFFFFFF), // Lilac violet and golden sparks
            description = "Violent alkali metal reaction where potassium melts into a skimming bead and ignites hydrogen with a signature lilac flame and popping explosion.",
            observation = "Potassium skates rapidly on water, burns with an intense lilac-purple flame, and pops with a loud sharp crack!",
            educationalNote = "The potassium flame color is caused by the 766.5 nm and 769.9 nm doublet spectral emission lines."
        ),

        // 26. Radioactive Decay: Uranium-238 Alpha Decay
        ReactionRule(
            id = "r_u238_alpha_decay",
            name = "Uranium-238 Alpha Disintegration",
            type = ReactionType.NUCLEAR_DECAY,
            equationString = "²³⁸U → ²³⁴Th + α (⁴He²⁺)",
            reactants = mapOf("U" to 1.0),
            products = mapOf("Th234" to 1.0, "He4_alpha" to 1.0),
            enthalpyDeltaH_kJ_mol = -4.1,
            isBlast = false,
            sparkColors = listOf(0xFF22C55E, 0xFF4ADE80, 0xFF67E8F9, 0xFFFFFFFF), // Cherenkov green-cyan ionizing sparks
            description = "Spontaneous nuclear alpha decay emitting a high-energy 4.27 MeV alpha particle (helium-4 nucleus) and transmuting into thorium-234.",
            observation = "Intense fluorescent Cherenkov radiation glow, ionizing gamma shockwaves, and continuous particle emission.",
            educationalNote = "²³⁸₉₂U → ²³⁴₉₀Th + ⁴₂He. Alpha decay decreases mass number by 4 and atomic number by 2."
        ),

        // 27. Radioactive Decay: Thorium-234 Beta Decay
        ReactionRule(
            id = "r_th234_beta_decay",
            name = "Thorium-234 Beta Disintegration",
            type = ReactionType.NUCLEAR_DECAY,
            equationString = "²³⁴Th → ²³⁴Pa + β⁻",
            reactants = mapOf("Th234" to 1.0),
            products = mapOf("Pa234" to 1.0, "Beta_minus" to 1.0),
            enthalpyDeltaH_kJ_mol = -2.6,
            sparkColors = listOf(0xFF06B6D4, 0xFF38BDF8, 0xFF86EFAC, 0xFFFFFFFF), // Cyan-green beta streaks
            description = "Nuclear beta-minus decay where a neutron in the thorium nucleus converts into a proton, ejecting a relativistic beta electron.",
            observation = "High-speed ionizing beta particle ionization tracks radiating radially outward.",
            educationalNote = "²³⁴₉₀Th → ²³⁴₉₁Pa + e⁻ + ν̄ₑ. Beta decay preserves mass number while increasing atomic number by 1."
        ),

        // 28. Radioactive Decay: Radium-226 Alpha Decay
        ReactionRule(
            id = "r_ra226_alpha_decay",
            name = "Radium-226 Alpha Decay to Radon Gas",
            type = ReactionType.NUCLEAR_DECAY,
            equationString = "²²⁶Ra → ²²²Rn↑ + α (⁴He²⁺)",
            reactants = mapOf("Ra" to 1.0),
            products = mapOf("Rn222" to 1.0, "He4_alpha" to 1.0),
            enthalpyDeltaH_kJ_mol = -4.7,
            gasProducedId = "Rn222",
            sparkColors = listOf(0xFF10B981, 0xFF34D399, 0xFF06B6D4, 0xFFFFFFFF),
            description = "Radium-226 nucleus undergoes alpha decay liberating heavy radioactive radon gas and 4.87 MeV alpha radiation.",
            observation = "Luminescent radioactive radon gas emanates with vivid ionizing particle flares.",
            educationalNote = "Discovered by Marie and Pierre Curie; fundamental step in the uranium decay series."
        ),

        // 29. Radioactive Decay: Polonium-210 Alpha Decay
        ReactionRule(
            id = "r_po210_alpha_decay",
            name = "Polonium-210 Alpha Transmutation to Stable Lead",
            type = ReactionType.NUCLEAR_DECAY,
            equationString = "²¹⁰Po → ²⁰⁶Pb + α (⁴He²⁺)",
            reactants = mapOf("Po" to 1.0),
            products = mapOf("Pb206" to 1.0, "He4_alpha" to 1.0),
            enthalpyDeltaH_kJ_mol = -5.1,
            sparkColors = listOf(0xFF22C55E, 0xFF86EFAC, 0xFFFDE047, 0xFFFFFFFF),
            description = "Intense alpha-emitter polonium-210 decays directly to stable, non-radioactive lead-206 with release of 5.3 MeV alpha particles.",
            observation = "High thermal self-heating accompanied by intense alpha particle ejection terminating in stable lead.",
            educationalNote = "Terminal alpha step of the uranium decay chain producing primordial stable lead ²⁰⁶₈₂Pb."
        ),

        // 30. Radioactive Decay: Plutonium-239 Alpha Decay
        ReactionRule(
            id = "r_pu239_alpha_decay",
            name = "Plutonium-239 Alpha Transmutation to Uranium-235",
            type = ReactionType.NUCLEAR_DECAY,
            equationString = "²³⁹Pu → ²³⁵U + α (⁴He²⁺)",
            reactants = mapOf("Pu239" to 1.0),
            products = mapOf("U235" to 1.0, "He4_alpha" to 1.0),
            enthalpyDeltaH_kJ_mol = -5.0,
            sparkColors = listOf(0xFF059669, 0xFF10B981, 0xFF6EE7B7, 0xFFFFFFFF),
            description = "Transuranic actinide alpha decay transmuting plutonium-239 into uranium-235.",
            observation = "Persistent Cherenkov glow and alpha particle track emission.",
            educationalNote = "²³⁹₉₄Pu → ²³⁵₉₂U + ⁴₂He."
        ),

        // 34. Direct Elemental Synthesis: Na + Cl -> NaCl (2Na + Cl2 -> 2NaCl)
        ReactionRule(
            id = "r_na_cl_synthesis",
            name = "Sodium and Chlorine Combustion Synthesis",
            type = ReactionType.COMBUSTION,
            equationString = "Na + Cl → NaCl",
            reactants = mapOf("Na" to 1.0, "Cl" to 1.0),
            products = mapOf("NaCl" to 1.0),
            enthalpyDeltaH_kJ_mol = -411.2,
            isBlast = true,
            blastIntensity = 0.88f,
            sparkColors = listOf(0xFFF59E0B, 0xFFFBBF24, 0xFFFFFFFF),
            description = "Direct energetic reaction between elemental sodium metal and chlorine to synthesize pure sodium chloride (table salt).",
            observation = "Sodium ignites with a blinding yellow flame, releasing billowing clouds of white salt smoke and intense heat shockwave.",
            educationalNote = "Classic ionic synthesis: 2Na(s) + Cl₂(g) → 2NaCl(s), ΔH° = -411.2 kJ/mol."
        ),

        // 35. 2Na + Cl2_gas -> 2NaCl
        ReactionRule(
            id = "r_na_cl2_gas_synthesis",
            name = "Sodium Metal in Chlorine Gas",
            type = ReactionType.COMBUSTION,
            equationString = "2Na + Cl₂ → 2NaCl",
            reactants = mapOf("Na" to 2.0, "Cl2_gas" to 1.0),
            products = mapOf("NaCl" to 2.0),
            enthalpyDeltaH_kJ_mol = -822.4,
            isBlast = true,
            blastIntensity = 0.90f,
            sparkColors = listOf(0xFFF59E0B, 0xFFFBBF24, 0xFFFFFFFF),
            description = "Violent reaction of solid sodium placed into greenish-yellow chlorine gas cylinder.",
            observation = "Immediate explosive yellow fireball, dense white sodium chloride smoke.",
            educationalNote = "Highly exothermic electron transfer from alkali metal (Na) to halogen (Cl₂)."
        ),

        // 36. 2Mg + O2_gas -> 2MgO (or Mg + O -> MgO)
        ReactionRule(
            id = "r_mg_o_synthesis",
            name = "Magnesium Metal Combustion in Oxygen",
            type = ReactionType.COMBUSTION,
            equationString = "Mg + O → MgO",
            reactants = mapOf("Mg" to 1.0, "O" to 1.0),
            products = mapOf("MgO" to 1.0),
            enthalpyDeltaH_kJ_mol = -601.7,
            isBlast = true,
            blastIntensity = 0.82f,
            sparkColors = listOf(0xFFFFFFFF, 0xFFF8FAFC, 0xFFF1F5F9),
            description = "Combustion of magnesium ribbon in oxygen forming white magnesium oxide ash.",
            observation = "Blinding, brilliant white light and high heat emission.",
            educationalNote = "2Mg(s) + O₂(g) → 2MgO(s), ΔH° = -601.7 kJ/mol."
        ),

        // 37. Fe + S -> FeS
        ReactionRule(
            id = "r_fe_s_synthesis",
            name = "Iron and Sulfur Synthesis",
            type = ReactionType.COMBUSTION,
            equationString = "Fe + S → FeS",
            reactants = mapOf("Fe" to 1.0, "S" to 1.0),
            products = mapOf("FeS" to 1.0),
            enthalpyDeltaH_kJ_mol = -100.0,
            sparkColors = listOf(0xFFEAB308, 0xFFF97316),
            description = "Exothermic solid-state synthesis of iron(II) sulfide from iron filings and sulfur powder.",
            observation = "Glows with a sustained red-hot heat once initiated, forming a dark black crust of iron(II) sulfide.",
            educationalNote = "Classic textbook example of a chemical change creating a new compound from a physical mixture."
        )
    )

    data class ReactionEvaluationResult(
        val updatedSubstances: List<SubstanceState>,
        val events: List<ReactionResult>,
        val totalHeatJoules: Double,
        val totalGasMolesProduced: Double,
        val totalPrecipitateGramsProduced: Double
    )

    /**
     * Evaluates all potential chemical reactions in the container based on available molar quantities,
     * checking both registered library rules and dynamically inferred chemistry rules.
     */
    fun evaluateReactions(
        substances: List<SubstanceState>,
        currentTemperatureC: Double,
        ambientTempC: Double = 20.0,
        dtSeconds: Double = 1.0
    ): ReactionEvaluationResult {
        var activeSubstances = substances.toMutableList()
        val triggeredEvents = mutableListOf<ReactionResult>()
        var accumulatedHeatJoules = 0.0
        var accumulatedGasMoles = 0.0
        var accumulatedPrecipitateGrams = 0.0

        // Gather static rules plus dynamically inferred chemistry rules for any unreacted combinations
        val dynamicRules = ChemistryReactionAlgorithm.findDynamicReactions(activeSubstances, currentTemperatureC)
        val allRules = (rules + dynamicRules).distinctBy { it.id }

        for (rule in allRules) {
            // Check condition constraints
            if (currentTemperatureC < rule.conditions.minTemperatureCelsius ||
                currentTemperatureC > rule.conditions.maxTemperatureCelsius) {
                continue
            }

            // Check if all reactants are present
            val reactantStates = mutableMapOf<String, SubstanceState>()
            var allReactantsPresent = true

            for ((reactantId, _) in rule.reactants) {
                val found = activeSubstances.find { it.chemicalId == reactantId && it.moles > 1e-7 }
                if (found == null) {
                    allReactantsPresent = false
                    break
                }
                reactantStates[reactantId] = found
            }

            if (!allReactantsPresent) continue

            // Determine limiting reactant and extent of reaction
            var limitingReagentId = ""
            var minMolesRatio = Double.POSITIVE_INFINITY

            for ((reactantId, coeff) in rule.reactants) {
                val sub = reactantStates[reactantId]!!
                val ratio = sub.moles / coeff
                if (ratio < minMolesRatio) {
                    minMolesRatio = ratio
                    limitingReagentId = reactantId
                }
            }

            if (minMolesRatio <= 1e-8) continue

            // Kinetic rate calculation (homogeneous vs solid-liquid heterogeneous)
            val isSolidLiquid = reactantStates.values.any { it.phase == Phase.SOLID || it.phase == Phase.PRECIPITATE }
            val rateConstant = when {
                rule.type == ReactionType.NUCLEAR_DECAY -> 0.005 // Continuous steady nuclear decay
                rule.type == ReactionType.NEUTRALIZATION || rule.type == ReactionType.DOUBLE_DISPLACEMENT || rule.type == ReactionType.DISSOLUTION || rule.type == ReactionType.GAS_EVOLUTION || rule.type == ReactionType.COMPLEXATION -> 1.0 // Instantaneous aqueous precipitation, neutralization, and gas evolution
                rule.isBlast || rule.type == ReactionType.COMBUSTION -> 0.95 // Rapid firecracker combustion
                isSolidLiquid -> 0.40 // Dissolution limited
                else -> 1.0
            }

            val maxExtent = minMolesRatio
            val extentOfReaction = (maxExtent * rateConstant * dtSeconds).coerceAtMost(maxExtent)
            val limitingMolesReacted = extentOfReaction * (rule.reactants[limitingReagentId] ?: 1.0)

            if (extentOfReaction <= 1e-9) continue

            // Consume reactants
            for ((reactantId, coeff) in rule.reactants) {
                val molesConsumed = coeff * extentOfReaction
                val existing = activeSubstances.find { it.chemicalId == reactantId }
                if (existing != null) {
                    val newMoles = max(0.0, existing.moles - molesConsumed)
                    val chem = ChemicalRegistry.get(reactantId)
                    val molarMass = chem?.molarMass ?: 50.0
                    val density = chem?.density ?: 1.0
                    val newMass = newMoles * molarMass
                    val newVolume = if (existing.phase == Phase.SOLID) newMass / density else max(0.0, existing.volumeMl * (newMoles / existing.moles))

                    val idx = activeSubstances.indexOf(existing)
                    if (newMoles <= 1e-8) {
                        activeSubstances.removeAt(idx)
                    } else {
                        activeSubstances[idx] = existing.copy(
                            moles = newMoles,
                            massGrams = newMass,
                            volumeMl = newVolume
                        )
                    }
                }
            }

            // Produce products
            var precipitateGrams = 0.0
            var gasMoles = 0.0

            for ((productId, coeff) in rule.products) {
                val molesProduced = coeff * extentOfReaction
                val chem = ChemicalRegistry.get(productId) ?: continue
                val massProduced = molesProduced * chem.molarMass
                val volumeProduced = if (chem.density > 0) massProduced / chem.density else 0.0

                val isGas = productId == rule.gasProducedId || chem.defaultPhase == Phase.GAS
                val isPpt = productId == rule.precipitateProducedId || chem.defaultPhase == Phase.PRECIPITATE

                if (isGas) {
                    gasMoles += molesProduced
                }
                if (isPpt) {
                    precipitateGrams += massProduced
                }

                val existingProductIdx = activeSubstances.indexOfFirst { it.chemicalId == productId }
                if (existingProductIdx >= 0) {
                    val existing = activeSubstances[existingProductIdx]
                    val updatedMoles = existing.moles + molesProduced
                    val updatedMass = existing.massGrams + massProduced
                    val updatedVolume = existing.volumeMl + volumeProduced
                    activeSubstances[existingProductIdx] = existing.copy(
                        moles = updatedMoles,
                        massGrams = updatedMass,
                        volumeMl = updatedVolume,
                        isPrecipitated = isPpt,
                        isGasVapor = isGas
                    )
                } else {
                    activeSubstances.add(
                        SubstanceState(
                            chemicalId = productId,
                            massGrams = massProduced,
                            volumeMl = volumeProduced,
                            moles = molesProduced,
                            phase = chem.defaultPhase,
                            isPrecipitated = isPpt,
                            isGasVapor = isGas
                        )
                    )
                }
            }

            // Energy & Thermodynamics calculation
            // q = -ΔH * extent (ΔH is in kJ/mol, convert to Joules)
            val heatJoules = -rule.enthalpyDeltaH_kJ_mol * extentOfReaction * 1000.0
            accumulatedHeatJoules += heatJoules
            accumulatedGasMoles += gasMoles
            accumulatedPrecipitateGrams += precipitateGrams

            // Calculate temperature change: ΔT = q / (m * c)
            var totalHeatCapacity = 0.0
            for (sub in activeSubstances) {
                val chem = ChemicalRegistry.get(sub.chemicalId)
                val c = chem?.specificHeatCapacity ?: 4.184
                totalHeatCapacity += sub.massGrams * c
            }
            if (totalHeatCapacity < 1.0) totalHeatCapacity = 100.0 // Default baseline
            val deltaT = heatJoules / totalHeatCapacity

            // Energetic blast / shockwave detection for explosive or violent exothermic reactions
            val isViolentBlast = rule.isBlast || rule.id == "r_mg_hcl" || (heatJoules > 12000.0 && rule.gasProducedId != null) || rule.id == "r_al_hcl" || rule.id.startsWith("r_thermite") || rule.id.startsWith("r_kmno4_glycerol") || rule.id.startsWith("r_potassium_water") || rule.id.contains("pyrotechnic")
            val blastIntensity = if (rule.blastIntensity > 0f) {
                rule.blastIntensity
            } else if (isViolentBlast) {
                (heatJoules / 20000.0).toFloat().coerceIn(0.65f, 1.0f)
            } else 0f

            triggeredEvents.add(
                ReactionResult(
                    reactionId = rule.id,
                    reactionName = rule.name,
                    equationString = rule.equationString,
                    reactionType = rule.type,
                    limitingReagentId = limitingReagentId,
                    limitingMolesReacted = limitingMolesReacted,
                    extentOfReaction = extentOfReaction,
                    heatReleasedJoules = heatJoules,
                    temperatureChangeCelsius = deltaT,
                    precipitateFormedId = rule.precipitateProducedId,
                    precipitateGrams = precipitateGrams,
                    gasFormedId = rule.gasProducedId,
                    gasMoles = gasMoles,
                    isBlast = isViolentBlast,
                    blastIntensity = blastIntensity,
                    sparkColors = rule.sparkColors,
                    description = rule.observation
                )
            )
        }

        return ReactionEvaluationResult(
            updatedSubstances = activeSubstances,
            events = triggeredEvents,
            totalHeatJoules = accumulatedHeatJoules,
            totalGasMolesProduced = accumulatedGasMoles,
            totalPrecipitateGramsProduced = accumulatedPrecipitateGrams
        )
    }
}
