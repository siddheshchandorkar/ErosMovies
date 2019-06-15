package com.siddhesh.errosmovies.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.erostest.Constants
import com.selltm.app.networkkotlin.APIRequestsKotalin
import com.siddhesh.errosmovies.R
import com.siddhesh.errosmovies.ui.model.MovieDetails
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.MenuItem
import android.view.ViewTreeObserver




class MovieDetailsActivity : AppCompatActivity() {

    private var movieId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)

        var bundle: Bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(Constants.KEY_MOVIE_ID)) {
                movieId = bundle.getLong(Constants.KEY_MOVIE_ID)
            }
        }

        if (!movieId.equals(0))
            callAPi()

        findViewById<ImageView>(R.id.iv_back).setOnClickListener { onBackPressed() }
    }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callAPi() {


        APIRequestsKotalin.getMovieDetails(
            movieId, getString(R.string.app_key),
            object : Callback<MovieDetails> {
                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    LoadingDialog.with().dialogDismiss()
                    Log.d("Siddhesh", "onFailure: ")

                }

                override fun onResponse(
                    call: Call<MovieDetails>, response:
                    Response<MovieDetails>
                ) {

                    LoadingDialog.with().dialogDismiss()

                    Log.d("MovieDetailsActivity", "Check: " + response.body())

                    if (response.body() != null && response.body()!! != null) {
                        LoadingDialog.with().dialogDismiss()


                        setUIDetails(response.body()!!)
                    }

                }
            })
    }

    private fun setUIDetails(movieDetails: MovieDetails) {


        supportPostponeEnterTransition()
//        findViewById<ImageView>(R.id.iv_movie_poster).getViewTreeObserver().addOnPreDrawListener(
//            object : ViewTreeObserver.OnPreDrawListener {
//                override fun onPreDraw(): Boolean {
//                    ivBackdrop.getViewTreeObserver().removeOnPreDrawListener(this)
//                    supportStartPostponedEnterTransition()
//                    return true
//                }
//            }
//        )
        Picasso.with(this)
            .load(Constants.imageBaseURL + movieDetails.posterPath)

            .into(findViewById<ImageView>(R.id.iv_movie_poster))


        findViewById<TextView>(R.id.tv_overview).text = movieDetails.overview
        findViewById<TextView>(R.id.tv_title).text = movieDetails.title+"("+movieDetails.releaseDate.subSequence(0, 4)+")"
        findViewById<TextView>(R.id.tv_tags).text = movieDetails.genres.toString().replace("[","").replace("]", "")
        findViewById<TextView>(R.id.tv_production_company).text = movieDetails.productionCompanies.toString().replace("[","").replace("]", "")
        findViewById<TextView>(R.id.tv_production_country).text = movieDetails.productionCountries.toString().replace("[","").replace("]", "")
        findViewById<TextView>(R.id.tv_SpokenLanguages).text = movieDetails.spokenLanguages.toString().replace("[","").replace("]", "")

        val hours = movieDetails.runtime / 60
        val minutes = movieDetails.runtime % 60
        findViewById<TextView>(R.id.tv_duration).text = String.format("%d hr %02d min", hours, minutes)

    }
}