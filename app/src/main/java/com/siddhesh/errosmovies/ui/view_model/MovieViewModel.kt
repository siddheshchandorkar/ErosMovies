package com.siddhesh.errosmovies.ui.view_model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.persistence.room.RoomDatabase

class MovieViewModel(application: Application) :AndroidViewModel(application) {


//    private var repository: MovieRepository

    init {
        val movieDao:RoomDatabase = MovieRoomDB.getAppDatabase(application)
//        repository = MovieRepository(movieDao)
    }
}