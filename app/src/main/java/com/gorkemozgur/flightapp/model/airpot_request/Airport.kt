package com.gorkemozgur.flightapp.model.airpot_request

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Airport(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Int,

        @ColumnInfo(name = "offset")
        var offset: Int,

        @ColumnInfo(name = "gmt")
        @SerializedName("gmt")
        val gmt: String?,

        @ColumnInfo(name = "iata_code")
        @SerializedName("iata_code")
        val iata_code: String,

        @ColumnInfo(name = "city_iata_code")
        @SerializedName("city_iata_code")
        val city_iata_code: String,

        @ColumnInfo(name = "icao_code")
        @SerializedName("icao_code")
        val icao_code: String,

        @ColumnInfo(name = "country_iso2")
        @SerializedName("country_iso2")
        val country_iso2: String,

        @ColumnInfo(name = "geoname_id")
        @SerializedName("geoname_id")
        val geoname_id: String?,

        @ColumnInfo(name = "latitude")
        @SerializedName("latitude")
        val latitude: String,

        @ColumnInfo(name = "longitude")
        @SerializedName("longitude")
        val longitude: String,

        @ColumnInfo(name = "airport_name")
        @SerializedName("airport_name")
        val airport_name: String,

        @ColumnInfo(name = "country_name")
        @SerializedName("country_name")
        val country_name: String?,

        @ColumnInfo(name = "phone_number")
        @SerializedName("phone_number")
        val phone_number: String?,

        @ColumnInfo(name = "timezone")
        @SerializedName("timezone")
        val timezone: String,
)
