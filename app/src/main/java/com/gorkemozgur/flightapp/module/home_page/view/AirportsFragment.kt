package com.gorkemozgur.flightapp.module.home_page.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.module.home_page.adapter.AirportsRecyclerAdapter
import com.gorkemozgur.flightapp.module.home_page.viewmodel.AirportsViewModel
import kotlinx.android.synthetic.main.fragment_airports.*


class AirportsFragment : Fragment() {

    private val recyclerAdapter = AirportsRecyclerAdapter()
    private lateinit var viewModel: AirportsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_airports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        airportListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                        (activity as Homepage).showOrHide(false)

                    else if (dy < 0)
                            (activity as Homepage).showOrHide(true)

                if(!recyclerView.canScrollVertically(1))
                    viewModel.sendRequest()
            }
        })

        viewModel = ViewModelProviders.of(this).get(AirportsViewModel::class.java)
        viewModel.sendRequest()

        airportListRecyclerView.layoutManager = GridLayoutManager(context,2)
        airportListRecyclerView.adapter = recyclerAdapter


        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.airports.observe(
            viewLifecycleOwner, {
                recyclerAdapter.updateAirportListItems(it)
            }
        )
    }
}