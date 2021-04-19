package com.gorkemozgur.flightapp.service

import com.gorkemozgur.flightapp.model.notification.PushNotification
import com.gorkemozgur.flightapp.util.Constants.CONTENT_TYPE
import com.gorkemozgur.flightapp.util.Constants.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApiService {
    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>
}