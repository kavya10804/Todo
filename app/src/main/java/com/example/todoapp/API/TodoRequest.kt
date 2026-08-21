package com.example.todoapp.API

data class TodoRequest(
    val title: String,
    val completed: Boolean,
    val userId: Int
)