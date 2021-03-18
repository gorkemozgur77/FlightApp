package com.gorkemozgur.flightapp.module.authentication.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.module.authentication.viewmodel.RegisterViewModel
import com.gorkemozgur.flightapp.util.InputValidator
import com.gorkemozgur.flightapp.module.home_page.view.Homepage
import kotlinx.android.synthetic.main.fragment_login.airbus
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    companion object {
        const val EMAIL_EXIST_CODE = "ERROR_EMAIL_ALREADY_IN_USE"
        const val EMAIL_EXIST_MESSAGE = "Bu adres başkası tarafından kullanılıyor."

    }

    lateinit var mAuth: FirebaseAuth
    lateinit var viewModel: RegisterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        mAuth = FirebaseAuth.getInstance()

        airbus.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.up_down
            )
        )

        emailRegisterId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                emailRegisterLayoutId.isErrorEnabled = false
        }

        emailRegisterId.addTextChangedListener {
            emailRegisterLayoutId.isErrorEnabled = false
        }

        passwordRegisterId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                passwordRegisterLayoutId.isErrorEnabled = false
        }

        passwordRegisterId.addTextChangedListener {
            passwordRegisterLayoutId.isErrorEnabled = false
        }

        nameRegisterId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                nameRegisterLayoutId.isErrorEnabled = false
        }

        nameRegisterId.addTextChangedListener {
            nameRegisterLayoutId.isErrorEnabled = false
        }

        surnameRegisterId.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus)
                surnameRegisterLayoutId.isErrorEnabled = false
        }

        surnameRegisterId.addTextChangedListener {
            surnameRegisterLayoutId.isErrorEnabled = false
        }

        signupCloseActionButton.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

        registerButton.setOnClickListener {
            register()
        }

        observeLivedata()
    }

    private fun register(){
        validateLayouts()
        if (nameRegisterLayoutId.isErrorEnabled || passwordRegisterLayoutId.isErrorEnabled || emailRegisterLayoutId.isErrorEnabled || passwordRegisterLayoutId.isErrorEnabled)
            return
        sendRegisterRequest()
    }

    private fun sendRegisterRequest(){
        viewModel.sendLoginRequest(
            nameRegisterId.text.toString(),
            surnameRegisterId.text.toString(),
            emailRegisterId.text.toString(),
            passwordRegisterId.text.toString()
        )
    }

    private fun validateLayouts(){
        viewModel.validateLayouts(nameRegisterLayoutId, nameRegisterId)
        viewModel.validateLayouts(surnameRegisterLayoutId, surnameRegisterId)
        viewModel.validateEmailRegex(emailRegisterLayoutId, emailRegisterId)
        viewModel.validatePassword(passwordRegisterLayoutId, passwordRegisterId)
    }

    private fun observeLivedata(){
        viewModel.isRegisterSuccessful.observe(
            viewLifecycleOwner, {
                if (it){
                    startActivity(Intent(context, Homepage::class.java))
                    this.activity?.finish()
                }
            }
        )

        viewModel.errorException.observe(
            viewLifecycleOwner, {
                println((FirebaseAuthException::class.java.cast(it)!!.errorCode))
                if (FirebaseAuthException::class.java.cast(it)!!.errorCode == EMAIL_EXIST_CODE)
                    InputValidator().errorAction(emailRegisterLayoutId, EMAIL_EXIST_MESSAGE)
            }
        )
    }
}