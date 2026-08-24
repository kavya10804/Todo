package com.example.todoapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.data.Todo
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.RetrofitDemoFragment
import com.example.todoapp.ui.TodoAdapter
import com.example.todoapp.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

    private var allTodos = emptyList<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[TodoViewModel::class.java]

        adapter = TodoAdapter(
            onToggleDone = { todo ->
                viewModel.toggleDone(todo)
            },
            onDelete = { todo ->
                viewModel.deleteTodo(todo)
            }
        )

        binding.rvTodos.layoutManager = LinearLayoutManager(this)
        binding.rvTodos.adapter = adapter

        // Search functionality
        binding.etSearch.doOnTextChanged { text, _, _, _ ->

            val query = text.toString().trim()

            val filteredTodos = if (query.isEmpty()) {
                allTodos
            } else {
                allTodos.filter { todo ->
                    todo.title.contains(query, ignoreCase = true)
                }
            }

            adapter.submitList(filteredTodos)
        }

        // Add Todo
        binding.btnAdd.setOnClickListener {

            val text = binding.etTodo.editText?.text.toString()

            if (text.isNotBlank()) {
                viewModel.insertTodo(text)

                binding.etTodo.editText?.text?.clear()
            }
        }

        // Open REST API screen
        binding.btnApi.setOnClickListener {

            binding.todoContainer.visibility = View.GONE
            binding.fragmentContainer.visibility = View.VISIBLE

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    RetrofitDemoFragment()
                )
                .addToBackStack(null)
                .commit()
        }

        // Observe Todo database
        viewModel.todos.observe(this) { todos ->

            allTodos = todos

            val query = binding.etSearch.text.toString().trim()

            val filteredTodos = if (query.isEmpty()) {
                allTodos
            } else {
                allTodos.filter { todo ->
                    todo.title.contains(query, ignoreCase = true)
                }
            }

            adapter.submitList(filteredTodos)
        }
    }
}