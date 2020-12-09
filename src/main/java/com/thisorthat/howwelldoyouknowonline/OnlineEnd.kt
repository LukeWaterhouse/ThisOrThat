package com.thisorthat.howwelldoyouknowonline


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.end_game.*

class OnlineEnd:AppCompatActivity() {

    val ref = FirebaseDatabase.getInstance().getReference("newGame")
    var third:Int = 0;
    var scoresSecond:Int = 69

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_game)

        //Sets the players top right score text
        var scoreText = "score: "+ (PlayerInfo.player.score).toString()
        score13.text = scoreText


        //Initialises draw to false
        var Firstdraw = false

        //creates a Mutable List of Lists of type any for Name and Score
        var NameScore: MutableList<MutableList<Any>> = arrayListOf()

        //creates a Mutable List of Int type
        var scores: MutableList<Int> = arrayListOf()


        //goes through the players and adds the name and score as items in the Name Score list
        //Also adds the scores to a separate list
        for (i in OnlineGameInfo.onlineGame.players){
            NameScore.add(arrayListOf(i.value.name,i.value.score))
            scores.add(i.value.score)
        }

        //numerically sorts scores
        scores.sort()


        //If top scores are the same sets draw condition to true
        if (scores.size>1 && scores[scores.size-1]==scores[scores.size-2]){
            Firstdraw=true
        }

        //removes duplicates from scores
        scores.distinct()
        //numerically sorts scores
        scores.sort()

        //Sets the First place score int
        var scoresFirstIndex = scores.size-1
        var scoresFirst:Int = scores[scoresFirstIndex]

        //If two scores, Sets the Second place score
        if (scores.size>1){
            var scoresSecondIndex = scores.size-2
            scoresSecond = scores[scoresSecondIndex]
        }



        //If only one score sets draw condition to true
        if (scores.size==1){
            Firstdraw=true
        }


        //Initially sets the text to you didn't place
        var text = "You didn't place.."


        //If not a draw and you got first score sets the text to You came First!
        if (OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.score==scoresFirst&&!Firstdraw){
            text = "You came First!"
            imageView.setImageResource(R.drawable.gold)
        }

        //If a draw and you got the first score set text to You drew for First!
        if (OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.score==scoresFirst&&Firstdraw){
            text = "You drew for First!"
            imageView.setImageResource(R.drawable.gold)
        }



        //If scores size is more than one and your score is the same as the second score set text to You came Second!
        if ((OnlineGameInfo.onlineGame.players[PlayerInfo.player.name]?.score==scoresSecond && !Firstdraw)){
                text = "You came Second!"
            imageView.setImageResource(R.drawable.silveraward)
            }










        finalScore.text = text

        nextYourTurn5.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    override fun onPause() {
        super.onPause()
        if (PlayerInfo.player.host){
            ref.child(OnlineGameInfo.onlineGame.Pin).removeValue()
        }
    }
}