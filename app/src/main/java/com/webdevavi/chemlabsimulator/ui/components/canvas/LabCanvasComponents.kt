package com.webdevavi.chemlabsimulator.ui.components.canvas

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.webdevavi.chemlabsimulator.simulation.model.ContainerState
import com.webdevavi.chemlabsimulator.simulation.model.EquipmentType
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun GlasswareCanvas(
    container: ContainerState,
    modifier: Modifier = Modifier,
    isSelected: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "liquid_bubbles")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height

        val equipment = container.equipmentType
        val visual = container.visualState
        val fillFraction = (container.totalVolumeMl / equipment.capacityMl).toFloat().coerceIn(0f, 1f)
        val formulaString = container.getFormulaDisplayString()
        val isShattered = visual.isShattered || container.isShattered || visual.isExploded || container.isExploded
        val isMelted = visual.isMelted || container.isMelted
        val isCracked = visual.isCracked || container.isCracked
        val crackLevel = visual.crackLevel
        val isLightRedGlass = visual.isLightRedGlass
        val isRedHotBottom = visual.isRedHotBottom

        when (equipment) {
            EquipmentType.BEAKER_100, EquipmentType.BEAKER_250, EquipmentType.BEAKER_500, EquipmentType.BEAKER_1000 -> {
                drawBeaker(
                    width = width,
                    height = height,
                    fillFraction = fillFraction,
                    liquidColor = Color(visual.liquidColorHex),
                    precipitateColor = Color(visual.precipitateColorHex),
                    precipitateHeight = visual.precipitateHeight,
                    turbidity = visual.turbidity,
                    bubbleIntensity = visual.bubbleIntensity,
                    steamIntensity = visual.steamIntensity,
                    isBoiling = visual.isBoiling,
                    stirrerActive = container.stirrerActive,
                    blastIntensity = visual.blastIntensity,
                    blastFlashColorHex = visual.blastFlashColorHex,
                    gasType = visual.gasType,
                    surfaceRippleIntensity = visual.surfaceRippleIntensity,
                    isShattered = isShattered,
                    isMelted = isMelted,
                    isCracked = isCracked,
                    crackLevel = crackLevel,
                    isLightRedGlass = isLightRedGlass,
                    isRedHotBottom = isRedHotBottom,
                    thermalStress = visual.thermalStress,
                    formulaString = formulaString,
                    capacityMl = equipment.capacityMl,
                    phase = phase,
                    isSelected = isSelected,
                    textMeasurer = textMeasurer
                )
            }
            EquipmentType.TEST_TUBE_15, EquipmentType.TEST_TUBE_20, EquipmentType.TEST_TUBE_30, EquipmentType.TEST_TUBE_50 -> {
                drawTestTube(
                    width = width,
                    height = height,
                    fillFraction = fillFraction,
                    liquidColor = Color(visual.liquidColorHex),
                    precipitateColor = Color(visual.precipitateColorHex),
                    precipitateHeight = visual.precipitateHeight,
                    bubbleIntensity = visual.bubbleIntensity,
                    steamIntensity = visual.steamIntensity,
                    blastIntensity = visual.blastIntensity,
                    gasType = visual.gasType,
                    isShattered = isShattered,
                    isMelted = isMelted,
                    isCracked = isCracked,
                    crackLevel = crackLevel,
                    isLightRedGlass = isLightRedGlass,
                    isRedHotBottom = isRedHotBottom,
                    formulaString = formulaString,
                    phase = phase,
                    isSelected = isSelected,
                    textMeasurer = textMeasurer
                )
            }
            EquipmentType.ERLENMEYER_125, EquipmentType.ERLENMEYER_250, EquipmentType.ERLENMEYER_500 -> {
                drawErlenmeyer(
                    width = width,
                    height = height,
                    fillFraction = fillFraction,
                    liquidColor = Color(visual.liquidColorHex),
                    precipitateColor = Color(visual.precipitateColorHex),
                    precipitateHeight = visual.precipitateHeight,
                    bubbleIntensity = visual.bubbleIntensity,
                    steamIntensity = visual.steamIntensity,
                    blastIntensity = visual.blastIntensity,
                    gasType = visual.gasType,
                    isShattered = isShattered,
                    isMelted = isMelted,
                    isCracked = isCracked,
                    crackLevel = crackLevel,
                    isLightRedGlass = isLightRedGlass,
                    isRedHotBottom = isRedHotBottom,
                    formulaString = formulaString,
                    phase = phase,
                    isSelected = isSelected,
                    textMeasurer = textMeasurer
                )
            }
            EquipmentType.FLORENCE_FLASK_250, EquipmentType.FLORENCE_FLASK_500 -> {
                drawFlorenceFlask(width, height, fillFraction, Color(visual.liquidColorHex), visual.bubbleIntensity, formulaString, isSelected, textMeasurer)
            }
            EquipmentType.VOLUMETRIC_FLASK_100, EquipmentType.VOLUMETRIC_FLASK_250 -> {
                drawVolumetricFlask(width, height, fillFraction, Color(visual.liquidColorHex), formulaString, isSelected, textMeasurer)
            }
            EquipmentType.GRADUATED_CYLINDER_10, EquipmentType.GRADUATED_CYLINDER_50, EquipmentType.GRADUATED_CYLINDER_100, EquipmentType.GRADUATED_CYLINDER_250 -> {
                drawGraduatedCylinder(
                    width = width,
                    height = height,
                    fillFraction = fillFraction,
                    liquidColor = Color(visual.liquidColorHex),
                    bubbleIntensity = visual.bubbleIntensity,
                    phase = phase,
                    capacityMl = equipment.capacityMl,
                    isSelected = isSelected,
                    textMeasurer = textMeasurer
                )
            }
            EquipmentType.BURETTE_50 -> {
                drawBurette(width, height, fillFraction, Color(visual.liquidColorHex), isSelected, textMeasurer)
            }
            EquipmentType.PIPETTE_10 -> {
                drawPipette(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.DROPPER_5 -> {
                drawDropper(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.SEPARATORY_FUNNEL_250 -> {
                drawSeparatoryFunnel(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.EVAPORATING_DISH_100 -> {
                drawEvaporatingDish(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.CRUCIBLE_50 -> {
                drawCrucible(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.WATCH_GLASS_50 -> {
                drawWatchGlass(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.PETRI_DISH_60 -> {
                drawPetriDish(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.BUCHNER_FUNNEL_250 -> {
                drawBuchnerFlask(width, height, fillFraction, Color(visual.liquidColorHex), isSelected)
            }
            EquipmentType.GAS_SYRINGE_100 -> {
                drawGasSyringe(width, height, fillFraction, visual.gasType, isSelected)
            }
        }

        // Draw energetic chemical blast & firecracker colorful sparks
        if (visual.blastIntensity > 0.02f) {
            ParticleEffects.drawBlastShockwave(
                drawScope = this,
                centerX = width / 2f,
                centerY = height * 0.55f,
                blastIntensity = visual.blastIntensity,
                flashColorHex = visual.blastFlashColorHex,
                phase = phase,
                sparkColors = visual.sparkColors,
                isFirecracker = visual.isFirecrackerBlast
            )
        }

        // Draw radioactive ray emission animation if radioactive
        if (visual.isRadioactive || container.isRadioactive) {
            ParticleEffects.drawRadioactiveRays(
                drawScope = this,
                cx = width / 2f,
                cy = height * 0.55f,
                radius = width * 0.35f,
                phase = phase,
                intensity = visual.radioactivityIntensity.coerceAtLeast(1.0f)
            )
        }
    }
}

private fun DrawScope.drawBeaker(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    precipitateColor: Color,
    precipitateHeight: Float,
    turbidity: Float,
    bubbleIntensity: Float,
    steamIntensity: Float,
    isBoiling: Boolean,
    stirrerActive: Boolean,
    blastIntensity: Float,
    blastFlashColorHex: Long,
    gasType: String?,
    surfaceRippleIntensity: Float,
    isShattered: Boolean,
    isMelted: Boolean = false,
    isCracked: Boolean,
    crackLevel: Int = 0,
    isLightRedGlass: Boolean = false,
    isRedHotBottom: Boolean = false,
    thermalStress: Float,
    formulaString: String,
    capacityMl: Double,
    phase: Float,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val paddingX = width * 0.12f
    val topY = height * 0.12f
    val bottomY = height * 0.90f
    val leftX = paddingX
    val rightX = width - paddingX
    val beakerWidth = rightX - leftX
    val beakerHeight = bottomY - topY
    val cornerRadius = 18f
    val cx = leftX + beakerWidth / 2f

    if (blastIntensity > 0.02f) {
        ParticleEffects.drawBlastShockwave(
            drawScope = this,
            centerX = cx,
            centerY = (topY + bottomY) / 2f,
            blastIntensity = blastIntensity,
            flashColorHex = blastFlashColorHex,
            phase = phase
        )
    }

    if (isMelted) {
        ParticleEffects.drawMoltenGlassware(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            bottomY = bottomY,
            phase = phase,
            liquidColor = liquidColor
        )
        return
    }

    if (isShattered) {
        ParticleEffects.drawShatteredGlassware(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            bottomY = bottomY,
            phase = phase,
            liquidColor = liquidColor
        )
        return
    }

    if (isBoiling || steamIntensity > 0.05f) {
        val liquidTop = bottomY - (beakerHeight * 0.85f * fillFraction.coerceAtLeast(0.1f))
        ParticleEffects.drawBoilingEffects(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            bottomY = bottomY,
            liquidTopY = liquidTop,
            topY = topY,
            phase = phase,
            steamIntensity = steamIntensity
        )
    }

    if (bubbleIntensity > 0.05f || gasType != null) {
        val liquidTop = bottomY - (beakerHeight * 0.85f * fillFraction.coerceAtLeast(0.1f))
        ParticleEffects.drawGasEmanation(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            topY = topY,
            liquidTopY = liquidTop,
            phase = phase,
            gasType = gasType,
            bubbleIntensity = bubbleIntensity
        )
    }

    if (fillFraction > 0.005f) {
        val liquidTop = bottomY - (beakerHeight * 0.85f * fillFraction)
        val liquidPath = Path().apply {
            moveTo(leftX + 4f, liquidTop)
            val waveFreq = if (stirrerActive || isBoiling) 4 else 2
            val waveAmp = if (isBoiling) 5f else if (stirrerActive || surfaceRippleIntensity > 0.5f) 3.5f else 1.5f
            for (x in (leftX + 4f).toInt()..(rightX - 4f).toInt() step 4) {
                val relX = (x - leftX) / beakerWidth
                val wave1 = waveAmp * sin(relX * waveFreq * PI.toFloat() + phase * 2.5f)
                val wave2 = (waveAmp * 0.5f) * sin(relX * waveFreq * 2f * PI.toFloat() + phase * 4f)
                lineTo(x.toFloat(), liquidTop + wave1 + wave2)
            }
            lineTo(rightX - 4f, bottomY - cornerRadius)
            quadraticTo(rightX - 4f, bottomY - 4f, rightX - cornerRadius, bottomY - 4f)
            lineTo(leftX + cornerRadius, bottomY - 4f)
            quadraticTo(leftX + 4f, bottomY - 4f, leftX + 4f, bottomY - cornerRadius)
            close()
        }
        drawPath(liquidPath, brush = Brush.verticalGradient(
            colors = listOf(
                liquidColor.copy(alpha = (liquidColor.alpha * 0.85f).coerceIn(0.25f, 1f)),
                liquidColor.copy(alpha = (liquidColor.alpha * 1.0f).coerceIn(0.45f, 1f))
            ),
            startY = liquidTop,
            endY = bottomY
        ))
        if (turbidity > 0.1f) {
            drawPath(liquidPath, color = Color.White.copy(alpha = (turbidity * 0.4f).coerceIn(0f, 0.6f)))
        }
        if (precipitateHeight > 0.01f) {
            val pptTop = bottomY - (beakerHeight * 0.7f * precipitateHeight)
            val pptPath = Path().apply {
                moveTo(leftX + 4f, pptTop)
                lineTo(rightX - 4f, pptTop)
                lineTo(rightX - 4f, bottomY - cornerRadius)
                quadraticTo(rightX - 4f, bottomY - 4f, rightX - cornerRadius, bottomY - 4f)
                lineTo(leftX + cornerRadius, bottomY - 4f)
                quadraticTo(leftX + 4f, bottomY - 4f, leftX + 4f, bottomY - cornerRadius)
                close()
            }
            drawPath(pptPath, color = precipitateColor.copy(alpha = 0.88f))
            for (i in 0..12) {
                val gx = leftX + 10f + (i * 19f) % (beakerWidth - 20f)
                val gy = bottomY - 6f - (i * 7f) % (beakerHeight * 0.6f * precipitateHeight)
                drawCircle(color = precipitateColor.copy(alpha = 0.95f), radius = 2.5f, center = Offset(gx, gy))
            }
        }
        if (stirrerActive) {
            val barWidth = beakerWidth * 0.35f
            val barHeight = 8f
            drawRoundRect(
                color = Color.White,
                topLeft = Offset(leftX + (beakerWidth - barWidth) / 2f, bottomY - 14f),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )
        }
    }

    val glassBodyPath = Path().apply {
        moveTo(leftX, topY)
        lineTo(leftX, bottomY - cornerRadius)
        quadraticTo(leftX, bottomY, leftX + cornerRadius, bottomY)
        lineTo(rightX - cornerRadius, bottomY)
        quadraticTo(rightX, bottomY, rightX, bottomY - cornerRadius)
        lineTo(rightX, topY)
        close()
    }
    if (isLightRedGlass) {
        drawPath(glassBodyPath, color = Color(0x33EF4444))
    }

    if (isRedHotBottom) {
        ParticleEffects.drawRedHotBottom(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            bottomY = bottomY,
            height = beakerHeight,
            cornerRadius = cornerRadius,
            phase = phase
        )
    }

    val glassPath = Path().apply {
        moveTo(leftX - 8f, topY)
        lineTo(leftX, topY + 4f)
        lineTo(leftX, bottomY - cornerRadius)
        quadraticTo(leftX, bottomY, leftX + cornerRadius, bottomY)
        lineTo(rightX - cornerRadius, bottomY)
        quadraticTo(rightX, bottomY, rightX, bottomY - cornerRadius)
        lineTo(rightX, topY)
    }

    val glassColor = when {
        isRedHotBottom -> Color(0xFFDC2626)
        isLightRedGlass || isCracked -> Color(0xFFEF4444)
        isSelected -> Color(0xFF38BDF8)
        else -> Color(0x8894A3B8)
    }
    drawPath(glassPath, color = glassColor, style = Stroke(width = if (isSelected) 3.5f else 2.5f, cap = StrokeCap.Round))

    drawLine(
        color = Color.White.copy(alpha = 0.25f),
        start = Offset(leftX + 8f, topY + 10f),
        end = Offset(leftX + 8f, bottomY - 14f),
        strokeWidth = 2f
    )

    val steps = 5
    for (i in 1 until steps) {
        val gy = bottomY - (beakerHeight * 0.85f * (i.toFloat() / steps))
        val markLength = if (i % 2 == 0) 18f else 10f
        drawLine(
            color = Color.White.copy(alpha = 0.45f),
            start = Offset(rightX - markLength, gy),
            end = Offset(rightX - 3f, gy),
            strokeWidth = 1.5f
        )
        if (i % 2 == 0) {
            val volText = "${(capacityMl * (i.toFloat() / steps)).toInt()}"
            val textLayout = textMeasurer.measure(
                AnnotatedString(volText),
                style = TextStyle(color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp, fontFamily = FontFamily.Monospace)
            )
            drawText(textLayout, topLeft = Offset(rightX - markLength - textLayout.size.width - 4f, gy - 7f))
        }
    }

    if (isCracked || crackLevel > 0 || thermalStress > 0.4f) {
        ParticleEffects.drawThermalCracks(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            topY = topY,
            bottomY = bottomY,
            phase = phase,
            crackLevel = crackLevel.coerceAtLeast(1)
        )
    }

    if (formulaString.isNotBlank() && formulaString != "Empty") {
        val labelText = if (formulaString.length > 20) formulaString.take(18) + "..." else formulaString
        val labelLayout = textMeasurer.measure(
            AnnotatedString(labelText),
            style = TextStyle(
                color = Color.White.copy(alpha = 0.95f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        )
        val labelW = labelLayout.size.width + 16f
        val labelH = labelLayout.size.height + 8f
        val labelX = cx - labelW / 2f
        val labelY = topY + beakerHeight * 0.22f

        drawRoundRect(
            color = Color(0x990F172A),
            topLeft = Offset(labelX, labelY),
            size = Size(labelW, labelH),
            cornerRadius = CornerRadius(6f, 6f)
        )
        drawRoundRect(
            color = if (isCracked) Color(0xFFEF4444) else Color(0x6638BDF8),
            topLeft = Offset(labelX, labelY),
            size = Size(labelW, labelH),
            cornerRadius = CornerRadius(6f, 6f),
            style = Stroke(width = 1f)
        )
        drawText(labelLayout, topLeft = Offset(labelX + 8f, labelY + 4f))
    }
}

private fun DrawScope.drawTestTube(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    precipitateColor: Color,
    precipitateHeight: Float,
    bubbleIntensity: Float,
    steamIntensity: Float,
    blastIntensity: Float,
    gasType: String?,
    isShattered: Boolean,
    isMelted: Boolean = false,
    isCracked: Boolean,
    crackLevel: Int = 0,
    isLightRedGlass: Boolean = false,
    isRedHotBottom: Boolean = false,
    formulaString: String,
    phase: Float,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val tubeWidth = width * 0.35f
    val leftX = (width - tubeWidth) / 2f
    val rightX = leftX + tubeWidth
    val topY = height * 0.15f
    val bottomY = height * 0.88f
    val radius = tubeWidth / 2f
    val cx = width / 2f

    if (blastIntensity > 0.02f) {
        ParticleEffects.drawBlastShockwave(
            drawScope = this,
            centerX = cx,
            centerY = (topY + bottomY) / 2f,
            blastIntensity = blastIntensity,
            flashColorHex = 0xFFFF5722,
            phase = phase
        )
    }

    if (isMelted) {
        ParticleEffects.drawMoltenGlassware(drawScope = this, leftX = leftX, rightX = rightX, bottomY = bottomY, phase = phase, liquidColor = liquidColor)
        return
    }

    if (isShattered) {
        ParticleEffects.drawShatteredGlassware(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            bottomY = bottomY,
            phase = phase,
            liquidColor = liquidColor
        )
        return
    }

    if (fillFraction > 0.005f) {
        val liquidTop = bottomY - ((bottomY - topY - radius) * fillFraction + radius)
        val liquidPath = Path().apply {
            moveTo(leftX + 3f, liquidTop)
            lineTo(rightX - 3f, liquidTop)
            lineTo(rightX - 3f, bottomY - radius)
            arcTo(
                rect = Rect(leftX + 3f, bottomY - 2 * radius + 3f, rightX - 3f, bottomY - 3f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            close()
        }
        drawPath(liquidPath, color = liquidColor)

        if (bubbleIntensity > 0.05f || gasType != null) {
            ParticleEffects.drawGasEmanation(
                drawScope = this,
                leftX = leftX,
                rightX = rightX,
                topY = topY,
                liquidTopY = liquidTop,
                phase = phase,
                gasType = gasType,
                bubbleIntensity = bubbleIntensity
            )
        }
    }

    if (isLightRedGlass) {
        val tubeBodyPath = Path().apply {
            moveTo(leftX, topY)
            lineTo(leftX, bottomY - radius)
            arcTo(
                rect = Rect(leftX, bottomY - 2 * radius, rightX, bottomY),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            lineTo(rightX, topY)
            close()
        }
        drawPath(tubeBodyPath, color = Color(0x33EF4444))
    }

    if (isRedHotBottom) {
        ParticleEffects.drawRedHotBottom(
            drawScope = this,
            leftX = leftX,
            rightX = rightX,
            bottomY = bottomY,
            height = bottomY - topY,
            cornerRadius = radius,
            phase = phase
        )
    }

    val tubePath = Path().apply {
        moveTo(leftX - 4f, topY)
        lineTo(leftX, topY)
        lineTo(leftX, bottomY - radius)
        arcTo(
            rect = Rect(leftX, bottomY - 2 * radius, rightX, bottomY),
            startAngleDegrees = 180f,
            sweepAngleDegrees = -180f,
            forceMoveTo = false
        )
        lineTo(rightX, topY)
        lineTo(rightX + 4f, topY)
    }

    val glassColor = when {
        isRedHotBottom -> Color(0xFFDC2626)
        isLightRedGlass || isCracked -> Color(0xFFEF4444)
        isSelected -> Color(0xFF38BDF8)
        else -> Color(0x8894A3B8)
    }
    drawPath(tubePath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))

    if (isCracked || crackLevel > 0) {
        ParticleEffects.drawThermalCracks(drawScope = this, leftX = leftX, rightX = rightX, topY = topY, bottomY = bottomY, phase = phase, crackLevel = crackLevel.coerceAtLeast(1))
    }
}

private fun DrawScope.drawErlenmeyer(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    precipitateColor: Color,
    precipitateHeight: Float,
    bubbleIntensity: Float,
    steamIntensity: Float,
    blastIntensity: Float,
    gasType: String?,
    isShattered: Boolean,
    isMelted: Boolean = false,
    isCracked: Boolean,
    crackLevel: Int = 0,
    isLightRedGlass: Boolean = false,
    isRedHotBottom: Boolean = false,
    formulaString: String,
    phase: Float,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val topY = height * 0.12f
    val neckBottomY = height * 0.32f
    val bottomY = height * 0.88f
    val neckWidth = width * 0.22f
    val baseWidth = width * 0.78f
    val leftNeck = (width - neckWidth) / 2f
    val rightNeck = leftNeck + neckWidth
    val leftBase = (width - baseWidth) / 2f
    val rightBase = leftBase + baseWidth
    val cornerRadius = 14f
    val cx = width / 2f

    if (blastIntensity > 0.02f) {
        ParticleEffects.drawBlastShockwave(
            drawScope = this,
            centerX = cx,
            centerY = (topY + bottomY) / 2f,
            blastIntensity = blastIntensity,
            flashColorHex = 0xFFFF5722,
            phase = phase
        )
    }

    if (isMelted) {
        ParticleEffects.drawMoltenGlassware(drawScope = this, leftX = leftBase, rightX = rightBase, bottomY = bottomY, phase = phase, liquidColor = liquidColor)
        return
    }

    if (isShattered) {
        ParticleEffects.drawShatteredGlassware(
            drawScope = this,
            leftX = leftBase,
            rightX = rightBase,
            bottomY = bottomY,
            phase = phase,
            liquidColor = liquidColor
        )
        return
    }

    if (fillFraction > 0.005f) {
        val liquidTop = bottomY - ((bottomY - neckBottomY) * fillFraction * 1.1f)
        val liquidLeft = leftBase + (leftNeck - leftBase) * ((bottomY - liquidTop) / (bottomY - neckBottomY)).coerceIn(0f, 1f)
        val liquidRight = rightBase - (rightBase - rightNeck) * ((bottomY - liquidTop) / (bottomY - neckBottomY)).coerceIn(0f, 1f)

        val liquidPath = Path().apply {
            moveTo(liquidLeft + 3f, liquidTop)
            lineTo(liquidRight - 3f, liquidTop)
            lineTo(rightBase - cornerRadius, bottomY - 4f)
            quadraticTo(rightBase - 4f, bottomY - 4f, rightBase - 4f, bottomY - cornerRadius)
            lineTo(leftBase + cornerRadius, bottomY - 4f)
            quadraticTo(leftBase + 4f, bottomY - 4f, leftBase + 4f, bottomY - cornerRadius)
            close()
        }
        drawPath(liquidPath, color = liquidColor)
    }

    if (isLightRedGlass) {
        val flaskBody = Path().apply {
            moveTo(leftNeck, topY)
            lineTo(leftNeck, neckBottomY)
            lineTo(leftBase, bottomY - cornerRadius)
            quadraticTo(leftBase, bottomY, leftBase + cornerRadius, bottomY)
            lineTo(rightBase - cornerRadius, bottomY)
            quadraticTo(rightBase, bottomY, rightBase, bottomY - cornerRadius)
            lineTo(rightNeck, neckBottomY)
            lineTo(rightNeck, topY)
            close()
        }
        drawPath(flaskBody, color = Color(0x33EF4444))
    }

    if (isRedHotBottom) {
        ParticleEffects.drawRedHotBottom(
            drawScope = this,
            leftX = leftBase,
            rightX = rightBase,
            bottomY = bottomY,
            height = bottomY - topY,
            cornerRadius = cornerRadius,
            phase = phase
        )
    }

    val flaskPath = Path().apply {
        moveTo(leftNeck - 4f, topY)
        lineTo(leftNeck, topY + 2f)
        lineTo(leftNeck, neckBottomY)
        lineTo(leftBase, bottomY - cornerRadius)
        quadraticTo(leftBase, bottomY, leftBase + cornerRadius, bottomY)
        lineTo(rightBase - cornerRadius, bottomY)
        quadraticTo(rightBase, bottomY, rightBase, bottomY - cornerRadius)
        lineTo(rightNeck, neckBottomY)
        lineTo(rightNeck, topY + 2f)
        lineTo(rightNeck + 4f, topY)
    }

    val glassColor = when {
        isRedHotBottom -> Color(0xFFDC2626)
        isLightRedGlass || isCracked -> Color(0xFFEF4444)
        isSelected -> Color(0xFF38BDF8)
        else -> Color(0x8894A3B8)
    }
    drawPath(flaskPath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))

    if (isCracked || crackLevel > 0) {
        ParticleEffects.drawThermalCracks(drawScope = this, leftX = leftBase, rightX = rightBase, topY = neckBottomY, bottomY = bottomY, phase = phase, crackLevel = crackLevel.coerceAtLeast(1))
    }

    if (formulaString.isNotBlank() && formulaString != "Empty") {
        val labelText = if (formulaString.length > 16) formulaString.take(14) + "..." else formulaString
        val labelLayout = textMeasurer.measure(
            AnnotatedString(labelText),
            style = TextStyle(
                color = Color.White.copy(alpha = 0.95f),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
            )
        )
        val labelW = labelLayout.size.width + 12f
        val labelH = labelLayout.size.height + 6f
        val labelX = cx - labelW / 2f
        val labelY = neckBottomY + 12f

        drawRoundRect(
            color = Color(0x990F172A),
            topLeft = Offset(labelX, labelY),
            size = Size(labelW, labelH),
            cornerRadius = CornerRadius(4f, 4f)
        )
        drawText(labelLayout, topLeft = Offset(labelX + 6f, labelY + 3f))
    }
}

private fun DrawScope.drawGraduatedCylinder(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    bubbleIntensity: Float,
    phase: Float,
    capacityMl: Double,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val cylWidth = width * 0.32f
    val leftX = (width - cylWidth) / 2f
    val rightX = leftX + cylWidth
    val topY = height * 0.12f
    val bottomY = height * 0.86f
    val baseWidth = width * 0.65f
    val baseLeft = (width - baseWidth) / 2f
    val baseRight = baseLeft + baseWidth

    if (fillFraction > 0.005f) {
        val liquidTop = bottomY - ((bottomY - topY) * 0.9f * fillFraction)
        val liquidPath = Path().apply {
            moveTo(leftX + 3f, liquidTop)
            lineTo(rightX - 3f, liquidTop)
            lineTo(rightX - 3f, bottomY - 3f)
            lineTo(leftX + 3f, bottomY - 3f)
            close()
        }
        drawPath(liquidPath, color = liquidColor)
    }

    val glassPath = Path().apply {
        moveTo(leftX - 4f, topY)
        lineTo(leftX, topY)
        lineTo(leftX, bottomY)
        lineTo(baseLeft, bottomY)
        lineTo(baseLeft, bottomY + 12f)
        lineTo(baseRight, bottomY + 12f)
        lineTo(baseRight, bottomY)
        lineTo(rightX, bottomY)
        lineTo(rightX, topY)
        lineTo(rightX + 4f, topY)
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(glassPath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))

    for (i in 1..10) {
        val gy = bottomY - ((bottomY - topY) * 0.9f * (i / 10f))
        val len = if (i % 2 == 0) 12f else 6f
        drawLine(
            color = Color.White.copy(alpha = 0.5f),
            start = Offset(rightX - len, gy),
            end = Offset(rightX - 2f, gy),
            strokeWidth = 1.2f
        )
    }
}

private fun DrawScope.drawDropper(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val cx = width / 2f
    val bulbTop = height * 0.15f
    val bulbBottom = height * 0.35f
    val tipBottom = height * 0.85f

    drawOval(
        color = Color(0xFFEF4444),
        topLeft = Offset(cx - 16f, bulbTop),
        size = Size(32f, bulbBottom - bulbTop)
    )

    val tubePath = Path().apply {
        moveTo(cx - 6f, bulbBottom)
        lineTo(cx - 6f, tipBottom - 20f)
        lineTo(cx - 2f, tipBottom)
        lineTo(cx + 2f, tipBottom)
        lineTo(cx + 6f, tipBottom - 20f)
        lineTo(cx + 6f, bulbBottom)
    }

    if (fillFraction > 0.05f) {
        drawPath(tubePath, color = liquidColor)
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(tubePath, color = glassColor, style = Stroke(width = 2.5f))
}

private fun DrawScope.drawFlorenceFlask(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    bubbleIntensity: Float,
    formulaString: String,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val cx = width / 2f
    val neckWidth = width * 0.18f
    val bulbRadius = width * 0.36f
    val topY = height * 0.12f
    val neckBottomY = height * 0.38f
    val bulbCenterY = height * 0.64f

    // Liquid fill
    if (fillFraction > 0.01f) {
        val liquidTop = (bulbCenterY + bulbRadius) - (bulbRadius * 2f * fillFraction.coerceIn(0f, 1f))
        val liquidPath = Path().apply {
            moveTo(cx - bulbRadius, bulbCenterY)
            arcTo(
                rect = Rect(cx - bulbRadius, bulbCenterY - bulbRadius, cx + bulbRadius, bulbCenterY + bulbRadius),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            close()
        }
        drawPath(liquidPath, color = liquidColor)
    }

    // Glass outline
    val flaskPath = Path().apply {
        moveTo(cx - neckWidth / 2f - 4f, topY)
        lineTo(cx - neckWidth / 2f, topY)
        lineTo(cx - neckWidth / 2f, neckBottomY)
        arcTo(
            rect = Rect(cx - bulbRadius, bulbCenterY - bulbRadius, cx + bulbRadius, bulbCenterY + bulbRadius),
            startAngleDegrees = 230f,
            sweepAngleDegrees = 260f,
            forceMoveTo = false
        )
        lineTo(cx + neckWidth / 2f, neckBottomY)
        lineTo(cx + neckWidth / 2f, topY)
        lineTo(cx + neckWidth / 2f + 4f, topY)
    }
    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(flaskPath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))
}

private fun DrawScope.drawVolumetricFlask(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    formulaString: String,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val cx = width / 2f
    val neckWidth = width * 0.12f
    val bulbRadius = width * 0.34f
    val topY = height * 0.10f
    val neckBottomY = height * 0.45f
    val bulbCenterY = height * 0.70f
    val bottomY = height * 0.88f

    // Liquid fill
    if (fillFraction > 0.01f) {
        val liquidPath = Path().apply {
            moveTo(cx - bulbRadius + 6f, bottomY - 6f)
            lineTo(cx + bulbRadius - 6f, bottomY - 6f)
            lineTo(cx + bulbRadius - 4f, bulbCenterY)
            lineTo(cx - bulbRadius + 4f, bulbCenterY)
            close()
        }
        drawPath(liquidPath, color = liquidColor)
    }

    val glassPath = Path().apply {
        moveTo(cx - neckWidth / 2f - 3f, topY)
        lineTo(cx - neckWidth / 2f, topY)
        lineTo(cx - neckWidth / 2f, neckBottomY)
        lineTo(cx - bulbRadius, bulbCenterY + 10f)
        lineTo(cx - bulbRadius + 10f, bottomY)
        lineTo(cx + bulbRadius - 10f, bottomY)
        lineTo(cx + bulbRadius, bulbCenterY + 10f)
        lineTo(cx + neckWidth / 2f, neckBottomY)
        lineTo(cx + neckWidth / 2f, topY)
        lineTo(cx + neckWidth / 2f + 3f, topY)
    }
    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(glassPath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))

    // Single calibration mark ring
    drawLine(
        color = Color(0xFFEF4444),
        start = Offset(cx - neckWidth / 2f - 2f, height * 0.28f),
        end = Offset(cx + neckWidth / 2f + 2f, height * 0.28f),
        strokeWidth = 2f
    )
}

private fun DrawScope.drawBurette(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean,
    textMeasurer: androidx.compose.ui.text.TextMeasurer
) {
    val cx = width / 2f
    val topY = height * 0.08f
    val stopcockY = height * 0.80f
    val tipY = height * 0.94f
    val tubeWidth = width * 0.16f
    val leftX = cx - tubeWidth / 2f
    val rightX = cx + tubeWidth / 2f

    // Liquid fill
    if (fillFraction > 0.01f) {
        val liquidTop = stopcockY - ((stopcockY - topY) * fillFraction)
        drawRect(
            color = liquidColor,
            topLeft = Offset(leftX + 2f, liquidTop),
            size = Size(tubeWidth - 4f, stopcockY - liquidTop)
        )
    }

    // Glass Tube
    val tubePath = Path().apply {
        moveTo(leftX, topY)
        lineTo(leftX, stopcockY)
        lineTo(cx - 3f, stopcockY + 12f)
        lineTo(cx - 2f, tipY)
        lineTo(cx + 2f, tipY)
        lineTo(cx + 3f, stopcockY + 12f)
        lineTo(rightX, stopcockY)
        lineTo(rightX, topY)
    }
    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(tubePath, color = glassColor, style = Stroke(width = 2.5f))

    // Stopcock Valve Handle
    drawCircle(color = Color(0xFF0284C7), radius = 8f, center = Offset(cx, stopcockY + 6f))
    drawLine(
        color = Color(0xFF0284C7),
        start = Offset(cx - 14f, stopcockY + 6f),
        end = Offset(cx + 14f, stopcockY + 6f),
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )

    // Graduation ticks
    for (i in 1..15) {
        val gy = topY + (stopcockY - topY) * (i / 16f)
        drawLine(
            color = Color.White.copy(alpha = 0.6f),
            start = Offset(rightX - 6f, gy),
            end = Offset(rightX - 1f, gy),
            strokeWidth = 1f
        )
    }
}

private fun DrawScope.drawPipette(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val cx = width / 2f
    val topY = height * 0.08f
    val bulbTop = height * 0.35f
    val bulbBottom = height * 0.60f
    val tipY = height * 0.92f
    val narrowW = 6f
    val bulbW = width * 0.22f

    val pipettePath = Path().apply {
        moveTo(cx - narrowW / 2f, topY)
        lineTo(cx - narrowW / 2f, bulbTop)
        quadraticTo(cx - bulbW / 2f, (bulbTop + bulbBottom) / 2f, cx - narrowW / 2f, bulbBottom)
        lineTo(cx - 2f, tipY)
        lineTo(cx + 2f, tipY)
        lineTo(cx + narrowW / 2f, bulbBottom)
        quadraticTo(cx + bulbW / 2f, (bulbTop + bulbBottom) / 2f, cx + narrowW / 2f, bulbTop)
        lineTo(cx + narrowW / 2f, topY)
    }

    if (fillFraction > 0.01f) {
        drawPath(pipettePath, color = liquidColor)
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(pipettePath, color = glassColor, style = Stroke(width = 2.5f))
}

private fun DrawScope.drawSeparatoryFunnel(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val cx = width / 2f
    val topY = height * 0.12f
    val bulbTop = height * 0.22f
    val stopcockY = height * 0.75f
    val tipY = height * 0.92f
    val mouthW = width * 0.20f
    val bodyW = width * 0.60f

    val funnelPath = Path().apply {
        moveTo(cx - mouthW / 2f, topY)
        lineTo(cx - mouthW / 2f, bulbTop)
        quadraticTo(cx - bodyW / 2f, height * 0.40f, cx - 4f, stopcockY)
        lineTo(cx - 2f, tipY)
        lineTo(cx + 2f, tipY)
        lineTo(cx + 4f, stopcockY)
        quadraticTo(cx + bodyW / 2f, height * 0.40f, cx + mouthW / 2f, bulbTop)
        lineTo(cx + mouthW / 2f, topY)
        close()
    }

    if (fillFraction > 0.01f) {
        drawPath(funnelPath, color = liquidColor)
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(funnelPath, color = glassColor, style = Stroke(width = 3f))

    // Stopcock Valve
    drawCircle(color = Color(0xFF0284C7), radius = 7f, center = Offset(cx, stopcockY))
    drawLine(color = Color(0xFF0284C7), start = Offset(cx - 12f, stopcockY), end = Offset(cx + 12f, stopcockY), strokeWidth = 3f)
}

private fun DrawScope.drawEvaporatingDish(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val cx = width / 2f
    val leftX = width * 0.15f
    val rightX = width * 0.85f
    val topY = height * 0.42f
    val bottomY = height * 0.72f

    val dishPath = Path().apply {
        moveTo(leftX - 8f, topY - 4f) // Pour spout
        lineTo(leftX, topY)
        quadraticTo(cx, bottomY, rightX, topY)
        lineTo(rightX + 4f, topY)
    }

    if (fillFraction > 0.01f) {
        val liquidPath = Path().apply {
            moveTo(leftX + 8f, topY + 12f)
            quadraticTo(cx, bottomY - 4f, rightX - 8f, topY + 12f)
            close()
        }
        drawPath(liquidPath, color = liquidColor)
    }

    drawPath(dishPath, color = Color(0xFFF1F5F9), style = Stroke(width = 6f, cap = StrokeCap.Round))
    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0xFFCBD5E1)
    drawPath(dishPath, color = glassColor, style = Stroke(width = 2f, cap = StrokeCap.Round))
}

private fun DrawScope.drawCrucible(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val leftTop = width * 0.22f
    val rightTop = width * 0.78f
    val leftBottom = width * 0.32f
    val rightBottom = width * 0.68f
    val topY = height * 0.32f
    val bottomY = height * 0.78f

    val cruciblePath = Path().apply {
        moveTo(leftTop - 4f, topY)
        lineTo(rightTop + 4f, topY)
        lineTo(rightBottom, bottomY)
        quadraticTo(width / 2f, bottomY + 6f, leftBottom, bottomY)
        close()
    }

    if (fillFraction > 0.01f) {
        drawPath(cruciblePath, color = liquidColor)
    }

    drawPath(cruciblePath, color = Color(0xFFF8FAFC), style = Stroke(width = 6f))
    val outlineColor = if (isSelected) Color(0xFF38BDF8) else Color(0xFF94A3B8)
    drawPath(cruciblePath, color = outlineColor, style = Stroke(width = 2.5f))
}

private fun DrawScope.drawWatchGlass(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val cx = width / 2f
    val leftX = width * 0.12f
    val rightX = width * 0.88f
    val topY = height * 0.50f
    val bottomY = height * 0.62f

    val watchPath = Path().apply {
        moveTo(leftX, topY)
        quadraticTo(cx, bottomY, rightX, topY)
    }

    if (fillFraction > 0.01f) {
        drawPath(watchPath, color = liquidColor, style = Stroke(width = 8f, cap = StrokeCap.Round))
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(watchPath, color = glassColor, style = Stroke(width = 4f, cap = StrokeCap.Round))
}

private fun DrawScope.drawPetriDish(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val leftX = width * 0.14f
    val rightX = width * 0.86f
    val topY = height * 0.50f
    val bottomY = height * 0.68f

    val dishPath = Path().apply {
        moveTo(leftX, topY)
        lineTo(leftX, bottomY)
        lineTo(rightX, bottomY)
        lineTo(rightX, topY)
    }

    if (fillFraction > 0.01f) {
        drawRect(
            color = liquidColor,
            topLeft = Offset(leftX + 3f, topY + 8f),
            size = Size(rightX - leftX - 6f, bottomY - topY - 10f)
        )
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(dishPath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))
}

private fun DrawScope.drawBuchnerFlask(
    width: Float,
    height: Float,
    fillFraction: Float,
    liquidColor: Color,
    isSelected: Boolean
) {
    val cx = width / 2f
    val topY = height * 0.12f
    val neckBottomY = height * 0.35f
    val bottomY = height * 0.86f
    val neckWidth = width * 0.22f
    val baseWidth = width * 0.76f
    val leftNeck = (width - neckWidth) / 2f
    val rightNeck = leftNeck + neckWidth
    val leftBase = (width - baseWidth) / 2f
    val rightBase = leftBase + baseWidth

    // Liquid
    if (fillFraction > 0.01f) {
        val liquidTop = bottomY - ((bottomY - neckBottomY) * fillFraction)
        val liquidPath = Path().apply {
            moveTo(leftBase + 4f, bottomY - 4f)
            lineTo(rightBase - 4f, bottomY - 4f)
            lineTo(rightBase - 10f, liquidTop)
            lineTo(leftBase + 10f, liquidTop)
            close()
        }
        drawPath(liquidPath, color = liquidColor)
    }

    // Flask body with side-arm vacuum nozzle
    val flaskPath = Path().apply {
        moveTo(leftNeck, topY)
        lineTo(leftNeck, neckBottomY)
        lineTo(leftBase, bottomY)
        lineTo(rightBase, bottomY)
        lineTo(rightNeck, neckBottomY)
        // Side arm nozzle
        lineTo(rightNeck + 16f, neckBottomY - 6f)
        lineTo(rightNeck + 16f, neckBottomY - 14f)
        lineTo(rightNeck, neckBottomY - 10f)
        lineTo(rightNeck, topY)
    }

    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(flaskPath, color = glassColor, style = Stroke(width = 3f, cap = StrokeCap.Round))

    // Top Büchner Funnel Cup
    val funnelPath = Path().apply {
        moveTo(cx - width * 0.26f, topY - 24f)
        lineTo(cx + width * 0.26f, topY - 24f)
        lineTo(cx + width * 0.14f, topY)
        lineTo(cx - width * 0.14f, topY)
        close()
    }
    drawPath(funnelPath, color = Color(0xFFF1F5F9))
    drawPath(funnelPath, color = Color(0xFF94A3B8), style = Stroke(width = 2f))
}

private fun DrawScope.drawGasSyringe(
    width: Float,
    height: Float,
    fillFraction: Float,
    gasType: String?,
    isSelected: Boolean
) {
    val cx = width / 2f
    val topY = height * 0.15f
    val barrelBottom = height * 0.72f
    val barrelW = width * 0.32f
    val leftX = cx - barrelW / 2f
    val rightX = cx + barrelW / 2f
    val plungerY = topY + (barrelBottom - topY) * (1f - fillFraction.coerceIn(0.1f, 1f))

    // Internal gas chamber
    drawRect(
        color = Color(0x2238BDF8),
        topLeft = Offset(leftX + 2f, plungerY),
        size = Size(barrelW - 4f, barrelBottom - plungerY)
    )

    // Plunger shaft and seal
    drawRoundRect(
        color = Color(0xFF0284C7),
        topLeft = Offset(leftX + 4f, plungerY - 8f),
        size = Size(barrelW - 8f, 10f),
        cornerRadius = CornerRadius(2f, 2f)
    )
    drawRect(
        color = Color(0xFF64748B),
        topLeft = Offset(cx - 4f, topY - 20f),
        size = Size(8f, plungerY - topY + 12f)
    )
    drawRoundRect(
        color = Color(0xFF334155),
        topLeft = Offset(cx - 16f, topY - 26f),
        size = Size(32f, 8f),
        cornerRadius = CornerRadius(3f, 3f)
    )

    // Glass Barrel
    val barrelPath = Path().apply {
        moveTo(leftX, topY)
        lineTo(leftX, barrelBottom)
        lineTo(cx - 3f, barrelBottom + 16f)
        lineTo(cx + 3f, barrelBottom + 16f)
        lineTo(rightX, barrelBottom)
        lineTo(rightX, topY)
    }
    val glassColor = if (isSelected) Color(0xFF38BDF8) else Color(0x8894A3B8)
    drawPath(barrelPath, color = glassColor, style = Stroke(width = 2.5f))

    // Graduations
    for (i in 1..8) {
        val gy = topY + (barrelBottom - topY) * (i / 9f)
        drawLine(
            color = Color.White.copy(alpha = 0.6f),
            start = Offset(rightX - 8f, gy),
            end = Offset(rightX - 1f, gy),
            strokeWidth = 1f
        )
    }
}

@Composable
fun FullScreenSmokeCanvas(
    smokeAlpha: Float,
    modifier: Modifier = Modifier
) {
    if (smokeAlpha <= 0.01f) return

    val infiniteTransition = rememberInfiniteTransition(label = "screen_smoke")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Canvas(modifier = modifier) {
        ParticleEffects.drawFullScreenSmoke(
            drawScope = this,
            width = size.width,
            height = size.height,
            smokeAlpha = smokeAlpha,
            phase = phase
        )
    }
}

@Composable
fun BunsenBurnerCanvas(
    isIgnited: Boolean,
    heatWatts: Double = 500.0,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "flame_flicker")
    val flicker by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flicker"
    )

    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val cx = width / 2f
        val topBarrelY = height * 0.45f
        val baseTopY = height * 0.85f

        drawRoundRect(
            color = Color(0xFF334155),
            topLeft = Offset(cx - width * 0.35f, baseTopY),
            size = Size(width * 0.7f, height * 0.12f),
            cornerRadius = CornerRadius(6f, 6f)
        )

        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF64748B), Color(0xFFCBD5E1), Color(0xFF475569)),
                startX = cx - 10f,
                endX = cx + 10f
            ),
            topLeft = Offset(cx - 10f, topBarrelY),
            size = Size(20f, baseTopY - topBarrelY)
        )

        drawRoundRect(
            color = Color(0xFF94A3B8),
            topLeft = Offset(cx - 13f, baseTopY - 20f),
            size = Size(26f, 14f),
            cornerRadius = CornerRadius(3f, 3f)
        )

        if (isIgnited) {
            val flameHeight = (height * 0.38f) * flicker
            val flameTop = topBarrelY - flameHeight

            val outerFlamePath = Path().apply {
                moveTo(cx - 12f, topBarrelY)
                quadraticTo(cx - 16f, topBarrelY - flameHeight * 0.5f, cx, flameTop)
                quadraticTo(cx + 16f, topBarrelY - flameHeight * 0.5f, cx + 12f, topBarrelY)
                close()
            }
            drawPath(
                outerFlamePath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.85f), Color(0xFF0284C7).copy(alpha = 0.95f)),
                    startY = flameTop,
                    endY = topBarrelY
                )
            )

            val innerFlameHeight = flameHeight * 0.55f
            val innerTop = topBarrelY - innerFlameHeight
            val innerFlamePath = Path().apply {
                moveTo(cx - 6f, topBarrelY)
                quadraticTo(cx - 8f, topBarrelY - innerFlameHeight * 0.5f, cx, innerTop)
                quadraticTo(cx + 8f, topBarrelY - innerFlameHeight * 0.5f, cx + 6f, topBarrelY)
                close()
            }
            drawPath(
                innerFlamePath,
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White.copy(alpha = 0.95f), Color(0xFF67E8F9)),
                    startY = innerTop,
                    endY = topBarrelY
                )
            )

            drawCircle(
                color = Color(0x3306B6D4),
                radius = flameHeight * 0.8f,
                center = Offset(cx, topBarrelY - flameHeight * 0.4f)
            )
        }
    }
}
