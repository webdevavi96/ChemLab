package com.webdevavi.chemlabsimulator.simulation.chemistry

import com.webdevavi.chemlabsimulator.simulation.model.Chemical
import com.webdevavi.chemlabsimulator.simulation.model.HazardType
import com.webdevavi.chemlabsimulator.simulation.model.Phase
import com.webdevavi.chemlabsimulator.simulation.model.ReactionCondition
import com.webdevavi.chemlabsimulator.simulation.model.ReactionRule
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
import com.webdevavi.chemlabsimulator.simulation.model.SubstanceState
import kotlin.math.abs
import kotlin.math.max

/**
 * Standard Chemistry Reaction and Reactivity Assessment Algorithm.
 * Implements fundamental principles of the Periodic Table, oxidation states,
 * electrochemical activity series, electronegativity gradients, solubility rules,
 * and stoichiometric balancing to dynamically evaluate and predict chemical reactions.
 */
object ChemistryReactionAlgorithm {

    data class ElementProfile(
        val symbol: String,
        val name: String,
        val atomicNumber: Int,
        val group: Int,
        val period: Int,
        val electronegativity: Double,
        val defaultValency: Int,
        val commonValencies: List<Int>,
        val isMetal: Boolean,
        val isNonmetal: Boolean,
        val isMetalloid: Boolean,
        val isNobleGas: Boolean,
        val isHalogen: Boolean,
        val isAlkaliMetal: Boolean,
        val isAlkalineEarth: Boolean,
        val standardReductionPotential: Double, // E° (Volts)
        val flameColorHex: Long? = null,
        val sparkColors: List<Long> = emptyList()
    )

    data class ReactivityAssessment(
        val isReactive: Boolean,
        val reactionType: ReactionType?,
        val predictedEquation: String?,
        val explanation: String,
        val isBlast: Boolean = false,
        val primaryProduct: String? = null
    )

    private val elementProfiles = mutableMapOf<String, ElementProfile>()

    init {
        registerElementProfiles()
    }

