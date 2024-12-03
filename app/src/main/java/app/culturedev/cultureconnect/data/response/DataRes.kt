package app.culturedev.cultureconnect.data.response

import com.google.gson.annotations.SerializedName

data class DataRes (

        @field:SerializedName("listData")
        val listData: List<ListDataItem> = emptyList(),

        @field:SerializedName("error")
        val error: Boolean? = null,

        @field:SerializedName("message")
        val message: String? = null,

        @field:SerializedName("data")
        val data: ListDataItem? = null
)

data class ListDataItem (

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("image")
        val image: String? = null,

        @field:SerializedName("rating")
        val rating: String? = null,

        @field:SerializedName("price")
        val price: String? = null,

        @field:SerializedName("schedule")
        val schedule: String? = null,
)