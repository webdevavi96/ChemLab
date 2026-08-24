package com.webdevavi.chemlabsimulator.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.webdevavi.chemlabsimulator.R

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import com.webdevavi.chemlabsimulator.theme.CyanAccent
import com.webdevavi.chemlabsimulator.theme.EmeraldSuccess
import com.webdevavi.chemlabsimulator.theme.LabBorder
import com.webdevavi.chemlabsimulator.theme.LabDarkBg
import com.webdevavi.chemlabsimulator.theme.LabSurface
import com.webdevavi.chemlabsimulator.theme.LabSurfaceVariant
import com.webdevavi.chemlabsimulator.theme.RubyHazard
import com.webdevavi.chemlabsimulator.theme.SkyAccent
import com.webdevavi.chemlabsimulator.theme.TextPrimary
import com.webdevavi.chemlabsimulator.theme.TextSecondary
import java.util.Locale

@Composable
fun CalculationInspectorDialog(
    reaction: ReactionResult,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CyanAccent, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp)),
            colors = CardDefaults.cardColors(containerColor = LabDarkBg)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        Text(
                            text = "Calculation Inspector",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Reaction Name & Equation Card
                Surface(
                    color = LabSurface,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, LabBorder, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = reaction.reactionType.displayName.uppercase(),
                            color = SkyAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = reaction.reactionName,
                            color = TextPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Surface(
                            color = LabSurfaceVariant,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = reaction.equationString,
                                color = CyanAccent,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.Monospace,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Stoichiometry Breakdown Card
                Surface(
                    color = LabSurface,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, LabBorder, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "📊 Stoichiometry & Limiting Reagent",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        val limitingChem = ChemicalRegistry.get(reaction.limitingReagentId)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Limiting Reagent:", color = TextSecondary, fontSize = 13.sp)
                            Text(
                                text = "${limitingChem?.formula ?: reaction.limitingReagentId} (${limitingChem?.name ?: ""})",
                                color = RubyHazard,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Moles Reacted (ξ):", color = TextSecondary, fontSize = 13.sp)
                            Text(
                                text = "${String.format(Locale.US, "%.4f", reaction.extentOfReaction)} mol",
                                color = TextPrimary,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp
                            )
                        }

                        if (reaction.gasMoles > 0.0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Gas Produced:", color = TextSecondary, fontSize = 13.sp)
                                Text(
                                    text = "${String.format(Locale.US, "%.4f", reaction.gasMoles)} mol (${reaction.gasFormedId})",
                                    color = SkyAccent,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        if (reaction.precipitateGrams > 0.0) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Precipitate Formed:", color = TextSecondary, fontSize = 13.sp)
                                Text(
                                    text = "${String.format(Locale.US, "%.2f", reaction.precipitateGrams)} g (${reaction.precipitateFormedId})",
                                    color = EmeraldSuccess,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Thermodynamics Calculation Card
                Surface(
                    color = LabSurface,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, LabBorder, RoundedCornerShape(12.dp))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalFireDepartment,
                                contentDescription = null,
                                tint = Color(0xFFF97316),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Thermodynamics: q = mcΔT",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Enthalpy Energy (q):", color = TextSecondary, fontSize = 13.sp)
                            Text(
                                text = "${String.format(Locale.US, "%.1f", reaction.heatReleasedJoules)} Joules",
                                color = if (reaction.heatReleasedJoules >= 0) RubyHazard else SkyAccent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Calculated ΔT:", color = TextSecondary, fontSize = 13.sp)
                            Text(
                                text = "${if (reaction.temperatureChangeCelsius >= 0) "+" else ""}${String.format(Locale.US, "%.2f", reaction.temperatureChangeCelsius)} °C",
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Close Inspector",
                        color = LabDarkBg,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
