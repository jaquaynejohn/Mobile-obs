
package com.mobileobs

data class HelpTopic(
    val id: String,
    val title: String,
    val description: String
)

class HelpManager {

    private val topics = listOf(
        HelpTopic(
            "1",
            "Creating Scenes",
            "Create different scenes for your stream, such as Gameplay and Starting Soon."
        ),
        HelpTopic(
            "2",
            "Adding Sources",
            "Add screen capture, camera, images, text, browser overlays, and chat."
        ),
        HelpTopic(
            "3",
            "Connecting Platforms",
            "Connect supported streaming platforms through their official account systems."
        ),
        HelpTopic(
            "4",
            "Starting a Stream",
            "Choose a connected platform, check your settings, and start streaming."
        ),
        HelpTopic(
            "5",
            "Recording",
            "Configure recording quality and save recordings to your device."
        ),
        HelpTopic(
            "6",
            "Chat Overlay",
            "Display supported live chat over your stream and adjust its appearance."
        )
    )

    fun getTopics(): List<HelpTopic> {
        return topics.toList()
    }

    fun getTopic(id: String): HelpTopic? {
        return topics.find {
            it.id == id
        }
    }

    fun searchTopics(query: String): List<HelpTopic> {
        if (query.isBlank()) return getTopics()

        return topics.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }
}
