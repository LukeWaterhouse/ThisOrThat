package com.example.howwelldoyouknow

class Game( var rounds: Int,
            var players:Int,
            var playerColor:String,
            var gameColors:List<String>,
            var yourTurn:Int, var currentTurn:Int,
            var currentChoice:String,
            var currentOption1:String,
            var currentOption2:String,
            var myGuess:String,
            var myScore:Int,
            var Round:Int){



    fun Stats(){
        println("Rounds: $rounds\nPlayers: $players\n PlayerColor: $playerColor\nGameColors: $gameColors\nyourTurn: $yourTurn\ncurrentTurn: $currentTurn" )
    }





}