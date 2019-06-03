package com.example.erostest.ui.main

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val mIndex = MutableLiveData<Int>()
    val text = Transformations.map(mIndex) { input -> "Hello world from section: " + input!! }

    fun setIndex(index: Int) {
        mIndex.value = index
    }
}