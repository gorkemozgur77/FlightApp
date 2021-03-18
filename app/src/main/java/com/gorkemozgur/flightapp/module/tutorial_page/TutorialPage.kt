package com.gorkemozgur.flightapp.module.tutorial_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.module.tutorial_page.adapter.TutorialPageViewPagerAdapter
import kotlinx.android.synthetic.main.activity_tutorial_page.*

class TutorialPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial_page)

        val fragmentList = arrayListOf(
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