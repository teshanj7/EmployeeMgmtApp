package com.example.firebasekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.firebasekotlin.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val firebase  : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertData = findViewById(R.id.btnInsert)
        btnFetchData = findViewById(R.id.btnFetch)

        btnInsertData.setOnClickListener{

            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }


    }
}