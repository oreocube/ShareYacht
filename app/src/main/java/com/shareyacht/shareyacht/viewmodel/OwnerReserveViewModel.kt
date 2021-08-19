package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.OwnerYachtReservation
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.Constants.STATE_CANCEL
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT

class OwnerReserveViewModel : ViewModel() {
    val _message: MutableLiveData<String> = MutableLiveData()
    val stateWaitList: MutableList<OwnerYachtReservation> = mutableListOf()
    val stateConfirmedList: MutableList<OwnerYachtReservation> = mutableListOf()
    val stateMovingList: MutableList<OwnerYachtReservation> = mutableListOf()
    val stateCancelList: MutableList<OwnerYachtReservation> = mutableListOf()
    val filterCompleted: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getReservationStatus()
    }

    // 예약 현황 가져오기
    private fun getReservationStatus() {
        RetrofitManager.instance.requestOwnerReserve { success, message, reservationList ->
            when (success) {
                0 -> {
                    if (reservationList!!.isNotEmpty()) {
                        // status 에 맞게 분류하기
                        categorizeList(reservationList)
                    }
                }
                1 -> {
                    _message.value = message
                }
            }

        }
    }

    // Status 에 따라 분류하기
    private fun categorizeList(list: List<OwnerYachtReservation>) {
        for (res in list) {
            when (res.status) {
                STATE_WAIT -> {
                    stateWaitList.add(res)
                }
                STATE_CONFIRMED -> {
                    stateConfirmedList.add(res)
                }
                STATE_MOVING -> {
                    stateMovingList.add(res)
                }
                STATE_CANCEL -> {
                    stateCancelList.add(res)
                }
            }
        }
        filterCompleted.value = true
    }

}