
package com.mobileobs

enum class RecordingFormat {
    MP4,
    MKV
}

data class OBSRecordingFile(
    val id: String,
    var name: String,
    var filePath: String,
    var format: RecordingFormat,
    var sizeBytes: Long = 0L,
    var createdAt: Long = System.currentTimeMillis()
)

class RecordingFileManager {

    private val recordings = mutableListOf<OBSRecordingFile>()
    private var nextId = 1

    fun getRecordings(): List<OBSRecordingFile> {
        return recordings.map { it.copy() }
    }

    fun addRecording(
        name: String,
        filePath: String,
        format: RecordingFormat,
        sizeBytes: Long
    ): Boolean {
        if (
            name.isBlank() ||
            filePath.isBlank() ||
            sizeBytes < 0L
        ) {
            return false
        }

        recordings.add(
            OBSRecordingFile(
                id = (nextId++).toString(),
                name = name.trim(),
                filePath = filePath.trim(),
                format = format,
                sizeBytes = sizeBytes
            )
        )

        return true
    }

    fun renameRecording(
        id: String,
        newName: String
    ): Boolean {
        if (newName.isBlank()) return false

        val recording = recordings.find {
            it.id == id
        } ?: return false

        recording.name = newName.trim()
        return true
    }

    fun deleteRecording(id: String): Boolean {
        return recordings.removeAll {
            it.id == id
        }
    }

    fun getRecording(id: String): OBSRecordingFile? {
        return recordings.find {
            it.id == id
        }?.copy()
    }

    fun getTotalRecordingSize(): Long {
        return recordings.sumOf {
            it.sizeBytes
        }
    }

    fun clearRecordingList() {
        recordings.clear()
        nextId = 1
    }
}
