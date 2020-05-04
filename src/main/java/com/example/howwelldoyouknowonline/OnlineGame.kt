package com.example.howwelldoyouknowonline

class OnlineGame() {

    var Pin:String = "0"
    var rounds:String = "1"
    var players:MutableMap<String,Player> = HashMap()





    fun reset(){
        Pin = ""
        val empty: MutableMap<String,Player> = HashMap()
        players = empty
        rounds = "1"
    }


}