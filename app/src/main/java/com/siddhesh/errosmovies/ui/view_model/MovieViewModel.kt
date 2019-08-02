package com.siddhesh.errosmovies.ui.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.erostest.model.MovieListItem

class MovieViewModel(application: Application) : AndroidViewModel(application) {


    private var repository: MovieRepository
    var allFavMovie: ArrayList<MovieListItem>

    init {
        val movieDao: MovieRoomDB = MovieRoomDB.getAppDatabase(application) as MovieRoomDB
        repository = MovieRepository(movieDao.daoAccess())

        allFavMovie = repository.alFavMovie as ArrayList<MovieListItem>
    }

     fun insert(word: MovieListItem) {
        repository.insertFavMovie(word)
    }
}