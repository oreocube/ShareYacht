package com.shareyacht.shareyacht.viewmodel

import android.content.SharedPreferences
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager

class LoginViewModel : ViewModel() {

    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    // 체크박스 상태 (normal : 일반, corp : 요트 사업자)
    val normal: MutableLiveData<Boolean> = MutableLiveData(true)
    val corp: MutableLiveData<Boolean> = MutableLiveData(false)

    private var userType: Int = 1

    // 로그인 결과
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()
    val failMessage: MutableLiveData<String> = MutableLiveData()

    // 일반 회원 체크된 경우 업데이트
    private fun normalChecked() {
        normal.value = true
        corp.value = false
        userType = 1
    }

    // 사업자 회원 체크된 경우 업데이트
    private fun corpChecked() {
        corp.value = true
        normal.value = false
        userType = 2
    }

    // 체크박스 변경 리스너
    val checkedChanged: CompoundButton.OnCheckedChangeListener =
        CompoundButton.OnCheckedChangeListener { button, isChecked ->
            if (button != null) {
                when (button.id) {
                    // 일반 회원 체크박스에 이벤트 발생
                    R.id.normalUserCheck -> {
                        if (isChecked) {
                            normalChecked()
                        } else {
                            corpChecked()
                        }
                    }
                    // 사업자 회원 체크박스에 이벤트 발생
                    R.id.corpUserCheck -> {
                        if (isChecked) {
                            corpChecked()
                        } else {
                            normalChecked()
                        }
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