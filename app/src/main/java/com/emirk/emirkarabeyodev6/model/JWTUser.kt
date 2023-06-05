package com.emirk.emirkarabeyodev6.model

import com.google.gson.annotations.SerializedName

data class JWTUser (
    val username: String,
    val password: String
)