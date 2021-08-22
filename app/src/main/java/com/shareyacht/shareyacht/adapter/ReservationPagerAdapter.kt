package com.shareyacht.shareyacht.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shareyacht.shareyacht.utils.Constants.STATE_CANCEL
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT
import com.shareyacht.shareyacht.view.owner.ReservationFragment

/* [사업자] 예약현황 - pager adapter */
class ReservationPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ReservationFragment(STATE_WAIT)
            1 -> ReservationFragment(STATE_CONFIRMED)
            2 -> ReservationFragment(STATE_MOVING)
            else -> ReservationFragment(STATE_CANCEL)
        }
    }
}