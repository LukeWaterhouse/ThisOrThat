package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.your_turn3.*

class YourTurn3: AppCompatActivity() {

    override fun onBackPressed() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.your_turn3)

        score5.text = "Score: " + GameData.game.myScore



        Answer.setText(GameData.game.currentChoice)

        Turnnext.setOnClickListener {
            GameData.game.currentTurn +=1
            println("TURN3")
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
            else if (GameData.game.currentTurn== GameData.game.yourTurn){
                startActivity(Intent(this, YourTurn1:: class.java))
            }

            else{
                startActivity(Intent(this, Guess1::class.java))
            }
        }

    }
}