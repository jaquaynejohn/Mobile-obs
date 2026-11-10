
package com.mobileobs

data class OBSProject(
    val id: String,
    var name: String,
    var description: String = "",
    var lastModified: Long = System.currentTimeMillis()
)

class ProjectManager {

    private val projects = mutableListOf<OBSProject>()
    private var nextId = 1

    fun getProjects(): List<OBSProject> {
        return projects.map { it.copy() }
    }

    fun createProject(
        name: String,
        description: String = ""
    ): Boolean {
        if (name.isBlank()) return false

        projects.add(
            OBSProject(
                id = (nextId++).toString(),
                name = name.trim(),
                description = description.trim()
            )
        )

        return true
    }

    fun renameProject(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val project = projects.find {
            it.id == id
        } ?: return false

        project.name = newName.trim()
        project.lastModified = System.currentTimeMillis()

        return true
    }

    fun updateDescription(
        id: String,
        description: String
    ): Boolean {
        val project = projects.find {
            it.id == id
        } ?: return false

        project.description = description.trim()
        project.lastModified = System.currentTimeMillis()

        return true
    }

    fun deleteProject(id: String): Boolean {
        return projects.removeAll {
            it.id == id
        }
    }

    fun getProject(id: String): OBSProject? {
        return projects.find {
            it.id == id
        }?.copy()
    }
}
