package com.example.howwelldoyouknowonline

class Player(){

    var name:String = "Placeholder"
    var Turn:Int = 1
    var host:Boolean = false
    var currentGuess = "Placeholder"
    var score = 0



    fun reset(){
        name = "Placeholder"
        Turn = 1
        host = false
    }

    fun stats(){
        println("Name:${this.name}")
        println("Turn:${this.Turn}")
        println("Host:${this.host}")
    }

}
