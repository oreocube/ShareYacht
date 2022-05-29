package com.shareyacht.shareyacht.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

val formatter = DecimalFormat("#,###")

fun getNowTime(): String {
    val currentTime = Calendar.getInstance().time
    val sdf = SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREAN)
    return sdf.format(currentTime)
}

fun getImageUrl(imageID: Long) = "${API.IMAGE_URL_BASE}/${imageID}"