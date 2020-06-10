package com.app.data.remote.api.app.task

import com.app.data.model.TaskModel
import com.app.data.remote.api.app.task.work.TaskDetailResponse
import com.app.data.remote.api.app.task.work.TaskResponse
import com.app.data.remote.api.app.task.work.UpdateTaskRequest
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface TaskApi {
    @GET("tasks")
    fun getListTask(): Observable<Response<TaskResponse>>

    @GET("tasks/{taskId}")
    fun getTaskDetail(@Path("taskId") taskId: String): Observable<Response<TaskDetailResponse>>

    @Multipart
    @PUT("tasks/{taskId}")
    fun updateTask(
        @Path("taskId") taskId: String?,
        @Part image: MultipartBody.Part
    ): Observable<Response<TaskDetailResponse>>
}