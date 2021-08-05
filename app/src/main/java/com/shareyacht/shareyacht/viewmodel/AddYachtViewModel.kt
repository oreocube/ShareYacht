package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddYachtViewModel : ViewModel() {

    val yachtName: MutableLiveData<String> = MutableLiveData()
    val yachtNum: MutableLiveData<String> = MutableLiveData()
    val maxPeople: MutableLiveData<String> = MutableLiveData()
    val location: MutableLiveData<String> = MutableLiveData()
    val price: MutableLiveData<String> = MutableLiveData()

    // 서버와의 연결 상태
    val busy: MutableLiveData<Boolean> = MutableLiveData()
    var imageID: Long? = null



}