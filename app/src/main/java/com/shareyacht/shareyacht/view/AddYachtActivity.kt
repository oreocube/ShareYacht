package com.shareyacht.shareyacht.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityAddYachtBinding
import com.shareyacht.shareyacht.viewmodel.AddYachtViewModel

class AddYachtActivity : AppCompatActivity() {
    private val viewModel: AddYachtViewModel by viewModels()

    private lateinit var mBinding: ActivityAddYachtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityAddYachtBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_yacht
        )

        // viewModel 바인딩
        binding.viewModel = viewModel

    }
}