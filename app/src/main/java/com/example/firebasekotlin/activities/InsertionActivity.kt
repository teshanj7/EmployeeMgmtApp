package com.example.firebasekotlin.activities

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firebasekotlin.models.EmployeeModel
import com.example.firebasekotlin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class InsertionActivity : AppCompatActivity() {

    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText

    private lateinit var btnSaveData: Button
    //database
    private lateinit var dbRef: DatabaseReference

    private companion object{
        private const val CHANNEL_ID = "channel01"
    }

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
            showNotification()
        }
    }

    private fun showNotification() {

        createNotificationChannel()

        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()

        val mainIntent = Intent(this, FetchingActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val mainPendingIntent = PendingIntent.getActivity(this, 1,mainIntent,PendingIntent.FLAG_IMMUTABLE)


        val notificationBuilder = NotificationCompat.Builder(this, "$CHANNEL_ID")

        notificationBuilder.setSmallIcon(R.drawable.ic_notification)
        notificationBuilder.setContentTitle("Hello World")
        notificationBuilder.setContentText("This is the description of the notification!")
        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setContentIntent(mainPendingIntent)


        val notificationManagerCompat = NotificationManagerCompat.from(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationManagerCompat.notify(notificationId, notificationBuilder.build())

    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name: CharSequence = "MyNotification"
            val description = "My notification channel description"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)

            notificationChannel.description = description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
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