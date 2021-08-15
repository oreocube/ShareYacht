package com.shareyacht.shareyacht.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.adapter.ReservationListAdapter
import com.shareyacht.shareyacht.databinding.ActivityReservationBinding
import com.shareyacht.shareyacht.viewmodel.ReservationViewModel

class ReservationActivity : AppCompatActivity() {
    private val viewModel: ReservationViewModel by viewModels()
    private lateinit var mBinding: ActivityReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityReservationBinding>(
            this, R.layout.activity_reservation
        )
        mBinding = binding

        initToolbar()

        val adapter = ReservationListAdapter()

        mBinding.recyclerview.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // 현재 화면에 보이는 아이템 중 마지막 위치
                    val lastVisibleItemPosition: Int =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    // 마지막 아이템 위치
                    val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                    // 스크롤이 마지막인 경우 더 불러오기
                    if (lastVisibleItemPosition == itemTotalCount) {
                        viewModel.getReservationList()
                    }
                }
            })
        }

        viewModel.results.observe(this) {
            adapter.submitList(it.toMutableList())
        }

    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}