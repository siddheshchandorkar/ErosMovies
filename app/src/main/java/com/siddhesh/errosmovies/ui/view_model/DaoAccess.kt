package com.siddhesh.errosmovies.ui.view_model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.erostest.model.MovieListItem


@Dao
interface DaoAccess {

    @Insert
    fun insertFavouriteMovie(movieListItem: MovieListItem): Long

    @Query("SELECT * FROM MovieListItem ")
    fun fetchAllFavouriteMovies(): LiveData<ArrayList<MovieListItem>>

}