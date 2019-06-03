package com.siddhesh.errosmovies.ui

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.erostest.model.MovieListItem

class MovieDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_FAVOURITE_MOVIE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_MOVIE_ID + " LONG,"
                + COLUMN_VOTE_COUNT + " INTEGER " +
                "," + COLUMN_VOTE_AVERAGE + " FLOAT " +
                "," + COLUMN_TITLE + " TEXT" +
                "," + COLUMN_POPULARITY + " FLOAT" +
                "," + COLUMN_POSTER_PATH + " TEXT" +
                "," + COLUMN_OVERVIEW + " TEXT" +
                "," + COLUMN_RELEASE_DATE + " TEXT" +
                "," + COLUMN_GENRE_IDS + " TEXT)" )
        db.execSQL(CREATE_PRODUCTS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

     @Throws(SQLiteConstraintException::class)
     fun insertUser(movieListItem: MovieListItem): Boolean {
         val db = writableDatabase

         val values = ContentValues()
         values.put(COLUMN_MOVIE_ID, movieListItem.id)
         values.put(MovieDB.COLUMN_VOTE_COUNT, movieListItem.voteCount)
         values.put(MovieDB.COLUMN_VOTE_AVERAGE, movieListItem.voteAverage)
         values.put(MovieDB.COLUMN_TITLE, movieListItem.title)
         values.put(MovieDB.COLUMN_POPULARITY, movieListItem.popularity)
         values.put(MovieDB.COLUMN_POSTER_PATH, movieListItem.posterPath)
         values.put(MovieDB.COLUMN_OVERVIEW, movieListItem.overview)
         values.put(MovieDB.COLUMN_RELEASE_DATE, movieListItem.releaseDate)
         val newRowId = db.insert(TABLE_FAVOURITE_MOVIE, null, values)

         return true
     }

    fun readAllFavMovie(): ArrayList<MovieListItem> {
        val movies = ArrayList<MovieListItem>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + TABLE_FAVOURITE_MOVIE+" order by "+ COLUMN_ID+" desc", null)
        } catch (e: SQLiteException) {
//            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }


        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val movieListItem = MovieListItem()
                movieListItem.id = cursor.getLong(cursor.getColumnIndex(COLUMN_MOVIE_ID))
                movieListItem.voteCount = cursor.getInt(cursor.getColumnIndex(COLUMN_VOTE_COUNT))
                movieListItem.voteAverage = cursor.getFloat(cursor.getColumnIndex(COLUMN_VOTE_AVERAGE))
                movieListItem.title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
                movieListItem.popularity = cursor.getFloat(cursor.getColumnIndex(COLUMN_POPULARITY)).toDouble()
                movieListItem.posterPath = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH))
                movieListItem.overview = cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW))
                movieListItem.releaseDate = cursor.getString(cursor.getColumnIndex(COLUMN_RELEASE_DATE))
                movieListItem.selected=true
//                movieListItem.genreIds = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH))


                movies.add(movieListItem)
                cursor.moveToNext()
            }
        }
        return movies
    }
    fun getAllFavId(): ArrayList<Long> {
        val movies = ArrayList<Long>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select "+ COLUMN_MOVIE_ID+" from " + TABLE_FAVOURITE_MOVIE, null)
        } catch (e: SQLiteException) {
//            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }


        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {

                movies.add(cursor.getLong(cursor.getColumnIndex(COLUMN_MOVIE_ID)))
                cursor.moveToNext()
            }
        }
        return movies
    }



        companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "theMovieDB.db"
        val TABLE_FAVOURITE_MOVIE = "favourite_movies"

        val COLUMN_ID = "_id"
        val COLUMN_MOVIE_ID = "id"
        val COLUMN_VOTE_COUNT = "vote_count"
        val COLUMN_VOTE_AVERAGE = "vote_average"
        val COLUMN_TITLE = "title"
        val COLUMN_POPULARITY = "popularity"
        val COLUMN_POSTER_PATH = "poster_path"
        val COLUMN_OVERVIEW = "overview"
        val COLUMN_RELEASE_DATE = "release_date"
        val COLUMN_GENRE_IDS = "genre_ids"


    }


}