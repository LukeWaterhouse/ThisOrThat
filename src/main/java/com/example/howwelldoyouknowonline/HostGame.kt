package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.GameData
import com.example.howwelldoyouknow.MainActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.choose_player_colour.*
import kotlinx.android.synthetic.main.host_game.*
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.schedule

class HostGame:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_game)
        val ref = FirebaseDatabase.getInstance().getReference("newGame")
        val mainHandler = Handler()
        println("START")

        PlayerInfo.player.stats()

        fun starterror() {
            startActivity(Intent(this, Error::class.java))
        }


        val Tools = FirebaseTools()

        val colours: Array<String> =
            arrayOf("red", "green", "blue", "yellow", "orange", "purple", "silver", "pink", "lime")
        var counter = 0


        // If Host, sets up game
        if (PlayerInfo.player.host) {
            var Pin = (1111111..9999999).random()
            Tools.checkPin(Pin)
            OnlineGameInfo.onlineGame.Pin = Pin.toString()
            gamePin.text = OnlineGameInfo.onlineGame.Pin
            ref.child(OnlineGameInfo.onlineGame.Pin).setValue(OnlineGameInfo.onlineGame)
            println("AFTER SETUP")
            PlayerInfo.player.stats()
            println("PIN: ${OnlineGameInfo.onlineGame.Pin}")
        }


        ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                println(OnlineGameInfo.onlineGame)
                println("CHANGE")
            }

        })

        if (!PlayerInfo.player.host){

            ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).setValue(PlayerInfo.player)

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



        var runnable = object :Runnable {

            override fun run() {
                println("BEFOREPOSTDELAYED PIN:${OnlineGameInfo.onlineGame.Pin}")
                mainHandler.postDelayed(this, 2000)

                println("DURING")
                println("PIN:${OnlineGameInfo.onlineGame.Pin}")

                PlayerInfo.player.stats()
                //Tools.updatePlayers()

                if (!PlayerInfo.player.host) {
                    gamePin.text = OnlineGameInfo.onlineGame.Pin
                }
                counter = 0
                OnlineGameInfo.onlineGame.players.forEach {

                    println(it.key)
                    println(it.value)
                    println("PIN:${OnlineGameInfo.onlineGame.Pin}")

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
                    println("AFTERLOOP")
                    println("PIN:${OnlineGameInfo.onlineGame.Pin}")

                    counter += 1


                }


            }
        }

        println("BEFORERUN PIN:${OnlineGameInfo.onlineGame.Pin}")

        runnable.run()

        println("AFTERRUN PIN:${OnlineGameInfo.onlineGame.Pin}")





            //Sets arguments for cancel button
            cancel.setOnClickListener {
                mainHandler.removeCallbacks(runnable)
                if (!PlayerInfo.player.host) {

                    Tools.removePlayer()
                }

                if (PlayerInfo.player.host) {
                    println("PINNO" + OnlineGameInfo.onlineGame.Pin)
                    ref.child(OnlineGameInfo.onlineGame.Pin).setValue(null)
                }
                Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
            }

        println("AFTERBUTTON PIN:${OnlineGameInfo.onlineGame.Pin}")





        }

    }


