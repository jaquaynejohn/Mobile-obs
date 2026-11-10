
package com.mobileobs

enum class OBSPermission {
    CAMERA,
    MICROPHONE,
    SCREEN_CAPTURE,
    NOTIFICATIONS
}

class PermissionManager {

    private val permissions = mutableMapOf(
        OBSPermission.CAMERA to false,
        OBSPermission.MICROPHONE to false,
        OBSPermission.SCREEN_CAPTURE to false,
        OBSPermission.NOTIFICATIONS to false
    )

    fun isGranted(permission: OBSPermission): Boolean {
        return permissions[permission] ?: false
    }

    fun setGranted(
        permission: OBSPermission,
        granted: Boolean
    ) {
        permissions[permission] = granted
    }

    fun getAllPermissions(): Map<OBSPermission, Boolean> {
        return permissions.toMap()
    }

    fun areRequiredPermissionsGranted(): Boolean {
        return isGranted(OBSPermission.CAMERA) &&
            isGranted(OBSPermission.MICROPHONE)
    }

    fun resetPermissions() {
        permissions.keys.forEach {
            permissions[it] = false
        }
    }
}
