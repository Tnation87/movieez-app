package com.example.common.data.models

import com.google.gson.annotations.SerializedName

data class MovieDataModel(
    val id: Int,
    val title: String,
    @SerializedName("release_date") val releaseDate: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String
) : DataModel
