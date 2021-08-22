package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.YachtReservation
import com.shareyacht.shareyacht.retrofit.RetrofitManager

private const val STARTING_PAGE_INDEX = 0

class ReservationViewModel : ViewModel() {
    var page = STARTING_PAGE_INDEX
    val _message: MutableLiveData<String> = MutableLiveData()
    val results: MutableLiveData<List<YachtReservation>> = MutableLiveData()
    private val currentList: MutableList<YachtReservation> = mutableListOf()
    var isEnd = false

    // 예약 내역 목록 불러오기
    init {
        getReservationList()
    }

    fun getReservationList() {
        if(!isEnd) {
            RetrofitManager.instance.requestReservationList(page) { success, message, reservationList ->
                when (success) {
                    0 -> {
                        if (reservationList!!.isNotEmpty()) {
                            currentList.addAll(reservationList)
                            results.value = currentList
                            page++

                            if (reservationList.size < 10) {
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