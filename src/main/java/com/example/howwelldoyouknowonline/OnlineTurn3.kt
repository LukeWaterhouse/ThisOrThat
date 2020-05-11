package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.online_turnthree.*

class OnlineTurn3:AppCompatActivity() {
    val ref = FirebaseDatabase.getInstance().getReference("newGame")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turnthree)
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score10.text = scoreText

        nextYourTurn4.setOnClickListener {

            ref.child(OnlineGameInfo.onlineGame.Pin).child("peopleGuessed").setValue(0)

            if (OnlineGameInfo.onlineGame.currentTurn==OnlineGameInfo.onlineGame.noPlayers){
                ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Round")
                var rounds:Int = OnlineGameInfo.onlineGame.currentRound +1
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentRound").setValue(rounds)
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentTurn").setValue(1)
                startActivity(Intent(this, OnlineRound::class.java))

            }
            else{
                ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Next")
                var turn:Int = OnlineGameInfo.onlineGame.currentTurn + 1
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentTurn").setValue(turn)
                startActivity(Intent(this, OnlineGuess1::class.java))

            }
        }
    }


}