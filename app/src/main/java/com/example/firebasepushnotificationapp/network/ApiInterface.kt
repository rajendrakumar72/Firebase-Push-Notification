package com.example.firebasepushnotificationapp.network

import com.example.firebasepushnotificationapp.model.PushNotification
import com.example.firebasepushnotificationapp.utils.Constants.Companion.CONTENT_TYPE
import com.example.firebasepushnotificationapp.utils.Constants.Companion.SERVER_KEY
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Authorization: key=$SERVER_KEY", "Content-Type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification:PushNotification
    ):Response<ResponseBody>
}