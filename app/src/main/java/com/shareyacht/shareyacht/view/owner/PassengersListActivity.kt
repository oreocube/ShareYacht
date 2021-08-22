package com.shareyacht.shareyacht.view.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shareyacht.shareyacht.adapter.PassengerAdapter
import com.shareyacht.shareyacht.databinding.ActivityPassengersListBinding
import com.shareyacht.shareyacht.viewmodel.PassengerViewModel

class PassengersListActivity : AppCompatActivity() {
    private val viewModel by viewModels<PassengerViewModel>()
    private lateinit var mBinding: ActivityPassengersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityPassengersListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val adapter = PassengerAdapter()
        mBinding.recyclerview.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}