
package com.mobileobs

data class StreamingServiceInfo(
    var running: Boolean = false,
    var notificationVisible: Boolean = false,
    var platform: StreamingPlatform = StreamingPlatform.YOUTUBE,
    var startedAt: Long = 0L
)

class StreamingServiceManager {

    private var serviceInfo = StreamingServiceInfo()

    fun getServiceInfo(): StreamingServiceInfo {
        return serviceInfo.copy()
    }

    fun startService(platform: StreamingPlatform): Boolean {
        if (serviceInfo.running) return false

        serviceInfo = StreamingServiceInfo(
            running = true,
            notificationVisible = true,
            platform = platform,
            startedAt = System.currentTimeMillis()
        )

        return true
    }

    fun stopService(): Boolean {
        if (!serviceInfo.running) return false

        serviceInfo.running = false
        serviceInfo.notificationVisible = false
        serviceInfo.startedAt = 0L

        return true
    }

    fun isRunning(): Boolean {
        return serviceInfo.running
    }

    fun setNotificationVisible(visible: Boolean) {
        serviceInfo.notificationVisible = visible
    }

    fun reset() {
        serviceInfo = StreamingServiceInfo()
    }
}
