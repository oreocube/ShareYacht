package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class User(
    @SerializedName(Keyword.ID) val email: String,
    @SerializedName(Keyword.PASSWORD) val password: String,
    @SerializedName(Keyword.USER_TYPE) val userType: Int,
    @SerializedName(Keyword.NAME) val name: String,
    @SerializedName(Keyword.BIRTHDAY) val birth: String,
    @SerializedName(Keyword.ADDRESS) val address: String,
    @SerializedName(Keyword.PHONE) val phone: String,
    @SerializedName(Keyword.SEX) val sex: String
)

data class ReqLogin(
    @SerializedName(Keyword.ID)
    val email: String,

    @SerializedName(Keyword.PASSWORD)
    val password: String,

    @SerializedName(Keyword.USER_TYPE)
    val userType: Int
)
