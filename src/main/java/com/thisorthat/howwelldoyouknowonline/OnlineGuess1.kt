package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.online_guess1.*

class OnlineGuess1:AppCompatActivity() {

    override fun onBackPressed() {

    }

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var leaveCheck:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_guess1)
        var selected = false
        leaveCheck = false
        var nextpress = false


        fun abort(){
            leaveCheck = true
            val text = "Someone left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score6.text = scoreText
        nextYourTurn2.visibility = View.INVISIBLE
        var myChoice:String = ""
        println(OnlineGameInfo.onlineGame.currentPlayer)
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
                println(OnlineGameInfo.onlineGame.currentPlayer)
                if (OnlineGameInfo.onlineGame.guessed=="yes"){
                    nextpress = true
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
            selected = true
            myChoice = option1.text.toString()
            option1.setBackgroundResource(R.drawable.start_button_selected)
            option2.setBackgroundResource(R.drawable.start_button)
        }

        option2.setOnClickListener {
            selected = true
            myChoice = option2.text.toString()
            option2.setBackgroundResource(R.drawable.start_button_selected)
            option1.setBackgroundResource(R.drawable.start_button)
        }

        nextYourTurn2.setOnClickListener {

            if(!selected){
                val text = "Make a selection!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

            else{

                if (nextpress){

                    leaveCheck = true
                    ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(
                        PlayerInfo.player.name).child("currentGuess").setValue(myChoice)

                    ref.child(OnlineGameInfo.onlineGame.Pin).child("peopleGuessed").runTransaction(object: Transaction.Handler{
                        override fun onComplete(p0: DatabaseError?, p1: Boolean, p2: DataSnapshot?) {
                            println("Done")
                        }

                        override fun doTransaction(p0: MutableData): Transaction.Result {
                            val p = p0.getValue(Long::class.java)
                            if (p != null) {
                                var x = p.toInt()
                                p0.value = x +1
                            }
                            return Transaction.success(p0)
                        }

                    })
                    startActivity(Intent(this, OnlineGuess2::class.java))


                }

            }



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