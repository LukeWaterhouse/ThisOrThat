package com.example.howwelldoyouknowonline

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OnlineGame(var Pin:String="", var rounds:String="",var players: MutableMap<String,Player> = HashMap())