
package com.mobileobs

enum class SyncStatus {
    IDLE,
    SYNCING,
    SUCCESS,
    FAILED
}

data class CloudSyncInfo(
    var status: SyncStatus = SyncStatus.IDLE,
    var lastSynced: Long = 0L,
    var errorMessage: String = ""
)

class CloudSyncManager {

    private var syncInfo = CloudSyncInfo()

    fun getSyncInfo(): CloudSyncInfo {
        return syncInfo.copy()
    }

    fun startSync(): Boolean {
        if (syncInfo.status == SyncStatus.SYNCING) {
            return false
        }

        syncInfo.status = SyncStatus.SYNCING
        syncInfo.errorMessage = ""
        return true
    }

    fun completeSync(): Boolean {
        if (syncInfo.status != SyncStatus.SYNCING) {
            return false
        }

        syncInfo.status = SyncStatus.SUCCESS
        syncInfo.lastSynced = System.currentTimeMillis()
        return true
    }

    fun failSync(message: String): Boolean {
        if (syncInfo.status != SyncStatus.SYNCING) {
            return false
        }

        syncInfo.status = SyncStatus.FAILED
        syncInfo.errorMessage = message
        return true
    }

    fun resetSyncStatus() {
        syncInfo.status = SyncStatus.IDLE
        syncInfo.errorMessage = ""
    }
}
