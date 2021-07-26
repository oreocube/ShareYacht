package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class BaseResponse<T>(
    @SerializedName(Keyword.ERROR)
    val error: Boolean,
    @SerializedName(Keyword.MESSAGE)
    val message: String,
    @SerializedName(Keyword.STATUS)
    val status: String,
    @SerializedName("data")
    val data: T
)