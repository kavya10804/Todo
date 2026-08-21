package com.example.todoapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.API.RetrofitInstance
import com.example.todoapp.R
import kotlinx.coroutines.launch
import com.example.todoapp.API.TodoRequest

class RetrofitDemoFragment : Fragment(R.layout.fragment_retrofit_demo) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val btnGet = view.findViewById<Button>(R.id.btnGet)
        val btnPost = view.findViewById<Button>(R.id.btnPost)
        val btnPut = view.findViewById<Button>(R.id.btnPut)
        val btnPatch = view.findViewById<Button>(R.id.btnPatch)
        val btnDelete = view.findViewById<Button>(R.id.btnDelete)

        val tvResult = view.findViewById<TextView>(R.id.tvResult)


        btnGet.setOnClickListener {
            getTodos(tvResult)
        }


        btnPost.setOnClickListener {
            postTodo(tvResult)
        }


        btnPut.setOnClickListener {
            putTodo(tvResult)
        }


        btnPatch.setOnClickListener {
            patchTodo(tvResult)
        }


        btnDelete.setOnClickListener {
            deleteTodo(tvResult)
        }
    }

    private fun getTodos(tvResult: TextView) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val todos = RetrofitInstance.api.getTodos()

                tvResult.text = todos.take(10).joinToString("\n\n") {
                    """
                    ID: ${it.id}
                    User ID: ${it.userId}
                    Title: ${it.title}
                    Completed: ${it.completed}
                    """.trimIndent()
                }

                Toast.makeText(
                    requireContext(),
                    "GET: ${todos.size} items fetched",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                tvResult.text = "GET Error: ${e.message}"

            }
        }
    }

    private fun postTodo(tvResult: TextView) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val request = TodoRequest(
                    userId = 1,
                    title = "New Todo",
                    completed = false
                )

                val response = RetrofitInstance.api.createTodo(request)

                tvResult.text = """
                    POST SUCCESS

                    ID: ${response.id}
                    User ID: ${response.userId}
                    Title: ${response.title}
                    Completed: ${response.completed}
                """.trimIndent()

                Toast.makeText(
                    requireContext(),
                    "POST successful",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                tvResult.text = "POST Error: ${e.message}"

            }
        }
    }

    private fun putTodo(tvResult: TextView) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val request = TodoRequest(
                    userId = 1,
                    title = "Updated Todo",
                    completed = true
                )

                val response = RetrofitInstance.api.updateTodo(
                    1,
                    request
                )

                tvResult.text = """
                    PUT SUCCESS

                    ID: ${response.id}
                    User ID: ${response.userId}
                    Title: ${response.title}
                    Completed: ${response.completed}
                """.trimIndent()

                Toast.makeText(
                    requireContext(),
                    "PUT successful",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                tvResult.text = "PUT Error: ${e.message}"

            }
        }
    }

    private fun patchTodo(tvResult: TextView) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val request = TodoRequest(
                    userId = 1,
                    title = "Patched Todo",
                    completed = true
                )

                val response = RetrofitInstance.api.patchTodo(
                    1,
                    request
                )

                tvResult.text = """
                    PATCH SUCCESS

                    ID: ${response.id}
                    Title: ${response.title}
                    Completed: ${response.completed}
                """.trimIndent()

                Toast.makeText(
                    requireContext(),
                    "PATCH successful",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                tvResult.text = "PATCH Error: ${e.message}"

            }
        }
    }

    private fun deleteTodo(tvResult: TextView) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val response = RetrofitInstance.api.deleteTodo(1)

                tvResult.text = """
                    DELETE SUCCESS

                    HTTP Response Code:
                    ${response.code()}

                    Message:
                    Todo deleted successfully
                """.trimIndent()

                Toast.makeText(
                    requireContext(),
                    "DELETE successful",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                tvResult.text = "DELETE Error: ${e.message}"

            }
        }
    }
}