package com.thisorthat.howwelldoyouknowonline

import android.content .Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.GameData
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.host_game.*

class HostGame:AppCompatActivity() {


    //start condition is false
    var start = false

    var hostQuit = false


    //Overrides BackPressed
    override fun onBackPressed() {

    }


    //Creates ref and event listener
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventlistener:ValueEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_game)



        //shuffles questions
        GameData.questions.shuffle()

        //If not host cannot begin game
        if(!PlayerInfo.player.host){
            HostNext.visibility = View.GONE
        }



        //Function starts Turn 1
        fun turn(){
            hostQuit = true
            start=true
            startActivity(Intent(this, OnlineTurn1::class.java))
        }

        //Function starts Guess 1
        fun guess(){
            hostQuit = true
            start = true
            startActivity(Intent(this, OnlineGuess1::class.java))
        }

        //Function goes back to menu
        fun menu(){
            hostQuit = true
            val text = "You ended the game"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        //Function goes to Error
        fun error(){
            hostQuit = true
            val text = "Host cancelled the game"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        //Colour list variable
        val colours: Array<String> = arrayOf("red", "green", "blue", "yellow", "orange", "purple", "silver", "pink", "lime")



        // If Host, sets up game:
        if (PlayerInfo.player.host) {
            var Pin = (1111111..9999999).random()
            OnlineGameInfo.onlineGame.Pin = Pin.toString()
            gamePin.text = OnlineGameInfo.onlineGame.Pin
            ref.child(OnlineGameInfo.onlineGame.Pin).setValue(
                OnlineGameInfo.onlineGame
            )
            PlayerInfo.player.stats()

        }

        //If not host adds player to database list and sets gamePin text
        if (!PlayerInfo.player.host){
            ref.child(OnlineGameInfo.onlineGame.Pin).child("players/${PlayerInfo.player.name}").setValue(
                PlayerInfo.player
            )
            gamePin.text = OnlineGameInfo.onlineGame.Pin
        }


        //Initially makes all Names invisible
        red2.visibility = View.INVISIBLE
        green2.visibility = View.INVISIBLE
        blue2.visibility = View.INVISIBLE
        yellow2.visibility = View.INVISIBLE
        orange2.visibility = View.INVISIBLE
        purple2.visibility = View.INVISIBLE
        silver2.visibility = View.INVISIBLE
        pink2.visibility = View.INVISIBLE
        lime2.visibility = View.INVISIBLE




        //DataChange Listener
        eventlistener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                error()

            }

            override fun onDataChange(p0: DataSnapshot) {


                //if Pin doesn't exist go to menu if host and error if not host
                if (!p0.exists()) {

                    if (PlayerInfo.player.host) {
                        menu()
                    }

                    if (!PlayerInfo.player.host) {
                        error()
                    }

                } else {


                    //Sets local OnlineGame
                    OnlineGameInfo.onlineGame = p0.getValue(
                        OnlineGame::class.java)!!

                    //sets Player Class info
                    PlayerInfo.player = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]!!

                    //Sets start condition to false
                    start = false

                    //If more than one player start condition is true and you are the host
                    if (PlayerInfo.player.host && OnlineGameInfo.onlineGame.players.size > 1) {
                        start = true
                    }

                    //If not host and the host sets gameState to turnOne start turn if playerTurn matches current turn and guess if it doesn't
                    if (!PlayerInfo.player.host && OnlineGameInfo.onlineGame.gameState == "turnOne") {
                        if (PlayerInfo.player.Turn == OnlineGameInfo.onlineGame.currentTurn) {
                            turn()
                        }
                        if (PlayerInfo.player.Turn != OnlineGameInfo.onlineGame.currentTurn) {
                            guess()
                        }
                    }

                    if (OnlineGameInfo.onlineGame.Pin == "111") {
                        error()
                    }
                    red2.visibility = View.INVISIBLE
                    green2.visibility = View.INVISIBLE
                    blue2.visibility = View.INVISIBLE
                    yellow2.visibility = View.INVISIBLE
                    orange2.visibility = View.INVISIBLE
                    purple2.visibility = View.INVISIBLE
                    silver2.visibility = View.INVISIBLE
                    pink2.visibility = View.INVISIBLE
                    lime2.visibility = View.INVISIBLE
                    var counter = 0
                    OnlineGameInfo.onlineGame.players.forEach {


                        var colour = colours[counter]
                        when (colour) {
                            "red" -> {
                                red2.visibility = View.VISIBLE
                                red2.text = it.key
                            }
                            "green" -> {
                                green2.visibility = View.VISIBLE
                                green2.text = it.key
                            }
                            "blue" -> {
                                blue2.visibility = View.VISIBLE
                                blue2.text = it.key
                            }
                            "yellow" -> {
                                yellow2.visibility = View.VISIBLE
                                yellow2.text = it.key
                            }
                            "orange" -> {
                                orange2.visibility = View.VISIBLE
                                orange2.text = it.key
                            }
                            "purple" -> {
                                purple2.visibility = View.VISIBLE
                                purple2.text = it.key
                            }
                            "silver" -> {
                                silver2.visibility = View.VISIBLE
                                silver2.text = it.key
                            }
                            "pink" -> {
                                pink2.visibility = View.VISIBLE
                                pink2.text = it.key
                            }
                            "lime" -> {
                                lime2.visibility = View.VISIBLE
                                lime2.text = it.key
                            }


                        }

                        counter += 1
                    }

                }
            }
        })




        //On cancel listener
        cancel.setOnClickListener {

            hostQuit = true

            //if not host remove the player from the player list
            if(!PlayerInfo.player.host){
                ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(
                    PlayerInfo.player.name).removeValue()
                val text = "You left the game"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }

            //if Host remove the game
            if(PlayerInfo.player.host){
                ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
            }

            //For both cases start the main Activity
            startActivity(Intent(this, MainActivity::class.java))
        }



        //For host begin the game for everyone unless host only person in lobby
        HostNext.setOnClickListener {
            hostQuit = true
            if (start){
                var counter = 1
                for(i in OnlineGameInfo.onlineGame.players){
                    i.value.Turn=counter
                    counter+=1
                }
                ref.child(OnlineGameInfo.onlineGame.Pin).setValue(
                    OnlineGameInfo.onlineGame
                )
                ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("turnOne")
                ref.child(OnlineGameInfo.onlineGame.Pin).child("noPlayers").setValue(
                    OnlineGameInfo.onlineGame.players.size)
                if (OnlineGameInfo.onlineGame.currentTurn== PlayerInfo.player.Turn){
                    startActivity(Intent(this, OnlineTurn1::class.java))
                }

                if (OnlineGameInfo.onlineGame.currentTurn!= PlayerInfo.player.Turn){
                    startActivity(Intent(this, OnlineGuess1::class.java))
                }
            }

            else{
                val text = "Can't play by yourself!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }


            }


        }




    override fun onPause() {
        super.onPause()
        println("HERE"+ OnlineGameInfo.onlineGame.players.size)
        println(start)
        if (!PlayerInfo.player.host && !start){
            startActivity(Intent(this, MainActivity::class.java))

        }

        if (PlayerInfo.player.host && !hostQuit){
            ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
            startActivity(Intent(this, MainActivity::class.java))
            val text = "You left and cancelled the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()


        }
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventlistener)

    }

}











