package com.example.todolist

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var todoIconImageView: ImageView = findViewById(R.id.ic_todo)
        todoIconImageView.alpha = 0f
        todoIconImageView.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.splash_in, R.anim.splash_out)
            finish()
        }
    }

}