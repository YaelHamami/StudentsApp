package com.sy.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sy.studentsapp.model.Student

class StudentDetailsActivity : AppCompatActivity() {

    private val studentEditResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val isDeleted = result.data?.getBooleanExtra("isDeleted", false) ?: false
                if (isDeleted) {
                    // Handle the deletion (finish details activity or perform any other necessary action)
                    finish()  // Finish the activity if the student was deleted
                } else {
                    val updatedStudent = result.data?.getSerializableExtra("updatedStudent") as? Student
                    updatedStudent?.let {
                        updateStudentDetails(it) // Update the UI with the new student details
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)

        // Set up the window insets listener
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.details_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve the Student object passed from the previous activity
        val student: Student? = intent.getSerializableExtra("student") as? Student
        student?.let {
            updateStudentDetails(it)
        }
    }

    // Method to update the UI with the updated student data and reassign listeners
    private fun updateStudentDetails(student: Student) {
        // Initialize the UI elements
        val nameText: TextView = findViewById(R.id.student_row_name_value_text_view)
        val idText: TextView = findViewById(R.id.student_row_id_value_text_view)
        val phoneText: TextView = findViewById(R.id.student_row_phone_value_text_view)
        val addressText: TextView = findViewById(R.id.student_row_address_value_text_view)
        val checkBox: CheckBox = findViewById(R.id.student_row_check_box_value)
        val backButton: ImageButton = findViewById(R.id.back_button)
        val editButton: Button = findViewById(R.id.edit_button)

        // Update the UI with the new student data
        nameText.text = student.name
        idText.text = student.id.toString()
        phoneText.text = student.phone
        addressText.text = student.address
        checkBox.isChecked = student.isChecked

        // Set the back button action
        backButton.setOnClickListener {
            finish()  // Finish the activity and go back
        }

        // Set the edit button action
        editButton.setOnClickListener {
            val intent = Intent(this, StudentEditActivity::class.java)
            intent.putExtra("student", student)  // Pass the entire student object
            studentEditResultLauncher.launch(intent)
        }
    }
}

