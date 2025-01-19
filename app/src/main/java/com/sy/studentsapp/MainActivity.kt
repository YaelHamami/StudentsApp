package com.sy.studentsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sy.studentsapp.adapter.StudentsAdapter
import com.sy.studentsapp.model.Model
import com.sy.studentsapp.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private var studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_add_student_button)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Fetch students from the model
        Model.shared.getAllStudents { students ->
            Log.d("StudentsList", "Students: $students")
            studentsList.clear()
            studentsList.addAll(students)

            // Initialize the RecyclerView and adapter after data is fetched
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)

            // Initialize the adapter and set it to the RecyclerView
            studentsAdapter = StudentsAdapter(studentsList) { student ->
                openStudentDetails(student)
            }
            recyclerView.adapter = studentsAdapter

            // Notify the adapter that the data has been updated
            studentsAdapter.notifyDataSetChanged()
        }

        val addStudentButton: Button = findViewById(R.id.main_activity_add_student_button)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openStudentDetails(student: Student) {
        val intent = Intent(this, StudentDetailsActivity::class.java)
        intent.putExtra("student", student)  // Pass the entire student object
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        // Refresh the list after returning to this screen
        Model.shared.getAllStudents { students ->
            Log.d("MainActivity1", "Fetched students: $students")
            studentsList.clear()
            studentsList.addAll(students)
            studentsAdapter.notifyDataSetChanged()
            Log.d("MainActivity2", "Length after update: ${studentsAdapter.itemCount}")

        }
    }
}

