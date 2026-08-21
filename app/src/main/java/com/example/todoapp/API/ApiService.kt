package com.example.todoapp.API

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("todos")
    suspend fun getTodos(): List<TodoResponse>

    @POST("todos")
    suspend fun createTodo(
        @Body todo: TodoRequest
    ): TodoResponse

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: TodoRequest
    ): TodoResponse

    @PATCH("todos/{id}")
    suspend fun patchTodo(
        @Path("id") id: Int,
        @Body todo: TodoRequest
    ): TodoResponse

    @DELETE("todos/{id}")
    suspend fun deleteTodo(
        @Path("id") id: Int
    ): Response<Unit>
}