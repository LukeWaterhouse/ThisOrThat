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

        nextYourTurn4.setOnClickListener {

            if (OnlineGameInfo.onlineGame.currentTurn==OnlineGameInfo.onlineGame.noPlayers){
                var rounds:Int = OnlineGameInfo.onlineGame.currentRound +1
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentRound").setValue(rounds)
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentTurn").setValue(1)

            }
            else{
                var turn:Int = OnlineGameInfo.onlineGame.currentTurn + 1
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentTurn").setValue(turn)

            }
            ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Next")
            ref.child(OnlineGameInfo.onlineGame.Pin).child("peopleGuessed").setValue(0)

            startActivity(Intent(this, OnlineGuess1::class.java))

        }
    }


}