package com.gorkemozgur.flightapp.module.authentication.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.gorkemozgur.flightapp.BaseFragment
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.Status
import com.gorkemozgur.flightapp.module.authentication.viewmodel.RegisterViewModel
import com.gorkemozgur.flightapp.module.home_page.view.Homepage
import com.gorkemozgur.flightapp.util.FirebaseErrorCodes.EMAIL_EXIST_CODE
import com.gorkemozgur.flightapp.util.InputValidator
import com.gorkemozgur.flightapp.util.setErrorDisableListener
import kotlinx.android.synthetic.main.fragment_login.airbus
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseFragment() {

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

        emailRegisterId.setErrorDisableListener(emailRegisterLayoutId)
        passwordRegisterId.setErrorDisableListener(passwordRegisterLayoutId)
        nameRegisterId.setErrorDisableListener(nameRegisterLayoutId)
        surnameRegisterId.setErrorDisableListener(surnameRegisterLayoutId)

        signupCloseActionButton.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

        registerButton.setOnClickListener {
            register()
        }

        observeLivedata()
    }

    private fun register() {
        validateLayouts()
        if (nameRegisterLayoutId.isErrorEnabled || passwordRegisterLayoutId.isErrorEnabled || emailRegisterLayoutId.isErrorEnabled || passwordRegisterLayoutId.isErrorEnabled)
            return
        sendRegisterRequest()
    }

    private fun sendRegisterRequest() {
        (activity as LoginActivity).showProgressBar()
        viewModel.sendLoginRequest(
                nameRegisterId.text.toString(),
                surnameRegisterId.text.toString(),
                emailRegisterId.text.toString(),
                passwordRegisterId.text.toString()
        )
    }

    private fun validateLayouts() {
        viewModel.validateLayouts(nameRegisterLayoutId, nameRegisterId)
        viewModel.validateLayouts(surnameRegisterLayoutId, surnameRegisterId)
        viewModel.validateEmailRegex(emailRegisterLayoutId, emailRegisterId)
        viewModel.validatePassword(passwordRegisterLayoutId, passwordRegisterId)
    }

    private fun observeLivedata() {
        viewModel.responseValue.observe(
                viewLifecycleOwner, {

            when (it.status) {
                Status.LOADING -> showProgressBar()

                Status.SUCCESS -> {
                    hideProgressBar()
                    startActivity(Intent(context, Homepage::class.java))
                    this.activity?.finish()
                }

                Status.ERROR -> {
                    hideProgressBar()
                    when (it.message) {
                        EMAIL_EXIST_CODE -> InputValidator().errorAction(emailRegisterLayoutId, getString(R.string.email_exist_message))
                    }
                }
            }
        }
        )
    }
}