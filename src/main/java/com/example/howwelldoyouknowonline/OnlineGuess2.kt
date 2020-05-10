package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_guess1.*
import kotlinx.android.synthetic.main.online_guess2.*

class OnlineGuess2:AppCompatActivity() {
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_guess2)

        fun reveal(){
            startActivity(Intent(this, OnlineGuess3::class.java))
        }
        peopleGuessed2.text = (OnlineGameInfo.onlineGame.peopleGuessed.toString()+"/"+((OnlineGameInfo.onlineGame.noPlayers-1).toString()))


        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
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
    }

}