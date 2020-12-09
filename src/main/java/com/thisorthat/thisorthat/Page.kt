package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.page.*

class Page:AppCompatActivity() {

    override fun onBackPressed() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page)


        score.text = "Score: "+ GameData.game.myScore.toString()
        rounds.text = "ROUND: "+ GameData.game.Round.toString()

        begin.setOnClickListener {
            if (GameData.game.currentTurn== GameData.game.yourTurn){
                startActivity(Intent(this, YourTurn1:: class.java))
            }
            else{
                startActivity(Intent(this, Guess1::class.java))
            }
        }

    }
}