package com.gorkemozgur.flightapp.module.authentication

import android.content.ContentValues
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
import com.google.firebase.auth.FirebaseAuth
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.Validator
import com.gorkemozgur.flightapp.module.Homepage
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.airbus


class LoginFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
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

        registerPageButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        loginButton.setOnClickListener {
            signIn()
        }

    }
    private fun signIn() {
        validateLayouts()
        if (emailLoginLayoutId.isErrorEnabled || passwordLoginLayoutId.isErrorEnabled)
            return
        sendRequest()
    }

    private fun sendRequest(){
        progressBarLogin.visibility = View.VISIBLE
        this.activity?.let { it1 ->
            mAuth.signInWithEmailAndPassword(emailLoginId.text.toString(), passwordLoginId.text.toString())
                .addOnCompleteListener(it1) {
                    progressBarLogin.visibility = View.GONE

                    if (it.isSuccessful) {
                        Log.d(ContentValues.TAG, "signInWithCustomToken:success")
                        startActivity(Intent(context, Homepage::class.java))
                        activity?.finish()

                    } else {
                        Log.w(ContentValues.TAG, "signInWithCustomToken:failure", it.exception)
                        Toast.makeText(context, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateLayouts(){
        val validator = Validator()
        validator.layoutEmailRegexValidator(emailLoginLayoutId, emailLoginId)
        validator.layoutEmptyErrorValidator(passwordLoginLayoutId, passwordLoginId)
    }
}