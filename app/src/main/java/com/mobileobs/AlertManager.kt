
package com.mobileobs

enum class AlertType {
    WELCOME,
    NEW_FOLLOWER,
    NEW_SUBSCRIBER,
    NEW_MEMBER,
    DONATION,
    CUSTOM
}

data class OBSAlert(
    val id: String,
    var name: String,
    var type: AlertType,
    var title: String,
    var message: String,
    var enabled: Boolean = true,
    var durationSeconds: Int = 5
)

class AlertManager {

    private val alerts = mutableListOf<OBSAlert>()
    private var nextId = 1

    fun getAlerts(): List<OBSAlert> {
        return alerts.map { it.copy() }
    }

    fun addAlert(
        name: String,
        type: AlertType,
        title: String,
        message: String
    ): Boolean {
        if (
            name.isBlank() ||
            title.isBlank() ||
            message.isBlank()
        ) {
            return false
        }

        alerts.add(
            OBSAlert(
                id = (nextId++).toString(),
                name = name.trim(),
                type = type,
                title = title.trim(),
                message = message.trim()
            )
        )

        return true
    }

    fun renameAlert(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val alert = alerts.find {
            it.id == id
        } ?: return false

        alert.name = newName.trim()
        return true
    }

    fun updateAlert(
        id: String,
        title: String,
        message: String
    ): Boolean {
        if (title.isBlank() || message.isBlank()) return false

        val alert = alerts.find {
            it.id == id
        } ?: return false

        alert.title = title.trim()
        alert.message = message.trim()

        return true
    }

    fun setEnabled(
        id: String,
        enabled: Boolean
    ): Boolean {
        val alert = alerts.find {
            it.id == id
        } ?: return false

        alert.enabled = enabled
        return true
    }

    fun setDuration(
        id: String,
        seconds: Int
    ): Boolean {
        if (seconds !in 1..60) return false

        val alert = alerts.find {
            it.id == id
        } ?: return false

        alert.durationSeconds = seconds
        return true
    }

    fun deleteAlert(id: String): Boolean {
        return alerts.removeAll {
            it.id == id
        }
    }

    fun clearAlerts() {
        alerts.clear()
        nextId = 1
    }
}
