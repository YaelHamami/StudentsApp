package com.sy.studentsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    @PrimaryKey val id: String,
    val name: String,
    val address: String,
    val phone: String,
    var isChecked: Boolean
)
