
package com.mobileobs

data class OBSStorageInfo(
    var totalBytes: Long = 0L,
    var availableBytes: Long = 0L,
    var usedBytes: Long = 0L,
    var warningThresholdPercent: Int = 90
) {
    fun getUsedPercentage(): Int {
        if (totalBytes <= 0L) return 0

        return ((usedBytes.toDouble() / totalBytes) * 100)
            .toInt()
            .coerceIn(0, 100)
    }

    fun hasLowStorage(): Boolean {
        return getUsedPercentage() >= warningThresholdPercent
    }
}

class OBSStorageManager {

    private var storageInfo = OBSStorageInfo()

    fun getStorageInfo(): OBSStorageInfo {
        return storageInfo.copy()
    }

    fun updateStorage(
        totalBytes: Long,
        availableBytes: Long
    ): Boolean {
        if (
            totalBytes <= 0L ||
            availableBytes < 0L ||
            availableBytes > totalBytes
        ) {
            return false
        }

        storageInfo.totalBytes = totalBytes
        storageInfo.availableBytes = availableBytes
        storageInfo.usedBytes = totalBytes - availableBytes

        return true
    }

    fun setWarningThreshold(percent: Int): Boolean {
        if (percent !in 50..99) return false

        storageInfo.warningThresholdPercent = percent
        return true
    }

    fun hasEnoughSpace(requiredBytes: Long): Boolean {
        if (requiredBytes < 0L) return false

        return storageInfo.availableBytes >= requiredBytes
    }

    fun reset() {
        storageInfo = OBSStorageInfo()
    }
}
