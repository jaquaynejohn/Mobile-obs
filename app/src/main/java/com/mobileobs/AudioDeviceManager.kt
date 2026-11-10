
package com.mobileobs

enum class AudioDeviceType {
    INPUT,
    OUTPUT
}

data class OBSAudioDevice(
    val id: String,
    val name: String,
    val type: AudioDeviceType,
    var selected: Boolean = false
)

class AudioDeviceManager {

    private val devices = mutableListOf<OBSAudioDevice>()

    fun getDevices(): List<OBSAudioDevice> {
        return devices.map { it.copy() }
    }

    fun addDevice(
        id: String,
        name: String,
        type: AudioDeviceType
    ): Boolean {
        if (id.isBlank() || name.isBlank()) return false

        if (devices.any { it.id == id }) return false

        devices.add(
            OBSAudioDevice(
                id = id.trim(),
                name = name.trim(),
                type = type
            )
        )

        return true
    }

    fun selectDevice(id: String): Boolean {
        val device = devices.find {
            it.id == id
        } ?: return false

        devices.filter {
            it.type == device.type
        }.forEach {
            it.selected = false
        }

        device.selected = true
        return true
    }

    fun getSelectedDevice(
        type: AudioDeviceType
    ): OBSAudioDevice? {
        return devices.find {
            it.type == type && it.selected
        }?.copy()
    }

    fun removeDevice(id: String): Boolean {
        return devices.removeAll {
            it.id == id
        }
    }

    fun clearDevices() {
        devices.clear()
    }
}
