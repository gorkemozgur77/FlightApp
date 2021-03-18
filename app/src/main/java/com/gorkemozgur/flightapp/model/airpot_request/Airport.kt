package com.gorkemozgur.flightapp.model.airpot_request

import com.google.gson.annotations.SerializedName

data class Airport(

    @SerializedName("gmt")
    val gmt: String?,

    @SerializedName("iata_code")
    val iata_code: String,

    @SerializedName("city_iata_code")
    val city_iata_code: String,

    @SerializedName("icao_code")
    val icao_code: String,

    @SerializedName("country_iso2")
    val country_iso2: String,

    @SerializedName("geoname_id")
    val geoname_id: String,

    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String,

    @SerializedName("airport_name")
    val airport_name: String,

    @SerializedName("country_name")
    val country_name: String?,

    @SerializedName("phone_number")
    val phone_number: String?,

    @SerializedName("timezone")
    val timezone: String,


)
