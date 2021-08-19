package com.shareyacht.shareyacht.view.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shareyacht.shareyacht.adapter.OwnerReservationAdapter
import com.shareyacht.shareyacht.databinding.FragmentReservationStateBinding

/* [사업자] 예약현황 - 상태별 목록을 보여줄 Fragment (Pager adapter 에서 사용) */
class ReservationFragment(val state: Int) : Fragment() {
    private var mBinding: FragmentReservationStateBinding? = null
    private lateinit var mAdapter: OwnerReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReservationStateBinding.inflate(inflater, container, false)

        mBinding = binding

        mAdapter = OwnerReservationAdapter()

        return binding.root
    }
}