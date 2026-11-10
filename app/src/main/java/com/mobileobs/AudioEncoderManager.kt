
package com.mobileobs

enum class AudioCodec {
    AAC,
    OPUS
}

data class AudioEncoderSettings(
    var codec: AudioCodec = AudioCodec.AAC,
    var bitrateKbps: Int = 128,
    var sampleRate: Int = 48000,
    var channels: Int = 2
)

class AudioEncoderManager {

    private var settings = AudioEncoderSettings()

    fun getSettings(): AudioEncoderSettings {
        return settings.copy()
    }

    fun setCodec(codec: AudioCodec) {
        settings.codec = codec
    }

    fun setBitrate(bitrateKbps: Int): Boolean {
        if (bitrateKbps !in 64..320) return false

        settings.bitrateKbps = bitrateKbps
        return true
    }

    fun setSampleRate(sampleRate: Int): Boolean {
        if (sampleRate !in listOf(44100, 48000)) return false

        settings.sampleRate = sampleRate
        return true
    }

    fun setChannels(channels: Int): Boolean {
        if (channels !in listOf(1, 2)) return false

        settings.channels = channels
        return true
    }

    fun updateSettings(
        newSettings: AudioEncoderSettings
    ): Boolean {
        if (
            newSettings.bitrateKbps !in 64..320 ||
            newSettings.sampleRate !in listOf(44100, 48000) ||
            newSettings.channels !in listOf(1, 2)
        ) {
            return false
        }

        settings = newSettings.copy()
        return true
    }
}
