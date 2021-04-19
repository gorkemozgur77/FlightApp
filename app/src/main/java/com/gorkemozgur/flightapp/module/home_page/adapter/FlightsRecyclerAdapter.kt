package com.gorkemozgur.flightapp.module.home_page.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.flight.Flight
import com.gorkemozgur.flightapp.module.home_page.view.DetailActivity
import com.gorkemozgur.flightapp.util.CustomTimeFunctions
import kotlinx.android.synthetic.main.flights_recycler_row.view.*

class FlightsRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<FlightsRecyclerAdapter.FlightsViewHolder>() {
    class FlightsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var flightList: List<Flight> = listOf()
    private val customTimeFunctions = CustomTimeFunctions(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.flights_recycler_row, parent, false)
        return FlightsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightsViewHolder, position: Int) {

        val flight = flightList[position]
        holder.itemView.apply {
            flighstAirlineTextView.text = flight.airline.name
            flightsCodeTextview.text = flight.flightDetail.iata
            flightsDepartureAirportTextView.text = flight.departure.iata
            flightsArrivalAirportTextView.text = flight.arrival.iata
            flightsDepartureTimeTextView.text =
                customTimeFunctions.getDateByHourAndMinute(flight.departure.scheduled)
            flightsArrivalTimeTextView.text =
                customTimeFunctions.getDateByHourAndMinute(flight.arrival.scheduled)
            flightsDurationTextView.text =
                customTimeFunctions.getDifferenceByHoursAndTime(
                    flight.departure.scheduled,
                    flight.arrival.scheduled
                )
            flightsRecyclerRelativeLayout.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("flight", flight)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return flightList.size
    }

    fun updateFlightListItems(flightList: List<Flight>) {
        this.flightList = this.flightList + flightList
        notifyItemRangeInserted(this.flightList.size, flightList.size)
    }


}