package com.webdevavi.chemlabsimulator.ui.screens.workspace

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.res.painterResource
import com.webdevavi.chemlabsimulator.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.ui.components.canvas.BunsenBurnerCanvas
import com.webdevavi.chemlabsimulator.ui.components.canvas.FullScreenSmokeCanvas
import com.webdevavi.chemlabsimulator.ui.components.canvas.GlasswareCanvas
import com.webdevavi.chemlabsimulator.ui.components.common.AutoClearCountdownHUD
import com.webdevavi.chemlabsimulator.ui.components.common.BeakerDamageAlertBanner
import com.webdevavi.chemlabsimulator.ui.components.common.BeakerFormulaBanner
import com.webdevavi.chemlabsimulator.ui.components.common.CalculationInspectorDialog
import com.webdevavi.chemlabsimulator.ui.components.common.ChemicalPickerModal
import com.webdevavi.chemlabsimulator.ui.components.common.GlassCard
import com.webdevavi.chemlabsimulator.ui.components.common.PourModal
import com.webdevavi.chemlabsimulator.ui.components.common.ReactionAlertBanner
import com.webdevavi.chemlabsimulator.ui.components.common.ReactionChainHUD
import com.webdevavi.chemlabsimulator.ui.components.common.TelemetryHUD
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkspaceScreen(
    viewModel: WorkspaceViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val inspectedReaction by viewModel.inspectedReaction.collectAsState()
    val latestAlert by viewModel.latestReactionAlert.collectAsState()
    val unreactedMessage by viewModel.unreactedMessage.collectAsState()

    val activeContainer = state.activeContainer ?: return
    val secondaryContainer = state.containers.find { it.id == state.secondaryContainerId }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(unreactedMessage) {
        unreactedMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissUnreactedMessage()
        }
    }

    var showChemicalPicker by remember { mutableStateOf(false) }
    var showPourModal by remember { mutableStateOf(false) }
    var showEquipmentPicker by remember { mutableStateOf(false) }
    var showSubstancesSheet by remember { mutableStateOf(false) }
    var showSaveDialog by remember { mutableStateOf(false) }
    var saveTitle by remember { mutableStateOf("") }
    var saveNotes by remember { mutableStateOf("") }
    var isMuted by remember { mutableStateOf(false) }

    // Auto-clear countdown timer & blast sound duration tracker
    var isCountdownActive by remember { mutableStateOf(false) }
    var countdownTotalDurationMs by remember { mutableLongStateOf(5000L) }
    var countdownRemainingMs by remember { mutableLongStateOf(5000L) }
    var userCancelledCountdown by remember { mutableStateOf(false) }
    var lastBlastEventTimeMs by remember { mutableLongStateOf(0L) }

    // Automatic sound cutoff: stops blast sound after 5 seconds
    LaunchedEffect(lastBlastEventTimeMs) {
        if (lastBlastEventTimeMs > 0L) {
            delay(5000L)
            viewModel.soundEffectsManager?.stopAll()
        }
    }

    LaunchedEffect(activeContainer.visualState.blastIntensity, activeContainer.isShattered) {
        val hasBlast = activeContainer.visualState.blastIntensity > 0.15f
        val isShattered = activeContainer.isShattered
        if ((hasBlast || isShattered) && !isCountdownActive && !userCancelledCountdown) {
            isCountdownActive = true
            countdownTotalDurationMs = 5000L
            countdownRemainingMs = 5000L
            lastBlastEventTimeMs = System.currentTimeMillis()
        } else if (hasBlast) {
            // If blast happens again during wait, re-trigger 5-second sound cutoff
            lastBlastEventTimeMs = System.currentTimeMillis()
        } else if (!hasBlast && !isShattered) {
            userCancelledCountdown = false
            isCountdownActive = false
        }
    }

    LaunchedEffect(isCountdownActive, countdownTotalDurationMs) {
        if (isCountdownActive) {
            val tickIntervalMs = 50L
            while (isActive && countdownRemainingMs > 0L && isCountdownActive) {
                delay(tickIntervalMs)
                countdownRemainingMs = (countdownRemainingMs - tickIntervalMs).coerceAtLeast(0L)
            }

            if (isCountdownActive && countdownRemainingMs <= 0L) {
                // Stop sound effects
                viewModel.soundEffectsManager?.stopAll()
                // Clear experiment area & replace with brand new clean beaker
                viewModel.resetActiveContainer()
                viewModel.dismissReactionAlert()
                isCountdownActive = false
                userCancelledCountdown = false
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Surface(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth()
                        .border(1.dp, CyanAccent.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    color = LabSurfaceCard,
                    shape = RoundedCornerShape(12.dp),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = data.visuals.message,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = { data.dismiss() },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = TextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        },
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
                        Text(
                            text = "Lab Workspace",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Surface(
                            color = if (state.isRunning) EmeraldSuccess.copy(alpha = 0.2f) else AmberWarning.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = if (state.isRunning) "LIVE" else "PAUSED",
                                color = if (state.isRunning) EmeraldSuccess else AmberWarning,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
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
                actions = {
                    // Play / Pause
                    IconButton(onClick = { viewModel.togglePlayPause() }) {
                        Icon(
                            imageVector = if (state.isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = "Play/Pause",
                            tint = CyanAccent
                        )
                    }

                    // Speed Toggle
                    Surface(
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .clickable {
                                val nextSpeed = when (state.timeSpeedMultiplier) {
                                    1.0f -> 2.0f
                                    2.0f -> 5.0f
                                    else -> 1.0f
                                }
                                viewModel.setSpeedMultiplier(nextSpeed)
                            }
                            .border(1.dp, LabBorder, RoundedCornerShape(8.dp)),
                        color = LabSurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "${state.timeSpeedMultiplier.toInt()}x",
                            color = SkyAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                        )
                    }

                    // Sound Mute/Unmute Toggle
                    IconButton(onClick = {
                        val muted = viewModel.soundEffectsManager?.toggleMute() ?: false
                        isMuted = muted
                    }) {
                        Icon(
                            imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = if (isMuted) "Unmute SFX" else "Mute SFX",
                            tint = if (isMuted) AmberWarning else CyanAccent
                        )
                    }

                    // Reset Container
                    IconButton(onClick = { viewModel.resetActiveContainer() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset Container", tint = TextSecondary)
                    }

                    // Save Experiment
                    IconButton(onClick = { showSaveDialog = true }) {
                        Icon(Icons.Default.Bookmark, contentDescription = "Save Experiment", tint = CyanAccent)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LabDarkBg)
            )
        },
        containerColor = LabDarkBg
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top HUD & Alerts
                Column {
                    ReactionAlertBanner(
                        reaction = latestAlert,
                        onInspectClick = {
                            viewModel.inspectReaction(latestAlert)
                            viewModel.dismissReactionAlert()
                        }
                    )

                    // Auto-Clear 5-Second Countdown HUD
                    AutoClearCountdownHUD(
                        isVisible = isCountdownActive && (activeContainer.visualState.blastIntensity > 0.15f || activeContainer.isShattered),
                        secondsRemaining = kotlin.math.ceil(countdownRemainingMs / 1000.0).toInt().coerceAtLeast(1),
                        progressFraction = (countdownRemainingMs.toFloat() / countdownTotalDurationMs.toFloat()).coerceIn(0f, 1f),
                        onClearNow = {
                            viewModel.soundEffectsManager?.stopAll()
                            viewModel.resetActiveContainer()
                            viewModel.dismissReactionAlert()
                            isCountdownActive = false
                            userCancelledCountdown = false
                        },
                        onWaitMore = { customSeconds ->
                            val durationMs = customSeconds * 1000L
                            countdownTotalDurationMs = durationMs
                            countdownRemainingMs = durationMs
                            isCountdownActive = true
                            userCancelledCountdown = false
                        },
                        onCancel = {
                            isCountdownActive = false
                            userCancelledCountdown = true
                        },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )

                    // Blast Alert Badge
                    AnimatedVisibility(visible = activeContainer.visualState.blastIntensity > 0.15f) {
                        Column {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = RubyHazard.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, RubyHazard, RoundedCornerShape(8.dp))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text("💥", fontSize = 16.sp)
                                    Text(
                                        text = "HIGH-ENERGY REACTION / BLAST SHOCKWAVE!",
                                        color = RubyHazard,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Damage Alert Banner (Shattered / Thermal Cracks)
                    AnimatedVisibility(visible = activeContainer.isShattered || activeContainer.isCracked) {
                        Column {
                            BeakerDamageAlertBanner(
                                container = activeContainer,
                                onReplaceVessel = { viewModel.resetActiveContainer() }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }

                    TelemetryHUD(container = activeContainer)

                    Spacer(modifier = Modifier.height(6.dp))

                    // Chemical Reaction Chain Information Bar
                    ReactionChainHUD(
                        container = activeContainer,
                        onReactionClick = { reaction -> viewModel.inspectReaction(reaction) }
                    )
                }

                // Center Workbench: 2D Glassware & Burner (Stable Layout, No Jarring Cross-Element Shaking)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Formula Banner for Active Vessel (e.g. HCl + NaOH + ...)
                        BeakerFormulaBanner(
                            container = activeContainer,
                            onChemicalsClick = { showChemicalPicker = true },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Stable Fixed Vessel Stage
                        Box(
                            modifier = Modifier.size(width = 240.dp, height = 280.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                GlasswareCanvas(
                                    container = activeContainer,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .padding(4.dp),
                                    isSelected = true
                                )

                                // Bunsen Burner underneath if heated
                                if (activeContainer.heatSourceWatts > 0.1 && !activeContainer.isShattered) {
                                    BunsenBurnerCanvas(
                                        isIgnited = true,
                                        heatWatts = activeContainer.heatSourceWatts,
                                        modifier = Modifier
                                            .size(width = 140.dp, height = 65.dp)
                                            .padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    // 2-Second Billowing Explosion Smoke Overlay covering Workbench
                    FullScreenSmokeCanvas(
                        smokeAlpha = activeContainer.visualState.smokeScreenAlpha,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Secondary Container thumbnail for pouring
                    if (secondaryContainer != null) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 8.dp, bottom = 8.dp)
                                .clickable { viewModel.selectActiveContainer(secondaryContainer.id) }
                        ) {
                            GlassCard(
                                modifier = Modifier.size(width = 100.dp, height = 110.dp),
                                borderColor = LabBorder
                            ) {
                                Column(
                                    modifier = Modifier.padding(6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = secondaryContainer.name,
                                        color = TextSecondary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    GlasswareCanvas(
                                        container = secondaryContainer,
                                        modifier = Modifier.size(60.dp),
                                        isSelected = false
                                    )
                                }
                            }
                        }
                    }
                }

                // Bottom Lab Controls & Action Rack
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    // Quick Action Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Add Chemical
                        Button(
                            onClick = { showChemicalPicker = true },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = LabDarkBg)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+ Chemical", color = LabDarkBg, fontWeight = FontWeight.Bold)
                        }

                        // Pour Liquid
                        if (secondaryContainer != null && activeContainer.totalVolumeMl > 0.01) {
                            Button(
                                onClick = { showPourModal = true },
                                colors = ButtonDefaults.buttonColors(containerColor = LabSurfaceVariant),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.SwapHoriz, contentDescription = null, tint = SkyAccent)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Pour", color = TextPrimary)
                            }
                        }

                        // Burner Toggle
                        val isBurnerOn = activeContainer.heatSourceWatts > 0.1
                        Button(
                            onClick = { viewModel.toggleBurner() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isBurnerOn) RubyHazard else LabSurfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = if (isBurnerOn) LabDarkBg else Color(0xFFF97316))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (isBurnerOn) "Extinguish" else "Heat", color = if (isBurnerOn) LabDarkBg else TextPrimary)
                        }

                        // Stirrer Toggle
                        Button(
                            onClick = { viewModel.toggleStirrer() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (activeContainer.stirrerActive) EmeraldSuccess else LabSurfaceVariant
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Sync, contentDescription = null, tint = if (activeContainer.stirrerActive) LabDarkBg else SkyAccent)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(if (activeContainer.stirrerActive) "Stirring" else "Stir", color = if (activeContainer.stirrerActive) LabDarkBg else TextPrimary)
                        }

                        // Change Apparatus
                        Button(
                            onClick = { showEquipmentPicker = true },
                            colors = ButtonDefaults.buttonColors(containerColor = LabSurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Science, contentDescription = null, tint = SkyAccent)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Apparatus", color = TextPrimary)
                        }

                        // View Chemical Contents
                        Button(
                            onClick = { showSubstancesSheet = true },
                            colors = ButtonDefaults.buttonColors(containerColor = LabSurfaceVariant),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.FormatListBulleted, contentDescription = null, tint = TextSecondary)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Substances (${activeContainer.substances.size})", color = TextPrimary)
                        }
                    }
                }
            }
        }
    }

    // Modal: Chemical Picker
    if (showChemicalPicker) {
        ChemicalPickerModal(
            onDismiss = { showChemicalPicker = false },
            onChemicalSelected = { chemical, amount, isVolume, conc ->
                viewModel.addChemical(chemical.id, amount, isVolume, conc)
            }
        )
    }

    // Modal: Pouring
    if (showPourModal && secondaryContainer != null) {
        PourModal(
            source = activeContainer,
            target = secondaryContainer,
            onDismiss = { showPourModal = false },
            onPour = { volume -> viewModel.pourLiquid(volume) }
        )
    }

    // Modal: Apparatus Picker
    if (showEquipmentPicker) {
        ModalBottomSheet(
            onDismissRequest = { showEquipmentPicker = false },
            containerColor = LabDarkBg
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Select Chemistry Lab Apparatus", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Choose from complete borosilicate glassware, volumetric equipment, and specialized analytical vessels", color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))

                EquipmentType.values().forEach { eq ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                viewModel.changeEquipment(eq)
                                showEquipmentPicker = false
                            }
                            .border(1.dp, if (activeContainer.equipmentType == eq) CyanAccent else LabBorder, RoundedCornerShape(10.dp)),
                        color = LabSurfaceCard,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(text = eq.displayName, color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    if (activeContainer.equipmentType == eq) {
                                        Surface(
                                            color = CyanAccent.copy(alpha = 0.2f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = "ACTIVE",
                                                color = CyanAccent,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                }
                                Text(text = "${eq.capacityMl.toInt()} mL • ${eq.description}", color = TextSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    // Bottom Sheet: Chemical Contents Breakdown
    if (showSubstancesSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSubstancesSheet = false },
            containerColor = LabDarkBg
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Container Contents & Solutes", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                if (activeContainer.substances.isEmpty()) {
                    Text("Container is currently empty. Add chemicals from the inventory!", color = TextSecondary, fontSize = 13.sp)
                } else {
                    activeContainer.substances.forEach { sub ->
                        val chem = ChemicalRegistry.get(sub.chemicalId)
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .border(1.dp, LabBorder, RoundedCornerShape(10.dp)),
                            color = LabSurfaceCard,
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = chem?.name ?: sub.chemicalId, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                    Text(
                                        text = "${chem?.formula ?: ""} • ${sub.phase.name}${if (sub.isPrecipitated) " (Precipitate)" else ""}",
                                        color = SkyAccent,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "${String.format(Locale.US, "%.2f", sub.massGrams)} g",
                                        color = TextPrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        text = "${String.format(Locale.US, "%.4f", sub.moles)} mol",
                                        color = TextSecondary,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Modal: Save Experiment Dialog
    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Experiment Snapshot", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    OutlinedTextField(
                        value = saveTitle,
                        onValueChange = { saveTitle = it },
                        label = { Text("Experiment Title") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = LabBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = saveNotes,
                        onValueChange = { saveNotes = it },
                        label = { Text("Notes / Observations") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CyanAccent,
                            unfocusedBorderColor = LabBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveExperiment(saveTitle, saveNotes)
                        showSaveDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanAccent)
                ) {
                    Text("Save", color = LabDarkBg, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showSaveDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = LabSurfaceVariant)
                ) {
                    Text("Cancel", color = TextPrimary)
                }
            },
            containerColor = LabSurface
        )
    }

    // Modal: Calculation Inspector
    if (inspectedReaction != null) {
        CalculationInspectorDialog(
            reaction = inspectedReaction!!,
            onDismiss = { viewModel.inspectReaction(null) }
        )
    }
}

