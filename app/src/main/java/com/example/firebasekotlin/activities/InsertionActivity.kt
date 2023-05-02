package com.example.firebasekotlin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.firebasekotlin.models.EmployeeModel
import com.example.firebasekotlin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText

    private lateinit var btnSaveData: Button
    //database
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.edtName)
        etEmpAge = findViewById(R.id.edtAge)
        etEmpSalary = findViewById(R.id.edtSalary)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val empName = etEmpName.text.toString()
        val empAge = etEmpAge.text.toString()
        val empSalary = etEmpSalary.text.toString()

        //validations
        if(empName.isEmpty()){
            etEmpName.error = "Please enter employee name!"
        }
        if(empAge.isEmpty()){
            etEmpAge.error = "Please enter employee age!"
        }
        if(empSalary.isEmpty()){
            etEmpSalary.error = "Please enter employee salary!"
        }

        val empID = dbRef.push().key!! //null check + creating unique id for record

        val employee = EmployeeModel(empID, empName, empAge, empSalary)

        dbRef.child(empID).setValue(employee).addOnCompleteListener{
            Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_LONG).show()

            etEmpName.text.clear()
            etEmpAge.text.clear()
            etEmpSalary.text.clear()

        }.addOnFailureListener{ err->
            Toast.makeText(this, "Error! ${err.message}", Toast.LENGTH_LONG).show()

        }
    }
}