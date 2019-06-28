package com.siddhesh.errosmovies.ui.view_model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.erostest.model.MovieListItem


@Database(entities = [MovieListItem::class], version = 1, exportSchema = false)
open abstract class MovieRoomDB : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess

    fun add(){

    }


    companion object {


        @Volatile
        private var INSTANCE: MovieRoomDB? = null

        fun getAppDatabase(context: Context): RoomDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, RoomDatabase::class.java, "Movie")
                    .allowMainThreadQueries()
                    .build() as MovieRoomDB
            }
            return INSTANCE as MovieRoomDB
        }
    }

}