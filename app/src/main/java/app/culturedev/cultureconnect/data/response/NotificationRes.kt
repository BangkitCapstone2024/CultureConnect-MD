package app.culturedev.cultureconnect.data.response

import com.google.gson.annotations.SerializedName

data class NotificationRes (
    @field:SerializedName("notificationResult")
    val notificationResult: NotificationResult? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,
)

data class NotificationResult(
    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("image")
    val image: String? = null
)