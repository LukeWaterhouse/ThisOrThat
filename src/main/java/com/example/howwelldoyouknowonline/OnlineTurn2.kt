package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_turntwo.*

class OnlineTurn2: AppCompatActivity() {
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turntwo)

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
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!

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
            ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Reveal")
            startActivity(Intent(this, OnlineTurn3::class.java))

        }



    }

    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
    }
}