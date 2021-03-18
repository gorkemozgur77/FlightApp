package com.gorkemozgur.flightapp.module.authentication.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.gorkemozgur.flightapp.BaseViewmodel
import com.gorkemozgur.flightapp.util.InputValidator

class LoginViewModel(application: Application): BaseViewmodel(application) {

    val isLoginSuccessful = MutableLiveData<Boolean>()
    val errorException = MutableLiveData<Exception>()
    private val validator = InputValidator()



    private val mAuth = FirebaseAuth.getInstance()

    fun sendLoginRequest(name: String, surname: String){
        mAuth.signInWithEmailAndPassword(name, surname).addOnCompleteListener {
            if (it.isSuccessful)
                isLoginSuccessful.value = true
            else
                errorException.value = it.exception
        }

    }

    fun validateLayouts(layout: TextInputLayout, view: TextView){
        validator.layoutEmptyErrorValidator(layout, view)
    }

    fun validateEmailRegex(layout: TextInputLayout, view: TextView){
        validator.layoutEmailRegexValidator(layout, view)
    }

    fun validatePassword(layout: TextInputLayout, view: TextView){
        validator.layoutPasswordValidator(layout, view)
    }
}