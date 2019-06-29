package com.example.erostest.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erostest.model.MovieListItem
import com.example.erostest.model.MovieResultByDiscoverPopularity
import com.selltm.app.networkkotlin.APIRequestsKotalin
import com.siddhesh.errosmovies.R
import com.siddhesh.errosmovies.ui.view.LoadingDialog
import com.siddhesh.errosmovies.ui.view.MovieAdapter
import com.siddhesh.errosmovies.ui.view.MovieSearchAdapter
import com.siddhesh.errosmovies.ui.view_model.MovieDB
import com.siddhesh.errosmovies.ui.view_model.MovieViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesFragment : Fragment(), MovieAdapter.IMovieClick {
    override fun addToFav(position: Int) {


        var movieViewModel: MovieViewModel = MovieViewModel(activity!!.application)



        Log.d("Siddhesh", "Fav Movies List: " + movieViewModel.allFavMovie)


        /*

        if (!alMovie[position].selected) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Add To Favourite")
            builder.setMessage("Do you want to add '" + alMovie[position] + "' as Favourite movie?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                if (movieDB != null) {

                    if (!movieDB.getAllFavId().toString().contains(alMovie[position].id.toString()))
                        movieDB.insertUser(alMovie[position])
                    Log.d("Siddhesh", "Check DB:" + movieDB.getAllFavId())

                    alMovie[position].selected = true
                    adapter.notifyItemChanged(position)


                }
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

                dialog.dismiss()
            }


            builder.show()
        }else{
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Remove From Favourite")
            builder.setMessage("Do you want to remove '" + alMovie[position] + "' from Favourite movie?")
            //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                if (movieDB != null) {

//                    if (!movieDB.getAllFavId().toString().contains(alMovie[position].id.toString()))
//                        movieDB.insertUser(alMovie[position])
//                    Log.d("Siddhesh", "Check DB:" + movieDB.getAllFavId())

                    movieDB.favoriteDelete(alMovie.get(position).id)

                    alMovie[position].selected = false
                    adapter.notifyItemChanged(position)


                }
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->

                dialog.dismiss()
            }


            builder.show()
        }*/

    }

    private lateinit var root: View;
    private lateinit var etSearch: AutoCompleteTextView;
    private var alMovie: ArrayList<MovieListItem> = ArrayList()
    private var alSearchMovie: ArrayList<MovieListItem> = ArrayList()
    private lateinit var adapter: MovieAdapter
    private lateinit var movieSearchAdapter: MovieSearchAdapter
    private lateinit var progressBar: ProgressBar
    var PAGE_SIZE: Long = 0
    private var nextPage: Long = 1
    private lateinit var layoutManager: LinearLayoutManager
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling: Boolean = false
    private var movieListItem: MovieListItem? = null;
    private lateinit var movieDB: MovieDB
    var index = 0


    override fun movieItemClick(position: Int) {
//        val options = ActivityOptions.makeSceneTransitionAnimation(activity)
//
//        var intent =Intent(activity, MovieDetailsActivity::class.java)
//        intent.putExtra(Constants.KEY_MOVIE_ID, alMovie[position].id)
//        startActivity(intent, options.toBundle())

    }


    private var pageViewModel: PageViewModel? = null
    private lateinit var rcvMovie: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            index = arguments!!.getInt(ARG_SECTION_NUMBER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_main, container, false)
//        val textView = root.findViewById<TextView>(R.id.section_label)
//        pageViewModel!!.text.observe(this, Observer { s -> textView.text = s })

        initialisation()

        movieDB = MovieDB(this!!.activity!!)

        Log.d("Siddhesh", "Check DB:" + movieDB.readAllFavMovie())


        return root
    }

    override fun onResume() {
        super.onResume()

        if (index == 1) {
            alMovie.clear()
            alMovie.addAll(movieDB.readAllFavMovie())
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("WrongConstant")
    private fun initialisation() {


        rcvMovie = root.findViewById(R.id.rcv_movie_list);
        progressBar = root.findViewById(R.id.pb_movie_list);
        etSearch = root.findViewById(R.id.et_search);

        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvMovie.layoutManager = layoutManager
        adapter = MovieAdapter(this!!.context!!, this, alMovie)
        rcvMovie.adapter = adapter
        adapter.notifyDataSetChanged()

        if (index == 1)
            etSearch.visibility = View.GONE
        else {


            rcvMovie.addOnScrollListener(recyclerViewOnScrollListener)


            movieSearchAdapter = MovieSearchAdapter(
                context,
                R.layout.item_movie_search, alSearchMovie
            )

            etSearch.setAdapter(movieSearchAdapter)

            etSearch.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                if (parent.adapter.getItem(position) != null) {
                    movieListItem = parent.adapter.getItem(position) as MovieListItem

                    nextPage = 1
                    PAGE_SIZE = 0

                    alMovie.clear()
                    adapter.notifyDataSetChanged()

                    callAPi(movieListItem!!.title)


                }
            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                    if (p0.toString().isEmpty()) {
                        movieListItem = null
                        nextPage = 1
                        PAGE_SIZE = 0

                        alMovie.clear()
                        adapter.notifyDataSetChanged()

                        callAPi()

                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                if (adapter != null)
//                    adapter!!.filter.filter(p0.toString())
                }
            })
            callAPi()
        }

    }

    private val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            //            super.onScrolled(recyclerView, dx, dy);
            var visibleItemCount = layoutManager.getChildCount()
            var totalItemCount = layoutManager.getItemCount()
            var firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()


            if (!isLoading && nextPage < PAGE_SIZE) {
                if (dy > 0)
                //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount()
                    totalItemCount = layoutManager.getItemCount()
                    firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount - 6) {

//                            progressBar.setVisibility(View.VISIBLE)
                        //                        callOrders();
                        if (movieListItem != null) {
                            callAPi(movieListItem!!.title)
                        } else
                            callAPi()
                    }
                }
            }
        }

    }

    private fun callAPi() {

        if (nextPage.equals(1)) {
            LoadingDialog.with().showLoadingDialog(activity, getString(R.string.loading_movies))
        } else
            progressBar.visibility = View.VISIBLE
        isLoading = true
        APIRequestsKotalin.getMoviesByDiscoverPopularity(getString(R.string.app_key),
            "popularity.desc", nextPage,
            object : Callback<MovieResultByDiscoverPopularity> {
                override fun onFailure(call: Call<MovieResultByDiscoverPopularity>, t: Throwable) {
                    LoadingDialog.with().dialogDismiss()
                    Log.d("Siddhesh", "onFailure: ")

                }

                override fun onResponse(
                    call: Call<MovieResultByDiscoverPopularity>, response:
                    Response<MovieResultByDiscoverPopularity>
                ) {
                    progressBar.visibility = View.GONE

                    LoadingDialog.with().dialogDismiss()

                    Log.d("Siddhesh", "Check: " + response.body()!!.results)

                    if (response.body() != null && response.body()!!.results != null) {
                        LoadingDialog.with().dialogDismiss()

                        nextPage = response.body()!!.page + 1
                        PAGE_SIZE = response.body()!!.totalPages

                        Log.d("Siddhesh", "Check page: " + nextPage + " & " + PAGE_SIZE)
                        var ids = movieDB.getAllFavId().toString()
                        for (i in 0..response.body()!!.results.size - 1) {
                            var movieListItem = response.body()!!.results[i]
                            if (ids.contains(movieListItem.id.toString())) {
                                movieListItem.selected = true

                            }
                            alMovie.add(movieListItem)
                        }

//                        alMovie.addAll(response.body()!!.results)
                        adapter.notifyDataSetChanged()

                        isLoading = false
                    }

                }
            })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            onResume()
        }
    }

    private fun callAPi(searchInput: String) {


        if (nextPage.equals(1)) {
            LoadingDialog.with().showLoadingDialog(activity, getString(R.string.loading_movies))
        } else
            progressBar.visibility = View.VISIBLE
        isLoading = true
        APIRequestsKotalin.getMoviesBySearch(getString(R.string.app_key),
            "popularity.desc", searchInput,
            object : Callback<MovieResultByDiscoverPopularity> {
                override fun onFailure(call: Call<MovieResultByDiscoverPopularity>, t: Throwable) {
                    LoadingDialog.with().dialogDismiss()
                    Log.d("Siddhesh", "onFailure: ")

                }

                override fun onResponse(
                    call: Call<MovieResultByDiscoverPopularity>, response:
                    Response<MovieResultByDiscoverPopularity>
                ) {
                    progressBar.visibility = View.GONE

                    LoadingDialog.with().dialogDismiss()

                    Log.d("Siddhesh", "Check: " + response.body()!!.results)

                    if (response.body() != null && response.body()!!.results != null) {
                        LoadingDialog.with().dialogDismiss()

                        nextPage = response.body()!!.page + 1
                        PAGE_SIZE = response.body()!!.totalPages

                        Log.d("Siddhesh", "Check page: " + nextPage + " & " + PAGE_SIZE)

                        alMovie.addAll(response.body()!!.results)
                        adapter.notifyDataSetChanged()

                        isLoading = false
                    }

                }
            })
    }

    companion object {

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): MoviesFragment {
            val fragment = MoviesFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }
}