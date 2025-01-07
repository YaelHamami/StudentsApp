package com.sy.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sy.studentsapp.adapter.StudentsAdapter
import com.sy.studentsapp.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Example static student data
        studentsList.add(Student(id = "S1", name = "John Doe", isChecked = true))
        studentsList.add(Student(id = "S2", name = "Jane Smith", isChecked = true))

        // Initialize the adapter and set it to the RecyclerView
        studentsAdapter = StudentsAdapter(studentsList) { student ->
            openStudentDetails(student)
        }
        recyclerView.adapter = studentsAdapter

        // Add New Student button
        val addButton: Button = findViewById(R.id.addStudentButton)
        addButton.setOnClickListener {
            openAddNewStudent()
        }
    }

    private fun openStudentDetails(student: Student) {
//        val intent = Intent(this, StudentDetailsActivity::class.java)
//        intent.putExtra("student_id", student.id)
//        startActivity(intent)
    }

    private fun openAddNewStudent() {
//        val intent = Intent(this, NewStudentActivity::class.java)
//        startActivity(intent)
    }
}
