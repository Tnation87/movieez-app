package com.example.common.data.models

import com.google.gson.annotations.SerializedName

data class MoviesPageDataModel(
    @SerializedName("page") val currentPage: Int,
    @SerializedName("results") val movies: List<MovieDataModel>,
    @SerializedName("total_pages") val totalNumberOfPages: Int
) : DataModel
