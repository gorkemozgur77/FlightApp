package com.gorkemozgur.flightapp.module.home_page.view

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.gorkemozgur.flightapp.BaseActivity
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.util.toast
import kotlinx.android.synthetic.main.activity_homepage.*

class Homepage : BaseActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        selectFragment(AirportsFragment())
        mAuth = FirebaseAuth.getInstance()

        home_page_bottomNavigationViewId.background = null
        home_page_bottomNavigationViewId.menu.getItem(1).isEnabled = false

        home_page_bottomNavigationViewId.setOnNavigationItemSelectedListener { it1 ->
            when (it1.itemId) {
                R.id.airports -> selectFragment(AirportsFragment())
                R.id.profile -> selectFragment(ProfileFragment())
            }
            fab.setColorFilter(ContextCompat.getColor(applicationContext, R.color.inactive_color))
            fab.isClickable = true
            true

        }

        home_page_bottomNavigationViewId.setOnNavigationItemReselectedListener {

        }

        fab.setOnClickListener {
            home_page_bottomNavigationViewId.menu.getItem(1).isChecked = true
            fab.setColorFilter(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.flight_appbar_blue
                )
            )
            selectFragment(FlightsFragment())
            fab.isClickable = false

        }
    }

    private fun selectFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
            replace(R.id.frameLayId, fragment)
            commit()
        }
    }
}