    private fun registerElementProfiles() {
        fun add(
            sym: String, name: String, z: Int, grp: Int, per: Int, en: Double,
            valency: Int, valencies: List<Int>,
            metal: Boolean, nonmetal: Boolean, metalloid: Boolean, noble: Boolean,
            halogen: Boolean, alkali: Boolean, alkEarth: Boolean,
            e0: Double, flameColor: Long? = null, sparks: List<Long> = emptyList()
        ) {
            elementProfiles[sym] = ElementProfile(
                symbol = sym, name = name, atomicNumber = z, group = grp, period = per,
                electronegativity = en, defaultValency = valency, commonValencies = valencies,
                isMetal = metal, isNonmetal = nonmetal, isMetalloid = metalloid,
                isNobleGas = noble, isHalogen = halogen, isAlkaliMetal = alkali,
                isAlkalineEarth = alkEarth, standardReductionPotential = e0,
                flameColorHex = flameColor, sparkColors = sparks
            )
        }

        // Period 1
        add("H", "Hydrogen", 1, 1, 1, 2.20, 1, listOf(1), false, true, false, false, false, false, false, 0.00, 0xFF38BDF8)
        add("He", "Helium", 2, 18, 1, 0.0, 0, listOf(0), false, false, false, true, false, false, false, 99.0)

        // Period 2
        add("Li", "Lithium", 3, 1, 2, 0.98, 1, listOf(1), true, false, false, false, false, true, false, -3.04, 0xFFEF4444, listOf(0xFFEF4444))
        add("Be", "Beryllium", 4, 2, 2, 1.57, 2, listOf(2), true, false, false, false, false, false, true, -1.85, 0xFF94A3B8)
        add("B", "Boron", 5, 13, 2, 2.04, 3, listOf(3), false, false, true, false, false, false, false, -0.87, 0xFF22C55E)
        add("C", "Carbon", 6, 14, 2, 2.55, 4, listOf(2, 4), false, true, false, false, false, false, false, 0.52, 0xFFF97316)
        add("N", "Nitrogen", 7, 15, 2, 3.04, 3, listOf(3, 5), false, true, false, false, false, false, false, 0.0)
        add("O", "Oxygen", 8, 16, 2, 3.44, 2, listOf(2), false, true, false, false, false, false, false, 1.23, 0xFF38BDF8)
        add("F", "Fluorine", 9, 17, 2, 3.98, 1, listOf(1), false, true, false, false, true, false, false, 2.87, 0xFFFACC15)
        add("Ne", "Neon", 10, 18, 2, 0.0, 0, listOf(0), false, false, false, true, false, false, false, 99.0)

        // Period 3
        add("Na", "Sodium", 11, 1, 3, 0.93, 1, listOf(1), true, false, false, false, false, true, false, -2.71, 0xFFF59E0B, listOf(0xFFF59E0B, 0xFFFBBF24))
        add("Mg", "Magnesium", 12, 2, 3, 1.31, 2, listOf(2), true, false, false, false, false, false, true, -2.37, 0xFFFFFFFF, listOf(0xFFFFFFFF, 0xFFF8FAFC))
        add("Al", "Aluminum", 13, 13, 3, 1.61, 3, listOf(3), true, false, false, false, false, false, false, -1.66, 0xFFF8FAFC, listOf(0xFFF8FAFC, 0xFFE2E8F0))
        add("Si", "Silicon", 14, 14, 3, 1.90, 4, listOf(4), false, false, true, false, false, false, false, -0.86)
        add("P", "Phosphorus", 15, 15, 3, 2.19, 3, listOf(3, 5), false, true, false, false, false, false, false, -0.06, 0xFFF97316)
        add("S", "Sulfur", 16, 16, 3, 2.58, 2, listOf(2, 4, 6), false, true, false, false, false, false, false, 0.14, 0xFF0284C7, listOf(0xFF38BDF8))
        add("Cl", "Chlorine", 17, 17, 3, 3.16, 1, listOf(1), false, true, false, false, true, false, false, 1.36, 0xFFA3E635)
        add("Ar", "Argon", 18, 18, 3, 0.0, 0, listOf(0), false, false, false, true, false, false, false, 99.0)

        // Period 4
        add("K", "Potassium", 19, 1, 4, 0.82, 1, listOf(1), true, false, false, false, false, true, false, -2.93, 0xFFA855F7, listOf(0xFFA855F7, 0xFFC084FC))
        add("Ca", "Calcium", 20, 2, 4, 1.00, 2, listOf(2), true, false, false, false, false, false, true, -2.87, 0xFFEA580C, listOf(0xFFFB923C))
        add("Sc", "Scandium", 21, 3, 4, 1.36, 3, listOf(3), true, false, false, false, false, false, false, -2.08)
        add("Ti", "Titanium", 22, 4, 4, 1.54, 4, listOf(4), true, false, false, false, false, false, false, -1.63, 0xFFFFFFFF, listOf(0xFFFFFFFF))
        add("V", "Vanadium", 23, 5, 4, 1.63, 5, listOf(3, 5), true, false, false, false, false, false, false, -1.18)
        add("Cr", "Chromium", 24, 6, 4, 1.66, 3, listOf(3, 6), true, false, false, false, false, false, false, -0.74, 0xFF10B981)
        add("Mn", "Manganese", 25, 7, 4, 1.55, 2, listOf(2, 4, 7), true, false, false, false, false, false, false, -1.18, 0xFF9333EA)
        add("Fe", "Iron", 26, 8, 4, 1.83, 2, listOf(2, 3), true, false, false, false, false, false, false, -0.44, 0xFFEAB308, listOf(0xFFEAB308, 0xFFCA8A04))
        add("Co", "Cobalt", 27, 9, 4, 1.88, 2, listOf(2, 3), true, false, false, false, false, false, false, -0.28)
        add("Ni", "Nickel", 28, 10, 4, 1.91, 2, listOf(2), true, false, false, false, false, false, false, -0.26)
        add("Cu", "Copper", 29, 11, 4, 1.90, 2, listOf(1, 2), true, false, false, false, false, false, false, 0.34, 0xFF059669, listOf(0xFF10B981, 0xFF06B6D4))
        add("Zn", "Zinc", 30, 12, 4, 1.65, 2, listOf(2), true, false, false, false, false, false, false, -0.76, 0xFF6EE7B7, listOf(0xFF6EE7B7))
        add("Ga", "Gallium", 31, 13, 4, 1.81, 3, listOf(3), true, false, false, false, false, false, false, -0.56)
        add("Ge", "Germanium", 32, 14, 4, 2.01, 4, listOf(4), false, false, true, false, false, false, false, 0.12)
        add("As", "Arsenic", 33, 15, 4, 2.18, 3, listOf(3, 5), false, false, true, false, false, false, false, 0.30)
        add("Se", "Selenium", 34, 16, 4, 2.55, 2, listOf(2, 4, 6), false, true, false, false, false, false, false, 0.74, 0xFF0284C7)
        add("Br", "Bromine", 35, 17, 4, 2.96, 1, listOf(1), false, true, false, false, true, false, false, 1.07, 0xFFB45309)
        add("Kr", "Krypton", 36, 18, 4, 3.00, 0, listOf(0), false, false, false, true, false, false, false, 99.0)

        // Period 5
        add("Rb", "Rubidium", 37, 1, 5, 0.82, 1, listOf(1), true, false, false, false, false, true, false, -2.98, 0xFF9333EA, listOf(0xFFA855F7))
        add("Sr", "Strontium", 38, 2, 5, 0.95, 2, listOf(2), true, false, false, false, false, false, true, -2.89, 0xFFDC2626, listOf(0xFFEF4444, 0xFFF87171))
        add("Ag", "Silver", 47, 11, 5, 1.93, 1, listOf(1), true, false, false, false, false, false, false, 0.80)
        add("Cd", "Cadmium", 48, 12, 5, 1.69, 2, listOf(2), true, false, false, false, false, false, false, -0.40)
        add("Sn", "Tin", 50, 14, 5, 1.96, 2, listOf(2, 4), true, false, false, false, false, false, false, -0.14)
        add("Sb", "Antimony", 51, 15, 5, 2.05, 3, listOf(3, 5), false, false, true, false, false, false, false, 0.15)
        add("I", "Iodine", 53, 17, 5, 2.66, 1, listOf(1), false, true, false, false, true, false, false, 0.54, 0xFF7C3AED)
        add("Xe", "Xenon", 54, 18, 5, 2.60, 0, listOf(0), false, false, false, true, false, false, false, 99.0)

        // Period 6
        add("Cs", "Cesium", 55, 1, 6, 0.79, 1, listOf(1), true, false, false, false, false, true, false, -3.03, 0xFF3B82F6, listOf(0xFF60A5FA))
        add("Ba", "Barium", 56, 2, 6, 0.89, 2, listOf(2), true, false, false, false, false, false, true, -2.91, 0xFF16A34A, listOf(0xFF22C55E, 0xFF86EFAC))
        add("Pt", "Platinum", 78, 10, 6, 2.28, 2, listOf(2, 4), true, false, false, false, false, false, false, 1.18)
        add("Au", "Gold", 79, 11, 6, 2.54, 3, listOf(1, 3), true, false, false, false, false, false, false, 1.50)
        add("Hg", "Mercury", 80, 12, 6, 2.00, 2, listOf(1, 2), true, false, false, false, false, false, false, 0.85)
        add("Pb", "Lead", 82, 14, 6, 2.33, 2, listOf(2, 4), true, false, false, false, false, false, false, -0.13)
        add("Bi", "Bismuth", 83, 15, 6, 2.02, 3, listOf(3), true, false, false, false, false, false, false, 0.32)
        add("Rn", "Radon", 86, 18, 6, 2.20, 0, listOf(0), false, false, false, true, false, false, false, 99.0)

        // Period 7
        add("Fr", "Francium", 87, 1, 7, 0.70, 1, listOf(1), true, false, false, false, false, true, false, -2.90)
        add("Ra", "Radium", 88, 2, 7, 0.90, 2, listOf(2), true, false, false, false, false, false, true, -2.92, 0xFFDC2626)
        add("U", "Uranium", 92, 3, 7, 1.38, 4, listOf(4, 6), true, false, false, false, false, false, false, -1.80)
        add("Pu", "Plutonium", 94, 3, 7, 1.28, 4, listOf(3, 4), true, false, false, false, false, false, false, -2.00)
    }

