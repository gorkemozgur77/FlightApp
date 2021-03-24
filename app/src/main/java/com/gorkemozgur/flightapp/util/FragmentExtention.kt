package com.gorkemozgur.flightapp.util

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_SHORT){
    context?.toast(message, duration)
}