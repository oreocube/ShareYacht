package com.shareyacht.shareyacht.view.normal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityYachtReserveBinding
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.viewmodel.YachtReserveViewModel

class YachtReserveActivity : AppCompatActivity() {
    private val viewModel: YachtReserveViewModel by viewModels()
    private var yachtID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityYachtReserveBinding>(
            this, R.layout.activity_yacht_reserve
        )
        yachtID = intent.getIntExtra(Keyword.YACHT_ID, 0)
        binding.viewModel = viewModel
        // 요트 정보 요청하기
        viewModel.getYachtDetail(yachtID)

        // 요트 정보 불러오기
        viewModel.selectedYacht.observe(this, {
            binding.yachtOverview.yacht = it
        })
        viewModel.imageUrl.observe(this, { url ->
            Glide.with(this)
                .load(url)
                .centerCrop()
                .into(binding.yachtOverview.yachtImage)
        })

        viewModel.peopleCount.observe(this, {
            binding.peopleCount.text = it.toString()
        })

        viewModel.startDate.observe(this, {
            binding.startDay.text = it
        })
        viewModel.endDate.observe(this, {
            binding.endDay.text = it
        })
        viewModel.startTime.observe(this, {
            binding.startTime.text = "$it:00"
        })
        viewModel.endTime.observe(this, {
            binding.endTime.text = "$it:00"
        })
        viewModel.timeInterval.observe(this, {
            binding.totalTimeValue.text = it.toString()
        })
        viewModel.price.observe(this, {
            binding.priceTextView.text = it
        })
        viewModel._message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.paySuccess.observe(this, {
            if (it) {
                setResult(RESULT_OK)
                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        binding.startContainer.setOnClickListener {
            val datePicker = viewModel.buildDatePicker("출항")
            datePicker.show(supportFragmentManager, "출항")
        }

    }
}