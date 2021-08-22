package com.shareyacht.shareyacht.view.normal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.shareyacht.shareyacht.R

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
    }

    fun finishActivity(view: View) {
        finish()
    }
}