    fun getElementProfile(idOrSymbol: String): ElementProfile? {
        val sym = when (idOrSymbol) {
            "H2_gas", "H2" -> "H"
            "O2_gas", "O2" -> "O"
            "N2_gas", "N2" -> "N"
            "Cl2_gas", "Cl2" -> "Cl"
            "F2_gas", "F2" -> "F"
            "Br2_liq", "Br2" -> "Br"
            "I2_solid", "I2" -> "I"
            else -> idOrSymbol
        }
        return elementProfiles[sym] ?: ChemicalRegistry.get(idOrSymbol)?.let { chem ->
            if (chem.isElement) {
                ElementProfile(
                    symbol = chem.formula,
                    name = chem.name,
                    atomicNumber = chem.atomicNumber ?: 1,
                    group = chem.periodicGroup ?: 1,
                    period = chem.periodicPeriod ?: 1,
                    electronegativity = 1.5,
                    defaultValency = 2,
                    commonValencies = listOf(2),
                    isMetal = chem.elementCategory?.contains("Metal", ignoreCase = true) == true,
                    isNonmetal = chem.elementCategory?.contains("Nonmetal", ignoreCase = true) == true,
                    isMetalloid = chem.elementCategory?.contains("Metalloid", ignoreCase = true) == true,
                    isNobleGas = chem.elementCategory?.contains("Noble", ignoreCase = true) == true,
                    isHalogen = chem.elementCategory?.contains("Halogen", ignoreCase = true) == true,
                    isAlkaliMetal = chem.elementCategory?.contains("Alkali Metal", ignoreCase = true) == true,
                    isAlkalineEarth = chem.elementCategory?.contains("Alkaline Earth", ignoreCase = true) == true,
                    standardReductionPotential = if (chem.elementCategory?.contains("Metal") == true) -1.0 else 0.5
                )
            } else null
        }
    }

