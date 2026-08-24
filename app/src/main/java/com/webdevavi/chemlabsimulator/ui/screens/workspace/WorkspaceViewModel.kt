package com.webdevavi.chemlabsimulator.ui.screens.workspace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webdevavi.chemlabsimulator.audio.SoundEffectsManager
import com.webdevavi.chemlabsimulator.data.repository.SavedExperimentRepository
import com.webdevavi.chemlabsimulator.simulation.SimulationEngine
import com.webdevavi.chemlabsimulator.simulation.chemistry.ChemicalRegistry
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import com.webdevavi.chemlabsimulator.simulation.model.SimulationState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class WorkspaceViewModel(
    private val savedExperimentRepository: SavedExperimentRepository = SavedExperimentRepository(),
    var soundEffectsManager: SoundEffectsManager? = null
) : ViewModel() {

    private val _state = MutableStateFlow(
        SimulationState(
            containers = listOf(
                ContainerState(id = "c1", name = "Main Beaker", equipmentType = EquipmentType.BEAKER_250),
                ContainerState(id = "c2", name = "Secondary Flask", equipmentType = EquipmentType.ERLENMEYER_250)
            ),
            activeContainerId = "c1",
            secondaryContainerId = "c2"
        )
    )
    val state: StateFlow<SimulationState> = _state.asStateFlow()

    private val _inspectedReaction = MutableStateFlow<ReactionResult?>(null)
    val inspectedReaction: StateFlow<ReactionResult?> = _inspectedReaction.asStateFlow()

    private val _latestReactionAlert = MutableStateFlow<ReactionResult?>(null)
    val latestReactionAlert: StateFlow<ReactionResult?> = _latestReactionAlert.asStateFlow()

    private val _unreactedMessage = MutableStateFlow<String?>(null)
    val unreactedMessage: StateFlow<String?> = _unreactedMessage.asStateFlow()

    private var simulationLoopJob: Job? = null

    init {
        startSimulationLoop()
    }

    private fun startSimulationLoop() {
        simulationLoopJob?.cancel()
        simulationLoopJob = viewModelScope.launch {
            while (isActive) {
                val currentState = _state.value
                if (currentState.isRunning) {
                    val dt = 0.25 * currentState.timeSpeedMultiplier
                    val previousShattered = currentState.containers.associate { it.id to it.isShattered }
                    val updatedContainers = currentState.containers.map { container ->
                        val updated = SimulationEngine.step(container, dtSeconds = dt)
                        if (updated.lastReactionEvents.isNotEmpty()) {
                            val wasShattered = previousShattered[container.id] ?: false
                            val newlyShattered = updated.isShattered && !wasShattered
                            val latest = updated.lastReactionEvents.last()
                            if (latest.isBlast || latest.blastIntensity > 0.05f || newlyShattered) {
                                soundEffectsManager?.playReactionEffects(latest, isShattered = newlyShattered)
                            }
                        }
                        updated
                    }

                    _state.update {
                        it.copy(
                            containers = updatedContainers,
                            simulationTimeSeconds = it.simulationTimeSeconds + dt
                        )
                    }
                }
                delay(250) // 4Hz simulation tick rate
            }
        }
    }

    fun addChemical(chemicalId: String, amount: Double, isVolume: Boolean, concentrationMolar: Double? = null) {
        val currentState = _state.value
        val activeContainer = currentState.activeContainer ?: return

        val (updatedContainer, newEvents) = SimulationEngine.addChemical(
            container = activeContainer,
            chemicalId = chemicalId,
            amount = amount,
            isVolume = isVolume,
            concentrationMolar = concentrationMolar
        )

        val updatedList = currentState.containers.map {
            if (it.id == activeContainer.id) updatedContainer else it
        }

        val allTimelineEvents = if (newEvents.isNotEmpty()) {
            currentState.timelineEvents + newEvents
        } else {
            currentState.timelineEvents
        }

        _state.update {
            it.copy(
                containers = updatedList,
                timelineEvents = allTimelineEvents
            )
        }

        if (newEvents.isNotEmpty()) {
            _latestReactionAlert.value = newEvents.last()
            for (evt in newEvents) {
                soundEffectsManager?.playReactionEffects(evt, isShattered = updatedContainer.isShattered)
            }
        } else if (updatedContainer.substances.count { it.moles > 1e-5 } >= 2) {
            val lastSub = updatedContainer.substances.lastOrNull()
            val otherSub = updatedContainer.substances.firstOrNull { it.chemicalId != lastSub?.chemicalId }
            if (lastSub != null && otherSub != null) {
                val chemA = ChemicalRegistry.get(lastSub.chemicalId)
                val chemB = ChemicalRegistry.get(otherSub.chemicalId)
                if (chemA != null && chemB != null) {
                    val assessment = com.webdevavi.chemlabsimulator.simulation.chemistry.ChemistryReactionAlgorithm.assessReactivity(chemA, chemB)
                    _unreactedMessage.value = "ℹ️ ${assessment.explanation}"
                } else {
                    _unreactedMessage.value = "ℹ️ No chemical reaction occurred: Reagents remain unreacted in solution (Physical Mixture)."
                }
            } else {
                _unreactedMessage.value = "ℹ️ No chemical reaction occurred: Reagents remain unreacted in solution (Physical Mixture)."
            }
        }
    }

    fun dismissUnreactedMessage() {
        _unreactedMessage.value = null
    }

    fun pourLiquid(volumeMl: Double) {
        val currentState = _state.value
        val source = currentState.activeContainer ?: return
        val targetId = currentState.secondaryContainerId ?: return
        val target = currentState.containers.find { it.id == targetId } ?: return

        val (updatedSource, updatedTarget) = SimulationEngine.pour(source, target, volumeMl)

        val updatedList = currentState.containers.map {
            when (it.id) {
                source.id -> updatedSource
                target.id -> updatedTarget
                else -> it
            }
        }

        _state.update { it.copy(containers = updatedList) }

        if (updatedTarget.substances.count { it.moles > 1e-5 } >= 2 && updatedTarget.lastReactionEvents.isEmpty()) {
            _unreactedMessage.value = "ℹ️ Poured substances formed an unreacted physical mixture."
        }
    }

    fun toggleBurner() {
        _state.update { current ->
            val active = current.activeContainer ?: return@update current
            val newHeat = if (active.heatSourceWatts > 0.1) 0.0 else 500.0
            val updated = active.copy(heatSourceWatts = newHeat)
            current.copy(
                containers = current.containers.map { if (it.id == active.id) updated else it }
            )
        }
    }

    fun toggleStirrer() {
        _state.update { current ->
            val active = current.activeContainer ?: return@update current
            val updated = active.copy(stirrerActive = !active.stirrerActive)
            current.copy(
                containers = current.containers.map { if (it.id == active.id) updated else it }
            )
        }
    }

    fun togglePlayPause() {
        _state.update { it.copy(isRunning = !it.isRunning) }
    }

    fun setSpeedMultiplier(multiplier: Float) {
        _state.update { it.copy(timeSpeedMultiplier = multiplier) }
    }

    fun resetActiveContainer() {
        _state.update { current ->
            val active = current.activeContainer ?: return@update current
            val reset = ContainerState(
                id = active.id,
                name = active.name,
                equipmentType = active.equipmentType
            )
            current.copy(
                containers = current.containers.map { if (it.id == active.id) reset else it }
            )
        }
    }

    fun changeEquipment(equipmentType: EquipmentType) {
        _state.update { current ->
            val active = current.activeContainer ?: return@update current
            val updated = active.copy(
                equipmentType = equipmentType,
                name = equipmentType.displayName
            )
            current.copy(
                containers = current.containers.map { if (it.id == active.id) updated else it }
            )
        }
    }

    fun selectActiveContainer(id: String) {
        _state.update {
            val secondary = it.containers.find { c -> c.id != id }?.id
            it.copy(activeContainerId = id, secondaryContainerId = secondary)
        }
    }

    fun inspectReaction(reaction: ReactionResult?) {
        _inspectedReaction.value = reaction
    }

    fun dismissReactionAlert() {
        _latestReactionAlert.value = null
    }

    fun saveExperiment(title: String, notes: String) {
        viewModelScope.launch {
            savedExperimentRepository.saveExperiment(title, notes, _state.value)
        }
    }

    fun loadState(newState: SimulationState) {
        _state.value = newState
    }
}

