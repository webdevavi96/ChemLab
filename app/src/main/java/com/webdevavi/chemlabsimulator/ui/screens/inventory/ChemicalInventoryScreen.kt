package com.webdevavi.chemlabsimulator.ui.screens.inventory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import com.webdevavi.chemlabsimulator.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.Chemical
import com.webdevavi.chemlabsimulator.theme.AmberWarning
import com.webdevavi.chemlabsimulator.theme.CyanAccent
import com.webdevavi.chemlabsimulator.theme.EmeraldSuccess
import com.webdevavi.chemlabsimulator.theme.LabBorder
import com.webdevavi.chemlabsimulator.theme.LabDarkBg
import com.webdevavi.chemlabsimulator.theme.LabSurface
import com.webdevavi.chemlabsimulator.theme.LabSurfaceCard
import com.webdevavi.chemlabsimulator.theme.LabSurfaceVariant
import com.webdevavi.chemlabsimulator.theme.RubyHazard
import com.webdevavi.chemlabsimulator.theme.SkyAccent
import com.webdevavi.chemlabsimulator.theme.TextPrimary
import com.webdevavi.chemlabsimulator.theme.TextSecondary
import com.webdevavi.chemlabsimulator.ui.components.common.HazardBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChemicalInventoryScreen(
    onNavigateBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedMainTab by remember { mutableStateOf(0) } // 0: All, 1: Elements (1-118), 2: Compounds, 3: Radioactive ☢️
    var selectedSubCategory by remember { mutableStateOf("All") }
    var selectedChemical by remember { mutableStateOf<Chemical?>(null) }

    val mainTabs = listOf("All", "Elements (1-118)", "Compounds", "Radioactive ☢️")

    val elementCategories = listOf(
        "All", "Alkali Metal", "Alkaline Earth Metal", "Transition Metal",
        "Lanthanide", "Actinide", "Post-transition Metal", "Metalloid",
        "Reactive Nonmetal", "Halogen", "Noble Gas"
    )

    val compoundCategories = listOf(
        "All", "Acids", "Bases", "Salts", "Solvents", "Gases", "Indicators", "Precipitates", "Oxidizers"
    )

    val chemicalList = remember(searchQuery, selectedMainTab, selectedSubCategory) {
        val baseList = when (selectedMainTab) {
            1 -> ChemicalRegistry.getAllElements()
            2 -> ChemicalRegistry.getAllCompounds()
            3 -> ChemicalRegistry.getRadioactive()
            else -> ChemicalRegistry.getAll()
        }

        val filteredByCategory = if (selectedSubCategory == "All") {
            baseList
        } else {
            baseList.filter {
                it.category.equals(selectedSubCategory, ignoreCase = true) ||
                (it.elementCategory != null && it.elementCategory.equals(selectedSubCategory, ignoreCase = true))
            }
        }

        if (searchQuery.isBlank()) {
            filteredByCategory
        } else {
            val q = searchQuery.trim().lowercase()
            filteredByCategory.filter {
                it.name.lowercase().contains(q) ||
                it.formula.lowercase().contains(q) ||
                it.id.lowercase().contains(q) ||
                it.category.lowercase().contains(q) ||
                (it.elementCategory != null && it.elementCategory.lowercase().contains(q)) ||
                (it.atomicNumber != null && "${it.atomicNumber}".contains(q))
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "ChemLab Logo",
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.dp, CyanAccent.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        )
                        Text("Chemical Database", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Surface(
                            color = CyanAccent.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${chemicalList.size} Available",
                                color = CyanAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LabDarkBg)
            )
        },
        containerColor = LabDarkBg
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Main Filter Tabs
            TabRow(
                selectedTabIndex = selectedMainTab,
                containerColor = LabDarkBg,
                contentColor = CyanAccent,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedMainTab]),
                        color = CyanAccent,
                        height = 3.dp
                    )
                },
                divider = { Box(modifier = Modifier.height(1.dp).fillMaxWidth().background(LabBorder)) }
            ) {
                mainTabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedMainTab == index,
                        onClick = {
                            selectedMainTab = index
                            selectedSubCategory = "All"
                        },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedMainTab == index) CyanAccent else TextSecondary,
                                fontWeight = if (selectedMainTab == index) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = if (selectedMainTab == 1) "Search by element name, symbol, or atomic number (e.g. 92, U, Iron)..."
                               else "Search chemicals, formulas, properties (e.g. HCl, 238, NaOH)...",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CyanAccent) },
                trailingIcon = {
                    if (searchQuery.isNotBlank()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear", tint = TextSecondary)
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CyanAccent,
                    unfocusedBorderColor = LabBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Sub-category Horizontal Filter Pills
            val currentSubCategories = when (selectedMainTab) {
                1 -> elementCategories
                2 -> compoundCategories
                3 -> listOf("All", "Actinide", "Lanthanide", "Transition Metal", "Post-transition Metal", "Halogen", "Noble Gas")
                else -> listOf("All", "Elements", "Compounds", "Acids", "Bases", "Salts", "Solvents", "Radioactive")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentSubCategories.forEach { subCat ->
                    FilterChip(
                        selected = selectedSubCategory == subCat,
                        onClick = { selectedSubCategory = subCat },
                        label = { Text(subCat, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyanAccent,
                            selectedLabelColor = LabDarkBg,
                            containerColor = LabSurface,
                            labelColor = TextSecondary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Chemical / Element Cards List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(chemicalList, key = { it.id }) { chem ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedChemical = chem }
                            .border(
                                width = 1.dp,
                                color = if (chem.isRadioactive) EmeraldSuccess.copy(alpha = 0.5f) else LabBorder,
                                shape = RoundedCornerShape(14.dp)
                            ),
                        color = LabSurfaceCard,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    // Symbol / Avatar Box
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .background(
                                                color = Color(chem.defaultColorHex).copy(alpha = 0.22f),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                            .border(
                                                width = 1.dp,
                                                color = Color(chem.defaultColorHex).copy(alpha = 0.7f),
                                                shape = RoundedCornerShape(10.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            if (chem.atomicNumber != null) {
                                                Text(
                                                    text = "${chem.atomicNumber}",
                                                    color = TextSecondary,
                                                    fontSize = 9.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                            Text(
                                                text = chem.formula.take(4),
                                                color = TextPrimary,
                                                fontSize = if (chem.formula.length > 2) 11.sp else 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    Column {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(
                                                text = chem.name,
                                                color = TextPrimary,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )
                                            if (chem.isRadioactive) {
                                                Surface(
                                                    color = EmeraldSuccess.copy(alpha = 0.2f),
                                                    shape = RoundedCornerShape(4.dp)
                                                ) {
                                                    Text(
                                                        text = "☢️ RADIOACTIVE",
                                                        color = EmeraldSuccess,
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            text = if (chem.isElement) {
                                                "${chem.formula} • ${chem.elementCategory ?: chem.category} • Group ${chem.periodicGroup ?: "-"}, Period ${chem.periodicPeriod ?: "-"}"
                                            } else {
                                                "${chem.formula} • ${chem.category}"
                                            },
                                            color = SkyAccent,
                                            fontSize = 11.sp,
                                            fontFamily = FontFamily.Monospace
                                        )
                                    }
                                }

                                Surface(
                                    color = LabSurfaceVariant,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = if (chem.atomicWeight != null) "${chem.atomicWeight} u" else "${chem.molarMass} g/mol",
                                        color = TextSecondary,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                    )
                                }
                            }

                            if (chem.hazards.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    chem.hazards.forEach { hazard ->
                                        HazardBadge(hazard = hazard)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Detail Bottom Sheet
    if (selectedChemical != null) {
        val chem = selectedChemical!!
        ModalBottomSheet(
            onDismissRequest = { selectedChemical = null },
            containerColor = LabDarkBg
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = chem.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            if (chem.atomicNumber != null) {
                                Surface(
                                    color = CyanAccent.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "Z = ${chem.atomicNumber}",
                                        color = CyanAccent,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "${chem.formula} (${chem.elementCategory ?: chem.category})",
                            color = SkyAccent,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    IconButton(onClick = { selectedChemical = null }) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                Text(text = chem.description, color = TextSecondary, fontSize = 13.sp, lineHeight = 18.sp)

                Spacer(modifier = Modifier.height(14.dp))

                // Physical & Chemical Properties Table
                Text(
                    text = if (chem.isElement) "Periodic & Atomic Properties" else "Physical & Chemical Properties",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    color = LabSurface,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, LabBorder, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (chem.atomicNumber != null) {
                            PropertyRow("Atomic Number (Z)", "${chem.atomicNumber}")
                        }
                        if (chem.atomicWeight != null) {
                            PropertyRow("Atomic Weight (A)", "${chem.atomicWeight} u (g/mol)")
                        }
                        if (chem.periodicGroup != null) {
                            PropertyRow("Periodic Group", "${chem.periodicGroup}")
                        }
                        if (chem.periodicPeriod != null) {
                            PropertyRow("Periodic Period", "${chem.periodicPeriod}")
                        }
                        if (chem.elementCategory != null) {
                            PropertyRow("Category", chem.elementCategory)
                        }
                        PropertyRow("Default Phase at STP", chem.defaultPhase.name)
                        PropertyRow("Density", "${chem.density} g/cm³")
                        PropertyRow("Melting Point", "${chem.meltingPointCelsius} °C")
                        PropertyRow("Boiling Point", "${chem.boilingPointCelsius} °C")
                        PropertyRow("Specific Heat (c)", "${chem.specificHeatCapacity} J/(g·°C)")
                        if (chem.intrinsicPh != null) {
                            PropertyRow("Standard pH", "${chem.intrinsicPh}")
                        }
                        if (chem.pKa != null) {
                            PropertyRow("pKa", "${chem.pKa}")
                        }
                        if (chem.pKb != null) {
                            PropertyRow("pKb", "${chem.pKb}")
                        }
                        if (!chem.solubility.isInfinite()) {
                            PropertyRow("Solubility in H₂O", "${chem.solubility} g/100 mL")
                        }
                    }
                }

                // GHS Hazard & Safety Section
                if (chem.hazards.isNotEmpty() || chem.safetyInfo.isNotBlank() || chem.isRadioactive) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "Safety & Hazard Classification", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = LabSurface,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (chem.isRadioactive) EmeraldSuccess.copy(alpha = 0.5f) else AmberWarning.copy(alpha = 0.4f),
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            if (chem.hazards.isNotEmpty()) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    chem.hazards.forEach { HazardBadge(hazard = it) }
                                }
                            }
                            if (chem.safetyInfo.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = chem.safetyInfo, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
                            }
                        }
                    }
                }

                // Known Reactions
                if (chem.commonReactions.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(text = "Common Reactions & Equations", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    chem.commonReactions.forEach { rxn ->
                        Surface(
                            color = LabSurfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = rxn,
                                color = CyanAccent,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
private fun PropertyRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary, fontSize = 12.sp)
        Text(text = value, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, fontFamily = FontFamily.Monospace)
    }
}
