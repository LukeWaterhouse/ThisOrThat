package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import kotlinx.android.synthetic.main.nickname.*

class EnterNickname: AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nickname)
        val reg = "[a-zA-Z]".toRegex()

        //Accept nickname and proceed
        accept2.setOnClickListener {

            if (!name.text.toString().chars().allMatch(Character::isLetter)){
                val text = "Please don't use special characters"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

            else {


                if (name.text.toString() == "") {
                    val text = "Please Enter a nickname!"
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                } else {
                    PlayerInfo.player.name = name.text.toString()
                    OnlineGameInfo.onlineGame.players[PlayerInfo.player.name] =
                        PlayerInfo.player
                    println("${PlayerInfo.player.host}")

                    //If player is not the host go to enter pin number
                    if (!PlayerInfo.player.host) {
                        startActivity(Intent(this, EnterPin::class.java))

                    }
                    //If not host goes to HostGame
                    else (startActivity(Intent(this, HostGame::class.java)))

                }
            }
        }

        roundscancel2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}