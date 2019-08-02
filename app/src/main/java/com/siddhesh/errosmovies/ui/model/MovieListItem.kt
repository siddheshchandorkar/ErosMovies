package com.example.erostest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "movie_list_item")
public class MovieListItem {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "movie_id")
    @SerializedName("movieId")
    var movieId: Long = 0

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    var voteCount: Int = 0

    @ColumnInfo(name = "video")
    @SerializedName("video")
    var isVideo: Boolean = false

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Float = 0.0F

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String = ""

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    var popularity: Double = 0.toDouble()

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String = ""

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    var originalLanguage: String = ""

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var originalTitle: String = ""

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String = ""

    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    var isAdult: Boolean = false

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String = ""


    @ColumnInfo(name = "release_date")
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
