package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.choose_player_colour.*


class ChooseColour: AppCompatActivity() {


    private lateinit var colourOptionValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_player_colour)
        println("HERE")

        chooseColourResult.visibility = View.INVISIBLE
        nextYourTurn3.visibility = View.INVISIBLE

        fun reset(){
            red.setBackgroundResource(R.drawable.red)
            green.setBackgroundResource(R.drawable.green)
            blue.setBackgroundResource(R.drawable.blue)
            yellow.setBackgroundResource(R.drawable.yellow)
            orange.setBackgroundResource(R.drawable.orange)
            purple.setBackgroundResource(R.drawable.purple)
            silver.setBackgroundResource(R.drawable.silver)
            pink.setBackgroundResource(R.drawable.pink)
            lime.setBackgroundResource(R.drawable.lime)
        }



        GameData.game.gameColors = mutableListOf()

        var gameColors2 = mutableListOf<String>()
        for (i in 0 until GameData.game.players) {
            gameColors2.add(GameData.colors[i])
        }
        GameData.game.gameColors =gameColors2

        var redB:Button = findViewById(R.id.red)
        var greenB:Button = findViewById(R.id.green)
        var blueB:Button = findViewById(R.id.blue)
        var yellowB:Button = findViewById(R.id.yellow)
        var orangeB:Button = findViewById(R.id.orange)
        var purpleB:Button = findViewById(R.id.purple)
        var silverB:Button = findViewById(R.id.silver)
        var pinkB:Button = findViewById(R.id.pink)
        var limeB:Button = findViewById(R.id.lime)


        red.visibility = View.INVISIBLE
        green.visibility = View.INVISIBLE
        blue.visibility = View.INVISIBLE
        yellow.visibility = View.INVISIBLE
        orange.visibility = View.INVISIBLE
        purple.visibility = View.INVISIBLE
        silver.visibility = View.INVISIBLE
        pink.visibility = View.INVISIBLE
        lime.visibility = View.INVISIBLE


        var list = mutableListOf<Button>(redB,greenB,blueB,yellowB,orangeB,purpleB,silverB,pinkB,limeB)

        for(i in list){
            if(GameData.game.gameColors.contains(i.text)){
                i.visibility = View.VISIBLE
            }
        }



        red.setOnClickListener {
            reset()
            red.setBackgroundResource(R.drawable.redselected)
            nextYourTurn3.visibility = View.VISIBLE
            chooseColourResult.visibility = View.VISIBLE
            GameData.game.playerColor = "Red"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 1
            chooseColourResult.setBackgroundResource(R.drawable.red)
        }

        green.setOnClickListener {
            reset()
            green.setBackgroundResource(R.drawable.greenselected)
            nextYourTurn3.visibility = View.VISIBLE
            chooseColourResult.visibility = View.VISIBLE
            GameData.game.playerColor = "Green"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 2
            chooseColourResult.setBackgroundResource(R.drawable.green)


        }

        blue.setOnClickListener {
            reset()
            blue.setBackgroundResource(R.drawable.blueselected)
            nextYourTurn3.visibility = View.VISIBLE
            chooseColourResult.visibility = View.VISIBLE
            GameData.game.playerColor = "Blue"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 3
            chooseColourResult.setBackgroundResource(R.drawable.blue)


        }

        yellow.setOnClickListener {
            reset()
            yellow.setBackgroundResource(R.drawable.yellowselected)
            nextYourTurn3.visibility = View.VISIBLE
            chooseColourResult.visibility = View.VISIBLE
            GameData.game.playerColor = "Yellow"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 4
            chooseColourResult.setBackgroundResource(R.drawable.yellow)


        }

        orange.setOnClickListener {
            reset()
            orange.setBackgroundResource(R.drawable.orangeselected)
            chooseColourResult.visibility = View.VISIBLE
            nextYourTurn3.visibility = View.VISIBLE
            GameData.game.playerColor = "Orange"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 5
            chooseColourResult.setBackgroundResource(R.drawable.orange)


        }

        purple.setOnClickListener {
            reset()
            purple.setBackgroundResource(R.drawable.purpleselected)
            chooseColourResult.visibility = View.VISIBLE
            nextYourTurn3.visibility = View.VISIBLE
            GameData.game.playerColor = "Purple"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 6
            chooseColourResult.setBackgroundResource(R.drawable.purple)


        }

        silver.setOnClickListener {
            reset()
            silver.setBackgroundResource(R.drawable.silverselected)
            chooseColourResult.visibility = View.VISIBLE
            nextYourTurn3.visibility = View.VISIBLE
            GameData.game.playerColor = "Silver"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 7
            chooseColourResult.setBackgroundResource(R.drawable.silver)


        }

        pink.setOnClickListener {
            reset()
            pink.setBackgroundResource(R.drawable.pinkselected)
            chooseColourResult.visibility = View.VISIBLE
            nextYourTurn3.visibility = View.VISIBLE
            GameData.game.playerColor = "Pink"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 8
            chooseColourResult.setBackgroundResource(R.drawable.pink)


        }

        lime.setOnClickListener {
            reset()
            lime.setBackgroundResource(R.drawable.limeselected)
            chooseColourResult.visibility = View.VISIBLE
            nextYourTurn3.visibility = View.VISIBLE
            GameData.game.playerColor = "Lime"
            chooseColourResult.text = GameData.game.playerColor
            GameData.game.yourTurn = 9
            chooseColourResult.setBackgroundResource(R.drawable.lime)


        }



        nextYourTurn3.setOnClickListener {
            startActivity(Intent(this, BeginGame::class.java))
            GameData.game.playerColor = chooseColourResult.text.toString()


        }
    }
}