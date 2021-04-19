package com.gorkemozgur.flightapp.module.home_page.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

import com.gorkemozgur.flightapp.BaseViewModel
import com.gorkemozgur.flightapp.util.InputValidator
import java.lang.Exception


class ProfileViewModel(application: Application) : BaseViewModel(application) {

    private val mAuth = FirebaseAuth.getInstance()
    val user = MutableLiveData<FirebaseUser>()
    val exception = MutableLiveData<Exception>()
    private val current: FirebaseUser = mAuth.currentUser!!
    private val validator = InputValidator()


    fun updateUser(name: String, surname: String, email: String, password: String) {
        current.updateEmail(email).addOnCompleteListener {
            if (!it.isSuccessful){
               exception.value = it.exception
            }
        }
        current.updatePassword(password)
        current.updateProfile(UserProfileChangeRequest.Builder().setDisplayName("$name $surname").build())
    }

    fun getUser() {
        user.postValue(current)
    }


    fun validateLayouts(layout: TextInputLayout, view: TextView) {
        validator.layoutEmptyErrorValidator(layout, view)
    }


    fun validateEmailRegex(layout: TextInputLayout, view: TextView) {
        validator.layoutEmailRegexValidator(layout, view)
    }

    fun validatePassword(layout: TextInputLayout, view: TextView) {
        validator.layoutPasswordValidator(layout, view)
    }

}