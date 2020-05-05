package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.MainActivity
import com.example.howwelldoyouknow.R

class Error: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error)
        val mainHandler = Handler(Looper.getMainLooper())
        fun mainmenu(){
            startActivity(Intent(this, MainActivity::class.java))
        }


        mainHandler.post(object : Runnable {
            override fun run() {

                mainmenu()







                mainHandler.postDelayed(this, 4000)


            }





        })




    }

}
