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
import com.sy.studentsapp.databinding.ActivityStudentDetailsBinding
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

    private lateinit var binding: ActivityStudentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        // Set up the window insets listener
        ViewCompat.setOnApplyWindowInsetsListener(binding.detailsView) { v, insets ->
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
        // Update the UI with the new student data
        binding.nameTextView.text = student.name
        binding.idTextView.text = student.id.toString()
        binding.phoneTextView.text = student.phone
        binding.addressTextView.text = student.address
        binding.checkBox.isChecked = student.isChecked

        // Set the back button action
        binding.backButton.setOnClickListener {
            finish()  // Finish the activity and go back
        }

        // Set the edit button action
        binding.editButton.setOnClickListener {
            val intent = Intent(this, StudentEditActivity::class.java)
            intent.putExtra("student", student)  // Pass the entire student object
            studentEditResultLauncher.launch(intent)
        }
    }
}