    private fun gcd(a: Int, b: Int): Int {
        var x = abs(a)
        var y = abs(b)
        while (y != 0) {
            val t = y
            y = x % y
            x = t
        }
        return if (x == 0) 1 else x
    }

    /**
     * Checks whether two chemical substances are reactive under standard chemistry principles,
     * explaining the scientific rationale and predicting the balanced equation.
     */
    fun assessReactivity(chemA: Chemical, chemB: Chemical): ReactivityAssessment {
        if (chemA.id == chemB.id) {
            return ReactivityAssessment(
                isReactive = false,
                reactionType = null,
                predictedEquation = null,
                explanation = "Identical chemical species present in homogenous phase (No chemical reaction)."
            )
        }

        val elemA = getElementProfile(chemA.id)
        val elemB = getElementProfile(chemB.id)

        // Case 1: Noble Gas (Inert Octet)
        if (elemA?.isNobleGas == true || elemB?.isNobleGas == true) {
            val noble = elemA?.takeIf { it.isNobleGas } ?: elemB!!
            return ReactivityAssessment(
                isReactive = false,
                reactionType = null,
                predictedEquation = null,
                explanation = "${noble.name} (${noble.symbol}) is a Noble Gas with a completely filled valence octet (s²p⁶), rendering it chemically inert under standard laboratory conditions."
            )
        }

        // Case 2: Element + Element Synthesis (e.g. Na + Cl -> NaCl)
        if (elemA != null && elemB != null) {
            val metal = if (elemA.isMetal && !elemB.isMetal) elemA else if (elemB.isMetal && !elemA.isMetal) elemB else null
            val nonmetal = if (metal == elemA) elemB else if (metal == elemB) elemA else null

            if (metal != null && nonmetal != null) {
                val vM = metal.defaultValency
                val vX = nonmetal.defaultValency
                val g = gcd(vM, vX)
                val subM = vX / g
                val subX = vM / g

                val formula = formatBinaryFormula(metal.symbol, subM, nonmetal.symbol, subX)
                val eq = "${formatReactant(metal.symbol, subM)} + ${formatReactant(nonmetal.symbol, subX)} → $formula"
                val deltaEn = abs(nonmetal.electronegativity - metal.electronegativity)
                val isBlast = (metal.isAlkaliMetal || metal.isAlkalineEarth) && (nonmetal.isHalogen || nonmetal.symbol == "O")

                return ReactivityAssessment(
                    isReactive = true,
                    reactionType = ReactionType.COMBUSTION,
                    predictedEquation = eq,
                    explanation = "Direct synthesis of ionic salt $formula between electropositive ${metal.name} and electronegative ${nonmetal.name} (Δχ = ${String.format("%.2f", deltaEn)}). Highly exothermic bond formation.",
                    isBlast = isBlast,
                    primaryProduct = formula
                )
            }

            // Nonmetal + Nonmetal (Covalent binary)
            if (elemA.isNonmetal && elemB.isNonmetal) {
                val formula = when {
                    (elemA.symbol == "H" && elemB.symbol == "Cl") || (elemB.symbol == "H" && elemA.symbol == "Cl") -> "HCl"
                    (elemA.symbol == "H" && elemB.symbol == "O") || (elemB.symbol == "H" && elemA.symbol == "O") -> "H2O"
                    (elemA.symbol == "C" && elemB.symbol == "O") || (elemB.symbol == "C" && elemA.symbol == "O") -> "CO2"
                    (elemA.symbol == "S" && elemB.symbol == "O") || (elemB.symbol == "S" && elemA.symbol == "O") -> "SO2"
                    (elemA.symbol == "N" && elemB.symbol == "H") || (elemB.symbol == "N" && elemA.symbol == "H") -> "NH3"
                    else -> "${elemA.symbol}${elemB.symbol}"
                }
                return ReactivityAssessment(
                    isReactive = true,
                    reactionType = ReactionType.COMBUSTION,
                    predictedEquation = "${elemA.symbol} + ${elemB.symbol} → $formula",
                    explanation = "Covalent combination between reactive nonmetals ${elemA.name} and ${elemB.name} forming binary molecular compound $formula.",
                    isBlast = formula == "H2O" || formula == "HCl",
                    primaryProduct = formula
                )
            }

            // Metal + Metal (Alloy / Non-reactive mixture)
            if (elemA.isMetal && elemB.isMetal) {
                return ReactivityAssessment(
                    isReactive = false,
                    reactionType = null,
                    predictedEquation = null,
                    explanation = "Two elemental metals (${elemA.name} and ${elemB.name}) do not undergo chemical oxidation-reduction with each other; they form a physical solid mixture or metallic alloy."
                )
            }
        }

        // Case 3: Metal + Water / Metal + Acid
        val metalElem = elemA?.takeIf { it.isMetal } ?: elemB?.takeIf { it.isMetal }
        val otherChem = if (metalElem == elemA) chemB else chemA

        if (metalElem != null) {
            if (otherChem.id == "H2O") {
                if (metalElem.isAlkaliMetal) {
                    val hydroxide = "${metalElem.symbol}OH"
                    return ReactivityAssessment(
                        isReactive = true,
                        reactionType = ReactionType.SINGLE_DISPLACEMENT,
                        predictedEquation = "2${metalElem.symbol} + 2H₂O → 2$hydroxide + H₂↑",
                        explanation = "Violent exothermic oxidation of alkali metal ${metalElem.name} in water releasing hydrogen gas and creating a strongly alkaline hydroxide solution.",
                        isBlast = true,
                        primaryProduct = hydroxide
                    )
                } else if (metalElem.isAlkalineEarth && metalElem.symbol != "Be") {
                    val hydroxide = "${metalElem.symbol}(OH)2"
                    return ReactivityAssessment(
                        isReactive = true,
                        reactionType = ReactionType.SINGLE_DISPLACEMENT,
                        predictedEquation = "${metalElem.symbol} + 2H₂O → $hydroxide + H₂↑",
                        explanation = "${metalElem.name} reacts vigorously with water displacing hydrogen gas and forming metal hydroxide.",
                        isBlast = metalElem.symbol == "Ba" || metalElem.symbol == "Sr",
                        primaryProduct = hydroxide
                    )
                }
            }

            // Metal + Acid (HCl, H2SO4, HNO3, CH3COOH)
            if (otherChem.category == "Acids" || otherChem.id in listOf("HCl", "H2SO4", "HNO3", "CH3COOH")) {
                if (metalElem.standardReductionPotential < 0.0) {
                    val salt = "${metalElem.symbol}Cl${if (metalElem.defaultValency > 1) "${metalElem.defaultValency}" else ""}"
                    return ReactivityAssessment(
                        isReactive = true,
                        reactionType = ReactionType.SINGLE_DISPLACEMENT,
                        predictedEquation = "${metalElem.symbol} + acid → $salt + H₂↑",
                        explanation = "${metalElem.name} has a standard reduction potential (E° = ${metalElem.standardReductionPotential} V) below hydrogen (0.00 V) and displaces H⁺ ions to liberate hydrogen gas.",
                        isBlast = metalElem.isAlkaliMetal || metalElem.symbol == "Mg" || metalElem.symbol == "Al",
                        primaryProduct = salt
                    )
                } else {
                    return ReactivityAssessment(
                        isReactive = otherChem.id == "HNO3", // Noble metals react with oxidizing nitric acid
                        reactionType = if (otherChem.id == "HNO3") ReactionType.SINGLE_DISPLACEMENT else null,
                        predictedEquation = if (otherChem.id == "HNO3") "${metalElem.symbol} + 4HNO₃ → ${metalElem.symbol}(NO₃)₂ + 2NO₂↑ + 2H₂O" else null,
                        explanation = "${metalElem.name} (E° = +${metalElem.standardReductionPotential} V) is below Hydrogen in the electrochemical activity series and does not displace H₂ gas from non-oxidizing acids."
                    )
                }
            }
        }

        // Case 4: Acid + Base Neutralization
        if ((chemA.category == "Acids" && chemB.category == "Bases") || (chemB.category == "Acids" && chemA.category == "Bases")) {
            return ReactivityAssessment(
                isReactive = true,
                reactionType = ReactionType.NEUTRALIZATION,
                predictedEquation = "${chemA.formula} + ${chemB.formula} → Salt + H₂O",
                explanation = "Arrhenius acid-base neutralization: H⁺ and OH⁻ ions combine to form water with exothermic enthalpy (ΔH° ≈ -57.3 kJ/mol).",
                primaryProduct = "H2O"
            )
        }

        // Case 5: Acid + Carbonate / Bicarbonate
        val isCarbonateA = chemA.id.contains("CO3", ignoreCase = true) || chemA.id.contains("HCO3", ignoreCase = true)
        val isCarbonateB = chemB.id.contains("CO3", ignoreCase = true) || chemB.id.contains("HCO3", ignoreCase = true)
        val isAcid = chemA.category == "Acids" || chemB.category == "Acids" || chemA.id in listOf("HCl", "H2SO4", "HNO3", "CH3COOH") || chemB.id in listOf("HCl", "H2SO4", "HNO3", "CH3COOH")

        if ((isCarbonateA || isCarbonateB) && isAcid) {
            return ReactivityAssessment(
                isReactive = true,
                reactionType = ReactionType.GAS_EVOLUTION,
                predictedEquation = "Carbonate + Acid → Salt + H₂O + CO₂↑",
                explanation = "Acid protonates carbonate ions forming unstable carbonic acid (H₂CO₃) which spontaneously decomposes into water and carbon dioxide gas effervescence.",
                primaryProduct = "CO2_gas"
            )
        }

        // Default: Stable unreacted physical mixture
        return ReactivityAssessment(
            isReactive = false,
            reactionType = null,
            predictedEquation = null,
            explanation = "No thermodynamically favorable reaction pathway under standard laboratory conditions (Spectator mixture)."
        )
    }

