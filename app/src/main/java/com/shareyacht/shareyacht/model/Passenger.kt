package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class Passenger(
    @SerializedName(Keyword.EMBARK_ID)
    val id: String,
    @SerializedName(Keyword.EMBARK_TIME)
    val time: String,
    @SerializedName(Keyword.EMBARK_NAME)
    val name: String,
    @SerializedName(Keyword.EMBARK_ADDRESS)
    val address: String,
    @SerializedName(Keyword.EMBARK_SEX)
    val sex: String,
    @SerializedName(Keyword.EMBARK_PHONE)
    val phone: String,
    @SerializedName(Keyword.EMBARK_BIRTHDAY)
    val birthday: String
)