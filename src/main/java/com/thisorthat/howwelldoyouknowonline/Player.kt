package com.thisorthat.howwelldoyouknowonline

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
        currentGuess = "Placeholder"
        score=0
    }

    fun stats(){
        println("Name:${this.name}")
        println("Turn:${this.Turn}")
        println("Host:${this.host}")
    }

}
