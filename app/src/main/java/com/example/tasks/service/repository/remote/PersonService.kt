package com.example.tasks.service.repository.remote

import com.example.tasks.service.HeaderModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PersonService {

    @POST("Authentication/Login")
    @FormUrlEncoded
    fun login(@Field("email") email:String,
              @Field("password") password: String
    ): Call<HeaderModel>
}



/**
{
"token": "d+CVvGcmQ+BGHK24T2S9HT7huf33Y8Z630phkYL77F8=",
"personKey": "AOanaFzaY+kqXNN56MsmjatpH5y6yrqq1",
"name": "user_name"
}
*/

