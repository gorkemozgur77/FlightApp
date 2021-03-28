package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName

data class Codeshared(

    @SerializedName("airline_name")
    val airlineName: String,

    @SerializedName("airline_iata")
    val airlineIata: String,

    @SerializedName("airline_icao")
    val airlineIcao: String,

    @SerializedName("flight_number")
    val flightNumber: String,

    @SerializedName("flight_iata")
    val flightIata: String,

    @SerializedName("flight_icao")
    val flightIcao: String
)
