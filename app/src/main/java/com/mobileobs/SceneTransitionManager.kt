
package com.mobileobs

enum class SceneTransitionType {
    CUT,
    FADE,
    SLIDE_LEFT,
    SLIDE_RIGHT
}

data class SceneTransition(
    var type: SceneTransitionType = SceneTransitionType.FADE,
    var durationMs: Long = 500
)

class SceneTransitionManager {

    private var transition = SceneTransition()

    fun getTransition(): SceneTransition {
        return transition.copy()
    }

    fun setTransitionType(
        type: SceneTransitionType
    ) {
        transition.type = type
    }

    fun setDuration(
        durationMs: Long
    ): Boolean {
        if (durationMs !in 0..5000) return false

        transition.durationMs = durationMs
        return true
    }

    fun updateTransition(
        newTransition: SceneTransition
    ): Boolean {
        if (newTransition.durationMs !in 0..5000) {
            return false
        }

        transition = newTransition.copy()
        return true
    }
}
