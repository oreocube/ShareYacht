package com.shareyacht.shareyacht.utils

object Constants {
    const val TAG: String = "로그"
}

object API {
    const val BASE_URL: String = "http://wsx1341.tplinkdns.com:8080"

    /* User */
    const val USER_LOGIN: String = "/user/login"
    const val USER_SIGNUP: String = "/user/signup"
    const val USER_LOGOUT: String = "/user/logout"

    const val IMAGE_UPLOAD: String = "/image/upload"
}

object Keyword {
    // base response
    const val ERROR: String = "error"
    const val MESSAGE: String = "message"
    const val STATUS: String = "status"

    // user
    const val ID: String = "id"
    const val PASSWORD: String = "password"
    const val USER_TYPE: String = "usertype"
    const val NAME: String = "name"
    const val ADDRESS: String = "address"
    const val PHONE: String = "phone"
    const val BIRTHDAY: String = "birthday"
    const val SEX: String = "sex"

    // etc
    const val IMAGE_ID: String = "imageid"
}

object Preference {
    // Preference 이름
    const val SHARED_PREFERENCE_FILE = "yacht_preference"

    // Preference Key 값
    const val SHARED_PREFERENCE_NAME_COOKIE = "cookies"
}