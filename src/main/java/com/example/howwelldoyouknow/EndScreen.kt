package com.example.howwelldoyouknow

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.end_screen.*


class EndScreen:AppCompatActivity() {

    override fun onBackPressed() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_screen)

        menu.setOnClickListener {
            GameData.reset()
            startActivity(Intent(this, MainActivity::class.java))
        }

        score.text = "YOUR FINAL SCORE: "+ GameData.game.myScore.toString()
    }
}