package app.culturedev.cultureconnect.data.response.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null

)
