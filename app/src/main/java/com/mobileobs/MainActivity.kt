
package com.mobileobs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF101116)
private val Panel = Color(0xFF1B1D25)
private val Accent = Color(0xFF7657FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MobileOBSTheme {
                MobileOBSApp()
            }
        }
    }
}

@Composable
fun MobileOBSApp() {
    val sceneManager = remember { SceneManager() }

    var scenes by remember {
        mutableStateOf(sceneManager.getScenes())
    }

    var selectedSceneId by remember {
        mutableStateOf<String?>(null)
    }

    var selectedTab by remember {
        mutableStateOf("Studio")
    }

    var showCreateDialog by remember { mutableStateOf(false) }
    var renameSceneId by remember { mutableStateOf<String?>(null) }
    var sceneName by remember { mutableStateOf("") }

    fun refreshScenes() {
        scenes = sceneManager.getScenes()
        selectedSceneId = sceneManager.getActiveScene()?.id
    }

    LaunchedEffect(Unit) {
        listOf("Starting Soon", "Gameplay", "Be Right Back")
            .forEach { name ->
                if (sceneManager.getScenes().none { it.name == name }) {
                    sceneManager.createScene(name)
                }
            }

        refreshScenes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        Text(
            "Mobile OBS",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            "Your mobile streaming studio",
            color = Color.Gray,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedTab) {
            "Studio" -> {
                PreviewPanel(
                    sceneName = scenes.find {
                        it.id == selectedSceneId
                    }?.name ?: "No scene selected"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "ACTIVE SCENE",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    scenes.find { it.id == selectedSceneId }?.name
                        ?: "None",
                    color = Accent
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("STREAMING", color = Color.LightGray)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("TikTok", "YouTube", "Twitch").forEach {
                        PlatformButton(
                            it,
                            Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Streaming engine will be connected during the next integration stages.",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            "Scenes" -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "My Scenes",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            sceneName = ""
                            showCreateDialog = true
                        }
                    ) {
                        Text("+ Create")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(scenes, key = { it.id }) { scene ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Panel
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        scene.name,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(1f)
                                    )

                                    if (scene.id == selectedSceneId) {
                                        Text(
                                            "ACTIVE",
                                            color = Accent,
                                            fontSize = 11.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            sceneManager.switchScene(scene.id)
                                            refreshScenes()
                                        }
                                    ) {
                                        Text("Select")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            sceneName = scene.name
                                            renameSceneId = scene.id
                                        }
                                    ) {
                                        Text("Rename")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            sceneManager.duplicateScene(scene.id)
                                            refreshScenes()
                                        }
                                    ) {
                                        Text("Copy")
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            sceneManager.deleteScene(scene.id)
                                            refreshScenes()
                                        }
                                    ) {
                                        Text("Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "Sources" -> {
                Text(
                    "Sources",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Screen capture, camera, images, text and overlays will be connected here.",
                    color = Color.Gray
                )
            }

            "Settings" -> {
                Text(
                    "Settings",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Streaming quality, resolution, frame rate and audio settings will be connected here.",
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalDivider(color = Panel)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Studio", "Scenes", "Sources", "Settings")
                .forEach { tab ->
                    TextButton(
                        onClick = { selectedTab = tab }
                    ) {
                        Text(
                            tab,
                            color = if (selectedTab == tab) Accent else Color.Gray
                        )
                    }
                }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text("Create Scene") },
            text = {
                OutlinedTextField(
                    value = sceneName,
                    onValueChange = { sceneName = it },
                    label = { Text("Scene name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (sceneManager.createScene(sceneName) != null) {
                            refreshScenes()
                            showCreateDialog = false
                        }
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showCreateDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (renameSceneId != null) {
        AlertDialog(
            onDismissRequest = { renameSceneId = null },
            title = { Text("Rename Scene") },
            text = {
                OutlinedTextField(
                    value = sceneName,
                    onValueChange = { sceneName = it },
                    label = { Text("New scene name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        sceneManager.renameScene(
                            renameSceneId!!,
                            sceneName
                        )
                        refreshScenes()
                        renameSceneId = null
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { renameSceneId = null }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PreviewPanel(sceneName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .background(Panel, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("LIVE PREVIEW", color = Accent, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            sceneName,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "Preview rendering will be connected later",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun PlatformButton(
    name: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = {},
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Accent
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Text(name, fontSize = 10.sp)
    }
}
