package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

abstract class Base{
    abstract val error: Boolean
    abstract val message: String
    abstract val status: String
}

data class BaseResponse(
    @SerializedName(Keyword.ERROR)
    override val error: Boolean,

    @SerializedName(Keyword.MESSAGE)
    override val message: String,

    @SerializedName(Keyword.STATUS)
    override val status: String
) : Base()