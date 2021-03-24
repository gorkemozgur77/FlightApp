package com.gorkemozgur.flightapp.model.airpot_request

import com.google.gson.annotations.SerializedName
import com.gorkemozgur.flightapp.model.Pagination

data class AirportResponseModel(

        @SerializedName("pagination")
        val pagination: Pagination,

        @SerializedName("data")
        val airport: List<Airport>

)
