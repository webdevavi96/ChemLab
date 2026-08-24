package com.webdevavi.chemlabsimulator.data.model

import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import kotlinx.serialization.Serializable

@Serializable
data class ExperimentMaterial(
    val chemicalId: String,
    val amount: Double,
    val isVolume: Boolean,
    val concentrationMolar: Double? = null,
    val label: String = ""
)

@Serializable
data class ExperimentStep(
    val stepNumber: Int,
    val instruction: String,
    val expectedAction: String, // e.g. "ADD_CHEMICAL", "HEAT", "MEASURE_PH", "POUR"
    val targetChemicalId: String? = null,
    val targetAmount: Double? = null,
    val hint: String = "",
    val completionMessage: String = ""
)

@Serializable
data class Experiment(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: String,
    val difficulty: String, // "Beginner", "Intermediate", "Advanced"
    val durationMinutes: Int,
    val objective: String,
    val initialEquipment: EquipmentType = EquipmentType.BEAKER_250,
    val materials: List<ExperimentMaterial>,
    val steps: List<ExperimentStep>,
    val chemicalEquation: String,
    val expectedObservation: String,
    val educationalSummary: String,
    val iconName: String = "science"
)

@Serializable
data class SavedExperimentSnapshot(
    val id: String,
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val notes: String = "",
    val jsonState: String // Serialized SimulationState
)

