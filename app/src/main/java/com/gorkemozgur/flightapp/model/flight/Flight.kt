package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Flight(

        @SerializedName("flight_date")
        val flightDate: String,

        @SerializedName("flight_status")
        val flightStatus: String,

        @SerializedName("departure")
        val departure: Departure,

        @SerializedName("arrival")
        val arrival: Arrival,

        @SerializedName("airline")
        val airline: Airline,

        @SerializedName("flight")
        val flightDetail: FlightDetail,

        @SerializedName("aircraft")
        val aircraft: Aircraft,

        @SerializedName("live")
        val live: Live
): Serializable
