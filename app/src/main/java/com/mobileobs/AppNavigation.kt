
package com.mobileobs

enum class OBSPage {
    STUDIO,
    SCENES,
    SOURCES,
    OVERLAYS,
    CHAT,
    AUDIO,
    CAMERA,
    RECORDING,
    SETTINGS,
    ACCOUNT,
    PLATFORMS,
    PROJECTS,
    ANALYTICS,
    HELP
}

class AppNavigation {

    private var currentPage = OBSPage.STUDIO

    fun getCurrentPage(): OBSPage {
        return currentPage
    }

    fun navigateTo(page: OBSPage) {
        currentPage = page
    }

    fun goToStudio() {
        currentPage = OBSPage.STUDIO
    }

    fun isCurrentPage(page: OBSPage): Boolean {
        return currentPage == page
    }
}
