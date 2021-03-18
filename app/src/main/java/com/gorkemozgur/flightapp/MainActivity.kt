package com.gorkemozgur.flightapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.gorkemozgur.flightapp.module.authentication.view.LoginActivity
import com.gorkemozgur.flightapp.module.home_page.view.Homepage
import com.gorkemozgur.flightapp.module.tutorial_page.TutorialPage
import com.gorkemozgur.flightapp.util.Constants
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences

    companion object{
        const val TUTORIAL_PAGE = "tutorial_page"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = applicationContext.getSharedPreferences(
            Constants.TutorialPagePreferenceFile,
            Context.MODE_PRIVATE
        )

        versionTextView.text = BuildConfig.VERSION_NAME
        variantTextView.text = BuildConfig.BUILD_TYPE

        object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    checkTutorial()
                    finish()
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }.start()

    }

    private fun checkTutorial() {
        when(prefs.getBoolean(TUTORIAL_PAGE, false)){
            true -> startLoginOrHomepage()
            false -> startTutorialPageActivity()
        }
    }

    private fun startLoginOrHomepage(){
        val auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null)
            startActivity(Intent(baseContext, Homepage::class.java))

        else
            startActivity(Intent(baseContext, LoginActivity::class.java))
    }

    private fun startTutorialPageActivity(){
        startActivity(Intent(baseContext, TutorialPage::class.java))
        val editor = prefs.edit()
        editor.putBoolean(TUTORIAL_PAGE, true)
        editor.apply()
    }
}