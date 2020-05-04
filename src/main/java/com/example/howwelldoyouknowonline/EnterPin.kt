package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.GameData
import com.example.howwelldoyouknow.R
import kotlinx.android.synthetic.main.enter_pin.*

class EnterPin:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val Tools = FirebaseTools()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_pin)




        acceptpin.setOnClickListener {

            OnlineGameInfo.onlineGame.Pin = enterpin.text.toString()
            Tools.pullGame()

            Tools.pushPlayer()
            startActivity(Intent(this, HostGame::class.java))
        }
    }
}