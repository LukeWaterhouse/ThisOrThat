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
import kotlinx.android.synthetic.main.online_guess2.*

class OnlineGuess2:AppCompatActivity() {

    override fun onBackPressed() {

    }
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var leaveCheck:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_guess2)
        leaveCheck = false
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score7.text = scoreText

        fun reveal(){
            leaveCheck = true
            startActivity(Intent(this, OnlineGuess3::class.java))
        }

        fun abort(){
            leaveCheck = true
            val text = "Someone left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }
        peopleGuessed2.text = (OnlineGameInfo.onlineGame.peopleGuessed.toString()+"/"+((OnlineGameInfo.onlineGame.noPlayers-1).toString()))


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
                peopleGuessed2.text = (OnlineGameInfo.onlineGame.peopleGuessed.toString()+"/"+((OnlineGameInfo.onlineGame.noPlayers-1).toString()))
                if(OnlineGameInfo.onlineGame.gameState=="Reveal"){
                    reveal()
                }

            }
        })

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