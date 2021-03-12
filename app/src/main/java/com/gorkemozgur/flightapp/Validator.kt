package com.gorkemozgur.flightapp

import android.text.TextUtils
import android.util.Patterns
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class Validator {

    private val canNotEmpty = "Boş bırakılamaz."
    private val invalidEmail = "Geçersiz email"
    private val invalidPassword= "Şifre 6 karakterden kısa olamaz."


    fun errorAction(layout: TextInputLayout, message: String){
        layout.isErrorEnabled = true
        layout.error = message
    }

    private fun cleanError(layout: TextInputLayout){
        layout.isErrorEnabled = false
    }

    private  fun isEmpty(view: TextView): Boolean {
        return TextUtils.isEmpty( view.text.toString() )
    }

    private fun isEmailValid(view: TextView): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher( view.text.toString() ).matches()
    }

    private fun isPasswordValid(view: TextView): Boolean{
        return view.text.toString().length >= 6
    }

    fun layoutEmptyErrorValidator(layout : TextInputLayout, view : TextView): Boolean{
        if ( isEmpty(view) ) {
            errorAction(layout, canNotEmpty)
            return true
        }
        cleanError(layout)
        return false
    }

    fun layoutEmailRegexValidator(layout: TextInputLayout, view: TextView) {
        if (layoutEmptyErrorValidator(layout, view))
            return
        if( !isEmailValid(view) )
            errorAction(layout, invalidEmail)
        else
            cleanError(layout)
    }

    fun layoutPasswordValidator(layout: TextInputLayout, view: TextView){
        if (layoutEmptyErrorValidator(layout, view))
            return
        if (!isPasswordValid(view))
            errorAction(layout, invalidPassword)
        else
            cleanError(layout)
    }

}