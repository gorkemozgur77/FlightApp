package com.gorkemozgur.flightapp.module.authentication.viewmodel

import android.app.Application
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.UserProfileChangeRequest
import com.gorkemozgur.flightapp.BaseViewmodel
import com.gorkemozgur.flightapp.model.Resource
import com.gorkemozgur.flightapp.util.InputValidator

class RegisterViewModel(application: Application): BaseViewmodel(application) {

    private val mAuth = FirebaseAuth.getInstance()

    private val isRegisterSuccessful = MutableLiveData<Resource<Boolean>>()
    val responseValue : LiveData<Resource<Boolean>>
        get() = isRegisterSuccessful

    private val validator = InputValidator()

    fun sendLoginRequest(name: String, surname: String, email: String, password: String){
        isRegisterSuccessful.postValue(Resource.loading())
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                isRegisterSuccessful.postValue(Resource.success(true))
                mAuth.currentUser!!.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName("$name $surname").build()
                )
            }
            else{
                isRegisterSuccessful.postValue(Resource.error(FirebaseAuthException::class.java.cast(it.exception)?.errorCode))
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