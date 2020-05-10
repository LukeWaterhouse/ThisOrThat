package com.example.howwelldoyouknowonline

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_guess3.*


class OnlineGuess3:AppCompatActivity() {
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var mediaPlayer:MediaPlayer = MediaPlayer();




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_guess3)
        PlayerInfo.player.currentGuess = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.currentGuess.toString()
        var player = OnlineGameInfo.onlineGame.currentPlayer + " Chose"
        guesswas.text = player
        var chosen = OnlineGameInfo.onlineGame.correctOption
        chose.text = chosen

        if (PlayerInfo.player.currentGuess==OnlineGameInfo.onlineGame.correctOption){
            var points = "Correct! You got a point"

            mediaPlayer = MediaPlayer.create(this, R.raw.correct)

            mediaPlayer.start()
            point.text = points
            ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).child("score").setValue(PlayerInfo.player.score+1)
        }
        if (PlayerInfo.player.currentGuess!=OnlineGameInfo.onlineGame.correctOption){
            var non = "Incorrect!"
            mediaPlayer = MediaPlayer.create(this, R.raw.incorrect)
            mediaPlayer.start()
            point.text= non
        }
        fun turn(){
            startActivity(Intent(this, OnlineTurn1::class.java))
        }

        fun guess(){
            startActivity(Intent(this, OnlineGuess1::class.java))
        }

        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                PlayerInfo.player.currentGuess = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.currentGuess.toString()
                if (OnlineGameInfo.onlineGame.currentTurn==PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    turn()
                }
                if (OnlineGameInfo.onlineGame.currentTurn !=PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    guess()
                }


            }
        })
    }

    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
    }
}