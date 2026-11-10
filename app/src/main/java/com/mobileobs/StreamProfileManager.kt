
package com.mobileobs

data class StreamProfile(
    val id: String,
    var name: String,
    var platform: StreamingPlatform,
    var resolution: String = "720p",
    var frameRate: Int = 30,
    var bitrateKbps: Int = 3000,
    var audioBitrateKbps: Int = 128
)

class StreamProfileManager {

    private val profiles = mutableListOf<StreamProfile>()
    private var nextId = 1
    private var activeProfileId: String? = null

    fun getProfiles(): List<StreamProfile> {
        return profiles.map { it.copy() }
    }

    fun getActiveProfile(): StreamProfile? {
        return profiles.find {
            it.id == activeProfileId
        }?.copy()
    }

    fun createProfile(
        name: String,
        platform: StreamingPlatform
    ): Boolean {
        if (name.isBlank()) return false

        profiles.add(
            StreamProfile(
                id = (nextId++).toString(),
                name = name.trim(),
                platform = platform
            )
        )

        return true
    }

    fun updateProfile(
        id: String,
        updated: StreamProfile
    ): Boolean {
        if (updated.name.isBlank()) return false

        if (
            updated.resolution !in listOf("480p", "720p", "1080p") ||
            updated.frameRate !in listOf(24, 30, 60) ||
            updated.bitrateKbps !in 500..20000 ||
            updated.audioBitrateKbps !in 64..320
        ) {
            return false
        }

        val index = profiles.indexOfFirst {
            it.id == id
        }

        if (index == -1) return false

        profiles[index] = updated.copy(id = id)
        return true
    }

    fun selectProfile(id: String): Boolean {
        val exists = profiles.any {
            it.id == id
        }

        if (!exists) return false

        activeProfileId = id
        return true
    }

    fun deleteProfile(id: String): Boolean {
        val removed = profiles.removeAll {
            it.id == id
        }

        if (activeProfileId == id) {
            activeProfileId = null
        }

        return removed
    }

    fun clearProfiles() {
        profiles.clear()
        activeProfileId = null
        nextId = 1
    }
}
