package com.develop.tidytasks.data.model

data class Todo(
    val id: Int,
    val todo: String,
    val completed: Boolean,
    val userId: Int
)