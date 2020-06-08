package com.app.data.remote.api.app.user.signup

import com.app.data.model.UserModel
import com.app.data.remote.api.BaseApiResponse
import com.squareup.moshi.Json

class SignUpResponse : BaseApiResponse() {
    @field: Json(name = "data")
    var data: Data? = null

    class Data {
        @field: Json(name = "user")
        var user: UserModel? = null

        @field: Json(name = "token")
        var token: String? = null
    }
}