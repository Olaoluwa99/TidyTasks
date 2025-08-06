package com.develop.tidytasks.data.remote.api

import com.develop.tidytasks.data.model.Todo
import retrofit2.http.*

/*interface TodoApi {

    @GET("todos/user")
    suspend fun getTodos(): List<Todo>

    @POST("todos/add")
    suspend fun addTodo(
        @Body todo: Todo
    ): Todo

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: Todo
    ): Todo

    @DELETE("todos/{id}")
    suspend fun deleteTodo(
        @Path("id") id: Int
    )
}*/

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): TodoResponse

    @POST("todos/add")
    suspend fun addTodo(
        @Body todo: Todo
    ): Todo

    @PUT("todos/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int,
        @Body todo: Todo
    ): Todo

    @DELETE("todos/{id}")
    suspend fun deleteTodo(
        @Path("id") id: Int
    )
}
