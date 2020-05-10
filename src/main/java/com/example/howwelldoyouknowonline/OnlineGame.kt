package com.example.howwelldoyouknowonline

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class OnlineGame(var Pin:String="111",
                 var rounds:String="1",
                 var players: MutableMap<String,Player> = HashMap(),
                 var noPlayers:Int = 1,
                 var currentTurn:Int = 1,
                 var options: MutableMap<String,String> = HashMap(),
                 var gameState:String = "Settings",
                 var currentRound:Int = 1,
                 var correctOption:String = "Placeholder",
                 var peopleGuessed:Int = 0,
                 var currentPlayer:String = "Placeholder",
                 var trigger:String = "Trigger",
                 var guessed:String = "No")