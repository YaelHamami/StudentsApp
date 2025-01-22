package com.sy.studentsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sy.studentsapp.databinding.ActivityAddStudentBinding
import com.sy.studentsapp.databinding.ActivityMainBinding
import com.sy.studentsapp.model.Model
import com.sy.studentsapp.model.Student

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cancelButton.setOnClickListener{
            finish()
        }

        binding.saveButton.setOnClickListener{
            val student = Student(
                id = binding.idEditText.text?.toString() ?: "",
                name = binding.nameEditText.text?.toString() ?: "",
                address = binding.addressEditText.text?.toString() ?: "",
                phone = binding.phoneEditText.text?.toString() ?: "",
                isChecked = binding.checkBox.isChecked
            )

            Model.shared.add(student) {
                finish()
            }
        }
    }
}