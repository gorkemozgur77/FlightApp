package com.gorkemozgur.flightapp.module.authentication.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.module.authentication.viewmodel.LoginViewModel
import com.gorkemozgur.flightapp.module.home_page.view.Homepage
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        airbus.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.up_down
            )
        )

        val progressBar = ProgressBar(context)
        progressBar.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        progressBar.visibility = View.VISIBLE
        registerPageButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        loginButton.setOnClickListener {
            signIn()
            progressBar.visibility = View.VISIBLE;
        }

        emailLoginId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                emailLoginLayoutId.isErrorEnabled = false
        }

        passwordLoginId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                passwordLoginLayoutId.isErrorEnabled = false
        }

        emailLoginId.addTextChangedListener {
            emailLoginLayoutId.isErrorEnabled = false
        }

        passwordLoginId.addTextChangedListener {
            passwordLoginLayoutId.isErrorEnabled = false
        }

        observeLivedata()
    }

    private fun signIn() {
        validateLayouts()
        if (emailLoginLayoutId.isErrorEnabled || passwordLoginLayoutId.isErrorEnabled)
            return
        viewModel.sendLoginRequest(
            emailLoginId.text.toString(),
            passwordLoginId.text.toString()
        )
    }

    private fun validateLayouts(){
        viewModel.validateLayouts(emailLoginLayoutId, emailLoginId)
        viewModel.validateLayouts(passwordLoginLayoutId, passwordLoginId)
    }

    private fun observeLivedata(){
        viewModel.isLoginSuccessful.observe(
            viewLifecycleOwner, {
                if (it) {
                    startActivity(Intent(context, Homepage::class.java))
                    this.activity?.finish()
                }
            }
        )

        viewModel.errorException.observe(
            viewLifecycleOwner, {
                println((FirebaseAuthException::class.java.cast(it)!!.errorCode))
            }
        )
    }
}