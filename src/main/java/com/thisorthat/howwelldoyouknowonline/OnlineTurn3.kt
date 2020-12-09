package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_turnthree.*

class OnlineTurn3:AppCompatActivity() {

    override fun onBackPressed() {

    }
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    var leaveCheck:Boolean = false
    lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turnthree)
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score10.text = scoreText

        fun abort(){
            leaveCheck = true
            val text = "Someone left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }


        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                //Pulls the game
                OnlineGameInfo.onlineGame = p0.getValue(
                    OnlineGame::class.java)!!
                if (OnlineGameInfo.onlineGame.trigger=="abort"){
                    abort()
                }
            }
        })

        nextYourTurn4.setOnClickListener {

            leaveCheck = true

            ref.child(OnlineGameInfo.onlineGame.Pin).child("peopleGuessed").setValue(0)


            if (OnlineGameInfo.onlineGame.currentTurn== OnlineGameInfo.onlineGame.noPlayers){

                var rounds:Int = OnlineGameInfo.onlineGame.currentRound +1
                println(rounds)
                println(OnlineGameInfo.onlineGame.rounds)
                if (rounds> OnlineGameInfo.onlineGame.rounds){
                    println("YES")
                    ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("End")
                    startActivity(Intent(this, OnlineEnd::class.java))


                }
                else
                {
                    ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Round")
                    ref.child(OnlineGameInfo.onlineGame.Pin).child("currentRound").setValue(rounds)
                    ref.child(OnlineGameInfo.onlineGame.Pin).child("currentTurn").setValue(1)
                    startActivity(Intent(this, OnlineRound::class.java))
                }


            }
            else{
                ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Next")
                var turn:Int = OnlineGameInfo.onlineGame.currentTurn + 1
                ref.child(OnlineGameInfo.onlineGame.Pin).child("currentTurn").setValue(turn)
                startActivity(Intent(this, OnlineGuess1::class.java))

            }
        }
    }

    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
        if (!leaveCheck){
            ref.child(OnlineGameInfo.onlineGame.Pin).child("trigger").setValue("abort")
            ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
            val text = "You left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        "RESUMING HERE"
    }


}