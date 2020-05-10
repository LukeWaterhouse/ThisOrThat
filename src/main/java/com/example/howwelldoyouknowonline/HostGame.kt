package com.example.howwelldoyouknowonline

import android.content .Intent
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

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventlistener:ValueEventListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_game)

        GameData.questions.shuffle()

        if(!PlayerInfo.player.host){
            HostNext.visibility = View.GONE
        }


        fun turn(){
            startActivity(Intent(this, OnlineTurn1::class.java))

        }

        fun guess(){

            startActivity(Intent(this, OnlineGuess1::class.java))
        }

        fun menu(){
            startActivity(Intent(this, MainActivity::class.java))
        }
        fun error(){
            startActivity(Intent(this, Error::class.java))
        }


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
            var newNo:Int = (OnlineGameInfo.onlineGame.noPlayers).toInt() + 1
            ref.child(OnlineGameInfo.onlineGame.Pin).child("noPlayers").setValue(newNo)
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


        eventlistener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                error()

            }

            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(OnlineGame::class.java)!!
                println(OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.Turn)
                PlayerInfo.player = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]!!


                println("MyTurn "+PlayerInfo.player.Turn)
                println("CurrentTurn"+OnlineGameInfo.onlineGame.currentTurn)
                if(!PlayerInfo.player.host && OnlineGameInfo.onlineGame.gameState=="turnOne"){
                    if (PlayerInfo.player.Turn==OnlineGameInfo.onlineGame.currentTurn){
                        turn()
                    }
                    if (PlayerInfo.player.Turn!=OnlineGameInfo.onlineGame.currentTurn){
                        guess()
                    }
                }

                if (!p0.exists()) {

                    if (PlayerInfo.player.host){
                        menu()
                    }

                    if (!PlayerInfo.player.host){
                        error()
                    }




                } else {




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
            if (PlayerInfo.player.host){
                println("In cancel"+ OnlineGameInfo.onlineGame.Pin)
                ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
            }

            if(!PlayerInfo.player.host){
                ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).removeValue()
                var newNo:Int = (OnlineGameInfo.onlineGame.noPlayers).toInt() -1
                var newStr:String = newNo.toString()
                ref.child(OnlineGameInfo.onlineGame.Pin).child("noPlayers").setValue(newStr)
            }



            ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventlistener)
            startActivity(Intent(this, MainActivity::class.java))
        }


        fun next(){
            ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventlistener)

            if(OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.Turn  == OnlineGameInfo.onlineGame.currentTurn){
                startActivity(Intent(this, OnlineTurn1::class.java))
            }
            else{
                //start guess1
            }

        }

        HostNext.setOnClickListener {

            var counter = 1
            for(i in OnlineGameInfo.onlineGame.players){
                i.value.Turn=counter
                counter+=1
            }
            ref.child(OnlineGameInfo.onlineGame.Pin).setValue(OnlineGameInfo.onlineGame)
            ref.child(OnlineGameInfo.onlineGame.Pin).child("gameState").setValue("turnOne")
            ref.child(OnlineGameInfo.onlineGame.Pin).child("noPlayers").setValue(OnlineGameInfo.onlineGame.players.size)
            if (OnlineGameInfo.onlineGame.currentTurn==PlayerInfo.player.Turn){
                startActivity(Intent(this, OnlineTurn1::class.java))
            }

            if (OnlineGameInfo.onlineGame.currentTurn!=PlayerInfo.player.Turn){
                startActivity(Intent(this, OnlineGuess1::class.java))
            }
        }


    }

    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventlistener)

    }

}











