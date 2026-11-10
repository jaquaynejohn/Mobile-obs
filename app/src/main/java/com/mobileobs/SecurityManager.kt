
package com.mobileobs

data class SecuritySettings(
    var biometricEnabled: Boolean = false,
    var autoLockEnabled: Boolean = false,
    var autoLockMinutes: Int = 5,
    var rememberLogin: Boolean = true
)

class OBSSecurityManager {

    private var settings = SecuritySettings()
    private var sessionActive = false

    fun getSettings(): SecuritySettings {
        return settings.copy()
    }

    fun updateSettings(
        newSettings: SecuritySettings
    ): Boolean {
        if (newSettings.autoLockMinutes !in 1..60) {
            return false
        }

        settings = newSettings.copy()
        return true
    }

    fun setBiometricEnabled(enabled: Boolean) {
        settings.biometricEnabled = enabled
    }

    fun setAutoLockEnabled(enabled: Boolean) {
        settings.autoLockEnabled = enabled
    }

    fun setAutoLockMinutes(minutes: Int): Boolean {
        if (minutes !in 1..60) return false

        settings.autoLockMinutes = minutes
        return true
    }

    fun setRememberLogin(enabled: Boolean) {
        settings.rememberLogin = enabled
    }

    fun startSession() {
        sessionActive = true
    }

    fun endSession() {
        sessionActive = false
    }

    fun isSessionActive(): Boolean {
        return sessionActive
    }
}
