package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.data.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = TodoDatabase.getDatabase(application)
    private val repository = TodoRepository(database.todoDao())

    val todos: LiveData<List<Todo>> = repository.allTodos

    fun insertTodo(title: String) = viewModelScope.launch {
        val todo = Todo(title = title)
        repository.insert(todo)
    }

    fun toggleDone(todo: Todo) = viewModelScope.launch {
        val updatedTodo = todo.copy(isDone = !todo.isDone)
        repository.update(updatedTodo)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        repository.delete(todo)
    }
}