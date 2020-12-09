package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import kotlinx.android.synthetic.main.host_settings.*

class HostSettings:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_settings)
        OnlineGameInfo.onlineGame.rounds = 111
        OnlineGameInfo.onlineGame.options["Option1"] = "blank"
        OnlineGameInfo.onlineGame.options["Option2"] = "blank"



        var rounds: Int = 1
        roundNo.text = rounds.toString()


        plus.setOnClickListener {

            if(rounds<9){
                rounds +=1
                roundNo.text = rounds.toString()
            }

        }

        minus.setOnClickListener {
            if(rounds>1){
                rounds -= 1
                roundNo.text = rounds.toString()
            }
        }


        accept.setOnClickListener {
            OnlineGameInfo.onlineGame.rounds=rounds
            startActivity(Intent(this, EnterNickname::class.java))
        }
        roundscancel.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}