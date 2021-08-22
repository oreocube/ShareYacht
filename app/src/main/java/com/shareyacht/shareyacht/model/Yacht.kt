package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class Yacht(
    @SerializedName(Keyword.ID) val id: String,
    @SerializedName(Keyword.YACHT_NUMBER) val number: String,
    @SerializedName(Keyword.YACHT_NAME) val name: String,
    @SerializedName(Keyword.MAX_PEOPLE) val max: String,
    @SerializedName(Keyword.COMPANY) val company: String,
    @SerializedName(Keyword.LOCATION) val location: String,
    @SerializedName(Keyword.PRICE) val price: String,
    @SerializedName(Keyword.IMAGE_ID) val imageid: Long
)

data class ReqAddYacht(
    @SerializedName(Keyword.OWNER_ID) val owner: String,
    @SerializedName(Keyword.YACHT_NUMBER) val number: String,
    @SerializedName(Keyword.YACHT_NAME) val name: String,
    @SerializedName(Keyword.MAX_PEOPLE) val max: String,
    @SerializedName(Keyword.COMPANY) val company: String,
    @SerializedName(Keyword.LOCATION) val location: String,
    @SerializedName(Keyword.PRICE) val price: String,
    @SerializedName(Keyword.IMAGE_ID) val imageid: Long
)

data class YachtReservation(
    @SerializedName(Keyword.ID) val id: String,
    @SerializedName(Keyword.DEPARTURE) val departure: String,
    @SerializedName(Keyword.ARRIVAL) val arrival: String,
    @SerializedName(Keyword.YACHT) val yacht: Yacht,
    @SerializedName(Keyword.EMBARK_COUNT) val embarkCount: Int,
    @SerializedName(Keyword.STATUS) val status: Int
)

data class OwnerYachtReservation(
    @SerializedName(Keyword.ID) val id: String,
    @SerializedName(Keyword.DEPARTURE) val departure: String,
    @SerializedName(Keyword.ARRIVAL) val arrival: String,
    @SerializedName(Keyword.YACHT) val yacht: Yacht,
    @SerializedName(Keyword.EMBARK_COUNT) val embarkCount: Int,
    @SerializedName(Keyword.STATUS) val status: Int,
    @SerializedName(Keyword.LENDER_ID) val lenderID: String
)