package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naver.maps.geometry.LatLng
import com.shareyacht.shareyacht.retrofit.RetrofitManager

class OwnerPathViewModel : ViewModel() {
    // 경로
    val myPath = mutableListOf<LatLng>()
    private val _getMyPathCompleted = MutableLiveData<Boolean>()
    val getMyPathCompleted: LiveData<Boolean>
        get() = _getMyPathCompleted

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _finishEvent = MutableLiveData<Unit>()
    val finishEvent: LiveData<Unit>
        get() = _finishEvent

    private var reservationID: String? = null

    // 등록된 경로 가져오기
    fun getMyPath(reservationID: String) {
        this.reservationID = reservationID
        RetrofitManager.instance.requestGetPath(reservationID = reservationID) { success, message, data ->
            when (success) {
                0 -> {
                    if (data != null) {
                        parsePath(data)
                    } else {
                        _getMyPathCompleted.value = true
                    }
                }
                else -> {
                    _message.value = message
                }
            }
        }
    }

    // 경로 파싱
    private fun parsePath(data: String) {
        val list = data.split(";")
        for (i in list) {
            val point = i.split(":")
            if (point[0].isNotBlank() && point[1].isNotBlank()) {
                val latLng = LatLng(point[0].toDouble(), point[1].toDouble())
                // 경로에 추가
                myPath.add(latLng)
            }
        }
        _getMyPathCompleted.value = true
    }

    fun addMyPath(data: MutableList<LatLng>) {
        // 순서쌍은 세미콜론으로, 위도, 경도는 콜론으로 구분하여 스트링 생성
        var path = ""
        for (i in data) {
            val points = "${i.latitude}:${i.longitude};"
            path = "$path$points"
        }

        // reservation ID가 있으면
        if (reservationID != null) {
            RetrofitManager.instance.requestAddPath(
                data = path,
                reservationID = reservationID!!
            ) { success, message ->
                when (success) {
                    0 -> {
                        _finishEvent.postValue(Unit)
                    }
                    else -> {
                        _message.value = message
                    }
                }
            }
        } else {
            _message.value = "해당 예약을 찾을 수 없습니다."
        }

    }
}