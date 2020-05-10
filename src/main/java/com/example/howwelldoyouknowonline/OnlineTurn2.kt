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
import kotlinx.android.synthetic.main.guess1.*
import kotlinx.android.synthetic.main.online_turntwo.*

class OnlineTurn2: AppCompatActivity() {
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turntwo)
        ref.child(OnlineGameInfo.onlineGame.Pin).child("guessed").setValue("no")

        guessed2.visibility = View.GONE

        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                peopleGuessed.text = (OnlineGameInfo.onlineGame.peopleGuessed.toString()+"/"+((OnlineGameInfo.onlineGame.noPlayers-1).toString()))
                if ((OnlineGameInfo.onlineGame.peopleGuessed.toString())==((OnlineGameInfo.onlineGame.noPlayers-1).toString())){
                    guessed2.visibility = View.VISIBLE
                }


            }
        })

        guessed2.setOnClickListener {
            ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("Reveal")
            startActivity(Intent(this, OnlineTurn3::class.java))

        }



    }

    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
    }
}