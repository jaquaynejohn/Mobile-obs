
package com.mobileobs

data class LoginSession(
    var active: Boolean = false,
    var userId: String = "",
    var startedAt: Long = 0L,
    var expiresAt: Long = 0L
)

class LoginSessionManager {

    private var session = LoginSession()

    fun getSession(): LoginSession {
        return session.copy()
    }

    fun startSession(
        userId: String,
        durationHours: Int = 24
    ): Boolean {
        if (userId.isBlank() || durationHours !in 1..720) {
            return false
        }

        val now = System.currentTimeMillis()

        session = LoginSession(
            active = true,
            userId = userId.trim(),
            startedAt = now,
            expiresAt = now + durationHours * 60L * 60L * 1000L
        )

        return true
    }

    fun isSessionActive(): Boolean {
        if (!session.active) return false

        if (System.currentTimeMillis() >= session.expiresAt) {
            endSession()
            return false
        }

        return true
    }

    fun getUserId(): String? {
        return if (isSessionActive()) {
            session.userId
        } else {
            null
        }
    }

    fun endSession() {
        session = LoginSession()
    }

    fun extendSession(durationHours: Int): Boolean {
        if (!isSessionActive() || durationHours !in 1..720) {
            return false
        }

        session.expiresAt =
            System.currentTimeMillis() + durationHours * 60L * 60L * 1000L

        return true
    }
}
