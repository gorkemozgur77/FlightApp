package com.gorkemozgur.flightapp.module.home_page.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.flight.Flight
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val dateFormat = SimpleDateFormat("EEE, d MMM yyyy\nkk:mm", Locale.getDefault())
        val flight = intent.extras?.getSerializable("flight") as Flight

        detailPageAirbus.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.up_down
            )
        )
        detailDepartureAirportIata.text = flight.departure.iata
        detailDepartureAirport.text = flight.departure.airport
        detailDepartureTime.text = dateFormat.format(date.parse(flight.departure.scheduled)!!)
        detailArrivalAirportIata.text = flight.arrival.iata
        detailArrivalAirport.text = flight.arrival.airport
        detailArrivalTime.text = dateFormat.format(date.parse(flight.arrival.scheduled)!!)
        detailPageCodeTextView.text = flight.flightDetail.iata
        detailPageFlightCode2.text = "Flight: "+ flight.flightDetail.iata
        detailPageTerminalNumber.text = "Terminal: " + flight.departure.terminal
        detailPageGateNumber.text = "Gate: " + flight.departure.gate
        detailPageSeatNumber.text = "Seat: 20"


        detailPageCloseButton.setOnClickListener {
            onBackPressed()
        }
    }
}