    private fun formatBinaryFormula(m: String, subM: Int, x: String, subX: Int): String {
        val partM = if (subM > 1) "$m$subM" else m
        val partX = if (subX > 1) "$x$subX" else x
        return "$partM$partX"
    }

    private fun formatReactant(symbol: String, coeff: Int): String {
        return if (coeff > 1) "$coeff$symbol" else symbol
    }

    /**
     * Dynamically searches substances and constructs a standard chemistry reaction rule
     * if an unreacted combination of reactive elements or compounds is detected.
     */
    fun findDynamicReactions(
        substances: List<SubstanceState>,
        currentTemperatureC: Double
    ): List<ReactionRule> {
        val reactiveSubstances = substances.filter { it.moles > 1e-7 }
        if (reactiveSubstances.size < 2) return emptyList()

        val generatedRules = mutableListOf<ReactionRule>()

        // 1. Check pairs for Element + Element Direct Combination
        for (i in 0 until reactiveSubstances.size) {
            for (j in i + 1 until reactiveSubstances.size) {
                val subA = reactiveSubstances[i]
                val subB = reactiveSubstances[j]

                val chemA = ChemicalRegistry.get(subA.chemicalId) ?: continue
                val chemB = ChemicalRegistry.get(subB.chemicalId) ?: continue

                val elemA = getElementProfile(chemA.id)
                val elemB = getElementProfile(chemB.id)

                // Element + Element
                if (elemA != null && elemB != null && !elemA.isNobleGas && !elemB.isNobleGas) {
                    val metal = if (elemA.isMetal && !elemB.isMetal) elemA else if (elemB.isMetal && !elemA.isMetal) elemB else null
                    val nonmetal = if (metal == elemA) elemB else if (metal == elemB) elemA else null

                    if (metal != null && nonmetal != null) {
                        val subMetal = if (metal == elemA) subA else subB
                        val subNonmetal = if (nonmetal == elemA) subA else subB

                        val vM = metal.defaultValency
                        val vX = nonmetal.defaultValency
                        val g = gcd(vM, vX)
                        val coeffM = (vX / g).toDouble()
                        val coeffX = (vM / g).toDouble()

                        val productFormula = formatBinaryFormula(metal.symbol, coeffM.toInt(), nonmetal.symbol, coeffX.toInt())
                        val productId = ensureProductRegistered(productFormula, metal, nonmetal)

                        val deltaEn = abs(nonmetal.electronegativity - metal.electronegativity)
                        val deltaH = -(deltaEn * 160.0 + 200.0) // Exothermic synthesis
                        val isAlkaliHalogen = (metal.isAlkaliMetal || metal.isAlkalineEarth) && (nonmetal.isHalogen || nonmetal.symbol == "O")
                        val blastIntensity = if (isAlkaliHalogen) 0.85f else if (metal.symbol == "Mg" && nonmetal.symbol == "O") 0.80f else 0.0f

                        val sparks = metal.sparkColors.ifEmpty {
                            listOfNotNull(metal.flameColorHex)
                        }

                        generatedRules.add(
                            ReactionRule(
                                id = "dyn_${subMetal.chemicalId}_${subNonmetal.chemicalId}",
                                name = "${metal.name} and ${nonmetal.name} Direct Synthesis",
                                type = ReactionType.COMBUSTION,
                                equationString = "${if (coeffM > 1) "${coeffM.toInt()}" else ""}${subMetal.chemicalId} + ${if (coeffX > 1) "${coeffX.toInt()}" else ""}${subNonmetal.chemicalId} → $productFormula",
                                reactants = mapOf(subMetal.chemicalId to coeffM, subNonmetal.chemicalId to coeffX),
                                products = mapOf(productId to 1.0),
                                enthalpyDeltaH_kJ_mol = deltaH,
                                isBlast = isAlkaliHalogen,
                                blastIntensity = blastIntensity,
                                sparkColors = sparks,
                                description = "Direct combination synthesis between ${metal.name} and ${nonmetal.name} to yield pure $productFormula.",
                                observation = "Vigorous exothermic reaction with brilliant light emission, smoke production, and formation of solid $productFormula.",
                                educationalNote = "Ionic compound synthesis driven by electronegativity gradient (Δχ = ${String.format("%.2f", deltaEn)})."
                            )
                        )
                    }

                    // Nonmetal + Nonmetal (H + Cl, H + O, C + O, S + O, N + H)
                    if (elemA.isNonmetal && elemB.isNonmetal) {
                        val pairKey = setOf(elemA.symbol, elemB.symbol)
                        when {
                            pairKey == setOf("H", "Cl") -> {
                                val prodId = "HCl"
                                generatedRules.add(
                                    ReactionRule(
                                        id = "dyn_h_cl",
                                        name = "Hydrogen and Chlorine Combination",
                                        type = ReactionType.COMBUSTION,
                                        equationString = "H + Cl → HCl",
                                        reactants = mapOf(subA.chemicalId to 1.0, subB.chemicalId to 1.0),
                                        products = mapOf(prodId to 1.0),
                                        enthalpyDeltaH_kJ_mol = -92.3,
                                        gasProducedId = "HCl",
                                        isBlast = true,
                                        blastIntensity = 0.75f,
                                        description = "Photochemical and exothermic synthesis of hydrogen chloride gas.",
                                        observation = "Explosive flash producing acidic hydrogen chloride fumes."
                                    )
                                )
                            }
                            pairKey == setOf("H", "O") -> {
                                val prodId = "H2O"
                                generatedRules.add(
                                    ReactionRule(
                                        id = "dyn_h_o",
                                        name = "Hydrogen and Oxygen Combustion",
                                        type = ReactionType.COMBUSTION,
                                        equationString = "2H + O → H₂O",
                                        reactants = if (subA.chemicalId.startsWith("H")) mapOf(subA.chemicalId to 2.0, subB.chemicalId to 1.0) else mapOf(subB.chemicalId to 2.0, subA.chemicalId to 1.0),
                                        products = mapOf(prodId to 1.0),
                                        enthalpyDeltaH_kJ_mol = -285.8,
                                        isBlast = true,
                                        blastIntensity = 0.90f,
                                        description = "Rapid combustion of hydrogen and oxygen forming water vapor.",
                                        observation = "Loud explosive pop and steam formation."
                                    )
                                )
                            }
                            pairKey == setOf("C", "O") -> {
                                val prodId = "CO2_gas"
                                generatedRules.add(
                                    ReactionRule(
                                        id = "dyn_c_o",
                                        name = "Carbon Oxidation",
                                        type = ReactionType.COMBUSTION,
                                        equationString = "C + 2O → CO₂↑",
                                        reactants = if (subA.chemicalId == "C") mapOf(subA.chemicalId to 1.0, subB.chemicalId to 2.0) else mapOf(subB.chemicalId to 1.0, subA.chemicalId to 2.0),
                                        products = mapOf(prodId to 1.0),
                                        enthalpyDeltaH_kJ_mol = -393.5,
                                        gasProducedId = "CO2_gas",
                                        description = "Complete combustion of elemental carbon to carbon dioxide.",
                                        observation = "Steady glowing combustion producing carbon dioxide gas."
                                    )
                                )
                            }
                            pairKey == setOf("S", "O") -> {
                                val prodId = "SO2_gas"
                                ensureProductRegistered("SO2_gas", null, null)
                                generatedRules.add(
                                    ReactionRule(
                                        id = "dyn_s_o",
                                        name = "Sulfur Combustion",
                                        type = ReactionType.COMBUSTION,
                                        equationString = "S + 2O → SO₂↑",
                                        reactants = if (subA.chemicalId == "S") mapOf(subA.chemicalId to 1.0, subB.chemicalId to 2.0) else mapOf(subB.chemicalId to 1.0, subA.chemicalId to 2.0),
                                        products = mapOf(prodId to 1.0),
                                        enthalpyDeltaH_kJ_mol = -296.8,
                                        gasProducedId = prodId,
                                        sparkColors = listOf(0xFF0284C7),
                                        description = "Combustion of sulfur in oxygen with characteristic blue flame.",
                                        observation = "Brilliant blue flame producing choking sulfur dioxide gas."
                                    )
                                )
                            }
                        }
                    }
                }

                // Metal + Water
                val metalCandidate = elemA?.takeIf { it.isMetal } ?: elemB?.takeIf { it.isMetal }
                val otherCandidate = if (metalCandidate == elemA) chemB else chemA
                val subMetalCandidate = if (metalCandidate == elemA) subA else subB
                val subOtherCandidate = if (metalCandidate == elemA) subB else subA

                if (metalCandidate != null && otherCandidate.id == "H2O") {
                    if (metalCandidate.isAlkaliMetal) {
                        val hydroxideId = "${metalCandidate.symbol}OH"
                        ensureProductRegistered(hydroxideId, metalCandidate, null)
                        generatedRules.add(
                            ReactionRule(
                                id = "dyn_${metalCandidate.symbol}_water",
                                name = "${metalCandidate.name} Metal in Water",
                                type = ReactionType.SINGLE_DISPLACEMENT,
                                equationString = "2${metalCandidate.symbol} + 2H₂O → 2$hydroxideId + H₂↑",
                                reactants = mapOf(subMetalCandidate.chemicalId to 2.0, subOtherCandidate.chemicalId to 2.0),
                                products = mapOf(hydroxideId to 2.0, "H2_gas" to 1.0),
                                enthalpyDeltaH_kJ_mol = -184.0,
                                gasProducedId = "H2_gas",
                                isBlast = true,
                                blastIntensity = 0.92f,
                                sparkColors = metalCandidate.sparkColors.ifEmpty { listOfNotNull(metalCandidate.flameColorHex) },
                                description = "Violent alkali metal reaction with water generating hydrogen gas, intense heat, and strong alkaline base.",
                                observation = "Vigorous fizzing, rapid darting on surface, ignition with colored flame, and loud explosive blast."
                            )
                        )
                    }
                }
            }
        }

        return generatedRules
    }

