package com.shareyacht.shareyacht.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shareyacht.shareyacht.model.OwnerYachtReservation
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Constants.STATE_CANCEL
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.formatter
import java.text.SimpleDateFormat
import java.util.*

class OwnerReserveDetailViewModel : ViewModel() {

    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var selectedYacht: MutableLiveData<OwnerYachtReservation> = MutableLiveData()
    val _message: MutableLiveData<String> = MutableLiveData()
    val status: MutableLiveData<Int> = MutableLiveData()
    val totalPrice: MutableLiveData<Int> = MutableLiveData()
    var reservationID: String = ""

    // 예약내역 상세 가져오기
    fun getReserveDetail(reservationID: String) {
        this.reservationID = reservationID

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
    private fun getTotalPrice(reservation: OwnerYachtReservation) {
        // 출항 시각, 입항 시각
        val start = reservation.departure.substring(15, 17).toInt()
        val end = reservation.arrival.substring(15, 17).toInt()
        val time = end - start
        // 금액 합계
        totalPrice.value = reservation.yacht.price.toInt() * reservation.embarkCount * time
    }

    // 예약 승인
    fun acceptReservation() {
        RetrofitManager.instance.requestOwnerReserveDecision(
            reservationID = reservationID,
            status = STATE_CONFIRMED
        ) { success, message ->
            when(success) {
                0 -> {
                    // 승인
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }

    // 예약 거절
    fun refuseReservation() {
        RetrofitManager.instance.requestOwnerReserveDecision(
            reservationID= reservationID,
            status = STATE_CANCEL
        ) { success, message ->
            when(success) {
                0 -> {
                    // 거절
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }

    private fun getNowTime(): String {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREAN)
        return sdf.format(currentTime)
    }

    // 출항
    fun leave() {
        RetrofitManager.instance.requestOwnerLeave(
            reservationID = reservationID,
            status = STATE_MOVING,
            leaveTime = getNowTime()
        ) { success, message ->
            when(success) {
                0 -> {
                    // 출항
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }

    // 탑승자 등록

    // 입항
    fun enter() {
        RetrofitManager.instance.requestOwnerEnter(
            reservationID = reservationID,
            status = STATE_MOVING,
            enterTime = getNowTime()
        ) { success, message ->
            when(success) {
                0 -> {
                    // 입항
                }
                1 -> {
                    _message.value = message
                }
            }
        }
    }
}