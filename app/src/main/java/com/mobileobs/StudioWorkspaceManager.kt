
package com.mobileobs

data class StudioWorkspace(
    val id: String,
    var name: String,
    var isActive: Boolean = false,
    var createdAt: Long = System.currentTimeMillis()
)

class StudioWorkspaceManager {

    private val workspaces = mutableListOf<StudioWorkspace>()
    private var nextId = 1

    fun getWorkspaces(): List<StudioWorkspace> {
        return workspaces.map { it.copy() }
    }

    fun createWorkspace(name: String): StudioWorkspace? {
        if (name.isBlank()) return null

        val workspace = StudioWorkspace(
            id = (nextId++).toString(),
            name = name.trim()
        )

        workspaces.add(workspace)
        return workspace.copy()
    }

    fun renameWorkspace(id: String, newName: String): Boolean {
        if (newName.isBlank()) return false

        val workspace = workspaces.find { it.id == id } ?: return false
        workspace.name = newName.trim()
        return true
    }

    fun activateWorkspace(id: String): Boolean {
        if (workspaces.none { it.id == id }) return false

        workspaces.forEach {
            it.isActive = it.id == id
        }

        return true
    }

    fun getActiveWorkspace(): StudioWorkspace? {
        return workspaces
            .find { it.isActive }
            ?.copy()
    }

    fun deleteWorkspace(id: String): Boolean {
        return workspaces.removeAll { it.id == id }
    }
}
