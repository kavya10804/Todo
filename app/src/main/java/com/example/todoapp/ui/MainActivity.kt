package com.example.todoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.ActivityMainBinding
import com.example.todoapp.ui.TodoAdapter
import com.example.todoapp.viewmodel.TodoViewModel
import com.example.todoapp.ui.RetrofitDemoFragment
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TodoViewModel
    private lateinit var adapter: TodoAdapter

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

        binding.btnAdd.setOnClickListener {

            val text = binding.etTodo.editText?.text.toString()

            if (text.isNotBlank()) {
                viewModel.insertTodo(text)

                binding.etTodo.editText?.text?.clear()
            }
        }
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

        viewModel.todos.observe(this) { todos ->
            adapter.submitList(todos)

        }
    }
}