package com.app.data.remote.api.app.task.work

import android.media.Image
import com.squareup.moshi.Json
import java.io.File

class UpdateTaskRequest  {

    @field: Json(name = "image")
    var image: File? = null
}