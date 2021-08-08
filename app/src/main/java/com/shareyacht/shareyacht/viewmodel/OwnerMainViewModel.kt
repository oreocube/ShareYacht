package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.API

class OwnerMainViewModel : ViewModel() {

    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var myYacht: MutableLiveData<Yacht> = MutableLiveData()
    val hasYacht: MutableLiveData<Boolean> = MutableLiveData()
    val _message: MutableLiveData<String> = MutableLiveData()

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

}