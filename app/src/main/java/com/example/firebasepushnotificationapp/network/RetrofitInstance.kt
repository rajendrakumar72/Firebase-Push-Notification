package com.example.firebasepushnotificationapp.network

import com.example.firebasepushnotificationapp.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
      private  val getRetrofitInstance by lazy {
          Retrofit.Builder().baseUrl(BASE_URL)
              .addConverterFactory(GsonConverterFactory.create())
              .build()
      }

        val api by lazy {
            getRetrofitInstance.create(ApiInterface::class.java)
        }
    }
}