package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class ReqOwnerReserveView(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.ID)
    val reservationID: String
)