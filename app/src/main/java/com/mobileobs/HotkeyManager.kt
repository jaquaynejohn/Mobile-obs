
package com.mobileobs

data class OBSHotkey(
    val action: String,
    var key: String
)

class HotkeyManager {

    private val hotkeys = mutableListOf(
        OBSHotkey("Start Stream", "F5"),
        OBSHotkey("Stop Stream", "F6"),
        OBSHotkey("Switch Scene", "F7"),
        OBSHotkey("Start Recording", "F8"),
        OBSHotkey("Stop Recording", "F9")
    )

    fun getHotkeys(): List<OBSHotkey> {
        return hotkeys.map { it.copy() }
    }

    fun setHotkey(
        action: String,
        key: String
    ): Boolean {
        if (action.isBlank() || key.isBlank()) return false

        val hotkey = hotkeys.find {
            it.action == action
        } ?: return false

        hotkey.key = key.trim()
        return true
    }

    fun getHotkey(action: String): String? {
        return hotkeys.find {
            it.action == action
        }?.key
    }

    fun resetHotkeys() {
        hotkeys.clear()

        hotkeys.addAll(
            listOf(
                OBSHotkey("Start Stream", "F5"),
                OBSHotkey("Stop Stream", "F6"),
                OBSHotkey("Switch Scene", "F7"),
                OBSHotkey("Start Recording", "F8"),
                OBSHotkey("Stop Recording", "F9")
            )
        )
    }
}
