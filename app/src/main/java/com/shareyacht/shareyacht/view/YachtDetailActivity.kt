package com.shareyacht.shareyacht.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityYachtDetailBinding
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.viewmodel.YachtDetailViewModel

class YachtDetailActivity : AppCompatActivity() {
    private val viewModel: YachtDetailViewModel by viewModels()
    private var yachtID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityYachtDetailBinding>(
            this, R.layout.activity_yacht_detail
        )
        yachtID = intent.getIntExtra(Keyword.YACHT_ID, 0)

        // 요트 정보 요청하기
        viewModel.getYachtDetail(yachtID)

        // 요트 정보 불러오기
        viewModel.selectedYacht.observe(this, {
            binding.yacht = it
        })
        viewModel.imageUrl.observe(this, { url ->
            Glide.with(this)
                .load(url)
                .centerCrop()
                .into(binding.yachtImage)
        })
    }

    fun reserveButtonClick(view:View) {
        val intent = Intent(this, YachtReserveActivity::class.java)
        intent.putExtra(Keyword.YACHT_ID, yachtID)
        startActivity(intent)
    }
}