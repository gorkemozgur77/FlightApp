package com.gorkemozgur.flightapp.module.authentication.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.gorkemozgur.flightapp.BaseViewmodel
import com.gorkemozgur.flightapp.model.Resource
import com.gorkemozgur.flightapp.util.InputValidator

class LoginViewModel(application: Application): BaseViewmodel(application) {

    private val isLoginSuccessful = MutableLiveData<Resource<Boolean>>()
    val errorException = MutableLiveData<Exception>()
    val responseValue: LiveData<Resource<Boolean>>
        get() = isLoginSuccessful

    private val validator = InputValidator()
    private val mAuth = FirebaseAuth.getInstance()

    fun sendLoginRequest(name: String, surname: String){
        isLoginSuccessful.postValue(Resource.loading())
        mAuth.signInWithEmailAndPassword(name, surname).addOnCompleteListener {

            if (it.isSuccessful)
                isLoginSuccessful.postValue(Resource.success(true))
            else{
                errorException.value = it.exception
                isLoginSuccessful.postValue(Resource.error(FirebaseAuthException::class.java.cast(it.exception)?.errorCode))
            }
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