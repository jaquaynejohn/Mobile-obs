
package com.mobileobs

data class StudioProfile(
    val id: String,
    var name: String,
    var description: String = "",
    var createdAt: Long = System.currentTimeMillis(),
    var isActive: Boolean = false
)

class StudioProfileManager {

    private val profiles = mutableListOf<StudioProfile>()
    private var nextId = 1

    fun getProfiles(): List<StudioProfile> {
        return profiles.map { it.copy() }
    }

    fun createProfile(
        name: String,
        description: String = ""
    ): StudioProfile? {
        if (name.isBlank()) return null

        val profile = StudioProfile(
            id = (nextId++).toString(),
            name = name.trim(),
            description = description.trim()
        )

        profiles.add(profile)
        return profile.copy()
    }

    fun renameProfile(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val profile = profiles.find { it.id == id } ?: return false
        profile.name = newName.trim()
        return true
    }

    fun updateDescription(
        id: String,
        description: String
    ): Boolean {
        val profile = profiles.find { it.id == id } ?: return false
        profile.description = description.trim()
        return true
    }

    fun activateProfile(id: String): Boolean {
        if (profiles.none { it.id == id }) return false

        profiles.forEach {
            it.isActive = it.id == id
        }

        return true
    }

    fun getActiveProfile(): StudioProfile? {
        return profiles.find { it.isActive }?.copy()
    }

    fun deleteProfile(id: String): Boolean {
        return profiles.removeAll { it.id == id }
    }
}
