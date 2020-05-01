package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import kotlinx.android.synthetic.main.host_settings.*
import kotlin.math.min

class HostSettings:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_settings)

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
    }


}