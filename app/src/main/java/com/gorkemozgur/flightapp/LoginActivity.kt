package com.gorkemozgur.flightapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var animUpDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.up_down)

        collapseImage.startAnimation(animUpDown)
    }
}