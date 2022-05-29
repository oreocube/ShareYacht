package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.ReqStatus
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.API

class OwnerMainViewModel : ViewModel() {

    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var myYacht: MutableLiveData<Yacht> = MutableLiveData()
    val hasYacht: MutableLiveData<Boolean> = MutableLiveData()
    val _message: MutableLiveData<String> = MutableLiveData()
    private val _myStatus = MutableLiveData<ReqStatus>()
    val myStatus: LiveData<ReqStatus>
        get() = _myStatus

    init {
        requestMyYacht()
        requestMyStatus()
    }

    // 내 요트 가져오기
    fun requestMyYacht() {
        RetrofitManager.instance.requestMyYacht { success, message, yacht ->
            when (success) {
                0 -> {
                    if (yacht != null) {
                        hasYacht.value = true
                        imageUrl.value = "${API.BASE_URL}/image/${yacht.imageid}"
                        myYacht.value = yacht
                    } else {
                        hasYacht.value = false
                        _message.value = "아직 요트가 없습니다."
                    }
                }
                else -> {
                    _message.value = message
                }
            }
        }
    }

    fun requestMyStatus() {
        RetrofitManager.instance.requestReservationStatus { success, message, data ->
            when (success) {
                0 -> {
                    if (data != null) {
                        _myStatus.value = data
                    } else {
                        _message.value = "요트 현황을 조회할 수 없습니다."
                    }
                }
                else -> {
                    _message.value = message
                }
            }
        }
    }

}