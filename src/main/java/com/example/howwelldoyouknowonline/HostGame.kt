package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.MainActivity
import com.example.howwelldoyouknow.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.choose_player_colour.*
import kotlinx.android.synthetic.main.host_game.*
import kotlin.concurrent.fixedRateTimer

class HostGame:AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_game)

        //initialise Pin
        var Pin: Int = 0

        //Ref for Firebase Games
        val ref = FirebaseDatabase.getInstance().getReference("newGame")

        //Check rounds function
        fun checkPin(pin: Int) {


            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    println("")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild("$pin")) {
                        Pin = (1111111..9999999).random()
                        checkPin(Pin)
                    } else {
                        OnlineGameInfo.onlineGame.Pin = Pin
                        println(OnlineGameInfo.onlineGame.Pin)
                        return
                    }
                }
            })


        }

        //adds player to Hashmap
        OnlineGameInfo.onlineGame.players[PlayerInfo.player.name] = PlayerInfo.player

        // If Host sets Red player to host name and check Pin
        if(PlayerInfo.player.host){

            Pin = (1111111..9999999).random()
            checkPin(Pin)
            OnlineGameInfo.onlineGame.Pin = Pin
            gamePin.text = Pin.toString()

            red2.visibility = View.INVISIBLE
            green2.visibility = View.INVISIBLE
            blue2.visibility = View.INVISIBLE
            yellow2.visibility = View.INVISIBLE
            orange2.visibility = View.INVISIBLE
            purple2.visibility = View.INVISIBLE
            silver2.visibility = View.INVISIBLE
            pink2.visibility = View.INVISIBLE
            lime2.visibility = View.INVISIBLE

            red2.text = PlayerInfo.player.name
            red2.visibility = View.VISIBLE
            val gameid: String? = ref.push().key
            if (gameid != null) {
                OnlineGameInfo.onlineGame.pushKey = gameid

            }


            ref.child(gameid.toString()).setValue(OnlineGameInfo.onlineGame).addOnCompleteListener {
                Toast.makeText(applicationContext, "New Game Created!", Toast.LENGTH_LONG).show()
            }


        }








            ref.child(OnlineGameInfo.onlineGame.pushKey).child("players").addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError){
                    println("")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var map:MutableMap<String,Player> = snapshot.getValue() as MutableMap<String, Player>
                        OnlineGameInfo.onlineGame.players = map
                        println("${OnlineGameInfo.onlineGame.players}")
                    }
                }

        })



        cancel.setOnClickListener {
            var dGame = FirebaseDatabase.getInstance().getReference("newGame").child(OnlineGameInfo.onlineGame.pushKey)
            dGame.removeValue()
            Toast.makeText(applicationContext, "Deleted!", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,MainActivity::class.java))
        }

        }
    }
