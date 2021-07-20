package com.example.firebasepushnotificationapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebasepushnotificationapp.firebase.FirebaseService
import com.example.firebasepushnotificationapp.model.NotificationData
import com.example.firebasepushnotificationapp.model.PushNotification
import com.example.firebasepushnotificationapp.network.RetrofitInstance
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic2"
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseService.sharedPref =getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            FirebaseService.token=it!!
            etToken.setText(it)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        btnSend.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val recipientToken = etToken.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()){
                PushNotification(
                    NotificationData(title,message),
                    recipientToken
                ).also {
                    sendNotification(it)
                }
            }
        }


    }

    private fun sendNotification(notification: PushNotification)= CoroutineScope(Dispatchers.IO).launch {
        try {
            val response =RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful){
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            }else{
                Log.d(TAG, "error: ${response.errorBody().toString()} ")
            }
        }catch (e:Exception){
            Log.e(TAG, "sendNotification: ${e.toString()}", )
        }
    }
}