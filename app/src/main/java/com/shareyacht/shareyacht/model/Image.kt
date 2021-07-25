package com.shareyacht.shareyacht.model

import com.google.gson.annotations.SerializedName
import com.shareyacht.shareyacht.utils.Keyword

data class ResUploadImage(
    @SerializedName(Keyword.IMAGE_ID)
    val imageid: Long?
)