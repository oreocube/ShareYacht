package com.shareyacht.shareyacht.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.shareyacht.shareyacht.retrofit.RetrofitService

class YachtRepository(private val retrofitService: RetrofitService) {

    companion object {
        private var INSTANCE: YachtRepository? = null

        fun getInstance(retrofitService: RetrofitService): YachtRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: YachtRepository(retrofitService)
            }
    }

    fun getYachtList() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { YachtPagingSource(retrofitApi = retrofitService) }
    ).liveData
}