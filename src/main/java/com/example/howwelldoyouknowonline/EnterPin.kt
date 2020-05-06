package com.example.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.howwelldoyouknow.GameData
import com.example.howwelldoyouknow.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.enter_pin.*

class EnterPin:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val Tools = FirebaseTools()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_pin)

        fun hostgame(){
            startActivity(Intent(this, HostGame::class.java))

        }
        val ref = FirebaseDatabase.getInstance().getReference("newGame")
        valid.visibility = View.INVISIBLE




        acceptpin.setOnClickListener {
            OnlineGameInfo.onlineGame.Pin = enterpin.text.toString()
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if (!p0.child(OnlineGameInfo.onlineGame.Pin).exists()){
                        valid.visibility = View.VISIBLE
                        enterpin.text.clear()
                    }
                    else{
                        hostgame()
                    }
                }


            })
        }
    }
}