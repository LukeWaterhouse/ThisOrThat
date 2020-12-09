package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.begin_game.*

class BeginGame: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.begin_game)
        GameData.stats()



        begin_game.setOnClickListener {

            GameData.questions.shuffle()

            if(GameData.game.currentTurn== GameData.game.yourTurn){
                startActivity(Intent(this, YourTurn1:: class.java))
            }

            else{
                startActivity(Intent(this, Guess1::class.java))
            }

        }

    }
}