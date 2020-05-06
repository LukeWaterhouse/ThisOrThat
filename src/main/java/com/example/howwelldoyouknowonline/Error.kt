package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.MainActivity
import com.example.howwelldoyouknow.R
import kotlinx.android.synthetic.main.error.*

class Error: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error)

        menu.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
