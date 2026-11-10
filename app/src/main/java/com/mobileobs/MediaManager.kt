
package com.mobileobs

enum class MediaType {
    IMAGE,
    VIDEO,
    AUDIO
}

data class OBSMedia(
    val id: String,
    var name: String,
    val uri: String,
    val type: MediaType
)

class MediaManager {

    private val mediaFiles = mutableListOf<OBSMedia>()
    private var nextId = 1

    fun getMedia(): List<OBSMedia> {
        return mediaFiles.toList()
    }

    fun addMedia(
        name: String,
        uri: String,
        type: MediaType
    ): Boolean {
        if (name.isBlank() || uri.isBlank()) return false

        mediaFiles.add(
            OBSMedia(
                id = (nextId++).toString(),
                name = name.trim(),
                uri = uri,
                type = type
            )
        )

        return true
    }

    fun renameMedia(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val media = mediaFiles.find {
            it.id == id
        } ?: return false

        media.name = newName.trim()
        return true
    }

    fun deleteMedia(id: String): Boolean {
        return mediaFiles.removeAll {
            it.id == id
        }
    }

    fun clearMedia() {
        mediaFiles.clear()
    }

    fun getMediaByType(type: MediaType): List<OBSMedia> {
        return mediaFiles.filter {
            it.type == type
        }
    }
}
