package com.sy.studentsapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sy.studentsapp.databinding.ActivityStudentEditBinding
import com.sy.studentsapp.model.Model
import com.sy.studentsapp.model.Student

class StudentEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityStudentEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_view)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve the Student object passed from the previous activity
        val student: Student? = intent.getSerializableExtra("student") as? Student

        // Check if student data is available and update the UI accordingly
        student?.let {
            binding.nameEditText.setText(it.name)
            binding.idEditText.setText(it.id)
            binding.phoneEditText.setText(it.phone)
            binding.addressEditText.setText(it.address)
            binding.checkBox.isChecked = it.isChecked
        }

        // Set the cancelButton action
        binding.cancelButton.setOnClickListener {
            finish()  // Finish the activity and go back
        }

        // Set the deleteButton action
        binding.deleteButton.setOnClickListener {
            student?.let {
                Model.shared.delete(it) {
                    // Send delete status to the calling activity before finishing
                    val resultIntent = Intent()
                    resultIntent.putExtra("isDeleted", true)
                    setResult(RESULT_OK, resultIntent)
                    finish()  // Finish the activity after deleting the student
                }
            }
        }

        // Set the saveButton action
        binding.saveButton.setOnClickListener {
            student?.let { oldStudent ->
                val updatedStudent = oldStudent.copy(
                    name = binding.nameEditText.text.toString(),
                    id = binding.idEditText.text.toString(),
                    phone = binding.phoneEditText.text.toString(),
                    address = binding.addressEditText.text.toString(),
                    isChecked = binding.checkBox.isChecked
                )

                if (oldStudent.id != updatedStudent.id) {
                    binding.progressBar.visibility = View.VISIBLE

                    // ID has changed, delete the old record and insert the new one
                    Model.shared.delete(oldStudent) {
                        Model.shared.add(updatedStudent) {
                            // Send updated student back to the details activity
                            val resultIntent = Intent()
                            resultIntent.putExtra("updatedStudent", updatedStudent)
                            setResult(RESULT_OK, resultIntent)
                            finish() // Finish the activity after saving the changes
                        }

                        binding.progressBar.visibility = View.GONE
                    }
                } else {
                    binding.progressBar.visibility = View.VISIBLE

                    // ID has not changed, just update the record
                    Model.shared.edit(updatedStudent) {
                        // Send updated student back to the details activity
                        val resultIntent = Intent()
                        resultIntent.putExtra("updatedStudent", updatedStudent)
                        setResult(RESULT_OK, resultIntent)
                        finish() // Finish the activity after saving the changes

                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

    }
}
