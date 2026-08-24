package com.webdevavi.chemlabsimulator.ui.components.canvas

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

object ParticleEffects {

    /**
     * Draws animated rising gas particles and billowing mist clouds depending on gas type.
     */
    fun drawGasEmanation(
        drawScope: DrawScope,
        leftX: Float,
        rightX: Float,
        topY: Float,
        liquidTopY: Float,
        phase: Float,
        gasType: String?,
        bubbleIntensity: Float
    ) {
        if (bubbleIntensity <= 0.05f && gasType == null) return

        val width = rightX - leftX
        val cx = leftX + width / 2f

        drawScope.apply {
            when (gasType) {
                "H2_gas" -> {
                    // Fast rising, buoyant hydrogen gas particles with subtle glow
                    for (i in 0..14) {
                        val seed = (i * 29) % 100
                        val pPhase = phase * 2.5f + i * 0.9f
                        val progress = (pPhase % 3.0f) / 3.0f
                        val px = cx + (seed - 50) * 0.8f + 8f * sin(pPhase * 2f + i)
                        val py = liquidTopY - progress * (liquidTopY - topY + 80f)
                        val alpha = ((1f - progress) * 0.75f * bubbleIntensity).coerceIn(0f, 1f)
                        val radius = 3f + (i % 4) * 2f

                        drawCircle(
                            color = Color(0xFF67E8F9).copy(alpha = alpha),
                            radius = radius,
                            center = Offset(px, py)
                        )
                        drawCircle(
                            color = Color.White.copy(alpha = alpha * 0.8f),
                            radius = radius * 0.5f,
                            center = Offset(px, py)
                        )
                    }
                }
                "CO2_gas" -> {
                    // Dense, heavy carbon dioxide foaming froth and billowing cloud
                    // Surface Froth / Foam
                    for (i in 0..16) {
                        val fx = leftX + 4f + (i * 12f) % (width - 8f)
                        val fy = liquidTopY + 3f * sin(phase * 3f + i)
                        val radius = 4f + (i % 3) * 2.5f
                        drawCircle(
                            color = Color.White.copy(alpha = 0.85f * bubbleIntensity.coerceIn(0f, 1f)),
                            radius = radius,
                            center = Offset(fx, fy)
                        )
                    }

                    // Rolling dense mist above rim
                    for (i in 0..8) {
                        val mistPhase = phase * 1.2f + i * 1.4f
                        val mx = cx + 45f * sin(mistPhase)
                        val my = topY - 10f - ((mistPhase % 4f) * 12f)
                        val radius = 16f + i * 5f
                        val alpha = (0.35f * (1f - (topY - my) / 60f) * bubbleIntensity).coerceIn(0f, 0.45f)
                        drawCircle(
                            color = Color(0xFFE2E8F0).copy(alpha = alpha),
                            radius = radius,
                            center = Offset(mx, my)
                        )
                    }
                }
                else -> {
                    // Standard bubbling gas trail (e.g. O2 or generic gas evolution)
                    for (i in 0..10) {
                        val gPhase = phase * 2.0f + i * 1.2f
                        val progress = (gPhase % 2.5f) / 2.5f
                        val gx = cx + 25f * sin(gPhase + i)
                        val gy = liquidTopY - progress * 70f
                        val alpha = ((1f - progress) * 0.6f * bubbleIntensity).coerceIn(0f, 0.7f)
                        drawCircle(
                            color = Color.White.copy(alpha = alpha),
                            radius = 4f + (i % 3) * 2f,
                            center = Offset(gx, gy)
                        )
                    }
                }
            }
        }
    }

