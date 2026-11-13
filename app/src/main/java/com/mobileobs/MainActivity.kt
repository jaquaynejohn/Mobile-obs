
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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    val sceneManager = remember {
        SceneManager(context.applicationContext)
    }

    val sourceManager = remember {
        SourceManager(context.applicationContext)
    }

    var scenes by remember {
        mutableStateOf(sceneManager.getScenes())
    }

    var sources by remember {
        mutableStateOf(sourceManager.getSources())
    }

    var selectedSceneId by remember {
        mutableStateOf(sceneManager.getActiveScene()?.id)
    }

    var selectedTab by remember {
        mutableStateOf("Studio")
    }

    var showCreateDialog by remember {
        mutableStateOf(false)
    }

    var renameSceneId by remember {
        mutableStateOf<String?>(null)
    }

    var sceneName by remember {
        mutableStateOf("")
    }

    var showAddSourceDialog by remember {
        mutableStateOf(false)
    }

    var renameSourceId by remember {
        mutableStateOf<String?>(null)
    }

    var sourceName by remember {
        mutableStateOf("")
    }

    var selectedSourceType by remember {
        mutableStateOf(SourceType.SCREEN_CAPTURE)
    }

    fun refreshScenes() {
        scenes = sceneManager.getScenes()
        selectedSceneId = sceneManager.getActiveScene()?.id
    }

    fun refreshSources() {
        sources = sourceManager.getSources()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(16.dp)
    ) {
        Text(
            text = "Mobile OBS",
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Your mobile streaming studio",
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
                    scenes.find {
                        it.id == selectedSceneId
                    }?.name ?: "None",
                    color = Accent
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "STREAMING",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("TikTok", "YouTube", "Twitch").forEach {
                        PlatformButton(
                            name = it,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935)
                    )
                ) {
                    Text("START STREAM")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Streaming engine is not connected yet.",
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
                    items(
                        items = scenes,
                        key = { it.id }
                    ) { scene ->

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Panel
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = scene.name,
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
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Button(
                                        onClick = {
                                            sceneManager.switchScene(scene.id)
                                            refreshScenes()
                                        },
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(4.dp)
                                    ) {
                                        Text("Select", fontSize = 10.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            sceneName = scene.name
                                            renameSceneId = scene.id
                                        },
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(4.dp)
                                    ) {
                                        Text("Rename", fontSize = 10.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            sceneManager.duplicateScene(scene.id)
                                            refreshScenes()
                                        },
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(4.dp)
                                    ) {
                                        Text("Copy", fontSize = 10.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            sceneManager.deleteScene(scene.id)
                                            refreshScenes()
                                        },
                                        modifier = Modifier.weight(1f),
                                        contentPadding = PaddingValues(4.dp)
                                    ) {
                                        Text("Delete", fontSize = 10.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "Sources" -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "My Sources",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = {
                            sourceName = ""
                            selectedSourceType = SourceType.SCREEN_CAPTURE
                            showAddSourceDialog = true
                        }
                    ) {
                        Text("+ Add Source")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "${sources.size} source(s)",
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (sources.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "No sources yet",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            "Tap Add Source to create your first source.",
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(
                            items = sources,
                            key = { it.id }
                        ) { source ->

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Panel
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                source.name,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )

                                            Text(
                                                source.type.name.replace("_", " "),
                                                color = Color.Gray,
                                                fontSize = 11.sp
                                            )
                                        }

                                        Switch(
                                            checked = source.enabled,
                                            onCheckedChange = {
                                                sourceManager.toggleSource(source.id)
                                                refreshSources()
                                            }
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        OutlinedButton(
                                            onClick = {
                                                sourceName = source.name
                                                renameSourceId = source.id
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Rename")
                                        }

                                        OutlinedButton(
                                            onClick = {
                                                sourceManager.deleteSource(source.id)
                                                refreshSources()
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("Delete")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            "Settings" -> {
                Text(
                    "Settings",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

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
            listOf("Studio", "Scenes", "Sources", "Settings").forEach { tab ->
                TextButton(
                    onClick = {
                        selectedTab = tab
                    }
                ) {
                    Text(
                        text = tab,
                        color = if (selectedTab == tab) Accent else Color.Gray,
                        fontWeight = if (selectedTab == tab) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = {
                showCreateDialog = false
            },
            title = {
                Text("Create Scene")
            },
            text = {
                OutlinedTextField(
                    value = sceneName,
                    onValueChange = {
                        sceneName = it
                    },
                    label = {
                        Text("Scene name")
                    },
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
                    onClick = {
                        showCreateDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (renameSceneId != null) {
        AlertDialog(
            onDismissRequest = {
                renameSceneId = null
            },
            title = {
                Text("Rename Scene")
            },
            text = {
                OutlinedTextField(
                    value = sceneName,
                    onValueChange = {
                        sceneName = it
                    },
                    label = {
                        Text("New scene name")
                    },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val id = renameSceneId

                        if (id != null && sceneManager.renameScene(id, sceneName)) {
                            refreshScenes()
                            renameSceneId = null
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        renameSceneId = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showAddSourceDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddSourceDialog = false
            },
            title = {
                Text("Add Source")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = sourceName,
                        onValueChange = {
                            sourceName = it
                        },
                        label = {
                            Text("Source name")
                        },
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Choose source type",
                        fontWeight = FontWeight.Bold
                    )

                    SourceType.values().forEach { type ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedSourceType == type,
                                onClick = {
                                    selectedSourceType = type
                                }
                            )

                            Text(
                                type.name.replace("_", " "),
                                color = Color.White
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (sourceManager.addSource(sourceName, selectedSourceType)) {
                            refreshSources()
                            showAddSourceDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showAddSourceDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    if (renameSourceId != null) {
        AlertDialog(
            onDismissRequest = {
                renameSourceId = null
            },
            title = {
                Text("Rename Source")
            },
            text = {
                OutlinedTextField(
                    value = sourceName,
                    onValueChange = {
                        sourceName = it
                    },
                    label = {
                        Text("New source name")
                    },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val id = renameSourceId

                        if (id != null && sourceManager.renameSource(id, sourceName)) {
                            refreshSources()
                            renameSourceId = null
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        renameSourceId = null
                    }
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
        Text(
            "LIVE PREVIEW",
            color = Accent,
            fontSize = 12.sp
        )

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
