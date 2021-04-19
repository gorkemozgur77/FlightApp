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
import com.gorkemozgur.flightapp.module.authentication.viewmodel.LoginViewModel
import com.gorkemozgur.flightapp.module.home_page.view.Homepage
import com.gorkemozgur.flightapp.util.FirebaseErrorCodes.USER_NOT_FOUND_CODE
import com.gorkemozgur.flightapp.util.FirebaseErrorCodes.WRONG_PASSWORD_CODE
import com.gorkemozgur.flightapp.util.setErrorDisableListener
import com.gorkemozgur.flightapp.util.toast
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : BaseFragment() {

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

        registerPageButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        loginButton.setOnClickListener {
            signIn()

            //e7WnRQUDSWePUemsclsPG8:APA91bFi7jiPi92LtXVYvWjSF4meb2WS0PkvvEVYNGqaUKhqsgeEdsHlqtaSJjazYaaYNosO2F7U4QkZuEa3Tu8IbUU8KJ4Jbz3t3nI5BBhmCwba2dVvumcnXsBtbKv1zcVCmiZhau2W

//            PushNotification(
//                NotificationData("Etoya", "DENEME"),
//                "fuDijt00TE2DPfhjo3ySwD:APA91bE-mT6nSz8EEjjBpiYoBSmmdFzBugZ2IIEvJ3r9ghfYhttsxCu5oR0t0fqHcqbxhkyVQh0_D_wAsr_9IF4DTgxMNadR5_POoTCunsARPGgVghO4FZm4gfpvTCoe-YTLsQit3K7M"
//
//            ).also {
//                sendNotification(it)
//            }


        }

        emailLoginId.setErrorDisableListener(emailLoginLayoutId)
        passwordLoginId.setErrorDisableListener(passwordLoginLayoutId)

        observeLiveData()
    }

    private fun signIn() {
        validateLayouts()
        if (emailLoginLayoutId.isErrorEnabled || passwordLoginLayoutId.isErrorEnabled)
            return
        viewModel.sendLoginRequest(emailLoginId.text.toString(), passwordLoginId.text.toString())
    }

    private fun validateLayouts() {
        viewModel.validateLayouts(emailLoginLayoutId, emailLoginId)
        viewModel.validateLayouts(passwordLoginLayoutId, passwordLoginId)
    }

    private fun observeLiveData() {
        viewModel.responseValue.observe(
            viewLifecycleOwner, {

                when (it.status) {
                    Status.LOADING -> {
                        showProgressBar()
                    }
                    Status.SUCCESS -> {
                        startActivity(Intent(context, Homepage::class.java))
                        (activity as LoginActivity).finish()
                    }
                    Status.ERROR -> {
                        hideProgressBar()
                        when (it.message) {
                            WRONG_PASSWORD_CODE -> toast(getString(R.string.wrong_password))
                            USER_NOT_FOUND_CODE -> toast(getString(R.string.user_not_found))
                        }
                    }
                }
            }
        )
    }

//    private fun sendNotification(notification: PushNotification) =
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response =
//                    ApiClient().getNotificationApiService().postNotification(notification)
//            if(response.isSuccessful) {
//             Log.d(TAG, "Response: ${Gson().toJson(response)}")
//           } else {
//               Log.e(TAG, response.errorBody().toString())
//           }
//            } catch (e: Exception) {
//                Log.e(TAG, e.toString())
//            }
//        }
}