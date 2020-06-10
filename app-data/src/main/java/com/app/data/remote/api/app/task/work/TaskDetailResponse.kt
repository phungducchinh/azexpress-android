package com.app.data.remote.api.app.task.work

import com.app.data.model.TaskModel
import com.app.data.model.UserModel
import com.app.data.remote.api.BaseApiResponse
import com.squareup.moshi.Json

class TaskDetailResponse : BaseApiResponse() {

    @field: Json(name = "data")
    var data: TaskModel? = null
}