package com.gorkemozgur.flightapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gorkemozgur.flightapp.model.airpot_request.Airport
import com.gorkemozgur.flightapp.model.flight.Flight

@Database(entities = [Airport::class, Flight::class], version = 1)
abstract class FlightAppDatabase : RoomDatabase() {

    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var instance: FlightAppDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FlightAppDatabase::class.java, "FlightAppDatabase"
        ).build()
    }
}