package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.ReqAddYacht
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import java.io.File

class AddYachtViewModel : ViewModel() {

    val yachtName: MutableLiveData<String> = MutableLiveData()
    val yachtNum: MutableLiveData<String> = MutableLiveData()
    val maxPeople: MutableLiveData<String> = MutableLiveData()
    val location: MutableLiveData<String> = MutableLiveData()
    val price: MutableLiveData<String> = MutableLiveData()

    // 서버와의 연결 상태
    val busy: MutableLiveData<Boolean> = MutableLiveData()

    val uploadImageSuccess: MutableLiveData<Boolean> = MutableLiveData()
    private var imageID: Long? = null

    val addYachtSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val failMessage: MutableLiveData<String> = MutableLiveData()

    fun uploadImage(file: File) {
        busy.value = true
        RetrofitManager.instance.requestUploadImage(file = file) { success, message, imageid ->
            when (success) {
                0 -> {
                    imageID = imageid
                    uploadImageSuccess.value = true
                }
                else -> {

                }
            }
            busy.value = false
        }
    }

    // 모든 항목이 채워져 있는지 검사
    fun isNotEmptyForAllFields(): Boolean =
        !yachtName.value.isNullOrEmpty()
                && !yachtNum.value.isNullOrEmpty()
                && !maxPeople.value.isNullOrEmpty()
                && !location.value.isNullOrEmpty()
                && !price.value.isNullOrEmpty()

    fun uploadYacht() {
        val email = SharedPreferenceManager.instance.getString(
            Preference.SP_EMAIL, ""
        ) as String

        val name = SharedPreferenceManager.instance.getString(
            Preference.SP_NAME, ""
        ) as String

        if (email.isNotBlank() && name.isNotBlank() && isNotEmptyForAllFields()) {

            if (imageID != null) {
                val yacht = ReqAddYacht(
                    owner = email,
                    company = name,
                    number = yachtNum.value!!,
                    name = yachtName.value!!,
                    max = maxPeople.value!!,
                    location = location.value!!,
                    price = price.value!!,
                    imageid = imageID!!
                )

                busy.value = true
                RetrofitManager.instance.requestAddYacht(yacht) { success, message ->
                    when (success) {
                        0 -> {
                            addYachtSuccess.value = true
                        }
                        else -> {
                            failMessage.value = message
                        }
                    }
                }
                busy.value = false
            }
            else {
                failMessage.value = "이미지를 선택해주세요(필수)"
            }
        }
    }
}