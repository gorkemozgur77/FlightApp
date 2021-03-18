package com.gorkemozgur.flightapp.module.authentication.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.gorkemozgur.flightapp.BaseViewmodel
import com.gorkemozgur.flightapp.util.InputValidator

class RegisterViewModel(application: Application): BaseViewmodel(application) {

    private val mAuth = FirebaseAuth.getInstance()

    val isRegisterSuccessful = MutableLiveData<Boolean>()
    val errorException = MutableLiveData<Exception>()
    private val validator = InputValidator()

    fun sendLoginRequest(name: String, surname: String, email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                isRegisterSuccessful.value = true
                mAuth.currentUser!!.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName("$name $surname").build()
                )
            }
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