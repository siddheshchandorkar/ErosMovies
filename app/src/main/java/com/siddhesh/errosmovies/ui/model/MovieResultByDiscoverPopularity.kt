package com.example.erostest.model

import com.google.gson.annotations.SerializedName

class MovieResultByDiscoverPopularity{


    @SerializedName("page")
    var page: Long=0
    @SerializedName("total_results")
    var totalResults: Long=0
    @SerializedName("total_pages")
    var totalPages: Long=0
    @SerializedName("results")
    var results: ArrayList<MovieListItem> = ArrayList()

}