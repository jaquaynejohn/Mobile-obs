
package com.mobileobs

data class ExportInfo(
    val fileName: String,
    val exportedAt: Long,
    val format: String
)

class ExportManager {

    private var lastExport: ExportInfo? = null

    fun getLastExport(): ExportInfo? {
        return lastExport?.copy()
    }

    fun createExportInfo(
        fileName: String,
        format: String
    ): Boolean {
        if (fileName.isBlank()) return false

        if (format !in listOf("JSON", "ZIP")) {
            return false
        }

        lastExport = ExportInfo(
            fileName = fileName.trim(),
            exportedAt = System.currentTimeMillis(),
            format = format
        )

        return true
    }

    fun clearExportInfo() {
        lastExport = null
    }

    fun isSupportedFormat(format: String): Boolean {
        return format.uppercase() in listOf("JSON", "ZIP")
    }
}
