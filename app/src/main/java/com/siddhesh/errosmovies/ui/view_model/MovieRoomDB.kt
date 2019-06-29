package com.siddhesh.errosmovies.ui.view_model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.erostest.model.MovieListItem


@Database(entities = [MovieListItem::class], version = 1, exportSchema = false)
open abstract class MovieRoomDB : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess

    fun add() {

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