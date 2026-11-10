
package com.mobileobs

data class UserPreferences(
    var darkMode: Boolean = true,
    var autoSave: Boolean = true,
    var showChatOverlay: Boolean = true,
    var showPreview: Boolean = true,
    var compactLayout: Boolean = false,
    var confirmBeforeStopping: Boolean = true
)

class UserPreferencesManager {

    private var preferences = UserPreferences()

    fun getPreferences(): UserPreferences {
        return preferences.copy()
    }

    fun updatePreferences(
        newPreferences: UserPreferences
    ) {
        preferences = newPreferences.copy()
    }

    fun setDarkMode(enabled: Boolean) {
        preferences.darkMode = enabled
    }

    fun setAutoSave(enabled: Boolean) {
        preferences.autoSave = enabled
    }

    fun setChatOverlayVisible(enabled: Boolean) {
        preferences.showChatOverlay = enabled
    }

    fun setPreviewVisible(enabled: Boolean) {
        preferences.showPreview = enabled
    }

    fun setCompactLayout(enabled: Boolean) {
        preferences.compactLayout = enabled
    }

    fun setStopConfirmation(enabled: Boolean) {
        preferences.confirmBeforeStopping = enabled
    }
}
