package com.shareyacht.shareyacht.viewmodel

import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.retrofit.RetrofitManager
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.formatter
import java.text.SimpleDateFormat
import java.util.*

class YachtReserveViewModel : ViewModel() {
    var imageUrl: MutableLiveData<String> = MutableLiveData()
    var selectedYacht: MutableLiveData<Yacht> = MutableLiveData()
    val _message: MutableLiveData<String> = MutableLiveData()
    val peopleCount: MutableLiveData<Int> = MutableLiveData(1)
    val price: MutableLiveData<String> = MutableLiveData()
    val startDate: MutableLiveData<String> = MutableLiveData()
    val startTime: MutableLiveData<Int> = MutableLiveData()
    val endDate: MutableLiveData<String> = MutableLiveData()
    val endTime: MutableLiveData<Int> = MutableLiveData()
    private val start: Calendar = Calendar.getInstance()
    private val end: Calendar = Calendar.getInstance()
    val timeInterval: MutableLiveData<Int> = MutableLiveData()

    // 요트 상세 조회
    fun getYachtDetail(yachtID: Int) {
        RetrofitManager.instance.requestGetYachtDetail(yachtID) { success, message, yacht ->
            when (success) {
                0 -> {
                    if (yacht != null) {
                        imageUrl.value = "${API.BASE_URL}/image/${yacht.imageid}"
                        selectedYacht.value = yacht
                        price.value = yacht.price
                    } else {
                        _message.value = "요트를 찾을 수 없습니다."
                    }
                }
                else -> {
                    _message.value = message
                }
            }
        }
    }

    // 날짜 및 시간 기본 값 설정
    init {
        if (start.get(Calendar.MINUTE) > 0) {
            start.add(Calendar.HOUR_OF_DAY, 1)
        }
        if (end.get(Calendar.MINUTE) > 0) {
            end.add(Calendar.HOUR_OF_DAY, 1)
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREAN)
        // 출항시각은 오늘 날짜와 다음 정각 시각으로 설정함
        startDate.value = sdf.format(start.time)
        startTime.value = start.get(Calendar.HOUR_OF_DAY)

        // 입항시각의 기본 값은 출항시각에 1시간을 더해서 설정함
        end.add(Calendar.HOUR_OF_DAY, 1)
        endDate.value = sdf.format(end.time)
        endTime.value = end.get(Calendar.HOUR_OF_DAY)

        getTimeInterval()
    }

    // 이용 시간 계산
    private fun getTimeInterval() {
        val diff = (end.time.time - start.time.time) / 3600000
        updateTimeInterval(diff)
    }

    private fun updateTimeInterval(diff: Long) {
        timeInterval.value = diff.toInt()
        getTotalPrice()
    }

    // 이용 요금 계산
    private fun getTotalPrice() {
        if (selectedYacht.value != null) {
            val default = selectedYacht.value!!.price.toInt()
            val count = peopleCount.value!!
            val time = timeInterval.value!!
            val totalPrice = default * count * time
            price.value = formatter.format(totalPrice)
        }
    }

    // 탑승인원 (-) 버튼 클릭
    fun onMinusButtonClick() {
        // 탑승인원은 최소 1명이상
        if (peopleCount.value != null && peopleCount.value!! > 1) {
            peopleCount.value = peopleCount.value!!.toInt() - 1
        }
        getTotalPrice()
    }

    // 탑승인원 (+) 버튼 클릭
    fun onPlusButtonClick() {
        if (selectedYacht.value != null) {
            // 탑승인원이 탑승제한인원보다 작은 경우에만
            if (peopleCount.value!! < selectedYacht.value!!.max.toInt()) {
                peopleCount.value = peopleCount.value!!.toInt() + 1
            }
        }
        getTotalPrice()
    }

    fun buildDatePicker(title: String): MaterialDatePicker<Long> {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(title)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)

            val sdf = SimpleDateFormat("yyyy-MM-dd E요일", Locale.KOREA)
            when (title) {
                "출항" -> {
                    startDate.value = sdf.format(date)
                }
                "입항" -> {
                    endDate.value = sdf.format(date)
                }
            }
            datePicker.dismiss()
        }
        return datePicker
    }

    // TODO
    fun buildTimePicker(title: String, context: Context): TimePickerDialog {
        val currentTime = Calendar.getInstance()
        if (currentTime.get(Calendar.MINUTE) > 0) {
            currentTime.add(Calendar.HOUR_OF_DAY, 1)
        }
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            if (view.isShown) {
                when (title) {
                    "출항" -> {

                    }
                    "입항" -> {

                    }
                }
            }
        }

        val timePickerDialog = TimePickerDialog(
            context,
            listener,
            currentHour, currentMinute, false
        )

        return timePickerDialog
    }

    fun payButtonClick() {
      //  RetrofitManager.instance.
    }
}
