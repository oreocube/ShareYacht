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

    /* Owner */
    const val OWNER_MY_YACHT: String = "/owner/yacht/myyacht"
    const val OWNER_DRIVER: String = "/owner/yacht/driver"

    /* Consumer */
    const val CONSUMER_YACHT: String = "/consumer/yacht"
    const val CONSUMER_YACHT_RESERVE: String = "/consumer/yacht/reserve"
    const val CONSUMER_RESERVE_VIEW: String = "/consumer/reserve/view"

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

    // yacht
    const val OWNER_ID: String = "ownerid"
    const val YACHT_NUMBER: String = "yachtnumber"
    const val YACHT_NAME: String = "yachtname"
    const val MAX_PEOPLE: String = "maxpeople"
    const val COMPANY: String = "company"
    const val LOCATION: String = "location"
    const val PRICE: String = "price"

    // etc
    const val IMAGE_ID: String = "imageid"
    const val DATA: String = "data"
    const val PAGE_NUM: String = "pagenum"
}

object Preference {
    // Preference 이름
    const val SHARED_PREFERENCE_FILE = "yacht_preference"

    // Preference Key 값
    const val SHARED_PREFERENCE_NAME_COOKIE = "cookies"
    const val SP_EMAIL = "email"
    const val SP_PW = "pw"
    const val SP_USERTYPE = "usertype"
    const val SP_NAME = "name"
}