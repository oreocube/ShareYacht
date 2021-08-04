package com.shareyacht.shareyacht.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shareyacht.shareyacht.databinding.ActivityAddYachtBinding

class AddYachtActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAddYachtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAddYachtBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}