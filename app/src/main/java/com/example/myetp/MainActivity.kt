package com.example.myetp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var play: Button? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        play = findViewById(R.id.play)
        play!!.setOnClickListener {
            val intent = Intent(this@MainActivity, Game::class.java)
            startActivity(intent)
        }
    }
}