package com.gorkemozgur.flightapp.model.flight

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlightDetail(

        @SerializedName("number")
        val number: String,

        @SerializedName("iata")
        val iata: String,

        @SerializedName("icao")
        val icao: String,

        @Embedded
        @SerializedName("codeshared")
        val codeshared: Codeshared?
): Serializable
