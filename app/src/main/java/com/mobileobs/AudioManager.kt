
package com.mobileobs

data class AudioChannel(
    val id: String,
    var name: String,
    var volume: Float = 1.0f,
    var muted: Boolean = false
)

class OBSAudioManager {

    private val channels = mutableListOf(
        AudioChannel("1", "Microphone"),
        AudioChannel("2", "Game Audio"),
        AudioChannel("3", "Media Audio")
    )

    fun getChannels(): List<AudioChannel> {
        return channels.map { it.copy() }
    }

    fun setVolume(
        id: String,
        volume: Float
    ): Boolean {
        if (volume !in 0f..1f) return false

        val channel = channels.find {
            it.id == id
        } ?: return false

        channel.volume = volume
        return true
    }

    fun toggleMute(id: String): Boolean {
        val channel = channels.find {
            it.id == id
        } ?: return false

        channel.muted = !channel.muted
        return true
    }

    fun muteAll() {
        channels.forEach {
            it.muted = true
        }
    }

    fun unmuteAll() {
        channels.forEach {
            it.muted = false
        }
    }

    fun renameChannel(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val channel = channels.find {
            it.id == id
        } ?: return false

        channel.name = newName.trim()
        return true
    }
}
