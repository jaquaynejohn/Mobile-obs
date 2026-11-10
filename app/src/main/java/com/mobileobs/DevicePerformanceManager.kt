
package com.mobileobs

data class DevicePerformanceInfo(
    var cpuUsagePercent: Float = 0f,
    var memoryUsedMb: Long = 0L,
    var memoryTotalMb: Long = 0L,
    var temperatureCelsius: Float = 0f,
    var batteryPercent: Int = 100
)

class DevicePerformanceManager {

    private var performanceInfo = DevicePerformanceInfo()

    fun getPerformanceInfo(): DevicePerformanceInfo {
        return performanceInfo.copy()
    }

    fun updateCpuUsage(percent: Float): Boolean {
        if (!percent.isFinite() || percent !in 0f..100f) {
            return false
        }

        performanceInfo.cpuUsagePercent = percent
        return true
    }

    fun updateMemory(
        usedMb: Long,
        totalMb: Long
    ): Boolean {
        if (
            usedMb < 0L ||
            totalMb < 0L ||
            usedMb > totalMb
        ) {
            return false
        }

        performanceInfo.memoryUsedMb = usedMb
        performanceInfo.memoryTotalMb = totalMb

        return true
    }

    fun updateTemperature(celsius: Float): Boolean {
        if (!celsius.isFinite() || celsius !in -50f..150f) {
            return false
        }

        performanceInfo.temperatureCelsius = celsius
        return true
    }

    fun updateBattery(percent: Int): Boolean {
        if (percent !in 0..100) return false

        performanceInfo.batteryPercent = percent
        return true
    }

    fun reset() {
        performanceInfo = DevicePerformanceInfo()
    }
}
