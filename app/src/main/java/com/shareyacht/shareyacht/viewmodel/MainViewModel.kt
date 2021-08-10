package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.retrofit.RetrofitManager

private const val STARTING_PAGE_INDEX = 0

class MainViewModel : ViewModel() {

    var page = STARTING_PAGE_INDEX
    val _message: MutableLiveData<String> = MutableLiveData()
    val results: MutableLiveData<List<Yacht>> = MutableLiveData()
    private val currentList: MutableList<Yacht> = mutableListOf()
    var isEnd = false

    fun getYachtList() {
        if (!isEnd) {
            RetrofitManager.instance.requestYachtList(page) { success, message, yachtList ->
                when (success) {
                    0 -> {
                        if (yachtList!!.isNotEmpty()) {
                            currentList.addAll(yachtList)
                            results.value = currentList
                            page++

                            if (yachtList.size < 10) {
                                isEnd = true
                            }
                        }
                    }
                    1 -> {
                        _message.value = message
                    }
                }
            }
        }
    }

}