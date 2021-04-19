package com.gorkemozgur.flightapp.module.home_page.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gorkemozgur.flightapp.BaseFragment
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.Status
import com.gorkemozgur.flightapp.module.home_page.adapter.AirportsRecyclerAdapter
import com.gorkemozgur.flightapp.module.home_page.viewmodel.AirportsViewModel
import com.gorkemozgur.flightapp.util.toast
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.fragment_airports.*


class AirportsFragment : BaseFragment() {

    private val recyclerAdapter = AirportsRecyclerAdapter()
    private lateinit var viewModel: AirportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_airports, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AirportsViewModel::class.java)

        airportListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        viewModel.refreshData()
        airportListRecyclerView.layoutManager = GridLayoutManager(context, 2)
        airportListRecyclerView.adapter = recyclerAdapter


        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.airportsData.observe(
            viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.LOADING -> {
                        showProgressBar()
                    }
                    Status.SUCCESS -> {
                        hideProgressBar()
                        it.data?.let { it1 -> recyclerAdapter.updateAirportListItems(it1) }
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