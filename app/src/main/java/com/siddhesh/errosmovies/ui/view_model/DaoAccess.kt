package com.siddhesh.errosmovies.ui.view_model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import com.example.erostest.model.MovieListItem
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Query


@Dao
interface DaoAccess {

    @Insert
    fun insertFavouriteMovie(movieListItem: MovieListItem): Long

    @Query("SELECT * FROM MovieListItem ")
    fun fetchAllFavouriteMovies(): LiveData<ArrayList<MovieListItem>>

}