package com.example.howwelldoyouknowonline

class OnlineGame() {

    var Pin:Int = 0
    var pushKey: String = ""
    var rounds:Int = 0
    var players:MutableMap<String,Player> = HashMap()





    fun reset(){
        Pin = 0
        pushKey = ""
    }


}