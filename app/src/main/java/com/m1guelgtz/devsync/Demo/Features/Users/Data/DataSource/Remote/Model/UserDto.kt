package com.m1guelgtz.templatecarsapi.Demo.Features.Users.Data.DataSource.Remote.Model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("created_at") val createdAt: String? = null
)
