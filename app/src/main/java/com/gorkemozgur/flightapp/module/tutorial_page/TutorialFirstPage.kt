package com.gorkemozgur.flightapp.module.tutorial_page

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.gorkemozgur.flightapp.module.authentication.view.LoginActivity
import com.gorkemozgur.flightapp.R
import kotlinx.android.synthetic.main.fragment_tutorial_first_page.*


class TutorialFirstPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial_first_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        continueButtonID.setOnClickListener {
            viewpager?.currentItem = 1
        }

        skipButtonID.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
    }
}