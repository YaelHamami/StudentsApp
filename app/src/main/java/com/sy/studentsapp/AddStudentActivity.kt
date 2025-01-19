package com.sy.studentsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sy.studentsapp.model.Model
import com.sy.studentsapp.model.Student

class AddStudentActivity : AppCompatActivity() {
    private var students: List<Student>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton: Button = findViewById(R.id.save_button)
        val cancelButton: Button = findViewById(R.id.cancel_button)

        val nameEditText: EditText = findViewById(R.id.add_student_activity_name_edit_text)
        val idEditText: EditText = findViewById(R.id.add_student_activity_id_edit_text)
        val phoneEditText: EditText = findViewById(R.id.add_student_activity_phone_edit_text)
        val addressEditText: EditText = findViewById(R.id.add_student_activity_address_edit_text)

        val checkedCheckbox: CheckBox = findViewById(R.id.add_student_activity_checked_check_box)

        cancelButton.setOnClickListener{
            finish()
        }

        saveButton.setOnClickListener{
            val student = Student(
                id = idEditText?.text?.toString() ?: "",
                name = nameEditText?.text?.toString() ?: "",
                address = addressEditText?.text?.toString() ?: "",
                phone = phoneEditText?.text?.toString() ?: "",
                isChecked = checkedCheckbox?.isChecked ?: false
            )

            Model.shared.add(student) {
                finish()
            }
        }

        Model.shared.getAllStudents {
            this.students = it
        }
    }
}