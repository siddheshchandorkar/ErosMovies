package com.example.erostest.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

public class MovieListItem {


    @SerializedName("vote_count")
    var voteCount: Int = 0
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("video")
    var isVideo: Boolean = false
    @SerializedName("vote_average")
    var voteAverage: Float = 0.0F
    @SerializedName("title")
    var title: String = ""
    @SerializedName("popularity")
    var popularity: Double = 0.toDouble()
    @SerializedName("poster_path")
    var posterPath: String = ""
    @SerializedName("original_language")
    var originalLanguage: String = ""
    @SerializedName("original_title")
    var originalTitle: String = ""
    @SerializedName("backdrop_path")
    var backdropPath: String = ""
    @SerializedName("adult")
    var isAdult: Boolean = false
    @SerializedName("overview")
    var overview: String = ""
    @SerializedName("release_date")
    var releaseDate: String = ""
    @SerializedName("genre_ids")
    var genreIds: List<Int> = ArrayList()

    var selected=false

    override fun toString(): String {
        return title
    }


}
