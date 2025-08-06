package com.develop.tidytasks.data.remote.api

import com.develop.tidytasks.data.model.Todo

data class TodoResponse(
    val todos: List<Todo>,
    val total: Int,
    val skip: Int,
    val limit: Int
)