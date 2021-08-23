package com.shareyacht.shareyacht.viewmodel

import android.util.Log
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

    fun getMyPath() {
        RetrofitManager.instance.requestGetPath { success, message, data ->
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

    private fun parsePath(data: String) {
        // 순서쌍은 세미콜론으로 구분
        val list = data.split(";")
        // 위도, 경도는 콜론으로 구분
        for (i in list) {
            val point = i.split(":")
            if(point[0].isNotBlank() && point[1].isNotBlank()) {
                Log.d("태그", "lat : ${point[0].toDouble()}, lon : ${point[1].toDouble()}")
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

        RetrofitManager.instance.requestAddPath(path) { success, message ->
            when (success) {
                0 -> {
                    _finishEvent.postValue(Unit)
                }
                else -> {
                    _message.value = message
                }
            }
        }
    }
}