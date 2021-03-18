package com.gorkemozgur.flightapp.module.home_page.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gorkemozgur.flightapp.R
import com.gorkemozgur.flightapp.model.airpot_request.Airport
import kotlinx.android.synthetic.main.airports_recycler_row.view.*

class AirportsRecyclerAdapter: RecyclerView.Adapter<AirportsRecyclerAdapter.AirportViewHolder>() {
    class AirportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private var airportList: List<Airport> = listOf()
    lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.airports_recycler_row, parent, false)

        context = parent.context

        return AirportViewHolder(view)
    }

    override fun onBindViewHolder(holder: AirportViewHolder, position: Int) {
        holder.itemView.airportCodeTextView.text = airportList[position].iata_code
        holder.itemView.airportCountryTextView.text = airportList[position].country_name
        holder.itemView.airportNameTextView.text = airportList[position].airport_name

        holder.itemView.googleLocationButton.setOnClickListener {
            val map = "http://maps.google.com/maps?q=loc:${airportList[position].latitude},${airportList[position].longitude} (${airportList[position].airport_name})"
           // "http://maps.google.com/maps?q=loc:" + latitude + "," + longitude + " (" + yourLocationName + ")"
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(map)))
        }

        holder.itemView.callButton.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+16088441086")))
        }
    }

    override fun getItemCount(): Int {
        return airportList.size
    }

    fun updateAirportListItems(airportList: List<Airport>){
        this.airportList = this.airportList + airportList
        notifyItemRangeInserted(this.airportList.size, airportList.size)
    }


}