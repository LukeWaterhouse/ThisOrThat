package com.example.howwelldoyouknowonline

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OnlineGame(var Pin:String="111",
                 var rounds:String="1",
                 var players: MutableMap<String,Player> = HashMap(),
                 var noPlayers:String = "1")