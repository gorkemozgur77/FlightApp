package com.gorkemozgur.flightapp.module.authentication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.Validator
import com.gorkemozgur.flightapp.module.Homepage
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.airbus
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        airbus.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.up_down
            )
        )

        signupCloseActionButton.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

        registerButton.setOnClickListener {
            register()
        }
    }

    private fun register(){
        validateLayouts()
        if (nameRegisterLayoutId.isErrorEnabled || passwordRegisterLayoutId.isErrorEnabled || emailRegisterLayoutId.isErrorEnabled || passwordRegisterLayoutId.isErrorEnabled)
            return
        sendRequest()
    }

    private fun sendRequest(){
        progressBarRegister.visibility = View.VISIBLE
        this.activity?.let { it1 ->
            mAuth.createUserWithEmailAndPassword(emailRegisterId.text.toString(), passwordRegisterId.text.toString())
                .addOnCompleteListener(it1){
                    progressBarRegister.visibility = View.GONE
                    if (it.isSuccessful){
                        mAuth.currentUser!!.updateProfile(
                            UserProfileChangeRequest.Builder().setDisplayName(nameRegisterId.text.toString() + " " + surnameRegisterId.text.toString()).build()
                        )
                        startActivity(Intent(context, Homepage::class.java))
                        this.activity?.finish()
                    } else {
                        if (it.exception.toString() == "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.")
                            Validator().errorAction(emailRegisterLayoutId, "Bu adres başkası tarafından kullanılıyor.")
                    }
                }
        }
    }

    private fun validateLayouts(){
        val validator = Validator()
        validator.layoutEmptyErrorValidator(nameRegisterLayoutId, nameRegisterId)
        validator.layoutEmptyErrorValidator(surnameRegisterLayoutId, surnameRegisterId)
        validator.layoutEmailRegexValidator(emailRegisterLayoutId, emailRegisterId)
        validator.layoutPasswordValidator(passwordRegisterLayoutId, passwordRegisterId)
    }

}