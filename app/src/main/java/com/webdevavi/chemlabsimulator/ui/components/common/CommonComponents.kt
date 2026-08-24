package com.webdevavi.chemlabsimulator.ui.components.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.webdevavi.chemlabsimulator.simulation.chemistry.AcidBaseEngine
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.HazardType
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
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

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    borderColor: Color = LabBorder,
    backgroundColor: Color = LabSurfaceCard.copy(alpha = 0.85f),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        content()
    }
}

@Composable
fun HazardBadge(
    hazard: HazardType,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .border(0.5.dp, Color(hazard.colorHex).copy(alpha = 0.6f), RoundedCornerShape(6.dp)),
        color = Color(hazard.colorHex).copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = hazard.iconSymbol, fontSize = 11.sp)
            Text(
                text = hazard.displayName,
                color = Color(hazard.colorHex),
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun TelemetryHUD(
    container: ContainerState,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = if (container.isOverflown) RubyHazard else LabBorder
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = container.name,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                if (container.isOverflown) {
                    Text(
                        text = "OVERFLOW!",
                        color = RubyHazard,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Capacity & Volume bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Volume: ${String.format(Locale.US, "%.1f", container.totalVolumeMl)} / ${container.maxCapacityMl.toInt()} mL",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
                Text(
                    text = "Mass: ${String.format(Locale.US, "%.1f", container.totalMassGrams)} g",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { container.fillPercentage },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = if (container.isOverflown) RubyHazard else CyanAccent,
                trackColor = LabDarkBg
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Sensors Row: Temp & pH
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Temperature Sensor
                val tempColor = when {
                    container.temperatureCelsius >= 80.0 -> RubyHazard
                    container.temperatureCelsius >= 40.0 -> Color(0xFFF97316)
                    else -> SkyAccent
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, tempColor.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                    color = tempColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "🌡️ Temp", color = TextSecondary, fontSize = 12.sp)
                        Text(
                            text = "${String.format(Locale.US, "%.1f", container.temperatureCelsius)}°C",
                            color = tempColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // pH Sensor
                val phColor = Color(AcidBaseEngine.getUniversalIndicatorColor(container.pH))
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, phColor.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
                    color = phColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "🧪 pH", color = TextSecondary, fontSize = 12.sp)
                        Text(
                            text = String.format(Locale.US, "%.2f", container.pH),
                            color = phColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReactionAlertBanner(
    reaction: ReactionResult?,
    onInspectClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = reaction != null,
        enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
        modifier = modifier
    ) {
        if (reaction != null) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onInspectClick() },
                borderColor = CyanAccent,
                backgroundColor = LabSurface.copy(alpha = 0.95f)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(CyanAccent.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Science,
                                contentDescription = null,
                                tint = CyanAccent,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Reaction Detected!",
                                color = CyanAccent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text(
                                text = reaction.equationString,
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }

                    Surface(
                        color = CyanAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.clickable { onInspectClick() }
                    ) {
                        Text(
                            text = "Inspect",
                            color = CyanAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Interactive Chemical Contents Tag displaying "HCl + NaOH + ..." for the vessel.
 */
@Composable
fun BeakerFormulaBanner(
    container: ContainerState,
    onChemicalsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formulaStr = container.getFormulaDisplayString(maxItems = 5)
    val activeSubstances = container.substances.filter { it.moles > 1e-6 }

    Surface(
        color = LabSurfaceCard.copy(alpha = 0.92f),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, if (activeSubstances.isNotEmpty()) CyanAccent.copy(alpha = 0.45f) else LabBorder, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable { onChemicalsClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(if (activeSubstances.isNotEmpty()) CyanAccent.copy(alpha = 0.15f) else LabSurface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⚗️", fontSize = 14.sp)
                }

                Column {
                    Text(
                        text = "VESSEL CHEMICALS:",
                        color = TextSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = if (activeSubstances.isNotEmpty()) formulaStr else "Empty (No chemicals added)",
                        color = if (activeSubstances.isNotEmpty()) CyanAccent else TextSecondary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            if (activeSubstances.isNotEmpty()) {
                Surface(
                    color = LabSurfaceVariant,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${activeSubstances.size} species",
                        color = SkyAccent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

/**
 * Alert banner shown when the glassware is either shattered from explosion or cracked from extreme heat.
 */
@Composable
fun BeakerDamageAlertBanner(
    container: ContainerState,
    onReplaceVessel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isShattered = container.isShattered || container.visualState.isShattered
    val isCracked = container.isCracked || container.visualState.isCracked

    if (!isShattered && !isCracked) return

    val title = if (isShattered) "💥 BEAKER EXPLODED!" else "🔥 THERMAL CRACKING!"
    val message = if (isShattered) "Violent energetic reaction shattered the glassware. Reagents spilled." else "Extreme temperature caused thermal fracture in the glassware wall."
    val bannerColor = if (isShattered) RubyHazard else com.webdevavi.chemlabsimulator.theme.AmberWarning

    Surface(
        color = LabSurfaceCard.copy(alpha = 0.95f),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.5.dp, bannerColor, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(bannerColor.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(if (isShattered) "💥" else "🔥", fontSize = 18.sp)
                }
                Column {
                    Text(
                        text = title,
                        color = bannerColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = message,
                        color = TextPrimary,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
                color = bannerColor,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.clickable { onReplaceVessel() }
            ) {
                Text(
                    text = "Replace Vessel",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}



