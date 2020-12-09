package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.guess2.*
import kotlinx.android.synthetic.main.guess2.nextYourTurn3

class Guess2:AppCompatActivity() {

    override fun onBackPressed() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guess2)

        score2.text = "Score: " + GameData.game.myScore



        var scoreadd:Int = 0

        myguess.text = GameData.game.myGuess

        var yes:Button = findViewById(R.id.yes)
        var no:Button = findViewById(R.id.no)
        var next:Button = findViewById(R.id.nextYourTurn3)

        next.visibility = View.GONE



        yes.setOnClickListener {
            next.visibility = View.VISIBLE
            yes.setBackgroundResource(R.drawable.start_button_selected)
            no.setBackgroundResource(R.drawable.start_button)
            scoreadd = 1

        }

        no.setOnClickListener {
            next.visibility = View.VISIBLE
            no.setBackgroundResource(R.drawable.start_button_selected)
            yes.setBackgroundResource(R.drawable.start_button)
            scoreadd = 0

        }





        nextYourTurn3.setOnClickListener {
            GameData.game.myScore +=scoreadd
            GameData.game.currentTurn +=1
            println("GUESS2")
            GameData.stats()

            if (GameData.game.currentTurn> GameData.game.players){
                GameData.game.currentTurn = 1
                GameData.game.Round+=1
                if (GameData.game.rounds< GameData.game.Round){
                    startActivity(Intent(this,
                        EndScreen::class.java))
                }
                else{
                    startActivity(Intent(this,
                        Page::class.java))
                }
            }
            else if(GameData.game.currentTurn== GameData.game.yourTurn){
                startActivity(Intent(this, YourTurn1:: class.java))
            }

            else{
                startActivity(Intent(this, Guess1::class.java))
            }
        }
    }

}