    private fun ensureProductRegistered(formula: String, metal: ElementProfile?, nonmetal: ElementProfile?): String {
        val existing = ChemicalRegistry.get(formula) ?: ChemicalRegistry.getAll().find { it.formula.equals(formula, ignoreCase = true) }
        if (existing != null) return existing.id

        // Calculate molar mass dynamically
        val mMass = (metal?.let { ChemicalRegistry.get(it.symbol)?.molarMass } ?: 23.0)
        val xMass = (nonmetal?.let { ChemicalRegistry.get(it.symbol)?.molarMass } ?: 35.45)
        val totalMolarMass = mMass + xMass

        val color = when {
            formula.startsWith("Cu") -> 0xFF38BDF8 // Blue/Cyan
            formula.startsWith("Fe") -> 0xFFD97706 // Amber/Brown
            formula.startsWith("Ni") -> 0xFF22C55E // Green
            formula.startsWith("Co") -> 0xFFEC4899 // Pink
            else -> 0xFFF1F5F9 // White/Crystalline
        }

        val chem = Chemical(
            id = formula,
            name = "${metal?.name ?: ""} ${nonmetal?.name ?: ""}".trim().ifBlank { formula },
            formula = formula,
            molarMass = totalMolarMass,
            density = 2.16,
            meltingPointCelsius = 801.0,
            boilingPointCelsius = 1465.0,
            specificHeatCapacity = 0.864,
            defaultPhase = Phase.SOLID,
            defaultColorHex = color,
            hazards = emptyList(),
            category = "Compounds",
            isElement = false,
            description = "Synthesized binary chemical compound $formula.",
            safetyInfo = "Standard laboratory chemical protocol."
        )

        ChemicalRegistry.register(chem)
        return formula
    }
}
