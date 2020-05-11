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
import kotlinx.android.synthetic.main.online_guess1.*

class OnlineGuess1:AppCompatActivity() {

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_guess1)
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score6.text = scoreText
        nextYourTurn2.visibility = View.GONE
        var myChoice:String = ""
        println(OnlineGameInfo.onlineGame.currentPlayer)
        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                println(OnlineGameInfo.onlineGame.currentPlayer)
                if (OnlineGameInfo.onlineGame.guessed=="yes"){
                    nextYourTurn2.visibility = View.VISIBLE
                }
                var choose:String = "What would "+ OnlineGameInfo.onlineGame.currentPlayer + " choose?"

                textView23.text = choose

                option1.text = OnlineGameInfo.onlineGame.options["Option1"]
                option2.text = OnlineGameInfo.onlineGame.options["Option2"]
            }
        })
        ref.child(OnlineGameInfo.onlineGame.Pin).child("trigger").setValue("Trigger1")

        option1.setOnClickListener {
            myChoice = option1.text.toString()
            option1.setBackgroundResource(R.drawable.start_button_selected)
            option2.setBackgroundResource(R.drawable.start_button)
        }

        option2.setOnClickListener {
            myChoice = option2.text.toString()
            option2.setBackgroundResource(R.drawable.start_button_selected)
            option1.setBackgroundResource(R.drawable.start_button)
        }

        nextYourTurn2.setOnClickListener {
            ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).child("currentGuess").setValue(myChoice)
            ref.child(OnlineGameInfo.onlineGame.Pin).child("peopleGuessed").setValue(OnlineGameInfo.onlineGame.peopleGuessed+1)
            startActivity(Intent(this, OnlineGuess2::class.java))
        }



    }


    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
    }
}