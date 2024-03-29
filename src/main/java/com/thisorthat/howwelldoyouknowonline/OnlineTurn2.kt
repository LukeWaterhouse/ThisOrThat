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
import kotlinx.android.synthetic.main.online_turntwo.*

class OnlineTurn2: AppCompatActivity() {

    override fun onBackPressed() {

    }
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var leaveCheck:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turntwo)

        fun abort(){
            leaveCheck = true
            val text = "Someone left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        //sets score in top right
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score9.text = scoreText

        //resets the guessed option to "no"
        ref.child(OnlineGameInfo.onlineGame.Pin).child("guessed").setValue("no")


        //sets revealChoice to GONE
        revealChoice.visibility = View.GONE


        //Adds Database change listener
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

                //Sets the people guessed text
                peopleGuessed.text = (OnlineGameInfo.onlineGame.peopleGuessed.toString()+"/"+((OnlineGameInfo.onlineGame.noPlayers-1).toString()))

                //If the people guessed == number of people have to guess make the revealChoice visible
                if ((OnlineGameInfo.onlineGame.peopleGuessed.toString())==((OnlineGameInfo.onlineGame.noPlayers-1).toString())){
                    revealChoice.visibility = View.VISIBLE
                }


            }
        })


        //On revealing the choice start online turn 3 and set gamestate to reveal
        revealChoice.setOnClickListener {
            leaveCheck = true
            ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Reveal")
            startActivity(Intent(this, OnlineTurn3::class.java))

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