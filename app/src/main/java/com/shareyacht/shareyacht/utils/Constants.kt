package com.shareyacht.shareyacht.utils

object Constants {
    const val TAG: String = "로그"
    const val STATE_WAIT = 0
    const val STATE_CONFIRMED = 1
    const val STATE_MOVING = 2
    const val STATE_COMPLETED = 3
    const val STATE_CANCEL = 4
}

object API {
    const val BASE_URL = "http://52.231.139.31:8080"

    /* User */
    const val USER_LOGIN = "/user/login"
    const val USER_SIGNUP = "/user/signup"
    const val USER_LOGOUT = "/user/logout"

    /* Owner */
    const val OWNER_MY_YACHT = "/owner/yacht/myyacht"
    const val OWNER_DRIVER = "/owner/yacht/driver"
    const val OWNER_RESERVE = "/owner/reserve"
    const val OWNER_RESERVE_VIEW = "/owner/reserve/view"
    const val OWNER_RESERVE_DECISION = "/owner/reserve/decision"
    const val OWNER_EMBARK = "/owner/yacht/embark"
    const val OWNER_LEAVE = "/owner/yacht/leave"
    const val OWNER_ENTER = "/owner/yacht/enter"
    const val MAP_YACHT = "/map/yacht"

    /* Consumer */
    const val CONSUMER_YACHT = "/consumer/yacht"
    const val CONSUMER_YACHT_RESERVE = "/consumer/yacht/reserve"
    const val CONSUMER_RESERVE = "/consumer/reserve"
    const val CONSUMER_RESERVE_VIEW = "/consumer/reserve/view"

    const val IMAGE_UPLOAD = "/image/upload"
}

object Keyword {
    // base response
    const val ERROR = "error"
    const val MESSAGE = "message"
    const val STATUS = "status"

    // user
    const val ID = "id"
    const val PASSWORD = "password"
    const val USER_TYPE = "usertype"
    const val NAME = "name"
    const val ADDRESS = "address"
    const val PHONE = "phone"
    const val BIRTHDAY = "birthday"
    const val SEX = "sex"
    const val USER_ID = "userid"

    // yacht
    const val YACHT = "yacht"
    const val OWNER_ID = "ownerid"
    const val YACHT_ID = "yachtid"
    const val YACHT_NUMBER = "yachtnumber"
    const val YACHT_NAME = "yachtname"
    const val MAX_PEOPLE = "maxpeople"
    const val COMPANY = "company"
    const val LOCATION = "location"
    const val PRICE = "price"

    // 요트 예약
    const val DEPARTURE = "departure"
    const val ARRIVAL = "arrival"
    const val EMBARK_COUNT = "embarkcount"
    const val LENDER_ID = "lenderid"
    const val RESERVATION_ID = "reservationid"
    const val RESERVE_ID = "reserveid"
    const val LEAVE_TIME = "leavetime"
    const val ENTER_TIME = "entertime"
    const val EMBARK_TIME = "embarktime"
    const val EMBARK_USER_ID = "embarkuserid"

    // 승선명부
    const val EMBARK_ID = "embarkid"
    const val EMBARK_NAME = "embarkname"
    const val EMBARK_ADDRESS = "embarkaddress"
    const val EMBARK_SEX = "embarksex"
    const val EMBARK_PHONE = "embarkphone"
    const val EMBARK_BIRTHDAY = "embarkbirthday"

    // etc
    const val IMAGE_ID = "imageid"
    const val DATA = "data"
    const val PAGE_NUM = "pagenum"
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