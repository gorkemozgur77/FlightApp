package com.gorkemozgur.flightapp.model.flight

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Live(

    @SerializedName("updated")
    val updated: String,

    @SerializedName("latitude")
    val latitude: Float,

    @SerializedName("longitude")
    val longitude: Float,

    @SerializedName("altitude")
    val altitude: Float,

    @SerializedName("direction")
    val direction: Int,

    @SerializedName("speed_horizontal")
    val speedHorizontal: Float,

    @SerializedName("speed_vertical")
    val speedVertical: Float,

    @SerializedName("is_ground")
    val isGround: Boolean
): Serializable
