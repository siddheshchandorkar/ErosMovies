package com.example.erostest.networkkotlin

import com.example.erostest.model.MovieResultByDiscoverPopularity
import com.siddhesh.errosmovies.ui.model.MovieDetails
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @GET("discover/movie")
    fun getFilmByDiscoverPopularity(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Long
    ):
            Call<MovieResultByDiscoverPopularity>

    @GET("movie/{movieId}")
    fun getMovieDetailById( @Path("movieId") movieId: Long,  @Query("api_key") apiKey: String): Call<MovieDetails>

    @GET("search/movie")
    fun getMovieBySearch( @Query("api_key")apiKey:String ,@Query("sort_by") sortBy: String,
                          @Query("query")searchInput: String) : Call<MovieResultByDiscoverPopularity>

}
