package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.GameData
import com.example.howwelldoyouknow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_guess1.*
import kotlinx.android.synthetic.main.online_turnone.*

class OnlineTurn1: AppCompatActivity() {

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turnone)
        nextYourTurn.visibility = View.GONE

        ref.child(OnlineGameInfo.onlineGame.Pin).child("currentPlayer").setValue(PlayerInfo.player.name)

        var choice:String = ""


        var tab:Int = (OnlineGameInfo.onlineGame.currentRound-1) * OnlineGameInfo.onlineGame.players.size
        var option1:String = GameData.questions[tab+OnlineGameInfo.onlineGame.currentTurn][0]
        var option2:String = GameData.questions[tab+OnlineGameInfo.onlineGame.currentTurn][1]

        optionOne.text = option1
        optionTwo.text = option2

        ref.child(OnlineGameInfo.onlineGame.Pin).child("options").child("Option1").setValue(option1)
        ref.child(OnlineGameInfo.onlineGame.Pin).child("options").child("Option2").setValue(option2)


        optionOne.setOnClickListener {
            choice = optionOne.text.toString()
            optionOne.setBackgroundResource(R.drawable.start_button_selected)
            optionTwo.setBackgroundResource(R.drawable.start_button)
            nextYourTurn.visibility = View.VISIBLE
        }

        optionTwo.setOnClickListener {
            choice = optionTwo.text.toString()
            optionTwo.setBackgroundResource(R.drawable.start_button_selected)
            optionOne.setBackgroundResource(R.drawable.start_button)
            nextYourTurn.visibility = View.VISIBLE

        }

        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!


            }
        })


        nextYourTurn.setOnClickListener {

            ref.child(OnlineGameInfo.onlineGame.Pin).child("correctOption").setValue(choice)
            ref.child(OnlineGameInfo.onlineGame.Pin).child("guessed").setValue("yes")
            startActivity(Intent(this, OnlineTurn2::class.java))

        }
    }




    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
    }
}