package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class Yacht(
    @SerializedName(Keyword.OWNER_ID) val owner: String,
    @SerializedName(Keyword.YACHT_NUMBER) val number: String,
    @SerializedName(Keyword.YACHT_NAME) val name: String,
    @SerializedName(Keyword.MAX_PEOPLE) val max: String,
    @SerializedName(Keyword.COMPANY) val company: String,
    @SerializedName(Keyword.LOCATION) val location: String,
    @SerializedName(Keyword.PRICE) val price: String,
    @SerializedName(Keyword.IMAGE_ID) val imageid: Long
)

data class ResponseYachtList(
    val results: List<Yacht>
)