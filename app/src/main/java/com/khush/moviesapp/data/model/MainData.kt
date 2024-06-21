package com.khush.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("Search")
    val dataList: List<MainData>?
)

data class MainData(
    @SerializedName("Title")
    val title: String,

    @SerializedName("Poster")
    val poster: String
)