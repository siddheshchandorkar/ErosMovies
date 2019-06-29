package com.example.erostest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "favourite_movies")
public class MovieListItem {


    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("vote_count")
    var voteCount: Int = 0
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
//    @SerializedName("genre_ids")
//    var genreIds: List<Int> = ArrayList()

    var selected = false

//    @ColumnInfo(name = "created_at")
//    @TypeConverters({TimestampConverter::class})
//    private val createdAt: Date? = null


    override fun toString(): String {
        return title
    }


}
