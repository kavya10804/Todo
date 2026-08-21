package com.example.todoapp.data

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodo()

    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun update(todo: Todo) {
        todoDao.update(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDao.delete(todo)
    }

    suspend fun deleteAll() {
    }
}