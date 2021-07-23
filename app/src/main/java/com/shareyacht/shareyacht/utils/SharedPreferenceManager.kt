package com.shareyacht.shareyacht.utils

import android.content.Context
import android.content.SharedPreferences
import com.shareyacht.shareyacht.utils.Preference.SHARED_PREFERENCE_FILE

class SharedPreferenceManager {

    companion object {
        lateinit var instance: SharedPreferences
    }

    fun init(context: Context) {
        instance = context.getSharedPreferences(SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE)
    }

}