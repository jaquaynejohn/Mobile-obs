
package com.mobileobs

data class OBSWatermark(
    var enabled: Boolean = false,
    var text: String = "Mobile OBS",
    var imageUri: String = "",
    var opacity: Float = 1f,
    var x: Float = 0f,
    var y: Float = 0f,
    var width: Float = 150f,
    var height: Float = 60f
)

class WatermarkManager {

    private var watermark = OBSWatermark()

    fun getWatermark(): OBSWatermark {
        return watermark.copy()
    }

    fun setEnabled(enabled: Boolean) {
        watermark.enabled = enabled
    }

    fun setText(text: String) {
        watermark.text = text
    }

    fun setImageUri(uri: String) {
        watermark.imageUri = uri
    }

    fun setOpacity(opacity: Float): Boolean {
        if (opacity !in 0f..1f) return false

        watermark.opacity = opacity
        return true
    }

    fun setPosition(x: Float, y: Float) {
        watermark.x = x
        watermark.y = y
    }

    fun setSize(width: Float, height: Float): Boolean {
        if (width <= 0f || height <= 0f) return false

        watermark.width = width
        watermark.height = height
        return true
    }

    fun updateWatermark(
        newWatermark: OBSWatermark
    ): Boolean {
        if (
            newWatermark.opacity !in 0f..1f ||
            newWatermark.width <= 0f ||
            newWatermark.height <= 0f
        ) {
            return false
        }

        watermark = newWatermark.copy()
        return true
    }
}
