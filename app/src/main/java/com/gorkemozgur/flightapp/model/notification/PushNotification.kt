package com.gorkemozgur.flightapp.model.notification

data class PushNotification(
    val data: NotificationData,
    val to: String
)