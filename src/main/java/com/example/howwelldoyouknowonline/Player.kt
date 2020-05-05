package com.example.howwelldoyouknowonline

class Player(){

    var name:String = "Placeholder"
    var turn:Int = 1
    var host:Boolean = false


    fun reset(){
        name = "Placeholder"
        turn = 1
        host = false
    }

    fun stats(){
        println("Name:${this.name}")
        println("Turn:${this.turn}")
        println("Host:${this.host}")
    }

}
