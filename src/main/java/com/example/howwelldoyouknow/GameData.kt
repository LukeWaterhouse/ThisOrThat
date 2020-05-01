package com.example.howwelldoyouknow

object GameData {

    val colors = listOf<String>("Red", "Green", "Blue","Yellow","Orange","Purple", "Silver", "Pink", "Lime")
    var game: Game = Game(1,2,"", mutableListOf(),0,1,"","","","",0,1)
    var questions = mutableListOf<List<String>>(((listOf("Star Wars","Star Trek"))),
        (listOf("Putin", "Trump")),
        (listOf("Marmite","Jam")),
        (listOf("Crumpets","Toast")),
        (listOf("Pizza","Curry")),
        (listOf("Chinese","Indian")),
        (listOf("Golf","Tennis")),
        (listOf("Football","Rugby")),
        (listOf("Wooland","Seashore")),
        (listOf("Owl","Eagle")),
        (listOf("Dolphin","Whale")),
        (listOf("Bambi","Dumbo")),
        (listOf("Koala","Panda")),
        (listOf("Gorilla","Orangutan")),
        (listOf("Rice","Pasta")),
        (listOf("Bentley","Rolls")),
        (listOf("Landrover","Lexus")),
        (listOf("Jaguar","BMW")),
        (listOf("Mclaren","Ferrari")),
        (listOf("Gin","Vodka")),
        (listOf("Orange","Lemon")),
        (listOf("Apple","Pear")),
        (listOf("Beatles","Stones")),
        (listOf("Madonna","Minogue")),
        (listOf("Adele","Sheeran")),
        (listOf("ACDC","Guns'n'Roses")),
        (listOf("Federer","Nadal")),
        (listOf("Toy Story","Monsters Inc.")),
        (listOf("Star Trek","Star Wars")),
        (listOf("Lord of the Rings","Harry Potter")),
        (listOf("Legolas","Aragorn")),
        (listOf("Surfing","SnowBoard")),
        (listOf("Red Wine","White Wine")),
        (listOf("Brown Bread","White Bread")),
        (listOf("Butter","Margarine")),
        (listOf("Buried","Cremated")),
        (listOf("Sun","Moon")),
        (listOf("UK","USA")),
        (listOf("Spain","Portugal")),
        (listOf("Spain","France")),
        (listOf("Safari","Scuba")),
        (listOf("Australia","New Zealand")),
        (listOf("Paris","London")),
        (listOf("New York","Paris")),
        (listOf("London","New York")),
        (listOf("Labrador","Collie")),
        (listOf("Collie","Husky")),
        (listOf("Terrier","Bulldog")),
        (listOf("Tuna","Salmon")),
        (listOf("Blonde","Brunette")),
        (listOf("Disney","Pixar")),
        (listOf("Running","Cycling")),
        (listOf("Night In","Night Out")),
        (listOf("Hockey","Netball")),
        (listOf("Winter","Summer")),
        (listOf("Christmas","Easter")),
        (listOf("Xbox","PS4")),
        (listOf("Red","Blue")),
        (listOf("Tea","Coffee")),
        (listOf("Pub","Club")),
        (listOf("Guitar","Piano")),
        (listOf("Early Bird","Night Owl")),
        (listOf("Books","Films")),
        (listOf("Burger","Pizza")),
        (listOf("Tattoo","Piercing")),
        (listOf("KFC","McDonalds")),
        (listOf("MotorBike","Car")),
        (listOf("LA","New York")),
        (listOf("City","Countryside")),
        (listOf("Adidas","Nike")),
        (listOf("Gucci","Prada")),
        (listOf("Formal","Casual")))




    fun reset(){
        game.rounds = 1
        game.players = 2
        game.playerColor=""
        game.gameColors = mutableListOf()
        game.yourTurn = 0
        game.currentTurn = 1
        game.currentChoice = ""
        game.currentOption1 = ""
        game.currentOption2 = ""
        game.myGuess = ""
        game.myScore = 0
        game.Round = 1
    }

    fun stats(){
        println("ROUNDS "+game.rounds)
        println("PLAYERS "+game.players)
        println("PLAYERCOLOUR "+game.playerColor)
        println("GAMECOLOURS "+game.gameColors)
        println("YOURTURN " +game.yourTurn)
        println("CURRENTTURN "+ game.currentTurn)
        println("CURRENTCHOICE "+game.currentChoice)
        println("CURRENTGUESS "+ game.myGuess)
        println("MYSCORE "+ game.myScore)
    }

}