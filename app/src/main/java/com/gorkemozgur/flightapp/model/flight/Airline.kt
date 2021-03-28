package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName

data class Airline(

        @SerializedName("name")
        val name: String,

        @SerializedName("iata")
        val iata: String,

        @SerializedName("icao")
        val icao: String
)
