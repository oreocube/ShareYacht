package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PassengerViewModel : ViewModel() {
    val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    init {
        getPassengerList()
    }

    fun getPassengerList() {

    }
}