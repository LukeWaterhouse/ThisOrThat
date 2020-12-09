package com.thisorthat.thisorthat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.your_turn2.*

class YourTurn2: AppCompatActivity() {

    override fun onBackPressed() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.your_turn2)

        score4.text = "Score: " + GameData.game.myScore

        var option1button: Button = findViewById(R.id.option1)
        var option2button: Button = findViewById(R.id.option2)

        option1button.text = GameData.game.currentOption1
        option2button.text = GameData.game.currentOption2


        guessed.setOnClickListener {
            guessed.setBackgroundResource(R.drawable.start_button_selected)
            startActivity(Intent(this, YourTurn3::class.java))
        }
    }





}