package com.thisorthat.howwelldoyouknowonline

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thisorthat.thisorthat.MainActivity
import com.thisorthat.thisorthat.R
import kotlinx.android.synthetic.main.error.*

class Error: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.error)

        menu.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
