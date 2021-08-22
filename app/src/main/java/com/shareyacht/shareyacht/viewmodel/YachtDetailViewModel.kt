package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.API

class YachtDetailViewModel : ViewModel() {

    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var selectedYacht: MutableLiveData<Yacht> = MutableLiveData()
    val _message: MutableLiveData<String> = MutableLiveData()

    // 요트 상세 조회
    fun getYachtDetail(yachtID: Int) {
        RetrofitManager.instance.requestGetYachtDetail(yachtID) { success, message, yacht ->
            when (success) {
                0 -> {
                    if (yacht != null) {
                        imageUrl.value = "${API.BASE_URL}/image/${yacht.imageid}"
                        selectedYacht.value = yacht
                    } else {
                        _message.value = "요트를 찾을 수 없습니다."
                    }
                }
                else -> {
                    _message.value = message
                }
            }
        }
    }
}