    /**
     * Draws boiling turbulence, convection currents, expanding steam bubbles, and billowing steam.
     */
    fun drawBoilingEffects(
        drawScope: DrawScope,
        leftX: Float,
        rightX: Float,
        bottomY: Float,
        liquidTopY: Float,
        topY: Float,
        phase: Float,
        steamIntensity: Float
    ) {
        val width = rightX - leftX
        val cx = leftX + width / 2f

        drawScope.apply {
            // 1. Boiling Bottom-Originating Bubbles
            for (i in 0..18) {
                val seed = (i * 47) % 100
                val bPhase = phase * 3.5f + i * 0.7f
                val progress = (bPhase % 2.0f) / 2.0f
                val bx = leftX + 12f + (seed / 100f) * (width - 24f) + 6f * sin(bPhase * 3f + i)
                val by = bottomY - 6f - progress * (bottomY - liquidTopY)

                if (by > liquidTopY) {
                    val radius = 3.5f + progress * 5f + (i % 3) * 2f
                    drawCircle(
                        color = Color.White.copy(alpha = 0.85f),
                        radius = radius,
                        center = Offset(bx, by)
                    )
                    drawCircle(
                        color = Color(0xFF67E8F9).copy(alpha = 0.5f),
                        radius = radius * 0.65f,
                        center = Offset(bx - 1f, by - 1f)
                    )
                }
            }

            // 2. Swirling Steam Plume billows out of the container
            if (steamIntensity > 0.05f) {
                for (i in 0..10) {
                    val sPhase = phase * 1.6f + i * 0.95f
                    val progress = (sPhase % 3.5f) / 3.5f
                    val sx = cx + (progress * 35f + 10f) * sin(sPhase * 1.5f + i)
                    val sy = topY - progress * 95f * steamIntensity
                    val radius = 14f + progress * 24f + i * 3f
                    val alpha = ((1f - progress) * 0.45f * steamIntensity).coerceIn(0f, 0.55f)

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = alpha), Color.White.copy(alpha = 0f)),
                            center = Offset(sx, sy),
                            radius = radius
                        ),
                        radius = radius,
                        center = Offset(sx, sy)
                    )
                }
            }
        }
    }

    /**
     * Draws energetic chemical blast: radial flash, expanding shockwave rings, flying sparks, and smoke.
     */
    fun drawBlastShockwave(
        drawScope: DrawScope,
        centerX: Float,
        centerY: Float,
        blastIntensity: Float,
        flashColorHex: Long,
        phase: Float,
        sparkColors: List<Long> = emptyList(),
        isFirecracker: Boolean = false
    ) {
        if (blastIntensity <= 0.02f) return

        drawScope.apply {
            val flashColor = Color(flashColorHex)
            val activeSparkColors = if (sparkColors.isNotEmpty()) {
                sparkColors.map { Color(it) }
            } else {
                listOf(
                    Color(0xFFEF4444), // Crimson Red
                    Color(0xFF22C55E), // Emerald Green
                    Color(0xFF06B6D4), // Cyan
                    Color(0xFFA855F7), // Violet
                    Color(0xFFFDE047), // Gold
                    Color.White
                )
            }

            // 1. Central Fiery Flash Aura
            val flashRadius = 150f * blastIntensity
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.95f * blastIntensity),
                        flashColor.copy(alpha = 0.85f * blastIntensity),
                        flashColor.copy(alpha = 0f)
                    ),
                    center = Offset(centerX, centerY),
                    radius = flashRadius
                ),
                radius = flashRadius,
                center = Offset(centerX, centerY)
            )

            // 2. Expanding Shockwave Rings
            for (ring in 1..3) {
                val ringPhase = (phase * 4f + ring * 1.2f) % 2f
                val ringRadius = (30f + ringPhase * 90f) * blastIntensity
                val ringAlpha = ((1f - ringPhase / 2f) * 0.85f * blastIntensity).coerceIn(0f, 0.90f)
                val ringColor = activeSparkColors[ring % activeSparkColors.size]
                drawCircle(
                    color = ringColor.copy(alpha = ringAlpha),
                    radius = ringRadius,
                    center = Offset(centerX, centerY),
                    style = Stroke(width = 4.5f * (1f - ringPhase / 2f))
                )
            }

            // 3. Flying Fiery & Colorful Firecracker Starburst Sparks
            val sparkCount = if (isFirecracker || sparkColors.isNotEmpty()) (42 * blastIntensity).toInt() else (24 * blastIntensity).toInt()
            for (i in 0..sparkCount) {
                val angle = (i * 2 * PI / sparkCount) + sin(phase * 6f + i) * 0.35f
                val sparkDist = (35f + ((i * 37) % 85) + (phase * 70f) % 110f) * blastIntensity
                val sx = centerX + sparkDist * cos(angle).toFloat()
                val sy = centerY + sparkDist * sin(angle).toFloat() - (sparkDist * 0.25f)
                val sparkSize = 3.5f + (i % 4) * 2.2f
                val particleColor = activeSparkColors[i % activeSparkColors.size]

                // Outer colored halo
                drawCircle(
                    color = particleColor.copy(alpha = 0.85f * blastIntensity),
                    radius = sparkSize * 1.8f,
                    center = Offset(sx, sy)
                )
                // Bright Core
                drawCircle(
                    color = Color.White,
                    radius = sparkSize * 0.75f,
                    center = Offset(sx, sy)
                )

                // Firecracker Crosshair / Star Sparkle
                if (i % 3 == 0) {
                    val starLen = sparkSize * 2.2f
                    drawLine(
                        color = particleColor.copy(alpha = 0.9f),
                        start = Offset(sx - starLen, sy),
                        end = Offset(sx + starLen, sy),
                        strokeWidth = 1.5f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = particleColor.copy(alpha = 0.9f),
                        start = Offset(sx, sy - starLen),
                        end = Offset(sx, sy + starLen),
                        strokeWidth = 1.5f,
                        cap = StrokeCap.Round
                    )
                }
            }

            // 4. Rising Smoke Puff
            for (i in 0..6) {
                val smokePhase = phase * 2f + i * 1.1f
                val smX = centerX + 20f * sin(smokePhase + i)
                val smY = centerY - 40f - ((smokePhase % 3f) * 25f * blastIntensity)
                val smokeRadius = 18f + i * 6f
                val smokeAlpha = (0.4f * blastIntensity * (1f - (centerY - smY) / 140f)).coerceIn(0f, 0.45f)

                drawCircle(
                    color = Color(0xFF475569).copy(alpha = smokeAlpha),
                    radius = smokeRadius,
                    center = Offset(smX, smY)
                )
            }
        }
    }

    /**
     * Fills the entire screen/workbench with thick billowing explosion smoke when a beaker explodes.
     * Lasts for 2 seconds with rolling particulate vortexes.
     */
    fun drawFullScreenSmoke(
        drawScope: DrawScope,
        width: Float,
        height: Float,
        smokeAlpha: Float,
        phase: Float
    ) {
        if (smokeAlpha <= 0.01f) return

        drawScope.apply {
            // 1. Semi-opaque dark ambient smoke veil
            drawRect(
                color = Color(0xDD0F172A).copy(alpha = (0.80f * smokeAlpha).coerceIn(0f, 0.96f)),
                size = Size(width, height)
            )

            // 2. Thick rolling volumetric smoke clouds
            val cloudCount = 30
            for (i in 0..cloudCount) {
                val seed = (i * 61) % 100
                val sPhase = phase * 1.5f + i * 0.85f
                val cx = (seed / 100f) * width + 40f * sin(sPhase + i)
                val cy = ((i * 41) % 100 / 100f) * height + 30f * cos(sPhase * 1.2f + i)
                val radius = (width * 0.24f) + (i % 6) * 18f
                val cloudAlpha = ((0.55f + 0.35f * sin(sPhase)) * smokeAlpha).coerceIn(0f, 0.88f)

                val smokeColor = when (i % 3) {
                    0 -> Color(0xFF334155) // Dark slate smoke
                    1 -> Color(0xFF64748B) // Grey vapor
                    else -> Color(0xFF1E293B) // Dense charcoal
                }

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            smokeColor.copy(alpha = cloudAlpha),
                            smokeColor.copy(alpha = cloudAlpha * 0.6f),
                            Color.Transparent
                        ),
                        center = Offset(cx, cy),
                        radius = radius
                    ),
                    radius = radius,
                    center = Offset(cx, cy)
                )
            }

            // 3. Central explosion fiery ember glow remnants
            if (smokeAlpha > 0.3f) {
                val emberCount = (20 * smokeAlpha).toInt()
                for (i in 0..emberCount) {
                    val ex = width / 2f + (sin(phase * 3f + i) * width * 0.35f)
                    val ey = height / 2f + (cos(phase * 4f + i) * height * 0.35f)
                    drawCircle(
                        color = Color(0xFFF97316).copy(alpha = 0.85f * smokeAlpha),
                        radius = 3.5f + (i % 3) * 2f,
                        center = Offset(ex, ey)
                    )
                }
            }
        }
    }

    /**
     * Draws realistic thermal stress fracture crack patterns across the glassware walls.
    /**
     * Draws thermal glass fracture lines across the beaker body.
     * Level 1 (150-300°C): Hairline spider cracks.
     * Level 2 (300-500°C): Increased, dense branching fracture network.
     */
    fun drawThermalCracks(
        drawScope: DrawScope,
        leftX: Float,
        rightX: Float,
        topY: Float,
        bottomY: Float,
        phase: Float,
        crackLevel: Int = 1
    ) {
        drawScope.apply {
            val width = rightX - leftX
            val height = bottomY - topY
            val cx = leftX + width / 2f
            val cy = topY + height / 2f

            // Level 1: Primary vertical & diagonal stress fractures
            val crackPath1 = Path().apply {
                moveTo(cx - 15f, bottomY - 6f)
                lineTo(cx - 8f, cy + height * 0.25f)
                lineTo(cx + 12f, cy)
                lineTo(cx + 4f, cy - height * 0.25f)
                lineTo(cx + 18f, topY + 12f)
            }

            val crackPath2 = Path().apply {
                moveTo(cx + 12f, cy)
                lineTo(rightX - 10f, cy - 25f)
                lineTo(rightX - 4f, cy - 35f)
            }

            val crackPath3 = Path().apply {
                moveTo(cx - 8f, cy + height * 0.25f)
                lineTo(leftX + 14f, cy + height * 0.35f)
                lineTo(leftX + 4f, cy + height * 0.42f)
            }

            val paths = mutableListOf(crackPath1, crackPath2, crackPath3)

            // Level 2 (350-500°C): Increased extensive spiderweb fracture network
            if (crackLevel >= 2) {
                val crackPath4 = Path().apply {
                    moveTo(leftX + 8f, bottomY - 12f)
                    lineTo(cx - 20f, cy + height * 0.15f)
                    lineTo(cx - 5f, cy - height * 0.1f)
                    lineTo(cx - 25f, topY + 20f)
                }
                val crackPath5 = Path().apply {
                    moveTo(rightX - 8f, bottomY - 14f)
                    lineTo(cx + 25f, cy + height * 0.2f)
                    lineTo(cx + 15f, cy - height * 0.05f)
                    lineTo(rightX - 12f, topY + 30f)
                }
                val crackPath6 = Path().apply {
                    moveTo(cx, bottomY - 4f)
                    lineTo(cx - 30f, bottomY - 24f)
                    lineTo(cx + 30f, bottomY - 36f)
                }
                paths.addAll(listOf(crackPath4, crackPath5, crackPath6))
            }

            val crackGlowColor = if (crackLevel >= 2) Color(0xFFFCA5A5) else Color(0xFFE2E8F0)
            val crackInnerColor = if (crackLevel >= 2) Color(0xFF7F1D1D) else Color(0xFF0F172A)

            paths.forEach { path ->
                // Outer glass crack highlight reflection
                drawPath(
                    path = path,
                    color = crackGlowColor.copy(alpha = 0.95f),
                    style = Stroke(width = if (crackLevel >= 2) 3.0f else 2.2f, cap = StrokeCap.Round)
                )
                // Inner dark crack fracture line
                drawPath(
                    path = path,
                    color = crackInnerColor,
                    style = Stroke(width = 1.0f, cap = StrokeCap.Round)
                )
            }
        }
    }

    /**
     * Draws glowing red-hot incandescent heat glow at the bottom of the glass (350°C - 500°C).
     */
    fun drawRedHotBottom(
        drawScope: DrawScope,
        leftX: Float,
        rightX: Float,
        bottomY: Float,
        height: Float,
        cornerRadius: Float,
        phase: Float
    ) {
        drawScope.apply {
            val width = rightX - leftX
            val glowHeight = height * 0.35f
            val glowTop = bottomY - glowHeight

            val glowPath = Path().apply {
                moveTo(leftX + 2f, glowTop)
                lineTo(rightX - 2f, glowTop)
                lineTo(rightX - 2f, bottomY - cornerRadius)
                quadraticTo(rightX - 2f, bottomY, rightX - cornerRadius, bottomY)
                lineTo(leftX + cornerRadius, bottomY)
                quadraticTo(leftX + 2f, bottomY, leftX + 2f, bottomY - cornerRadius)
                close()
            }

            val pulseAlpha = (0.75f + 0.15f * sin(phase * 4f)).coerceIn(0.6f, 0.95f)

            // Red-hot incandescent gradient
            drawPath(
                path = glowPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x00EF4444),
                        Color(0x77EF4444).copy(alpha = pulseAlpha * 0.6f),
                        Color(0xFFDC2626).copy(alpha = pulseAlpha * 0.9f),
                        Color(0xFFFF0000).copy(alpha = pulseAlpha)
                    ),
                    startY = glowTop,
                    endY = bottomY
                )
            )

            // Intense glowing base rim line
            drawLine(
                color = Color(0xFFFF4444).copy(alpha = pulseAlpha),
                start = Offset(leftX + cornerRadius, bottomY - 1f),
                end = Offset(rightX - cornerRadius, bottomY - 1f),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
        }
    }

    /**
     * Draws sagging, deformed molten glass with glowing red-orange softened shards (550°C - 750°C).
     */
    fun drawMoltenGlassware(
        drawScope: DrawScope,
        leftX: Float,
        rightX: Float,
        bottomY: Float,
        phase: Float,
        liquidColor: Color
    ) {
        drawScope.apply {
            val width = rightX - leftX
            val cx = leftX + width / 2f

            // 1. Spilled chemical puddle on workbench
            drawOval(
                color = liquidColor.copy(alpha = 0.75f),
                topLeft = Offset(cx - width * 0.7f, bottomY - 6f),
                size = Size(width * 1.4f, 22f)
            )

            // 2. Deformed, molten sagging glass puddle with glowing edges
            val moltenPath = Path().apply {
                moveTo(leftX - 10f, bottomY)
                quadraticTo(leftX + width * 0.25f, bottomY - 24f + 3f * sin(phase * 2f), cx, bottomY - 16f)
                quadraticTo(rightX - width * 0.25f, bottomY - 28f + 4f * cos(phase * 2f), rightX + 10f, bottomY)
                close()
            }

            drawPath(
                path = moltenPath,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEA580C).copy(alpha = 0.85f),
                        Color(0xFFDC2626).copy(alpha = 0.95f),
                        Color(0xFF7F1D1D)
                    ),
                    startY = bottomY - 28f,
                    endY = bottomY
                )
            )

            // 3. Broken molten glass lumps & chunks
            for (i in 0..6) {
                val gx = leftX + ((i * 31f) % (width + 20f)) - 10f
                val gy = bottomY - 8f - (i % 3) * 6f
                val gr = 8f + (i % 4) * 3f
                drawCircle(
                    color = Color(0xFFF97316).copy(alpha = 0.9f),
                    radius = gr,
                    center = Offset(gx, gy)
                )
                drawCircle(
                    color = Color(0xFFFEF08A),
                    radius = gr * 0.4f,
                    center = Offset(gx, gy)
                )
            }
        }
    }

    /**
     * Draws exploded/shattered beaker with jagged broken base, flying glass shards, and spilled puddle.
     */
    fun drawShatteredGlassware(
        drawScope: DrawScope,
        leftX: Float,
        rightX: Float,
        bottomY: Float,
        phase: Float,
        liquidColor: Color
    ) {
        drawScope.apply {
            val width = rightX - leftX
            val cx = leftX + width / 2f

            // 1. Spilled chemical puddle on workbench
            drawOval(
                color = liquidColor.copy(alpha = 0.75f),
                topLeft = Offset(cx - width * 0.65f, bottomY - 6f),
                size = Size(width * 1.3f, 18f)
            )

            // 2. Broken jagged glass base
            val baseJaggedPath = Path().apply {
                moveTo(leftX, bottomY - 14f)
                lineTo(leftX + width * 0.2f, bottomY - 4f)
                lineTo(leftX + width * 0.35f, bottomY - 18f)
                lineTo(leftX + width * 0.55f, bottomY - 6f)
                lineTo(leftX + width * 0.75f, bottomY - 22f)
                lineTo(rightX, bottomY - 8f)
                lineTo(rightX, bottomY)
                lineTo(leftX, bottomY)
                close()
            }
            drawPath(baseJaggedPath, color = Color(0x8894A3B8), style = Stroke(width = 2.5f))
            drawPath(baseJaggedPath, color = Color(0x3338BDF8))

            // 3. Flying sharp glass shards
            for (i in 0..12) {
                val sAngle = (i * 28f) * PI.toFloat() / 180f
                val sDist = 30f + ((i * 43) % 70)
                val sx = cx + sDist * cos(sAngle)
                val sy = bottomY - 40f - (sDist * 0.7f) * sin(sAngle).coerceAtLeast(0.1f)
                val shardSize = 10f + (i % 4) * 4f

                val shardPath = Path().apply {
                    moveTo(sx, sy - shardSize)
                    lineTo(sx + shardSize * 0.6f, sy + shardSize * 0.5f)
                    lineTo(sx - shardSize * 0.6f, sy + shardSize * 0.2f)
                    close()
                }

                drawPath(shardPath, color = Color.White.copy(alpha = 0.85f))
                drawPath(shardPath, color = Color(0xFF38BDF8), style = Stroke(width = 1.2f))
            }
        }
    }

    /**
     * Draws liquid pouring stream arc from source to target container.
     */
    fun drawPouringStream(
        drawScope: DrawScope,
        sourceX: Float,
        sourceY: Float,
        targetX: Float,
        targetY: Float,
        streamColor: Color,
        progress: Float
    ) {
        if (progress <= 0.01f) return

        drawScope.apply {
            val controlX = (sourceX + targetX) / 2f
            val controlY = sourceY - 40f

            val path = Path().apply {
                moveTo(sourceX, sourceY)
                quadraticTo(controlX, controlY, targetX, targetY)
            }

            // Stream shadow/glow
            drawPath(
                path = path,
                color = streamColor.copy(alpha = 0.35f),
                style = Stroke(width = 10f, cap = StrokeCap.Round)
            )

            // Main Liquid Core Stream
            drawPath(
                path = path,
                color = streamColor.copy(alpha = 0.9f),
                style = Stroke(width = 6f, cap = StrokeCap.Round)
            )

            // Splash ripples at target impact point
            drawCircle(
                color = streamColor.copy(alpha = 0.6f),
                radius = 12f * progress,
                center = Offset(targetX, targetY),
                style = Stroke(width = 2.5f)
            )
        }
    }

    /**
     * Draws radioactive radiation particle rays: Cherenkov blue-green glow,
     * ionizing gamma shockwave rings, fast beta-particle tracks, and alpha clusters.
     */
    fun drawRadioactiveRays(
        drawScope: DrawScope,
        cx: Float,
        cy: Float,
        radius: Float,
        phase: Float,
        intensity: Float = 1.0f
    ) {
        if (intensity <= 0.05f) return

        drawScope.apply {
            // 1. Ambient Cherenkov luminescent green-cyan glow
            val pulseGlow = (0.25f + 0.15f * sin(phase * 4f)) * intensity
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF22C55E).copy(alpha = pulseGlow * 0.7f),
                        Color(0xFF06B6D4).copy(alpha = pulseGlow * 0.4f),
                        Color.Transparent
                    ),
                    center = Offset(cx, cy),
                    radius = radius * 2.2f
                ),
                radius = radius * 2.2f,
                center = Offset(cx, cy)
            )

            // 2. Concentric Expanding Gamma Ionization Waves
            for (w in 0..2) {
                val waveProgress = ((phase * 1.5f + w * 0.33f) % 1.0f)
                val waveRadius = radius * 0.6f + waveProgress * (radius * 1.8f)
                val waveAlpha = ((1f - waveProgress) * 0.6f * intensity).coerceIn(0f, 1f)

                drawCircle(
                    color = Color(0xFF4ADE80).copy(alpha = waveAlpha),
                    radius = waveRadius,
                    center = Offset(cx, cy),
                    style = Stroke(width = 2.0f * (1f - waveProgress * 0.5f))
                )
            }

            // 3. High-Velocity Beta Particle Ionization Tracks (8 radial directional beams)
            for (i in 0..7) {
                val angle = (i * (PI / 4.0) + phase * 0.8f).toFloat()
                val rayLength = radius * 0.8f + ((i * 19) % 30) + 20f * sin(phase * 6f + i)
                val startDist = radius * 0.4f
                val startX = cx + startDist * cos(angle)
                val startY = cy + startDist * sin(angle)
                val endX = cx + (startDist + rayLength) * cos(angle)
                val endY = cy + (startDist + rayLength) * sin(angle)

                // Ionization line
                drawLine(
                    color = Color(0xFF86EFAC).copy(alpha = 0.75f * intensity),
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = 2.0f,
                    cap = StrokeCap.Round
                )

                // Glowing particle tip
                drawCircle(
                    color = Color(0xFFFDE047).copy(alpha = 0.9f * intensity),
                    radius = 3.5f,
                    center = Offset(endX, endY)
                )
            }

            // 4. Alpha Particle Clusters (Heavy helium nuclei pairs)
            for (a in 0..4) {
                val aAngle = (a * (2 * PI / 5.0) - phase * 1.2f).toFloat()
                val aDist = radius * 0.7f + 25f * ((phase * 2f + a * 0.4f) % 1.0f)
                val aProgress = ((phase * 2f + a * 0.4f) % 1.0f)
                val ax = cx + aDist * cos(aAngle)
                val ay = cy + aDist * sin(aAngle)
                val aAlpha = ((1f - aProgress) * 0.85f * intensity).coerceIn(0f, 1f)

                drawCircle(
                    color = Color(0xFF22C55E).copy(alpha = aAlpha),
                    radius = 4.5f,
                    center = Offset(ax, ay)
                )
                drawCircle(
                    color = Color.White.copy(alpha = aAlpha),
                    radius = 2.0f,
                    center = Offset(ax, ay)
                )
            }
        }
    }
}

