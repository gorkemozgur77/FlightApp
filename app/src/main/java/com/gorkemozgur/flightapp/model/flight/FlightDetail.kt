package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName

data class FlightDetail(

        @SerializedName("number")
        val number: String,

        @SerializedName("iata")
        val iata: String,

        @SerializedName("icao")
        val icao: String,

        @SerializedName("codeshared")
        val codeshared: Codeshared
)
