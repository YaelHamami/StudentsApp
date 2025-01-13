package com.sy.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.os.Build
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_add_student_button)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Example static student data
        studentsList.add(Student(
            id = "S1", name = "John Doe", isChecked = true, address = "TLV"
        ))
        studentsList.add(Student(id = "S2", name = "Jane Smith", isChecked = true, address = "RMG"
        ))

        // Initialize the adapter and set it to the RecyclerView
        studentsAdapter = StudentsAdapter(studentsList) { student ->
            openStudentDetails(student)
        }
        recyclerView.adapter = studentsAdapter

        val addStudentButton: Button = findViewById(R.id.main_activity_add_student_button)
        addStudentButton.setOnClickListener{
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
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
