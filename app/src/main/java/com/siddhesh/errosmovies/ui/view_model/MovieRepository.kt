package com.siddhesh.errosmovies.ui.view_model

import androidx.annotation.WorkerThread
import com.example.erostest.model.MovieListItem

class MovieRepository(private var daoAccess: DaoAccess) {

    var alFavMovie = daoAccess.fetchAllFavouriteMovies()

    @WorkerThread
    suspend fun insertFavMovie(movieListItem: MovieListItem) {
        daoAccess.insertFavouriteMovie(movieListItem)

    }

//    fun getAllFavMovies() : LiveData<ArrayList<MovieListItem>>
//    {
//        return daoAccess.fetchAllFavouriteMovies()
//    }
}