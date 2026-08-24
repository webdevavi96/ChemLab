package com.webdevavi.chemlabsimulator.data.repository

import android.content.Context
import com.webdevavi.chemlabsimulator.data.model.SavedExperimentSnapshot
import com.webdevavi.chemlabsimulator.simulation.model.SimulationState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.UUID

class SavedExperimentRepository(private val context: Context? = null) {

    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    private val memoryCache = mutableListOf<SavedExperimentSnapshot>()

    init {
        // Seed default template saves
        val sampleState = SimulationState()
        val defaultJson = json.encodeToString(sampleState)
        memoryCache.add(
            SavedExperimentSnapshot(
                id = "saved_sample_1",
                title = "Acid Neutralization Sandbox",
                createdAt = System.currentTimeMillis() - 86400000,
                notes = "Equimolar titration test of 1.0 M HCl with 1.0 M NaOH.",
                jsonState = defaultJson
            )
        )
    }

    suspend fun getAllSavedExperiments(): List<SavedExperimentSnapshot> = withContext(Dispatchers.IO) {
        if (context != null) {
            loadFromDisk()
        }
        memoryCache.sortedByDescending { it.createdAt }
    }

    suspend fun saveExperiment(title: String, notes: String, state: SimulationState): SavedExperimentSnapshot = withContext(Dispatchers.IO) {
        val jsonString = json.encodeToString(state)
        val snapshot = SavedExperimentSnapshot(
            id = UUID.randomUUID().toString(),
            title = title.ifBlank { "Experiment ${memoryCache.size + 1}" },
            createdAt = System.currentTimeMillis(),
            notes = notes,
            jsonState = jsonString
        )
        memoryCache.add(0, snapshot)
        if (context != null) {
            saveToDisk()
        }
        snapshot
    }

    suspend fun deleteExperiment(id: String) = withContext(Dispatchers.IO) {
        memoryCache.removeAll { it.id == id }
        if (context != null) {
            saveToDisk()
        }
    }

    fun decodeState(snapshot: SavedExperimentSnapshot): SimulationState? {
        return try {
            json.decodeFromString<SimulationState>(snapshot.jsonState)
        } catch (e: Exception) {
            null
        }
    }

    private fun getStorageFile(): File? {
        val filesDir = context?.filesDir ?: return null
        return File(filesDir, "saved_experiments.json")
    }

    private fun loadFromDisk() {
        val file = getStorageFile() ?: return
        if (!file.exists()) return
        try {
            val content = file.readText()
            val list = json.decodeFromString<List<SavedExperimentSnapshot>>(content)
            memoryCache.clear()
            memoryCache.addAll(list)
        } catch (_: Exception) {}
    }

    private fun saveToDisk() {
        val file = getStorageFile() ?: return
        try {
            val content = json.encodeToString(memoryCache.toList())
            file.writeText(content)
        } catch (_: Exception) {}
    }
}

