package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shareyacht.shareyacht.model.YachtRepository

class MainViewModel(private val repository: YachtRepository) : ViewModel() {
    val results = repository.getYachtList().cachedIn(viewModelScope)
}