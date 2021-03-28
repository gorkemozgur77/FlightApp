package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName
import com.gorkemozgur.flightapp.model.Pagination

data class FlightResponseModel(
        @SerializedName("pagination")
        val pagination: Pagination,

        @SerializedName("data")
        val flight: List<Flight>
)
