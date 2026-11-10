
package com.mobileobs

data class OBSDeviceInfo(
    val manufacturer: String,
    val model: String,
    val androidVersion: String,
    val sdkVersion: Int
)

class DeviceInfoManager {

    fun getDeviceInfo(): OBSDeviceInfo {
        return OBSDeviceInfo(
            manufacturer = android.os.Build.MANUFACTURER,
            model = android.os.Build.MODEL,
            androidVersion = android.os.Build.VERSION.RELEASE,
            sdkVersion = android.os.Build.VERSION.SDK_INT
        )
    }

    fun getDeviceName(): String {
        val device = getDeviceInfo()

        return "${device.manufacturer} ${device.model}"
    }

    fun supportsModernAndroid(): Boolean {
        return android.os.Build.VERSION.SDK_INT >= 26
    }
}
