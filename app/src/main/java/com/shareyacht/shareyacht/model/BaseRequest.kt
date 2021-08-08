package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class BaseRequest(
    @SerializedName(Keyword.OWNER_ID)
    val email: String
)