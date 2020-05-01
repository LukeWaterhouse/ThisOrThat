package com.example.howwelldoyouknow

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.your_turn1.*

class YourTurn1: AppCompatActivity() {


    override fun onBackPressed() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.your_turn1)

        score3.text = "Score: " + GameData.game.myScore

        nextYourTurn3.visibility = View.GONE

        var option1: String = ""
        var option2: String = ""

        var questiontab: Int = (GameData.game.Round-1) * GameData.game.players


        option1 = GameData.questions[questiontab + GameData.game.currentTurn][0]
        option2 = GameData.questions[questiontab + GameData.game.currentTurn][1]

        GameData.game.currentOption1 = option1
        GameData.game.currentOption2 = option2



        var optionOne: Button = findViewById(R.id.option1)
        optionOne.text = option1

        var optionTwo: Button = findViewById(R.id.option2)
        optionTwo.text = option2

        optionOne.setOnClickListener {
            nextYourTurn3.visibility = View.VISIBLE
            optionOne.setBackgroundResource(R.drawable.start_button_selected)
            optionTwo.setBackgroundResource(R.drawable.start_button)
            GameData.game.currentChoice = option1
        }

        optionTwo.setOnClickListener {
            nextYourTurn3.visibility = View.VISIBLE
            optionTwo.setBackgroundResource(R.drawable.start_button_selected)
            optionOne.setBackgroundResource(R.drawable.start_button)
            GameData.game.currentChoice = option2

        }

        nextYourTurn3.setOnClickListener {
            startActivity(Intent(this, YourTurn2::class.java))
        }




        }

    }

