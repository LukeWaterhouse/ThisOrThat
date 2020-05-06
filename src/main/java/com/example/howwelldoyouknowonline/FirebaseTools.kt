package com.example.howwelldoyouknowonline

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseTools {

    val ref = FirebaseDatabase.getInstance().getReference("newGame")


    //Pushes the player to the player list of the game
    fun pushPlayer(){
        val path = FirebaseDatabase.getInstance().getReference("newGame/${OnlineGameInfo.onlineGame.Pin}/players/${PlayerInfo.player.name}")
        path.setValue(PlayerInfo.player)
    }


    //Updates the local rounds and pin number of the game
    fun pullGame(){
        ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame.rounds = p0.child("rounds").getValue(String::class.java).toString()

                println("databefore"+OnlineGameInfo.onlineGame.Pin)
                var thing =p0.child("pin").getValue(String::class.java)
                OnlineGameInfo.onlineGame.Pin = p0.child("pin").getValue(String::class.java).toString()
                println("dataafter"+OnlineGameInfo.onlineGame.Pin)
                println("HERHERHER + ${OnlineGameInfo.onlineGame.Pin}")
            }

        })
    }


    fun pullPin(){
        ref.child(OnlineGameInfo.onlineGame.Pin).child("pin").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
               OnlineGameInfo.onlineGame.Pin = p0.getValue(String::class.java).toString()
                println("PULLPIN "+OnlineGameInfo.onlineGame.Pin)
            }

        })
    }


    //Updates the locally stored players
    fun updatePlayers(){
        println("Before update")
        println(OnlineGameInfo.onlineGame.Pin)
        ref.child(OnlineGameInfo.onlineGame.Pin).child("players").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {

                var map: MutableMap<String, Player> = HashMap()
                for (i in p0.children){
                    println(i)
                    var key = i.key.toString()
                    var value = i.getValue(Player::class.java)

                    if (value != null) {
                        map[key] = value
                    }
                }
                OnlineGameInfo.onlineGame.players = map
            }
        })

        println("AFTERUP")
        println(OnlineGameInfo.onlineGame.Pin)
    }


    //Removes Player from the Game database
    fun removePlayer(){
        ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).removeValue()
    }



    //Ensures Pin is unique and sets it locally
    fun checkPin(pin: Int) {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("$pin")) {
                    var Pin = (1111111..9999999).random()
                    checkPin(Pin)
                } else {
                    OnlineGameInfo.onlineGame.Pin = pin.toString()
                    println(OnlineGameInfo.onlineGame.Pin)
                    return
                }
            }
        })
    }

    fun removeGame(){

        ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
    }



    fun pushGame(){

        ref.child(OnlineGameInfo.onlineGame.Pin).setValue(OnlineGameInfo.onlineGame)


    }
}