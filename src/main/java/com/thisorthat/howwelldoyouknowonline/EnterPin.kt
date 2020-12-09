package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thisorthat.thisorthat.R
import kotlinx.android.synthetic.main.enter_pin.*

class EnterPin:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_pin)

        fun hostgame(){
            startActivity(Intent(this, HostGame::class.java))

        }
        val ref = FirebaseDatabase.getInstance().getReference("newGame")




        acceptpin.setOnClickListener {

            if (enterpin.text.toString()==""){
                val text = "Enter a pin!"
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
            else{
                OnlineGameInfo.onlineGame.Pin = enterpin.text.toString()
                ref.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (!p0.child(OnlineGameInfo.onlineGame.Pin).exists()){
                            val text = "Please enter a valid pin"
                            val duration = Toast.LENGTH_SHORT
                            val toast = Toast.makeText(applicationContext, text, duration)
                            toast.show()

                            enterpin.text.clear()
                        }
                        else{
                            hostgame()
                        }
                    }



                })

            }
        }

        roundscancel3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}