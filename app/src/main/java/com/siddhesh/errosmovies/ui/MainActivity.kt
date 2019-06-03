package com.example.erostest

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.example.erostest.model.MovieResultByDiscoverPopularity
import com.example.erostest.ui.main.SectionsPagerAdapter
import com.selltm.app.networkkotlin.APIRequestsKotalin
import com.siddhesh.errosmovies.R

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs = findViewById<TabLayout>(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Click on heart icon to add movies as Favourite", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }



    }


}