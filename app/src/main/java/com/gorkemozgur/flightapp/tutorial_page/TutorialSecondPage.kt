package com.gorkemozgur.flightapp.tutorial_page

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gorkemozgur.flightapp.LoginActivity
import com.gorkemozgur.flightapp.R
import kotlinx.android.synthetic.main.fragment_tutorial_second_page.*

class TutorialSecondPage : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial_second_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        finishButtonID.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}