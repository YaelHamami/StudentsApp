package com.sy.studentsapp.model

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import com.sy.studentsapp.model.dao.AppLocalDb
import com.sy.studentsapp.model.dao.AppLocalDbRepository
import java.util.concurrent.Executors

typealias StudentsCallback = (List<Student>) -> Unit
typealias EmptyCallback = () -> Unit

//interface GetAllStudentsListener {
//    fun onCompletion(students: List<Student>)
//}
// for single instance so it can be accessed only from the shared val below
class Model private constructor() {
    private val database: AppLocalDbRepository = AppLocalDb.database
    private val executor = Executors.newSingleThreadExecutor() // side thread
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper()) // main thread

    // single tone
    companion object {
        val shared = Model()
    }

    fun getAllStudents(callback: StudentsCallback) {
        executor.execute{
            val students = database.studentDao().getAllStudents()
            mainHandler.post {
                callback(students)
            }
        }
    }

    fun add(student: Student, callback: EmptyCallback) {
        executor.execute{
            database.studentDao().insertStudents(student)
            Log.d("TAG", "Save student ${student.name}")
            mainHandler.post {
                callback()
            }
        }
    }
}