
package com.mobileobs

data class SceneCollection(
    val id: String,
    var name: String,
    var sceneIds: MutableList<String> = mutableListOf(),
    var isActive: Boolean = false
)

class SceneCollectionManager {

    private val collections = mutableListOf<SceneCollection>()
    private var nextId = 1
    private var activeCollectionId: String? = null

    fun getCollections(): List<SceneCollection> {
        return collections.map {
            it.copy(sceneIds = it.sceneIds.toMutableList())
        }
    }

    fun getActiveCollection(): SceneCollection? {
        return collections.find {
            it.id == activeCollectionId
        }?.let {
            it.copy(sceneIds = it.sceneIds.toMutableList())
        }
    }

    fun createCollection(name: String): Boolean {
        if (name.isBlank()) return false

        collections.add(
            SceneCollection(
                id = (nextId++).toString(),
                name = name.trim()
            )
        )

        return true
    }

    fun renameCollection(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val collection = collections.find {
            it.id == id
        } ?: return false

        collection.name = newName.trim()
        return true
    }

    fun addScene(
        collectionId: String,
        sceneId: String
    ): Boolean {
        if (sceneId.isBlank()) return false

        val collection = collections.find {
            it.id == collectionId
        } ?: return false

        if (sceneId in collection.sceneIds) return false

        collection.sceneIds.add(sceneId)
        return true
    }

    fun removeScene(
        collectionId: String,
        sceneId: String
    ): Boolean {
        val collection = collections.find {
            it.id == collectionId
        } ?: return false

        return collection.sceneIds.remove(sceneId)
    }

    fun selectCollection(id: String): Boolean {
        val collection = collections.find {
            it.id == id
        } ?: return false

        collections.forEach {
            it.isActive = false
        }

        collection.isActive = true
        activeCollectionId = id

        return true
    }

    fun deleteCollection(id: String): Boolean {
        val removed = collections.removeAll {
            it.id == id
        }

        if (activeCollectionId == id) {
            activeCollectionId = null
        }

        return removed
    }

    fun clearCollections() {
        collections.clear()
        activeCollectionId = null
        nextId = 1
    }
}
