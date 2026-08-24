package com.webdevavi.chemlabsimulator.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import com.webdevavi.chemlabsimulator.simulation.model.ReactionType
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
import java.util.Locale

/**
 * Reaction Chain HUD Component
 * Displays live, reactive chemical reaction chain flow:
 * e.g. HCl + NaOH -> NaCl + H2O -> AgNO3 + NaCl -> AgCl(s) + NaNO3
 */
@Composable
fun ReactionChainHUD(
    container: ContainerState,
    onReactionClick: (ReactionResult) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(true) }
    val reactions = container.lastReactionEvents

    GlassCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = if (reactions.isNotEmpty()) CyanAccent.copy(alpha = 0.5f) else LabBorder,
        backgroundColor = LabSurfaceCard.copy(alpha = 0.90f)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(
                                if (reactions.isNotEmpty()) CyanAccent.copy(alpha = 0.2f) else LabSurfaceVariant,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timeline,
                            contentDescription = "Reaction Chain",
                            tint = if (reactions.isNotEmpty()) CyanAccent else TextSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                    Text(
                        text = "CHEMICAL REACTION CHAIN",
                        color = if (reactions.isNotEmpty()) CyanAccent else TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )

                    if (reactions.isNotEmpty()) {
                        Surface(
                            color = CyanAccent.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.border(0.5.dp, CyanAccent.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                text = "${reactions.size} step${if (reactions.size > 1) "s" else ""}",
                                color = CyanAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle Chain",
                        tint = TextSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Expandable Content
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    if (reactions.isEmpty()) {
                        // Empty state hint
                        Surface(
                            color = LabDarkBg.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(0.5.dp, LabBorder, RoundedCornerShape(8.dp))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("🧪", fontSize = 14.sp)
                                Text(
                                    text = "Add reactants (e.g. HCl + NaOH, Zn + HCl) to initiate a reaction sequence.",
                                    color = TextSecondary,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    } else {
                        // Scrollable Horizontal Reaction Chain Flow
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            reactions.forEachIndexed { index, reaction ->
                                ReactionChainNode(
                                    stepNumber = index + 1,
                                    reaction = reaction,
                                    onClick = { onReactionClick(reaction) }
                                )

                                if (index < reactions.size - 1) {
                                    // Flow Arrow between chain links
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                        modifier = Modifier.padding(horizontal = 2.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(16.dp)
                                                .height(2.dp)
                                                .background(CyanAccent.copy(alpha = 0.5f))
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ChevronRight,
                                            contentDescription = "Next Reaction",
                                            tint = CyanAccent,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Individual Node / Card representing a step in the reaction chain.
 */
@Composable
private fun ReactionChainNode(
    stepNumber: Int,
    reaction: ReactionResult,
    onClick: () -> Unit
) {
    val typeColor = when (reaction.reactionType) {
        ReactionType.NEUTRALIZATION -> EmeraldSuccess
        ReactionType.SINGLE_DISPLACEMENT -> SkyAccent
        ReactionType.DOUBLE_DISPLACEMENT -> CyanAccent
        ReactionType.GAS_EVOLUTION -> AmberWarning
        ReactionType.DECOMPOSITION -> RubyHazard
        ReactionType.COMBUSTION -> Color(0xFFFF5722)
        ReactionType.DISSOLUTION -> Color(0xFFA78BFA)
        ReactionType.NUCLEAR_DECAY -> EmeraldSuccess
        ReactionType.COMPLEXATION -> Color(0xFF38BDF8)
    }

    Surface(
        color = LabDarkBg,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .border(1.dp, typeColor.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
            // Step header and reaction type
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Surface(
                    color = typeColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Step $stepNumber",
                        color = typeColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }

                Text(
                    text = reaction.reactionType.displayName,
                    color = TextSecondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Main Chemical Equation String: e.g. "HCl + NaOH → NaCl + H₂O"
            Text(
                text = reaction.equationString,
                color = TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Reaction Metadata & Indicators
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Thermal signature
                val isExo = reaction.heatReleasedJoules > 0.0
                Text(
                    text = if (isExo) "♨️ Exothermic" else "❄️ Endothermic",
                    color = if (isExo) Color(0xFFFB923C) else SkyAccent,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )

                // Byproducts badge (Gas / Precipitate)
                if (reaction.gasFormedId != null) {
                    Text(
                        text = "💨 ${reaction.gasFormedId}↑",
                        color = AmberWarning,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (reaction.precipitateFormedId != null) {
                    Text(
                        text = "⬇️ ${reaction.precipitateFormedId} (ppt)",
                        color = CyanAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

