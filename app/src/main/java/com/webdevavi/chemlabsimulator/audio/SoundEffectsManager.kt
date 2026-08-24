package com.webdevavi.chemlabsimulator.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.SoundPool
import android.os.Build
import android.util.Log
import com.webdevavi.chemlabsimulator.R
import com.webdevavi.chemlabsimulator.simulation.model.ReactionResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.exp
import kotlin.math.sin
import kotlin.random.Random

/**
 * Manages low-latency audio sound effects for laboratory reactions, explosions,
 * firecrackers, shattering glassware, and bubbling effervescence.
 */
class SoundEffectsManager(private val context: Context) {

    private var soundPool: SoundPool? = null
    private var blastSoundId: Int = 0
    private var firecrackerSoundId: Int = 0
    private var glassShatterSoundId: Int = 0
    private var fizzSoundId: Int = 0

    private val isLoaded = AtomicBoolean(false)
    private var isMuted: Boolean = false
    private val activeStreamIds = java.util.concurrent.CopyOnWriteArrayList<Int>()

    init {
        initSoundPool()
    }

    private fun initSoundPool() {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(8)
                .setAudioAttributes(audioAttributes)
                .build().apply {
                    setOnLoadCompleteListener { _, _, status ->
                        if (status == 0) {
                            isLoaded.set(true)
                        }
                    }

                    blastSoundId = load(context, R.raw.sfx_explosion_blast, 1)
                    firecrackerSoundId = load(context, R.raw.sfx_firecracker_kracker, 1)
                    glassShatterSoundId = load(context, R.raw.sfx_glass_shatter, 1)
                    fizzSoundId = load(context, R.raw.sfx_chemical_fizz, 1)
                }
        } catch (e: Exception) {
            Log.e("SoundEffectsManager", "Failed to initialize SoundPool, will use synthesis fallback", e)
        }
    }

    fun playBlastSound(intensity: Float = 1.0f) {
        if (isMuted) return
        val vol = intensity.coerceIn(0.4f, 1.0f)
        val rate = 0.92f + (Random.nextFloat() * 0.16f) // 0.92 to 1.08 pitch variation

        if (isLoaded.get() && blastSoundId != 0) {
            val streamId = soundPool?.play(blastSoundId, vol, vol, 1, 0, rate) ?: 0
            if (streamId != 0) activeStreamIds.add(streamId)
        } else {
            synthesizeBlastAudio(intensity)
        }
    }

    fun playFirecrackerSound(intensity: Float = 1.0f) {
        if (isMuted) return
        val vol = intensity.coerceIn(0.5f, 1.0f)
        val rate = 0.95f + (Random.nextFloat() * 0.12f)

        if (isLoaded.get() && firecrackerSoundId != 0) {
            val streamId = soundPool?.play(firecrackerSoundId, vol, vol, 1, 0, rate) ?: 0
            if (streamId != 0) activeStreamIds.add(streamId)
        } else {
            synthesizeBlastAudio(intensity)
        }
    }

    fun playGlassShatterSound() {
        if (isMuted) return
        val vol = 0.9f
        val rate = 0.98f + (Random.nextFloat() * 0.08f)

        if (isLoaded.get() && glassShatterSoundId != 0) {
            val streamId = soundPool?.play(glassShatterSoundId, vol, vol, 1, 0, rate) ?: 0
            if (streamId != 0) activeStreamIds.add(streamId)
        }
    }

    fun playFizzSound() {
        if (isMuted) return
        val vol = 0.65f
        if (isLoaded.get() && fizzSoundId != 0) {
            val streamId = soundPool?.play(fizzSoundId, vol, vol, 0, 0, 1.0f) ?: 0
            if (streamId != 0) activeStreamIds.add(streamId)
        }
    }

    /**
     * Intelligently plays the appropriate sound effects for an incoming reaction result.
     */
    fun playReactionEffects(
        reaction: ReactionResult,
        isShattered: Boolean = false
    ) {
        if (isMuted) return

        if (reaction.isBlast || reaction.blastIntensity > 0.05f) {
            if (reaction.sparkColors.isNotEmpty()) {
                playFirecrackerSound(reaction.blastIntensity)
            } else {
                playBlastSound(reaction.blastIntensity)
            }

            if (isShattered) {
                playGlassShatterSound()
            }
        } else if (reaction.gasMoles > 0.005 || reaction.gasFormedId != null) {
            playFizzSound()
        }
    }

    fun stopAll() {
        try {
            activeStreamIds.forEach { streamId ->
                soundPool?.stop(streamId)
            }
            activeStreamIds.clear()
            soundPool?.autoPause()
            soundPool?.autoResume()
        } catch (e: Exception) {
            Log.e("SoundEffectsManager", "Error stopping audio streams", e)
        }
    }

    fun toggleMute(): Boolean {
        isMuted = !isMuted
        return isMuted
    }

    fun release() {
        try {
            stopAll()
            soundPool?.release()
            soundPool = null
        } catch (e: Exception) {
            Log.e("SoundEffectsManager", "Error releasing SoundPool", e)
        }
    }

    /**
     * Fallback real-time PCM audio synthesis using AudioTrack in case raw resources fail to decode.
     */
    private fun synthesizeBlastAudio(intensity: Float) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val sampleRate = 22050
                val durationSeconds = 1.0
                val numSamples = (sampleRate * durationSeconds).toInt()
                val buffer = ShortArray(numSamples)

                for (i in 0 until numSamples) {
                    val t = i.toDouble() / sampleRate
                    val env = exp(-4.0 * t)
                    val freq = 45.0 + 80.0 * exp(-10.0 * t)
                    val sub = sin(2.0 * Math.PI * freq * t)
                    val noise = (Random.nextDouble() * 2.0 - 1.0) * exp(-2.5 * t)
                    val sample = ((sub * 0.6 + noise * 0.4) * env * intensity * 30000.0).toInt()
                    buffer[i] = sample.coerceIn(-32767, 32767).toShort()
                }

                val audioTrack = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    AudioTrack.Builder()
                        .setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_GAME)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build()
                        )
                        .setAudioFormat(
                            AudioFormat.Builder()
                                .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                                .setSampleRate(sampleRate)
                                .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                                .build()
                        )
                        .setBufferSizeInBytes(buffer.size * 2)
                        .setTransferMode(AudioTrack.MODE_STATIC)
                        .build()
                } else {
                    @Suppress("DEPRECATION")
                    AudioTrack(
                        AudioManager.STREAM_MUSIC,
                        sampleRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        buffer.size * 2,
                        AudioTrack.MODE_STATIC
                    )
                }

                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.play()
            } catch (e: Exception) {
                Log.e("SoundEffectsManager", "AudioTrack synthesis error", e)
            }
        }
    }
}

