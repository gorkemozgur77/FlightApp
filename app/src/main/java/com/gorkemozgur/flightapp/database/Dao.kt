package com.gorkemozgur.flightapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gorkemozgur.flightapp.model.airpot_request.Airport
import com.gorkemozgur.flightapp.model.flight.Flight

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllAirports(vararg airport: Airport): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllFlights(vararg airport: Flight): List<Long>

    @Query("SELECT * FROM Airport ORDER BY id")
    suspend fun getAllAirports(): List<Airport>

    @Query("SELECT * FROM Flight ORDER BY id")
    suspend fun getAllFlights(): List<Flight>

    @Query("DELETE FROM Airport")
    suspend fun deleteAllAirports()

    @Query("DELETE FROM Flight")
    suspend fun deleteAllFlights()

}