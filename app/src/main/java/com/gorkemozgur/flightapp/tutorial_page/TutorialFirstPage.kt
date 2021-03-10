package com.gorkemozgur.flightapp.tutorial_page

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.gorkemozgur.flightapp.LoginActivity
import com.gorkemozgur.flightapp.R
import kotlinx.android.synthetic.main.activity_tutorial_page.*
import kotlinx.android.synthetic.main.fragment_tutorial_first_page.*


class TutorialFirstPage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tutorial_first_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewp = activity?.findViewById<ViewPager2>(R.id.viewPager)

        continueButtonID.setOnClickListener {
            viewp?.currentItem = 1
        }

        skipButtonID.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }

    }


}