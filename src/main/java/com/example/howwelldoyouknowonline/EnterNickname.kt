package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import kotlinx.android.synthetic.main.nickname.*

class EnterNickname: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nickname)

        accept2.setOnClickListener {
            PlayerInfo.player.name = name.text.toString()
            println("${PlayerInfo.player.host}")

            startActivity(Intent(this, HostGame::class.java))
        }
    }
}