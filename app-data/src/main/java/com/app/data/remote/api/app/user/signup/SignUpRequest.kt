package com.app.data.remote.api.app.user.signup

import com.squareup.moshi.Json

class SignUpRequest(
    name: String?,
    email: String?,
    password: String?
) {
    @field: Json(name = "name")
    var name: String? = name

    @field: Json(name = "email")
    var email: String? = email

    @field: Json(name = "password")
    var password: String? = password
}