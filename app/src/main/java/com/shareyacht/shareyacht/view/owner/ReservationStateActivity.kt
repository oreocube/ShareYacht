package com.shareyacht.shareyacht.view.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.shareyacht.shareyacht.adapter.ReservationPagerAdapter
import com.shareyacht.shareyacht.databinding.ActivityReservationStateBinding

class ReservationStateActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityReservationStateBinding
    private val tabTextList = arrayListOf("예약신청", "예약확정", "운항중", "예약취소")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityReservationStateBinding.inflate(layoutInflater)
        initToolbar()

        val tabLayout = mBinding.tabLayout
        val pager = mBinding.viewPager

        pager.adapter = ReservationPagerAdapter(this)

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()

        setContentView(mBinding.root)
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}