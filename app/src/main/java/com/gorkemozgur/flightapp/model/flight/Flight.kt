package com.gorkemozgur.flightapp.model.flight

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Flight(

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Int,

  @ColumnInfo(name = "flight_date")
  @SerializedName("flight_date")
  val flightDate: String,

  @ColumnInfo(name = "flight_status")
  @SerializedName("flight_status")
  val flightStatus: String,

  @Embedded(prefix = "departure_")
  @SerializedName("departure")
  val departure: Departure,

  @Embedded(prefix = "arrival_")
  @SerializedName("arrival")
  val arrival: Arrival,

  @Embedded(prefix = "airline_")
  @SerializedName("airline")
  val airline: Airline,

  @Embedded(prefix = "flightDetail_")
  @SerializedName("flight")
  val flightDetail: FlightDetail,

  @Embedded
  @SerializedName("aircraft")
  val aircraft: Aircraft?,

  @Embedded
  @SerializedName("live")
  val live: Live?
) : Serializable
