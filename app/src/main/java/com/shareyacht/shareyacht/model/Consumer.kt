package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class ReqReserve(
    @SerializedName(Keyword.DEPARTURE)
    val departure: String,
    @SerializedName(Keyword.ARRIVAL)
    val arrival: String,
    @SerializedName(Keyword.EMBARK_COUNT)
    val embarkCount: String,
    @SerializedName(Keyword.LENDER_ID)
    val lenderID: String,
    @SerializedName(Keyword.YACHT_ID)
    val yachtID: String
)

data class ReqReservationList(
    @SerializedName(Keyword.LENDER_ID)
    val lenderID: String,
    @SerializedName(Keyword.PAGE_NUM)
    val page: Int
)