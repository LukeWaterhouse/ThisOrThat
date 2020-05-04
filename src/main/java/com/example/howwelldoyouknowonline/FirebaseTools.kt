package com.example.howwelldoyouknowonline

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
                OnlineGameInfo.onlineGame.rounds = p0.child("rounds").toString()
                OnlineGameInfo.onlineGame.Pin = p0.child("pin").toString()
            }

        })
    }


    //Updates the locally stored players
    fun updatePlayers(){
        ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
            override fun onDataChange(p0: DataSnapshot) {

                var map: MutableMap<String, Player> = HashMap()
                for (i in p0.child("players").children){
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
    }


    //Removes Player from the Game database
    fun removePlayer(){
        ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(PlayerInfo.player.name).removeValue()
    }



    //Ensures Pin is unique and sets it locally
    fun checkPin(pin: Int): Int {
        var Pin: Int = 0
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("")
            }
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild("$pin")) {
                    Pin = (1111111..9999999).random()
                    checkPin(Pin)
                } else {
                    OnlineGameInfo.onlineGame.Pin = Pin.toString()
                    println(OnlineGameInfo.onlineGame.Pin)
                    return
                }
            }
        })
        return Pin

    }
}