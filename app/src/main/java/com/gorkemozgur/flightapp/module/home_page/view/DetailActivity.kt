package com.gorkemozgur.flightapp.module.home_page.view

import android.os.Bundle
import android.view.animation.AnimationUtils
import com.gorkemozgur.flightapp.BaseActivity
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.flight.Flight
import com.gorkemozgur.flightapp.util.CustomTimeFunctions
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val customTimeFunctions = CustomTimeFunctions(this)

        val flight = intent.extras?.getSerializable("flight") as Flight

        detailPageAirbus.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.up_down
            )
        )
        detailDepartureAirportIata.text = flight.departure.iata
        detailDepartureAirport.text = flight.departure.airport
        detailDepartureTime.text = customTimeFunctions.getDateByHourMonthDay(flight.departure.scheduled)
        detailArrivalAirportIata.text = flight.arrival.iata
        detailArrivalAirport.text = flight.arrival.airport
        detailArrivalTime.text = customTimeFunctions.getDateByHourMonthDay(flight.arrival.scheduled)
        detailPageCodeTextView.text = flight.flightDetail.iata
        detailPageFlightCode2.text = getString(R.string.flight, flight.flightDetail.iata)
        detailPageTerminalNumber.text = getString(R.string.terminal, flight.departure.terminal)
        detailPageGateNumber.text = getString(R.string.gate, flight.departure.gate)
        detailPageSeatNumber.text = getString(R.string.seat, "20")



        detailPageCloseButton.setOnClickListener {
            onBackPressed()
        }
    }
}