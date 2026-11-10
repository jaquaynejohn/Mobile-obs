
package com.mobileobs

enum class BackupStatus {
    IDLE,
    CREATING,
    COMPLETED,
    FAILED
}

data class OBSBackup(
    val id: String,
    val name: String,
    val createdAt: Long,
    val sizeBytes: Long = 0L
)

class BackupManager {

    private val backups = mutableListOf<OBSBackup>()
    private var status = BackupStatus.IDLE
    private var nextId = 1

    fun getBackups(): List<OBSBackup> {
        return backups.toList()
    }

    fun getStatus(): BackupStatus {
        return status
    }

    fun createBackup(name: String): Boolean {
        if (name.isBlank()) return false
        if (status == BackupStatus.CREATING) return false

        status = BackupStatus.CREATING

        backups.add(
            OBSBackup(
                id = (nextId++).toString(),
                name = name.trim(),
                createdAt = System.currentTimeMillis()
            )
        )

        status = BackupStatus.COMPLETED
        return true
    }

    fun deleteBackup(id: String): Boolean {
        return backups.removeAll {
            it.id == id
        }
    }

    fun findBackup(id: String): OBSBackup? {
        return backups.find {
            it.id == id
        }?.copy()
    }

    fun clearBackups() {
        backups.clear()
        nextId = 1
        status = BackupStatus.IDLE
    }
}
