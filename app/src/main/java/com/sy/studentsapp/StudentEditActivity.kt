package com.sy.studentsapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
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

        // Find the UI elements
//        val cancelButton: Button = findViewById(R.id.cancel_button)
//        val deleteButton: Button = findViewById(R.id.delete_button)
//        val saveButton: Button = findViewById(R.id.save_button)
//
//        val nameText: EditText = findViewById(R.id.student_row_name_value_text_view)
//        val idText: EditText = findViewById(R.id.student_row_id_value_text_view)
//        val phoneText: EditText = findViewById(R.id.student_row_phone_value_text_view)
//        val addressText: EditText = findViewById(R.id.student_row_address_value_text_view)
//        val checkBox: CheckBox = findViewById(R.id.student_row_check_box_value)

        // Retrieve the Student object passed from the previous activity
        val student: Student? = intent.getSerializableExtra("student") as? Student

        // Check if student data is available and update the UI accordingly
        student?.let {
            Log.d("StudentEditActivity", "Populating fields with: $it")
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
                    // ID has changed, delete the old record and insert the new one
                    Model.shared.delete(oldStudent) {
                        Model.shared.add(updatedStudent) {
                            // Send updated student back to the details activity
                            val resultIntent = Intent()
                            resultIntent.putExtra("updatedStudent", updatedStudent)
                            setResult(RESULT_OK, resultIntent)
                            finish() // Finish the activity after saving the changes
                        }
                    }
                } else {
                    // ID has not changed, just update the record
                    Model.shared.edit(updatedStudent) {
                        // Send updated student back to the details activity
                        val resultIntent = Intent()
                        resultIntent.putExtra("updatedStudent", updatedStudent)
                        setResult(RESULT_OK, resultIntent)
                        finish() // Finish the activity after saving the changes
                    }
                }
            }
        }

    }
}
