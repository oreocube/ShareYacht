package com.shareyacht.shareyacht.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.model.User
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import com.shareyacht.shareyacht.utils.UserType

class SignUpViewModel : ViewModel() {

    /* 회원가입 페이지 */
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordCheck: MutableLiveData<String> = MutableLiveData()
    var userType = UserType.NORMAL

    // 아이디, 비밀번호 관련 에러 메시지
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    // 상세정보 입력 화면으로 이동가능한지 여부
    val navStatus: MutableLiveData<Boolean> = MutableLiveData(false)

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

    // Next 버튼이 클릭된 경우
    fun onNextClick() {
        if (!email.value.isNullOrEmpty() && !password.value.isNullOrEmpty() && !passwordCheck.value.isNullOrEmpty()) {
            // 비밀번호와 비밀번호 확인이 서로 일치하는 경우
            if (password.value.equals(passwordCheck.value)) {
                // 화면전환 가능
                navStatus.value = true
            } else {
                errorMessage.value = "비밀번호가 서로 일치하지 않습니다."
            }
        } else {
            errorMessage.value = "모든 항목을 입력해주세요."
        }
    }

    /* 면허정보 입력 페이지 */
    val _licenseNumber = MutableLiveData<String>()
    val licenseNumber: LiveData<String>
        get() = _licenseNumber

    fun saveLicense() {
        if (!licenseNumber.value.isNullOrEmpty()) {
            navStatus.value = true
        } else {
            errorMessage.value = "면허정보를 입력해주세요."
        }
    }

    /* 상세정보 입력 페이지 */
    val name: MutableLiveData<String> = MutableLiveData()
    val birth: MutableLiveData<String> = MutableLiveData()
    val phone: MutableLiveData<String> = MutableLiveData()
    val zipcode: MutableLiveData<String> = MutableLiveData()
    val addressBase: MutableLiveData<String> = MutableLiveData()
    val addressDetail: MutableLiveData<String> = MutableLiveData()
    private val sex: MutableLiveData<String> = MutableLiveData()

    // MaterialButtonToggleGroup - onButtonCheckedListener
    val onToggleChecked =
        MaterialButtonToggleGroup.OnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.maleButton -> {
                        sex.value = "남"
                    }
                    R.id.femaleButton -> {
                        sex.value = "여"
                    }
                }
            }
        }

    // 회원가입 결과
    val signUpResult: MutableLiveData<Boolean> = MutableLiveData()
    val failMessage: MutableLiveData<String> = MutableLiveData()
    val loginResult: MutableLiveData<Boolean> = MutableLiveData()

    // 모든 항목이 채워져 있는지 검사
    fun isNotEmptyForAllFields(): Boolean =
        !name.value.isNullOrEmpty()
                && !birth.value.isNullOrEmpty()
                && !zipcode.value.isNullOrEmpty()
                && !addressBase.value.isNullOrEmpty()
                && !phone.value.isNullOrEmpty()
                && !addressDetail.value.isNullOrEmpty()
                && !sex.value.isNullOrEmpty()

    // 회원가입 버튼 클릭
    fun onSignUpClick() {
        // 빈칸이 없는 경우 회원가입 요청
        if (isNotEmptyForAllFields()) {
            val user = User(
                email = email.value!!,
                password = password.value!!,
                userType = userType,
                name = name.value!!,
                birth = birth.value!!,
                address = "${addressBase.value!!} ${addressDetail.value!!}",
                phone = phone.value!!,
                sex = sex.value!!,
                driverLicense = null
            )

            if (userType == UserType.DRIVER) {
                user.driverLicense = licenseNumber.value
            }

            RetrofitManager.instance.requestSignup(
                user = user
            ) { success, message ->
                when (success) {
                    0 -> {
                        signUpResult.value = true
                        login() // 회원가입에 성공한 경우 자동 로그인
                    }
                    else -> {
                        signUpResult.value = false
                        failMessage.value = message
                    }
                }
            }
        } else {
            errorMessage.value = "모든 항목을 입력해주세요."
        }
    }

    private fun login() {
        RetrofitManager.instance.requestLogin(
            email = email.value!!,
            password = password.value!!,
            userType = userType
        ) { success, message, _ ->
            when (success) {
                0 -> {
                    saveUserInfo()
                    loginResult.value = true
                }
                else -> {
                    loginResult.value = false
                    failMessage.value = message
                }
            }
        }
    }


    private fun saveUserInfo() {
        val preferences = SharedPreferenceManager.instance
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(Preference.SP_EMAIL, email.value)
        editor.putString(Preference.SP_PW, password.value)
        editor.putInt(Preference.SP_USERTYPE, userType)
        editor.putString(Preference.SP_NAME, name.value)
        editor.apply()
    }
}