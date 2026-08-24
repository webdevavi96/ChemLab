package com.webdevavi.chemlabsimulator.simulation.model

import kotlinx.serialization.Serializable

@Serializable
data class SubstanceState(
    val chemicalId: String,
    val massGrams: Double,
    val volumeMl: Double,
    val moles: Double,
    val concentrationMolar: Double? = null,
    val temperatureCelsius: Double = 20.0,
    val phase: Phase = Phase.LIQUID,
    val isPrecipitated: Boolean = false,
    val isDissolved: Boolean = false,
    val isGasVapor: Boolean = false
)

