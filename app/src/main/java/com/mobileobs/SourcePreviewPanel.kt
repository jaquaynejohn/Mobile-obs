
package com.mobileobs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PreviewAccent = Color(0xFF7657FF)
private val PreviewPanelColor = Color(0xFF1B1D25)

@Composable
fun SourcePreviewPanel(
    sceneName: String,
    sceneId: String?,
    sources: List<OBSSource>,
    sceneSources: List<SceneSource>,
    transformManager: SourceTransformManager
) {
    var selectedSourceId by remember(sceneId) {
        mutableStateOf<String?>(null)
    }

    var selectedTransform by remember(sceneId) {
        mutableStateOf<SourceTransform?>(null)
    }

    val visibleSources = sceneSources.mapNotNull { sceneSource ->
        val source = sources.find { it.id == sceneSource.sourceId }

        if (source != null && sceneSource.visible && source.enabled) {
            source to sceneSource
        } else {
            null
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "LIVE PREVIEW",
            color = PreviewAccent,
            fontSize = 12.sp
        )

        Spacer(Modifier.height(8.dp))

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(Color.Black, RoundedCornerShape(12.dp))
        ) {
            val previewWidth = maxWidth
            val previewHeight = maxHeight

            visibleSources.forEach { (source, sceneSource) ->
                val transform = transformManager.getTransform(
                    sceneSource.sceneId,
                    source.id
                )

                val isSelected = selectedSourceId == source.id

                Box(
                    modifier = Modifier
                        .offset(
                            x = previewWidth * (transform.x / 720f),
                            y = previewHeight * (transform.y / 405f)
                        )
                        .size(
                            width = previewWidth * (transform.width / 720f),
                            height = previewHeight * (transform.height / 405f)
                        )
                        .background(
                            if (isSelected) {
                                Color(0xFF38305C)
                            } else {
                                Color(0xFF292B36)
                            },
                            RoundedCornerShape(6.dp)
                        )
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) {
                                PreviewAccent
                            } else {
                                Color.Gray
                            },
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable {
                            selectedSourceId = source.id
                            selectedTransform = transform
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        source.name,
                        color = Color.White,
                        fontSize = 11.sp
                    )
                }
            }

            if (visibleSources.isEmpty()) {
                Text(
                    "No visible sources",
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            sceneName,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )

        if (selectedSourceId != null && selectedTransform != null) {
            val current = selectedTransform!!

            Text(
                "EDIT SOURCE POSITION AND SIZE",
                color = PreviewAccent,
                fontSize = 12.sp
            )

            Text(
                "X position: ${current.x.toInt()}",
                color = Color.White
            )

            Slider(
                value = current.x.coerceIn(0f, 720f),
                onValueChange = {
                    updateSelectedTransform(
                        current.copy(x = it),
                        sceneId,
                        selectedSourceId,
                        transformManager,
                        { selectedTransform = it }
                    )
                },
                valueRange = 0f..720f
            )

            Text(
                "Y position: ${current.y.toInt()}",
                color = Color.White
            )

            Slider(
                value = current.y.coerceIn(0f, 405f),
                onValueChange = {
                    updateSelectedTransform(
                        current.copy(y = it),
                        sceneId,
                        selectedSourceId,
                        transformManager,
                        { selectedTransform = it }
                    )
                },
                valueRange = 0f..405f
            )

            Text(
                "Width: ${current.width.toInt()}",
                color = Color.White
            )

            Slider(
                value = current.width.coerceIn(50f, 720f),
                onValueChange = {
                    updateSelectedTransform(
                        current.copy(width = it),
                        sceneId,
                        selectedSourceId,
                        transformManager,
                        { selectedTransform = it }
                    )
                },
                valueRange = 50f..720f
            )

            Text(
                "Height: ${current.height.toInt()}",
                color = Color.White
            )

            Slider(
                value = current.height.coerceIn(50f, 405f),
                onValueChange = {
                    updateSelectedTransform(
                        current.copy(height = it),
                        sceneId,
                        selectedSourceId,
                        transformManager,
                        { selectedTransform = it }
                    )
                },
                valueRange = 50f..405f
            )

            Text(
                "Rotation: ${current.rotation.toInt()}°",
                color = Color.White
            )

            Slider(
                value = current.rotation.coerceIn(-180f, 180f),
                onValueChange = {
                    updateSelectedTransform(
                        current.copy(rotation = it),
                        sceneId,
                        selectedSourceId,
                        transformManager,
                        { selectedTransform = it }
                    )
                },
                valueRange = -180f..180f
            )
        } else {
            Text(
                "Select a source in the preview to edit it.",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

private fun updateSelectedTransform(
    transform: SourceTransform,
    sceneId: String?,
    sourceId: String?,
    manager: SourceTransformManager,
    onUpdated: (SourceTransform) -> Unit
) {
    if (sceneId == null || sourceId == null) return

    manager.setTransform(
        sceneId = sceneId,
        sourceId = sourceId,
        x = transform.x,
        y = transform.y,
        width = transform.width,
        height = transform.height,
        rotation = transform.rotation
    )

    onUpdated(transform)
}
