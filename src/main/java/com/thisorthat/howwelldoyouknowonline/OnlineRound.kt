package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.end_round.*

class OnlineRound:AppCompatActivity() {
    override fun onBackPressed() {

    }

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var leaveCheck:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_round)
        leaveCheck = false


        var scoreText = "CURRENT SCORE: "+ (PlayerInfo.player.score).toString()
        score12.text = scoreText

        fun turn(){
            leaveCheck=true
            startActivity(Intent(this, OnlineTurn1::class.java))
        }

        fun guess(){
            leaveCheck=true
            startActivity(Intent(this, OnlineGuess1::class.java))
        }
        fun round(){
            startActivity(Intent(this, OnlineRound::class.java))
        }
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
                OnlineGameInfo.onlineGame = p0.getValue(
                    OnlineGame::class.java)!!
                if (OnlineGameInfo.onlineGame.trigger=="abort"){
                    abort()
                }
                var roundstxt = "Round: "+ OnlineGameInfo.onlineGame.currentRound
                rounds2.text = roundstxt
                var newscore:Long = p0.child("players").child(PlayerInfo.player.name).child("score").value as Long
                var newscoreInt:Int = newscore.toInt()
                PlayerInfo.player.score = newscoreInt
                var playerTurn: Long = p0.child("players").child(PlayerInfo.player.name).child("turn").value as Long
                var newplayerTurn = playerTurn.toInt()
                PlayerInfo.player.Turn = newplayerTurn
                PlayerInfo.player.currentGuess = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.currentGuess.toString()


                if (OnlineGameInfo.onlineGame.currentTurn== PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    turn()
                }
                if (OnlineGameInfo.onlineGame.currentTurn != PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    guess()
                }


            }
        })

        hostbegin.visibility = View.INVISIBLE

        if(PlayerInfo.player.host){
            hostbegin.visibility=View.VISIBLE
        }

        hostbegin.setOnClickListener {
            if(hostbegin.visibility==View.VISIBLE){
                ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Next")
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
}