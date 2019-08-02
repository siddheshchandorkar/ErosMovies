package com.siddhesh.errosmovies.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.erostest.Constants
import com.example.erostest.model.MovieListItem
import com.siddhesh.errosmovies.R
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val context: Context,
    private val iMovieClick: IMovieClick,
    private var alMovie: ArrayList<MovieListItem>
) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_movie_list,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return alMovie.size
//        return 10

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {


        Picasso.with(context)
            .load(Constants.imageBaseURL + alMovie[position].posterPath)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .into(holder.ivMoviePoster)
        holder.rootView.setOnClickListener {
            iMovieClick.movieItemClick(position)

            val options =
                ActivityOptions.makeSceneTransitionAnimation(context as Activity?, holder.ivMoviePoster, "robot")

            var intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(Constants.KEY_MOVIE_ID, alMovie[position].movieId)
            (context as Activity).startActivity(intent, options.toBundle())
        }

        holder.voteCountValue.text = alMovie[position].voteCount.toString()
        holder.popularityValue.text = alMovie[position].popularity.toString()
        holder.movieTitleValue.text = alMovie[position].title
        holder.releaseDateValue.text = alMovie[position].releaseDate

        if (alMovie[position].selected) {
            holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_selected_favorite))
        } else {
            holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unselected_favorite_border))
        }


        holder.ivFav.setOnClickListener {

            iMovieClick.addToFav(position)

//            if (alMovie[position].selected) {
//                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_unselected_favorite_border))
//                alMovie[position].selected = false
//            } else {
//                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_selected_favorite))
//                alMovie[position].selected = true
//            }
//
//            notifyItemChanged(position)

        }

    }


    interface IMovieClick {
        fun movieItemClick(position: Int)
        fun addToFav(position: Int)

    }


    class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {

        var rootView: View = view
        var ivMoviePoster: ImageView = view.findViewById(R.id.iv_movie_poster)
        var ivFav: ImageView = view.findViewById(R.id.iv_fav)
        var voteCountValue: TextView = view.findViewById(R.id.vote_count_value)
        var popularityValue: TextView = view.findViewById(R.id.popularity_value)
        var movieTitleValue: TextView = view.findViewById(R.id.movie_title_value)
        var releaseDateValue: TextView = view.findViewById(R.id.release_date_value)


    }
}