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

        fun menu(){
            startActivity(Intent(this, MainActivity::class.java))
        }
        fun error(){
            startActivity(Intent(this, Error::class.java))
        }
        val ref = FirebaseDatabase.getInstance().getReference("newGame")

        val Tools = FirebaseTools()
        val colours: Array<String> = arrayOf("red", "green", "blue", "yellow", "orange", "purple", "silver", "pink", "lime")



        // If Host, sets up game
        if (PlayerInfo.player.host) {
            var Pin = (1111111..9999999).random()
            OnlineGameInfo.onlineGame.Pin = Pin.toString()
            gamePin.text = OnlineGameInfo.onlineGame.Pin
            ref.child(OnlineGameInfo.onlineGame.Pin).setValue(OnlineGameInfo.onlineGame)
            println("AFTER SETUP")
            PlayerInfo.player.stats()
            println("PIN: ${OnlineGameInfo.onlineGame.Pin}")
        }

        if (!PlayerInfo.player.host){
            ref.child(OnlineGameInfo.onlineGame.Pin).child("players/${PlayerInfo.player.name}").setValue(PlayerInfo.player)
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


        ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                error()

            }

            override fun onDataChange(p0: DataSnapshot) {

                if (!p0.exists()) {

                    if (PlayerInfo.player.host){
                        menu()
                    }

                    if (!PlayerInfo.player.host){
                        error()
                    }




                } else {


                    println("Before update" + OnlineGameInfo.onlineGame.Pin)
                    OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                    println("AFter update" + OnlineGameInfo.onlineGame.Pin)
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
                    println("CHANGE")
                    var counter = 0
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
        })

        cancel.setOnClickListener {



            println("HOST?"+ PlayerInfo.player.host)

            if (PlayerInfo.player.host){
                println("In cancel"+ OnlineGameInfo.onlineGame.Pin)
                ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
            }

            if(!PlayerInfo.player.host){
                ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).removeValue()
            }

            startActivity(Intent(this, MainActivity::class.java))
        }

        HostNext.setOnClickListener {
            OnlineGameInfo.onlineGame.noPlayers = OnlineGameInfo.onlineGame.players.size.toString()
        }


    }
}











