
package com.mobileobs

enum class DiagnosticLevel {
    INFO,
    WARNING,
    ERROR
}

data class StreamDiagnostic(
    val id: String,
    val level: DiagnosticLevel,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

class StreamDiagnosticsManager {

    private val diagnostics = mutableListOf<StreamDiagnostic>()
    private var nextId = 1

    fun getDiagnostics(): List<StreamDiagnostic> {
        return diagnostics.toList()
    }

    fun addDiagnostic(
        level: DiagnosticLevel,
        message: String
    ): Boolean {
        if (message.isBlank()) return false

        diagnostics.add(
            StreamDiagnostic(
                id = (nextId++).toString(),
                level = level,
                message = message.trim()
            )
        )

        return true
    }

    fun getErrors(): List<StreamDiagnostic> {
        return diagnostics.filter {
            it.level == DiagnosticLevel.ERROR
        }
    }

    fun getWarnings(): List<StreamDiagnostic> {
        return diagnostics.filter {
            it.level == DiagnosticLevel.WARNING
        }
    }

    fun removeDiagnostic(id: String): Boolean {
        return diagnostics.removeAll {
            it.id == id
        }
    }

    fun clearDiagnostics() {
        diagnostics.clear()
        nextId = 1
    }
}
