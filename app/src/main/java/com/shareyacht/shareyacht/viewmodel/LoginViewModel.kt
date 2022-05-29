package com.shareyacht.shareyacht.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import com.shareyacht.shareyacht.utils.UserType

class LoginViewModel : ViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    private var userType: Int = UserType.NORMAL

    // 로그인 결과
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val failMessage: MutableLiveData<String> = MutableLiveData()

    // MaterialButtonToggleGroup - onButtonCheckedListener
    val onUserTypeChecked =
        MaterialButtonToggleGroup.OnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.normalButton -> {
                        userType = UserType.NORMAL
                    }
                    R.id.corpButton -> {
                        userType = UserType.OWNER
                    }
                    R.id.driverButton -> {
                        userType = UserType.DRIVER
                    }
                }
            }
        }

    // 로그인 버튼이 클릭된 경우
    fun onLoginClick() {
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty()) {
            RetrofitManager.instance.requestLogin(
                email = email.value!!,
                password = password.value!!,
                userType = userType
            ) { success, message, data ->
                when (success) {
                    0 -> {
                        saveUserInfo(data!!)
                        loginResult.value = true
                    }
                    else -> {
                        loginResult.value = false
                        failMessage.value = message
                    }
                }
            }
        }
    }

    private fun saveUserInfo(data: String) {
        val preferences = SharedPreferenceManager.instance
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(Preference.SP_EMAIL, email.value)
        editor.putString(Preference.SP_PW, password.value)
        editor.putInt(Preference.SP_USERTYPE, userType)
        editor.putString(Preference.SP_NAME, data)
        editor.apply()
    }
}