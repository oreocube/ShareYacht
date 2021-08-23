package com.shareyacht.shareyacht.model

import androidx.constraintlayout.motion.widget.KeyCycleOscillator
import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class ReqOwnerReserveView(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.ID)
    val reservationID: String
)

data class ReqOwnerDecision(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.ID)
    val reservationID: String,
    @SerializedName(Keyword.STATUS)
    val status: Int
)

data class ReqOwnerLeave(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.ID)
    val reservationID: String,
    @SerializedName(Keyword.STATUS)
    val status: Int,
    @SerializedName(Keyword.LEAVE_TIME)
    val leaveTime: String
)

data class ReqOwnerEnter(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.ID)
    val reservationID: String,
    @SerializedName(Keyword.STATUS)
    val status: Int,
    @SerializedName(Keyword.ENTER_TIME)
    val enterTime: String
)

data class ReqAddPassenger(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.RESERVE_ID)
    val reserveID: String,
    @SerializedName(Keyword.EMBARK_USER_ID)
    val userID: String,
    @SerializedName(Keyword.EMBARK_TIME)
    val embarkTime: String
)

data class ReqGetPassenger(
    @SerializedName(Keyword.OWNER_ID)
    val ownerID: String,
    @SerializedName(Keyword.RESERVE_ID)
    val reserveID: String
)

data class ReqGetPath(
    @SerializedName(Keyword.USER_ID)
    val userID: String
)

data class ReqAddPath(
    @SerializedName(Keyword.USER_ID)
    val userID: String,
    @SerializedName(Keyword.DATA)
    val data: String
)