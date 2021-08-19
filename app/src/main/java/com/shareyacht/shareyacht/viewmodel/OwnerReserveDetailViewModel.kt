package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.OwnerYachtReservation
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.formatter

class OwnerReserveDetailViewModel : ViewModel() {

    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var selectedYacht: MutableLiveData<OwnerYachtReservation> = MutableLiveData()
    val _message: MutableLiveData<String> = MutableLiveData()
    val status: MutableLiveData<Int> = MutableLiveData()
    val totalPrice: MutableLiveData<Int> = MutableLiveData()

    // 예약내역 상세 가져오기
    fun getReserveDetail(reservationID: String) {
        RetrofitManager.instance.requestOwnerReserveView(reservationID) { success, message, data ->
            when (success) {
                0 -> {
                    if (data != null) {
                        imageUrl.value = "${API.BASE_URL}/image/${data.yacht.imageid}"
                        selectedYacht.value = data
                        status.value = data.status
                        getTotalPrice(data)
                    } else {
                        _message.value = "요트를 찾을 수 없습니다."
                    }
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }

    // 이용 요금 계산
    private fun getTotalPrice(reservation : OwnerYachtReservation) {
        // 출항 시각, 입항 시각
        val start = reservation.departure.substring(15, 17).toInt()
        val end = reservation.arrival.substring(15, 17).toInt()
        val time = end - start
        // 금액 합계
        totalPrice.value = reservation.yacht.price.toInt() * reservation.embarkCount * time
    }

    // 예약 승인

    // 예약 거절

    // 출항

    // 탑승자 등록

    // 입항

}