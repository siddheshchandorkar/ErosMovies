package com.siddhesh.errosmovies.ui.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.erostest.Constants
import com.selltm.app.networkkotlin.APIRequestsKotalin
import com.siddhesh.errosmovies.R
import com.siddhesh.errosmovies.databinding.ActivityMovieDetailsBinding
import com.siddhesh.errosmovies.ui.model.MovieDetails
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MovieDetailsActivity : AppCompatActivity() {

    private var movieId: Long = 0
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_movie_details)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)


        var bundle: Bundle = intent.extras
        if (bundle != null) {
            if (bundle.containsKey(Constants.KEY_MOVIE_ID)) {
                movieId = bundle.getLong(Constants.KEY_MOVIE_ID)
            }
        }

        if (!movieId.equals(0))
            callAPi(binding)

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

    private fun callAPi(binding: ActivityMovieDetailsBinding) {


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


                        setUIDetails(response.body()!!, binding)
                    }

                }
            })
    }

    private fun setUIDetails(movieDetails: MovieDetails, binding: ActivityMovieDetailsBinding) {


        supportPostponeEnterTransition()
//        findViewById<ImageView>(R.movieId.iv_movie_poster).getViewTreeObserver().addOnPreDrawListener(
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
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(findViewById<ImageView>(R.id.iv_movie_poster))


//        binding.setVariable(com.siddhesh.errosmovies.BR.movi)
//        binding.setVariable(BR.user, movieDetails)
        binding.movieDetails = movieDetails
        binding.executePendingBindings()

//        findViewById<TextView>(R.movieId.tv_overview).text = movieDetails.overview
//        findViewById<TextView>(R.movieId.tv_title).text = movieDetails.title+"("+movieDetails.releaseDate.subSequence(0, 4)+")"
//        findViewById<TextView>(R.movieId.tv_tags).text = movieDetails.genres.toString().replace("[","").replace("]", "")
//        findViewById<TextView>(R.movieId.tv_production_company).text = movieDetails.productionCompanies.toString().replace("[","").replace("]", "")
//        findViewById<TextView>(R.movieId.tv_production_country).text = movieDetails.productionCountries.toString().replace("[","").replace("]", "")
//        findViewById<TextView>(R.movieId.tv_SpokenLanguages).text = movieDetails.spokenLanguages.toString().replace("[","").replace("]", "")

//        val hours = movieDetails.runtime / 60
//        val minutes = movieDetails.runtime % 60
//        findViewById<TextView>(R.movieId.tv_duration).text = String.format("%d hr %02d min", hours, minutes)

    }
}