
package com.mobileobs

enum class NetworkConnectionType {
    WIFI,
    MOBILE_DATA,
    ETHERNET,
    NONE
}

data class NetworkMonitorInfo(
    var connected: Boolean = false,
    var connectionType: NetworkConnectionType = NetworkConnectionType.NONE,
    var downloadSpeedMbps: Float = 0f,
    var uploadSpeedMbps: Float = 0f,
    var latencyMs: Long = 0L
)

class NetworkMonitorManager {

    private var networkInfo = NetworkMonitorInfo()

    fun getNetworkInfo(): NetworkMonitorInfo {
        return networkInfo.copy()
    }

    fun updateConnection(
        connected: Boolean,
        type: NetworkConnectionType
    ) {
        networkInfo.connected = connected
        networkInfo.connectionType = if (connected) {
            type
        } else {
            NetworkConnectionType.NONE
        }
    }

    fun updateDownloadSpeed(speedMbps: Float): Boolean {
        if (!speedMbps.isFinite() || speedMbps < 0f) {
            return false
        }

        networkInfo.downloadSpeedMbps = speedMbps
        return true
    }

    fun updateUploadSpeed(speedMbps: Float): Boolean {
        if (!speedMbps.isFinite() || speedMbps < 0f) {
            return false
        }

        networkInfo.uploadSpeedMbps = speedMbps
        return true
    }

    fun updateLatency(latencyMs: Long): Boolean {
        if (latencyMs < 0L) return false

        networkInfo.latencyMs = latencyMs
        return true
    }

    fun reset() {
        networkInfo = NetworkMonitorInfo()
    }
}
