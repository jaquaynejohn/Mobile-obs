
package com.mobileobs

data class OBSUser(
    val userId: String,
    val email: String,
    val displayName: String
)

class AccountManager {

    private var currentUser: OBSUser? = null

    fun getCurrentUser(): OBSUser? {
        return currentUser
    }

    fun isSignedIn(): Boolean {
        return currentUser != null
    }

    fun setSignedInUser(user: OBSUser) {
        currentUser = user
    }

    fun signOut() {
        currentUser = null
    }

    fun updateProfile(
        displayName: String
    ): Boolean {
        if (displayName.isBlank()) return false

        val user = currentUser ?: return false

        currentUser = user.copy(
            displayName = displayName.trim()
        )

        return true
    }
}
