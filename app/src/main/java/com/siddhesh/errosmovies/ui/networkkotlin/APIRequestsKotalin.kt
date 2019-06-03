package com.selltm.app.networkkotlin

import com.example.erostest.model.MovieResultByDiscoverPopularity
import com.example.erostest.networkkotlin.ApiInterface
import com.siddhesh.errosmovies.ui.model.MovieDetails
import mekotlinapps.dnyaneshwar.`in`.restdemo.rest.APIClient
import retrofit2.Callback
import retrofit2.http.Query


class APIRequestsKotalin {


    companion object {

        fun getMoviesByDiscoverPopularity(
            apiKey: String,
            sortBy: String,
            page: Long,
            callback: Callback<MovieResultByDiscoverPopularity>
        ) {
            val apiServices = APIClient.client.create(ApiInterface::class.java)
            val call = apiServices.getFilmByDiscoverPopularity(apiKey, sortBy, page)
            call.enqueue(callback)
        }
        fun getMoviesBySearch(
            apiKey: String,
            sortBy: String,
            searchInput: String,
            callback: Callback<MovieResultByDiscoverPopularity>
        ) {
            val apiServices = APIClient.client.create(ApiInterface::class.java)
            val call = apiServices.getMovieBySearch(apiKey, sortBy, searchInput)
            call.enqueue(callback)
        }

        fun getMovieDetails(
            movieId: Long,
            apiKey: String,
            callback: Callback<MovieDetails>
        ) {
            val apiServices = APIClient.client.create(ApiInterface::class.java)
            val call = apiServices.getMovieDetailById(movieId, apiKey)
            call.enqueue(callback)
        }


    }


}
