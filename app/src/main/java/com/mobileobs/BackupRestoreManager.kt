
package com.mobileobs

enum class RestoreStatus {
    IDLE,
    RESTORING,
    COMPLETED,
    FAILED
}

data class RestoreInfo(
    var status: RestoreStatus = RestoreStatus.IDLE,
    var backupName: String = "",
    var restoredAt: Long = 0L,
    var errorMessage: String = ""
)

class BackupRestoreManager {

    private var restoreInfo = RestoreInfo()

    fun getRestoreInfo(): RestoreInfo {
        return restoreInfo.copy()
    }

    fun startRestore(backupName: String): Boolean {
        if (backupName.isBlank()) return false

        if (restoreInfo.status == RestoreStatus.RESTORING) {
            return false
        }

        restoreInfo.status = RestoreStatus.RESTORING
        restoreInfo.backupName = backupName.trim()
        restoreInfo.errorMessage = ""

        return true
    }

    fun completeRestore(): Boolean {
        if (restoreInfo.status != RestoreStatus.RESTORING) {
            return false
        }

        restoreInfo.status = RestoreStatus.COMPLETED
        restoreInfo.restoredAt = System.currentTimeMillis()

        return true
    }

    fun failRestore(message: String): Boolean {
        if (restoreInfo.status != RestoreStatus.RESTORING) {
            return false
        }

        restoreInfo.status = RestoreStatus.FAILED
        restoreInfo.errorMessage = message

        return true
    }

    fun reset() {
        restoreInfo = RestoreInfo()
    }
}
