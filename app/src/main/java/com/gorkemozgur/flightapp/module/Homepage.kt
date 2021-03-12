package com.gorkemozgur.flightapp.module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.gorkemozgur.flightapp.R
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)


        mAuth = FirebaseAuth.getInstance()

        textView2.text = mAuth.currentUser.email
        textView3.text = mAuth.currentUser.displayName
        textView4.text = mAuth.currentUser.uid
    }
}