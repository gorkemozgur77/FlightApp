package com.gorkemozgur.flightapp.tutorial_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.adapter.TutorialPageViewPagerAdapter
import kotlinx.android.synthetic.main.activity_tutorial_page.*

class TutorialPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_page)

        val fragmentList = arrayListOf<Fragment>(
            TutorialFirstPage(),
            TutorialSecondPage()
        )

        val adapter = TutorialPageViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout,viewPager){ _, _ ->
        }.attach()
    }
}