package com.gorkemozgur.flightapp.module.home_page.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.gorkemozgur.flightapp.BaseFragment
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.module.authentication.view.LoginActivity
import com.gorkemozgur.flightapp.module.home_page.viewmodel.ProfileViewModel
import com.gorkemozgur.flightapp.util.FirebaseErrorCodes.EMAIL_EXIST_CODE
import com.gorkemozgur.flightapp.util.setErrorDisableListener
import com.gorkemozgur.flightapp.util.toast
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*
import kotlin.concurrent.schedule


class ProfileFragment : BaseFragment() {

    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getUser()

        airbus.startAnimation(
                AnimationUtils.loadAnimation(
                        context,
                        R.anim.up_down
                )
        )

        profileNestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                (activity as Homepage).fab.hide()
                (activity as Homepage).bottomAppBar.performHide()
            } else if (scrollY < oldScrollY) {
                (activity as Homepage).bottomAppBar.performShow()
                (activity as Homepage).fab.show()
            }
        }

        nameProfileId.setErrorDisableListener(nameProfileLayoutId)
        surnameProfileId.setErrorDisableListener(passwordProfileLayoutId)
        emailProfileId.setErrorDisableListener(emailProfileLayoutId)
        passwordProfileId.setErrorDisableListener(passwordProfileLayoutId)



        uptadeButton.setOnClickListener {
            updateUserCurrentUser()
        }

        logoutButton.setOnClickListener {
            signOut()
        }

        observeLiveData()

    }

    private fun updateUserCurrentUser() {
        validateLayouts()
        if (nameProfileLayoutId.isErrorEnabled || surnameProfileLayoutId.isErrorEnabled || emailProfileLayoutId.isErrorEnabled || passwordProfileLayoutId.isErrorEnabled)
            return
        viewModel.updateUser(nameProfileId.text.toString(), surnameProfileId.text.toString(), emailProfileId.text.toString(), passwordProfileId.text.toString())
        uptadeButton.isEnabled = false
        showProgressBar()
        Timer().schedule(2500) {
            viewModel.getUser()
        }

    }

    private fun signOut() {
        showProgressBar()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(context, LoginActivity::class.java))
        activity?.finish()
    }

    private fun validateLayouts() {
        viewModel.validateLayouts(nameProfileLayoutId, nameProfileId)
        viewModel.validateLayouts(surnameProfileLayoutId, surnameProfileId)
        viewModel.validateEmailRegex(emailProfileLayoutId, emailProfileId)
        viewModel.validatePassword(passwordProfileLayoutId, passwordProfileId)
    }


    private fun observeLiveData() {
        viewModel.user.observe(
                viewLifecycleOwner, {
            val list = it.displayName!!.split(" ")
            appbarUsernameTextfield.text = it.displayName
            nameProfileId.setText(list[0])
            surnameProfileId.setText(list[1])
            emailProfileId.setText(it.email)
            uptadeButton.isEnabled = true
            hideProgressBar()
        }
        )
        viewModel.exception.observe(
                viewLifecycleOwner, {
            if (FirebaseAuthException::class.java.cast(it)?.errorCode == EMAIL_EXIST_CODE)
                toast(getString(R.string.email_exist_message))
        }
        )
    }
}