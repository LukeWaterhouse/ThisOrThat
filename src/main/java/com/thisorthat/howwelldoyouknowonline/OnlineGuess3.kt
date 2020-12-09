package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.online_guess3.*


class OnlineGuess3:AppCompatActivity() {

    override fun onBackPressed() {

    }
    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    lateinit var eventListener: ValueEventListener
    var mediaPlayer:MediaPlayer = MediaPlayer();
    var leaveCheck:Boolean = false









    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_guess3)

        var correct: MediaPlayer = MediaPlayer.create(this, R.raw.correct)
        var burp:MediaPlayer  = MediaPlayer.create(this, R.raw.burp)
        var cry1:MediaPlayer  = MediaPlayer.create(this, R.raw.cry1)
        var cry2:MediaPlayer = MediaPlayer.create(this, R.raw.cry2)
        var fanfare:MediaPlayer  = MediaPlayer.create(this, R.raw.fanfare)
        var fart:MediaPlayer  = MediaPlayer.create(this, R.raw.fart)
        var firework:MediaPlayer  = MediaPlayer.create(this, R.raw.firework)
        var huhm1:MediaPlayer  = MediaPlayer.create(this, R.raw.huhm1)
        var huhm2:MediaPlayer  = MediaPlayer.create(this, R.raw.huhm2)
        var incorrect:MediaPlayer  = MediaPlayer.create(this, R.raw.incorrect)
        var ohyeahh:MediaPlayer  = MediaPlayer.create(this, R.raw.ohyeahh)
        var positivesound:MediaPlayer  = MediaPlayer.create(this, R.raw.positivesound)
        var toothorn:MediaPlayer  = MediaPlayer.create(this, R.raw.toothorn)
        var vom:MediaPlayer  = MediaPlayer.create(this, R.raw.vom)

        val correctList: MutableList<MediaPlayer> = arrayListOf(correct,fanfare,firework,huhm1,huhm2,ohyeahh,positivesound,toothorn)
        val incorrectList: List<MediaPlayer> = arrayListOf(burp,cry1,cry2,fart,incorrect,vom)





        leaveCheck = false
        PlayerInfo.player.currentGuess = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.currentGuess.toString()
        var player = OnlineGameInfo.onlineGame.currentPlayer + " Chose"
        guesswas.text = player
        var chosen = OnlineGameInfo.onlineGame.correctOption
        chose.text = chosen

        fun abort(){
            leaveCheck = true
            val text = "Someone left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        if (PlayerInfo.player.currentGuess== OnlineGameInfo.onlineGame.correctOption){
            val randomP = (0..7).random()
            var points = "Correct! You got a point"

            correctList[randomP].start()
            point.text = points
            var newpoints:Int = PlayerInfo.player.score +1
            ref.child(OnlineGameInfo.onlineGame.Pin).child("players").child(
                PlayerInfo.player.name).child("score").setValue(newpoints)
        }
        if (PlayerInfo.player.currentGuess!= OnlineGameInfo.onlineGame.correctOption){
            val randomN = (0..5).random()
            var non = "Incorrect!"

            incorrectList[randomN].start()
            point.text= non
        }
        fun turn(){
            leaveCheck = true
            startActivity(Intent(this, OnlineTurn1::class.java))
        }

        fun guess(){
            leaveCheck = true
            startActivity(Intent(this, OnlineGuess1::class.java))
        }
        fun round(){
            leaveCheck = true
            startActivity(Intent(this, OnlineRound::class.java))
        }
        fun end(){
            leaveCheck=true
            startActivity(Intent(this, OnlineEnd::class.java))
        }

        eventListener = ref.child(OnlineGameInfo.onlineGame.Pin).addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                OnlineGameInfo.onlineGame = p0.getValue(
                    OnlineGame::class.java)!!

                if (OnlineGameInfo.onlineGame.trigger=="abort"){
                    abort()
                }
                var newscore:Long = p0.child("players").child(PlayerInfo.player.name).child("score").value as Long
                var newscoreInt:Int = newscore.toInt()
                PlayerInfo.player.score = newscoreInt
                var scoreText = "score: "+ (PlayerInfo.player.score).toString()
                score11.text = scoreText
                PlayerInfo.player.currentGuess = OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.currentGuess.toString()

                if (OnlineGameInfo.onlineGame.gameState=="End"){
                    end()
                }

                if (OnlineGameInfo.onlineGame.currentTurn==1&& OnlineGameInfo.onlineGame.gameState=="Round"){
                    round()

                }
                if (OnlineGameInfo.onlineGame.currentTurn== PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    turn()
                }
                if (OnlineGameInfo.onlineGame.currentTurn != PlayerInfo.player.Turn&& OnlineGameInfo.onlineGame.gameState=="Next"){
                    guess()
                }


            }
        })
    }

    override fun onPause() {
        super.onPause()
        ref.child(OnlineGameInfo.onlineGame.Pin).removeEventListener(eventListener)
        if (!leaveCheck){
            ref.child(OnlineGameInfo.onlineGame.Pin).child("trigger").setValue("abort")
            ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
            val text = "You left the game!"
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(applicationContext, text, duration)
            toast.show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}