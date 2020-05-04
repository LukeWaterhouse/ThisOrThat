package com.example.howwelldoyouknow

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.howwelldoyouknowonline.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onBackPressed() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Resets the online game stats
        OnlineGameInfo.onlineGame.reset()

        //Starts offline mode
        startGame.setOnClickListener {
            startActivity(Intent(this,Settings::class.java))
        }



        //Sets player as not host and go to enter nickname
        Join.setOnClickListener {
            PlayerInfo.player.host = false
            startActivity(Intent(this, EnterNickname::class.java))
        }

        //Sets player as host and go to host settings
        Host.setOnClickListener {
            PlayerInfo.player.host = true
            startActivity(Intent(this, HostSettings::class.java))
        }

        }


    }

