package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.end_round.*
import kotlinx.android.synthetic.main.online_guess3.*
import kotlinx.android.synthetic.main.online_turnone.*

class OnlineRound:AppCompatActivity() {

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_round)


        var scoreText = "CURRENT SCORE: "+ (PlayerInfo.player.score).toString()
        score12.text = scoreText

        fun turn(){
            startActivity(Intent(this, OnlineTurn1::class.java))
        }

        fun guess(){
            startActivity(Intent(this, OnlineGuess1::class.java))
        }
        fun round(){
            startActivity(Intent(this, OnlineRound::class.java))
        }


        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                var roundstxt = "Round: "+OnlineGameInfo.onlineGame.currentRound
                rounds2.text = roundstxt
                var newscore:Long = p0.child("players").child(PlayerInfo.player.name).child("score").value as Long
                var newscoreInt:Int = newscore.toInt()
                PlayerInfo.player.score = newscoreInt
                var playerTurn: Long = p0.child("players").child(PlayerInfo.player.name).child("turn").value as Long
                var newplayerTurn = playerTurn.toInt()
                PlayerInfo.player.Turn = newplayerTurn
                PlayerInfo.player.currentGuess = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.currentGuess.toString()


                if (OnlineGameInfo.onlineGame.currentTurn==PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    turn()
                }
                if (OnlineGameInfo.onlineGame.currentTurn !=PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
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
    }
}