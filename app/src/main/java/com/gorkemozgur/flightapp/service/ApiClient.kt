package com.gorkemozgur.flightapp.service

import com.gorkemozgur.flightapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiClient {

    private lateinit var authApiService: ApiService
    private lateinit var notificationApiService: NotificationApiService


    fun getAuthApiService(): ApiService {

        if (!::authApiService.isInitialized) {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            authApiService = retrofit.create(ApiService::class.java)
        }
        return authApiService
    }

    fun getNotificationApiService(): NotificationApiService {
        if (!::notificationApiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.NOTIFICATION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            notificationApiService = retrofit.create(NotificationApiService::class.java)
        }
        return notificationApiService
    }


}