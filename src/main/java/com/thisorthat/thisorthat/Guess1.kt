package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.guess1.*

class Guess1:AppCompatActivity() {

    override fun onBackPressed() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guess1)

        score.text = "Score: " + GameData.game.myScore

        var currentColour:String = ""
        currentColour = GameData.colors[(GameData.game.currentTurn)-1]

        textView9.text = "Player $currentColour will tell you the options"






        submit.setOnClickListener {
            var myGuess = guess.text.toString()
            GameData.game.myGuess = myGuess
            startActivity(Intent(this, Guess2::class.java))

        }
    }


}