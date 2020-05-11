package com.example.howwelldoyouknow

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.howwelldoyouknowonline.EnterNickname
import com.example.howwelldoyouknowonline.HostSettings
import com.example.howwelldoyouknowonline.OnlineGameInfo
import com.example.howwelldoyouknowonline.PlayerInfo
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onBackPressed() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        PlayerInfo.player.reset()
        OnlineGameInfo.onlineGame.Pin ="111"
        OnlineGameInfo.onlineGame.rounds="1"
        OnlineGameInfo.onlineGame.players.clear()


        //Resets the online game stats

        //Starts offline mode
        startGame.setOnClickListener {
            startActivity(Intent(this,Settings::class.java))
        }



        //Sets player as not host and go to enter nickname
        Join.setOnClickListener {
            var connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            var isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

            if (isConnected){
                PlayerInfo.player.host = false
                startActivity(Intent(this, EnterNickname::class.java))
            }

            else{
                val text = "Please connect to the internet!"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            }
        }

        //Sets player as host and go to host settings
        Host.setOnClickListener {
            var connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            var isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if (isConnected){
                PlayerInfo.player.host = true
                startActivity(Intent(this, HostSettings::class.java))
            }
            else{
                val text = "Please connect to the internet!"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()

            }
        }

        }


    }

