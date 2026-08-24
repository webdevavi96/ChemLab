package com.webdevavi.chemlabsimulator.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.webdevavi.chemlabsimulator.R

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.webdevavi.chemlabsimulator.data.repository.ExperimentPresets
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
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
import com.webdevavi.chemlabsimulator.theme.TextMuted
import com.webdevavi.chemlabsimulator.theme.TextPrimary
import com.webdevavi.chemlabsimulator.theme.TextSecondary
import com.webdevavi.chemlabsimulator.ui.components.common.GlassCard

@Composable
fun HomeScreen(
    onNavigateToWorkspace: () -> Unit,
    onNavigateToExperiments: () -> Unit,
    onNavigateToInventory: () -> Unit,
    onNavigateToSaved: () -> Unit
) {
    Scaffold(
        containerColor = LabDarkBg
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // App Hero Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ChemLab Simulator",
                        color = TextPrimary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Virtual Chemistry Laboratory",
                        color = SkyAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0F172A), CircleShape)
                        .border(1.5.dp, CyanAccent, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "ChemLab Simulator Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Featured Sandbox Banner
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, CyanAccent.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                    .clickable { onNavigateToWorkspace() },
                color = LabSurface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF0F2B48), Color(0xFF131C2E))
                            )
                        )
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Surface(
                                color = CyanAccent,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "INTERACTIVE SANDBOX",
                                    color = LabDarkBg,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "Start Chemistry Experiment",
                                color = TextPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Mix, pour, heat, and observe real-time reactions with the deterministic physics and stoichiometry engine.",
                                color = TextSecondary,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Enter Virtual Lab",
                                    color = CyanAccent,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = CyanAccent,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Logo Emblem
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFF0B1120))
                                .border(1.dp, CyanAccent.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Laboratory Sections",
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Cards Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Experiment Library Card
                HomeNavCard(
                    title = "Guided Labs",
                    subtitle = "${ExperimentPresets.experiments.size} Guided experiments",
                    icon = Icons.Default.MenuBook,
                    iconColor = SkyAccent,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToExperiments
                )

                // Chemical Inventory Card
                HomeNavCard(
                    title = "Inventory",
                    subtitle = "${ChemicalRegistry.getAll().size} Chemicals & Reagents",
                    icon = Icons.Default.Science,
                    iconColor = EmeraldSuccess,
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToInventory
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Saved Experiments Card
                HomeNavCard(
                    title = "Saved Labs",
                    subtitle = "History & Snapshots",
                    icon = Icons.Default.Bookmark,
                    iconColor = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToSaved
                )

                // Quick Fact Card
                GlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(115.dp),
                    borderColor = LabBorder
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "💡 Chemistry Insight",
                            color = CyanAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Neutralization ΔH is -57.3 kJ/mol for all strong acids & bases!",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Educational Safety Notice
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                borderColor = Color(0x33F59E0B)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = AmberWarning,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "ChemLab Simulator is an educational simulation. Virtual calculations model real reaction stoichiometry and thermodynamics for learning and study.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
private fun HomeNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(115.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, LabBorder, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        color = LabSurfaceCard
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(iconColor.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = subtitle,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
            }
        }
    }
}

