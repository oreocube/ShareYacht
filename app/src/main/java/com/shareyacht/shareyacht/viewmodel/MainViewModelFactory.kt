package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shareyacht.shareyacht.model.YachtRepository

class MainViewModelFactory(private val repository: YachtRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}