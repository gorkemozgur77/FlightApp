package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Aircraft(

    @SerializedName("registration")
    val registration: String,

    @SerializedName("iata")
    val iata: String,

    @SerializedName("icao")
    val icao: String,

    @SerializedName("icao24")
    val icao24: String
): Serializable
