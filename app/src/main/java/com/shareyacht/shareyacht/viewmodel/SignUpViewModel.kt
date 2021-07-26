package com.shareyacht.shareyacht.viewmodel

import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.model.User
import com.shareyacht.shareyacht.retrofit.RetrofitManager

class SignUpViewModel : ViewModel() {

    /* 회원가입 페이지 */
    val email: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val passwordCheck: MutableLiveData<String> = MutableLiveData()

    // 체크박스 상태 (normal : 일반, corp : 요트 사업자)
    val normal: MutableLiveData<Boolean> = MutableLiveData(true)
    val corp: MutableLiveData<Boolean> = MutableLiveData(false)

    private var userType: Int = 1

    // 아이디, 비밀번호 관련 에러 메시지
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    // 상세정보 입력 화면으로 이동가능한지 여부
    val navStatus: MutableLiveData<Boolean> = MutableLiveData(false)


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
                sex = sex.value!!
            )
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
        ) { success, message ->
            when (success) {
                0 -> {
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