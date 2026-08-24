package com.webdevavi.chemlabsimulator.ui.screens.experiments

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.platform.LocalContext
import com.webdevavi.chemlabsimulator.audio.SoundEffectsManager
import com.webdevavi.chemlabsimulator.data.repository.ExperimentPresets
import com.webdevavi.chemlabsimulator.simulation.SimulationEngine
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import com.webdevavi.chemlabsimulator.ui.components.common.BeakerFormulaBanner
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
import com.webdevavi.chemlabsimulator.ui.components.canvas.BunsenBurnerCanvas
import com.webdevavi.chemlabsimulator.ui.components.canvas.FullScreenSmokeCanvas
import com.webdevavi.chemlabsimulator.ui.components.canvas.GlasswareCanvas
import com.webdevavi.chemlabsimulator.ui.components.common.AutoClearCountdownHUD
import com.webdevavi.chemlabsimulator.ui.components.common.BeakerDamageAlertBanner
import com.webdevavi.chemlabsimulator.ui.components.common.CalculationInspectorDialog
import com.webdevavi.chemlabsimulator.ui.components.common.GlassCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidedExperimentScreen(
    experimentId: String,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    val soundEffectsManager = remember { SoundEffectsManager(context) }

    val experiment = remember(experimentId) {
        ExperimentPresets.getById(experimentId) ?: ExperimentPresets.experiments.first()
    }

    var currentStepIndex by remember { mutableIntStateOf(0) }
    var container by remember {
        mutableStateOf(
            ContainerState(
                id = "exp_container",
                name = experiment.initialEquipment.displayName,
                equipmentType = experiment.initialEquipment
            )
        )
    }

    var inspectedReaction by remember { mutableStateOf<ReactionResult?>(null) }
    var isBurnerActive by remember { mutableStateOf(false) }

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
            soundEffectsManager.stopAll()
        }
    }

    LaunchedEffect(container.visualState.blastIntensity, container.isShattered) {
        val hasBlast = container.visualState.blastIntensity > 0.15f
        val isShattered = container.isShattered
        if ((hasBlast || isShattered) && !isCountdownActive && !userCancelledCountdown) {
            isCountdownActive = true
            countdownTotalDurationMs = 5000L
            countdownRemainingMs = 5000L
            lastBlastEventTimeMs = System.currentTimeMillis()
        } else if (hasBlast) {
            // Blast happened again while waiting: re-trigger 5s sound cutoff
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
                soundEffectsManager.stopAll()
                container = ContainerState(
                    id = "exp_container",
                    name = experiment.initialEquipment.displayName,
                    equipmentType = experiment.initialEquipment
                )
                isCountdownActive = false
                userCancelledCountdown = false
            }
        }
    }

    // Simulation step loop for guided experiment
    LaunchedEffect(isBurnerActive) {
        while (isActive) {
            val prevShattered = container.isShattered
            container = SimulationEngine.step(container, dtSeconds = 0.5)
            if (container.lastReactionEvents.isNotEmpty()) {
                val newlyShattered = container.isShattered && !prevShattered
                val latest = container.lastReactionEvents.last()
                if (latest.isBlast || newlyShattered) {
                    soundEffectsManager.playReactionEffects(latest, isShattered = newlyShattered)
                }
            }
            delay(250)
        }
    }

    val isFinished = currentStepIndex >= experiment.steps.size
    val currentStep = if (!isFinished) experiment.steps[currentStepIndex] else null

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(experiment.title, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(
                            text = "Step ${if (isFinished) experiment.steps.size else currentStepIndex + 1} of ${experiment.steps.size}",
                            color = SkyAccent,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        container = ContainerState(id = "exp_container", name = experiment.initialEquipment.displayName, equipmentType = experiment.initialEquipment)
                        currentStepIndex = 0
                        isBurnerActive = false
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Restart", tint = TextSecondary)
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
                .verticalScroll(rememberScrollState())
        ) {
            // Progress Bar
            val progress = ((currentStepIndex.toFloat()) / experiment.steps.size).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = CyanAccent,
                trackColor = LabSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Step Instruction Banner
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, if (isFinished) EmeraldSuccess else CyanAccent, RoundedCornerShape(14.dp)),
                color = LabSurfaceCard
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    if (isFinished) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = EmeraldSuccess, modifier = Modifier.size(24.dp))
                            Text("Experiment Protocol Completed!", color = EmeraldSuccess, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(experiment.educationalSummary, color = TextSecondary, fontSize = 12.sp, lineHeight = 16.sp)
                    } else {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .background(CyanAccent, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("${currentStepIndex + 1}", color = LabDarkBg, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Text("Step ${currentStepIndex + 1}", color = CyanAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(currentStep?.instruction ?: "", color = TextPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)

                        if (!currentStep?.hint.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("💡 Hint: ${currentStep!!.hint}", color = SkyAccent, fontSize = 12.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Auto-Clear Countdown HUD
            AutoClearCountdownHUD(
                isVisible = isCountdownActive && (container.visualState.blastIntensity > 0.15f || container.isShattered),
                secondsRemaining = kotlin.math.ceil(countdownRemainingMs / 1000.0).toInt().coerceAtLeast(1),
                progressFraction = (countdownRemainingMs.toFloat() / countdownTotalDurationMs.toFloat()).coerceIn(0f, 1f),
                onClearNow = {
                    soundEffectsManager.stopAll()
                    container = ContainerState(
                        id = "exp_container",
                        name = experiment.initialEquipment.displayName,
                        equipmentType = experiment.initialEquipment
                    )
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
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Telemetry HUD
            TelemetryHUD(container = container)

            Spacer(modifier = Modifier.height(10.dp))

            // Damage Alert Banner (Shattered / Thermal Cracks)
            BeakerDamageAlertBanner(
                container = container,
                onReplaceVessel = {
                    container = ContainerState(
                        id = "exp_vessel",
                        name = "Reaction Vessel",
                        equipmentType = com.webdevavi.chemlabsimulator.simulation.model.EquipmentType.BEAKER_250
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Reaction Chain Flow
            ReactionChainHUD(
                container = container,
                onReactionClick = { reaction -> inspectedReaction = reaction }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Workbench View
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(LabSurface)
                    .border(1.dp, LabBorder, RoundedCornerShape(16.dp))
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BeakerFormulaBanner(
                        container = container,
                        onChemicalsClick = {},
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    GlasswareCanvas(
                        container = container,
                        modifier = Modifier.size(190.dp),
                        isSelected = true
                    )
                    if (isBurnerActive && !container.isShattered) {
                        BunsenBurnerCanvas(
                            isIgnited = true,
                            heatWatts = 500.0,
                            modifier = Modifier.size(width = 130.dp, height = 65.dp)
                        )
                    }
                }

                // 2-Second Billowing Explosion Smoke Overlay
                FullScreenSmokeCanvas(
                    smokeAlpha = container.visualState.smokeScreenAlpha,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Guided Action Button
            if (!isFinished && currentStep != null) {
                when (currentStep.expectedAction) {
                    "ADD_CHEMICAL" -> {
                        val chemId = currentStep.targetChemicalId ?: "H2O"
                        val amount = currentStep.targetAmount ?: 25.0
                        val chem = ChemicalRegistry.get(chemId)
                        val isVol = chem?.isSolvent == true || chem?.defaultPhase == com.webdevavi.chemlabsimulator.simulation.model.Phase.AQUEOUS || chem?.defaultPhase == com.webdevavi.chemlabsimulator.simulation.model.Phase.LIQUID

                        Button(
                            onClick = {
                                val (newContainer, events) = SimulationEngine.addChemical(
                                    container = container,
                                    chemicalId = chemId,
                                    amount = amount,
                                    isVolume = isVol,
                                    concentrationMolar = if (chem?.defaultPhase == com.webdevavi.chemlabsimulator.simulation.model.Phase.AQUEOUS) 1.0 else null
                                )
                                container = newContainer
                                if (events.isNotEmpty()) {
                                    inspectedReaction = events.last()
                                    for (evt in events) {
                                        soundEffectsManager.playReactionEffects(evt, isShattered = newContainer.isShattered)
                                    }
                                }
                                currentStepIndex++
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = LabDarkBg)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Perform Action: Add ${amount.toInt()} ${if (isVol) "mL" else "g"} ${chem?.name ?: chemId}",
                                color = LabDarkBg,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    "HEAT" -> {
                        Button(
                            onClick = {
                                isBurnerActive = !isBurnerActive
                                container = container.copy(heatSourceWatts = if (isBurnerActive) 500.0 else 0.0)
                                if (isBurnerActive) {
                                    soundEffectsManager.playFizzSound()
                                    currentStepIndex++
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isBurnerActive) RubyHazard else Color(0xFFF97316)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.LocalFireDepartment, contentDescription = null, tint = LabDarkBg)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isBurnerActive) "Turn Off Burner" else "Ignite Bunsen Burner",
                                color = LabDarkBg,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    else -> {
                        Button(
                            onClick = { currentStepIndex++ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = CyanAccent),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Next Step", color = LabDarkBg, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Chemical Equation & Educational Note Card
            Surface(
                color = LabSurfaceCard,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, LabBorder, RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Chemical Equation & Reaction Model", color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        color = LabSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = experiment.chemicalEquation,
                            color = CyanAccent,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Observation: ${experiment.expectedObservation}",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (inspectedReaction != null) {
        CalculationInspectorDialog(
            reaction = inspectedReaction!!,
            onDismiss = { inspectedReaction = null }
        )
    }
}

