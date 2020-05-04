package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        val mainHandler = Handler(Looper.getMainLooper())
        val Tools = FirebaseTools()
        val colours: Array<String> = arrayOf("red", "green", "blue", "yellow", "orange", "purple", "silver", "pink", "lime")
        var counter = 0


        // If Host, sets up game
        if (PlayerInfo.player.host) {
            var Pin = (1111111..9999999).random()
            Tools.checkPin(Pin)
            OnlineGameInfo.onlineGame.Pin = Pin.toString()
            gamePin.text = Pin.toString()
            val ref = FirebaseDatabase.getInstance().getReference("newGame")
            ref.child(Pin.toString()).setValue(OnlineGameInfo.onlineGame).addOnCompleteListener {
                Toast.makeText(applicationContext, "New Game Created!", Toast.LENGTH_LONG).show()
            }
            Tools.pushPlayer()
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


        //Runs every 2 seconds, updates the players joining on the screen
        mainHandler.post(object : Runnable {
            override fun run() {
                Tools.updatePlayers()
                println(OnlineGameInfo.onlineGame.players.keys)
                println("HERE")
                println(OnlineGameInfo.onlineGame.rounds)



                counter = 0
                OnlineGameInfo.onlineGame.players.forEach {

                    println(it.key)
                    println(it.value)

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
                        "orange" ->{
                            orange2.visibility=View.VISIBLE
                            orange2.text = it.key
                        }
                        "purple"->{
                            purple2.visibility = View.VISIBLE
                            purple2.text = it.key
                        }
                        "silver"->{
                            silver2.visibility = View.VISIBLE
                            silver2.text = it.key
                        }
                        "pink"->{
                            pink2.visibility = View.VISIBLE
                            pink2.text=it.key
                        }
                        "lime"->{
                            lime2.visibility=View.VISIBLE
                            lime2.text = it.key
                        }

                    }
                    counter+=1


                }
                mainHandler.postDelayed(this, 4000)


            }


        })








       //Sets arguments for cancel button
        cancel.setOnClickListener {
            var dGame = FirebaseDatabase.getInstance().getReference("newGame").child(OnlineGameInfo.onlineGame.Pin)
            if (!PlayerInfo.player.host){

                OnlineGameInfo.onlineGame.reset()
                Tools.removePlayer()
            }

            if (PlayerInfo.player.host) {
                OnlineGameInfo.onlineGame.reset()
                dGame.removeValue()
            }
            Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }





    }

}

