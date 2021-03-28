package com.gorkemozgur.flightapp.module.home_page.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gorkemozgur.flightapp.BaseFragment
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.Status
import com.gorkemozgur.flightapp.model.flight.FlightResponseModel
import com.gorkemozgur.flightapp.module.home_page.adapter.FlightsRecyclerAdapter
import com.gorkemozgur.flightapp.module.home_page.viewmodel.AirportsViewModel
import com.gorkemozgur.flightapp.module.home_page.viewmodel.FlightsViewModel
import com.gorkemozgur.flightapp.service.ApiClient
import com.gorkemozgur.flightapp.util.Constants
import com.gorkemozgur.flightapp.util.toast
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.fragment_flights.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightsFragment : BaseFragment() {

    private val recyclerAdapter = FlightsRecyclerAdapter()
    private lateinit var viewModel: FlightsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flights, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FlightsViewModel::class.java)

        flightListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0)
                    (activity as Homepage).fab.hide()
                else if (dy < 0)
                    (activity as Homepage).fab.show()

                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.sendRequest()
                }
            }
        })

        flightListRecyclerView.layoutManager = LinearLayoutManager(context)
        flightListRecyclerView.adapter = recyclerAdapter
        viewModel.sendRequest()

        observeLiveData()

    }

    private fun observeLiveData(){
        viewModel.flightData.observe(
            viewLifecycleOwner, {
                when(it.status){
                    Status.LOADING -> showProgressBar()
                    Status.SUCCESS -> {
                        hideProgressBar()
                        it.data?.let { it1 -> recyclerAdapter.updateFlightListItems(it1) }
                    }
                    Status.ERROR -> {
                        hideProgressBar()
                        toast("Sonra tekrar deneyin")
                    }
                }
            }
        )

    }
}