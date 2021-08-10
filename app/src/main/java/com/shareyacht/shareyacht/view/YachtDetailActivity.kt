package com.shareyacht.shareyacht.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityYachtDetailBinding

class YachtDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityYachtDetailBinding>(
            this, R.layout.activity_yacht_detail
        )
    }
}