
package com.mobileobs

enum class OBSNotificationType {
    SUCCESS,
    WARNING,
    ERROR,
    INFO
}

data class OBSNotification(
    val id: String,
    val title: String,
    val message: String,
    val type: OBSNotificationType
)

class OBSNotificationManager {

    private val notifications = mutableListOf<OBSNotification>()
    private var nextId = 1

    fun getNotifications(): List<OBSNotification> {
        return notifications.toList()
    }

    fun addNotification(
        title: String,
        message: String,
        type: OBSNotificationType
    ): Boolean {
        if (title.isBlank() || message.isBlank()) return false

        notifications.add(
            OBSNotification(
                id = (nextId++).toString(),
                title = title.trim(),
                message = message.trim(),
                type = type
            )
        )

        return true
    }

    fun removeNotification(id: String): Boolean {
        return notifications.removeAll {
            it.id == id
        }
    }

    fun clearNotifications() {
        notifications.clear()
    }

    fun getLatestNotification(): OBSNotification? {
        return notifications.lastOrNull()
    }
}
