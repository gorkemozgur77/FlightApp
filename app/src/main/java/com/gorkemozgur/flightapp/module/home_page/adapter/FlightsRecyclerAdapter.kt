package com.gorkemozgur.flightapp.module.home_page.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.flight.Flight
import com.gorkemozgur.flightapp.util.getDifferenceByHoursAndTime
import kotlinx.android.synthetic.main.flights_recycler_row.view.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FlightsRecyclerAdapter : RecyclerView.Adapter<FlightsRecyclerAdapter.FlightsViewHolder>() {
    class FlightsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var flightList: List<Flight> = listOf()
    private val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("kk:mm", Locale.getDefault())



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.flights_recycler_row, parent, false)
        return FlightsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightsViewHolder, position: Int) {

        holder.itemView.flighstAirlineTextView.text = flightList[position].airline.name
        holder.itemView.flightsCodeTextview.text = flightList[position].flightDetail.iata
        holder.itemView.flightsDepartureAirportTextView.text = flightList[position].departure.iata
        holder.itemView.flightsArrivalAirportTextView.text = flightList[position].arrival.iata
        holder.itemView.flightsDepartureTimeTextView.text = dateFormat.format(date.parse(flightList[position].departure.scheduled)!!)
        holder.itemView.flightsArrivalTimeTextView.text = dateFormat.format(date.parse(flightList[position].arrival.scheduled)!!)
        holder.itemView.flightsDurationTextView.text = getDifferenceByHoursAndTime(date.parse(flightList[position].departure.scheduled)!!, date.parse(flightList[position].arrival.scheduled)!!)

    }

    override fun getItemCount(): Int {
        return flightList.size
    }

    fun updateFlightListItems(flightList: List<Flight>) {
        this.flightList = this.flightList + flightList
        notifyItemRangeInserted(this.flightList.size, flightList.size)
    }


}