package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.GameData
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_turnone.*

class OnlineTurn1: AppCompatActivity() {
    override fun onBackPressed() {

    }

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var leaveCheck:Boolean = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_turnone)

        fun abort(){
            leaveCheck = true
            val text = "Someone left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        //Sets the score to the current score in top right
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score8.text = scoreText

        //Initially sets the next button to invisible till chosen an option
        nextYourTurn.visibility = View.GONE

        //Sets Turn one player to the current player in database
        ref.child(OnlineGameInfo.onlineGame.Pin).child("currentPlayer").setValue(
            PlayerInfo.player.name)



        //Sorts out the options choices and pushes them to database
        var choice:String = ""
        var tab:Int = (OnlineGameInfo.onlineGame.currentRound-1) * OnlineGameInfo.onlineGame.players.size
        var option1:String = GameData.questions[tab+ OnlineGameInfo.onlineGame.currentTurn][0]
        var option2:String = GameData.questions[tab+ OnlineGameInfo.onlineGame.currentTurn][1]
        optionOne.text = option1
        optionTwo.text = option2
        ref.child(OnlineGameInfo.onlineGame.Pin).child("options").child("Option1").setValue(option1)
        ref.child(OnlineGameInfo.onlineGame.Pin).child("options").child("Option2").setValue(option2)


        //Sets Option one as the choice
        optionOne.setOnClickListener {
            choice = optionOne.text.toString()
            optionOne.setBackgroundResource(R.drawable.start_button_selected)
            optionTwo.setBackgroundResource(R.drawable.start_button)
            nextYourTurn.visibility = View.VISIBLE
        }


        //Sets Option two as the choice
        optionTwo.setOnClickListener {
            choice = optionTwo.text.toString()
            optionTwo.setBackgroundResource(R.drawable.start_button_selected)
            optionOne.setBackgroundResource(R.drawable.start_button)
            nextYourTurn.visibility = View.VISIBLE

        }



        //Listens for database changes
        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                //Pulls the online game
                OnlineGameInfo.onlineGame = p0.getValue(
                    OnlineGame::class.java)!!

                if (OnlineGameInfo.onlineGame.trigger=="abort"){
                    abort()
                }
            }
        })



        //On next pushes the correct option and guessed to "yes"
        nextYourTurn.setOnClickListener {
            leaveCheck = true
            ref.child(OnlineGameInfo.onlineGame.Pin).child("correctOption").setValue(choice)
            ref.child(OnlineGameInfo.onlineGame.Pin).child("guessed").setValue("yes")
            startActivity(Intent(this, OnlineTurn2::class.java))

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