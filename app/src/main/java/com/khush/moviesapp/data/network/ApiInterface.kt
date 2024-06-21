package com.khush.moviesapp.data.network

import com.khush.moviesapp.common.Const
import com.khush.moviesapp.data.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(".")
    suspend fun getMoviesData(
        @Query("s") s: String = "army",
        @Query("apikey") apikey: String = Const.API_KEY
    ): ApiResponse
}