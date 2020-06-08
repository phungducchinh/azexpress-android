package com.app.data.remote.api.app.user.login

import com.squareup.moshi.Json

class LoginRequest(
    email: String?,
    password: String?
) {
    @field: Json(name = "email")
    var email: String? = email

    @field: Json(name = "password")
    var password: String? = password

}