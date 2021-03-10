package com.gorkemozgur.flightapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gorkemozgur.flightapp.tutorial_page.TutorialPage
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

        prefs = applicationContext.getSharedPreferences(Constants.TutorialPagePreferenceFile, Context.MODE_PRIVATE)

        versionTextView.text = applicationContext.packageManager.getPackageInfo(applicationContext.packageName,0).versionName
        variantTextView.text = BuildConfig.BUILD_TYPE

        object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    checkTutorial()
                    finish()
                    println("SplashScreen gecti.")
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }.start()

    }

    private fun checkTutorial() {
        when(prefs.getBoolean(TUTORIAL_PAGE, false)){
            true -> startLoginActivity()
            false -> startTutorialPageActivity()
        }
    }

    private fun startLoginActivity(){
        startActivity(Intent(baseContext,LoginActivity::class.java))
    }
    private fun startTutorialPageActivity(){
        startActivity(Intent(baseContext,TutorialPage::class.java))
        val editor = prefs.edit()
        editor.putBoolean(TUTORIAL_PAGE, true)
        editor.apply()
    }
}