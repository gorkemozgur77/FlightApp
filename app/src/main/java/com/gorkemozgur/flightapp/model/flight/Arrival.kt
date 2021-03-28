package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Arrival(
        @SerializedName("airport")
        val airport: String,

        @SerializedName("timezone")
        val timezone: String,

        @SerializedName("iata")
        val iata: String,

        @SerializedName("terminal")
        val terminal: String,

        @SerializedName("gate")
        val gate: String,

        @SerializedName("delay")
        val delay: String,

        @SerializedName("scheduled")
        val scheduled: String,

        @SerializedName("estimated")
        val estimated: String,

        @SerializedName("actual")
        val actual: String,

        @SerializedName("estimated_runway")
        val estimatedRunway: String,

        @SerializedName("actual_runway")
        val actualRunway: String
): Serializable
