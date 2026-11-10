
package com.mobileobs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
            MobileOBSApp()
        }
    }
}

@Composable
fun MobileOBSApp() {
    var selectedTab by remember { mutableStateOf("Studio") }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Accent,
            background = Background,
            surface = Panel
        )
    ) {
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

            Spacer(modifier = Modifier.height(22.dp))

            PreviewPanel()

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SCENES",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                SceneButton("Starting Soon")
                SceneButton("Gameplay")
                SceneButton("BRB")
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "STREAMING",
                color = Color.LightGray,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                PlatformButton("TikTok")
                PlatformButton("YouTube")
                PlatformButton("Twitch")
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                listOf("Studio", "Scenes", "Sources", "Settings")
                    .forEach { tab ->
                        TextButton(onClick = { selectedTab = tab }) {
                            Text(
                                text = tab,
                                color = if (selectedTab == tab)
                                    Accent else Color.Gray
                            )
                        }
                    }
            }
        }
    }
}

@Composable
fun PreviewPanel() {
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
            "Your stream preview",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Text("No source added", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun SceneButton(name: String) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Panel
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(10.dp)
    ) {
        Text(name, color = Color.White, fontSize = 11.sp)
    }
}

@Composable
fun PlatformButton(name: String) {
    Button(
        onClick = {},
        modifier = Modifier.weight(1f),
        colors = ButtonDefaults.buttonColors(
            containerColor = Accent
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        Text(name, fontSize = 10.sp)
    }
}
