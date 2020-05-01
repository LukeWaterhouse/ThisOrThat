package com.example.howwelldoyouknow

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.settings.*

class Settings : AppCompatActivity() {

    var players1: Int = 2
    var rounds1: Int = 1


    lateinit var players: SeekBar
    lateinit var playersValue: TextView
    lateinit var rounds: SeekBar
    lateinit var roundsValue: TextView


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)



        players = findViewById(R.id.seekPlayers)
        playersValue = findViewById(R.id.playersValue)
        players.max = 15
        players.min = 2

        players.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                playersValue.text = progress.toString()
                players1 = progress


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        rounds = findViewById(R.id.seekRounds)
        roundsValue = findViewById(R.id.roundsValue)
        rounds.max = 15
        rounds.min = 1

        rounds.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                roundsValue.text = progress.toString()
                rounds1 = progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        choose_colour.setOnClickListener {
            GameData.reset()
            GameData.stats()

            GameData.game.players = players1
            GameData.game.rounds = rounds1


            println(GameData.game.Stats())
            startActivity(Intent(this, ChooseColour::class.java))
        }


    }

}