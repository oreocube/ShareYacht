package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.Passenger
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.getNowTime

class PassengerViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _list = MutableLiveData<List<Passenger>>()
    val list: LiveData<List<Passenger>>
        get() = _list

    private var reservationID: String? = null

    private val _passengerUpdateEvent = MutableLiveData<Boolean>()
    val passengerUpdateEvent: LiveData<Boolean>
        get() = _passengerUpdateEvent

    // 탑승자 명단 조회하기
    fun getPassengerList(reservationID: String) {
        // reservation ID 저장하기
        if (this.reservationID == null) {
            this.reservationID = reservationID
        }
        // 탑승자 명단 요청
        RetrofitManager.instance.requestGetPassenger(reservationID = reservationID)
        { success, message, data ->
            when (success) {
                0 -> {
                    _list.value = data
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }

    // 탑승자 등록
    fun addPassenger(id: String) {
        RetrofitManager.instance.requestAddPassenger(
            reservationID = reservationID!!,
            userID = id,
            embarkTime = getNowTime()
        ) { success, message ->
            when (success) {
                0 -> {
                    // 성공
                    _passengerUpdateEvent.value = true
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }
}