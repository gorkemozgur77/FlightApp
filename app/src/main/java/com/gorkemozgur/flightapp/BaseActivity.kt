package com.gorkemozgur.flightapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.gorkemozgur.flightapp.util.ProgressDialog

abstract class BaseActivity : AppCompatActivity() {

     var  progressBar: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        progressBar = applicationContext?.let{
            ProgressDialog(it)
        }


    }
     fun showProgressBar(){
            progressBar?.show()
    }

    fun hideProgressBar(){
            progressBar?.hide()
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}