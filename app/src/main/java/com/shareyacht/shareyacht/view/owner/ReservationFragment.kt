package com.shareyacht.shareyacht.view.owner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.shareyacht.shareyacht.adapter.OwnerReservationAdapter
import com.shareyacht.shareyacht.databinding.FragmentReservationStateBinding
import com.shareyacht.shareyacht.utils.Constants.STATE_CANCEL
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT
import com.shareyacht.shareyacht.viewmodel.OwnerReserveViewModel

/* [사업자] 예약현황 - 상태별 목록을 보여줄 Fragment (Pager adapter 에서 사용) */
class ReservationFragment(val state: Int) : Fragment() {
    private var mBinding: FragmentReservationStateBinding? = null
    private val viewModel: OwnerReserveViewModel by activityViewModels()
    private lateinit var mAdapter: OwnerReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReservationStateBinding.inflate(inflater, container, false)

        mBinding = binding

        mAdapter = OwnerReservationAdapter()

        viewModel.filterCompleted.observe(viewLifecycleOwner, {
            if (it) {
                when (state) {
                    STATE_WAIT -> {
                        mAdapter.submitList(viewModel.stateWaitList)
                    }
                    STATE_CONFIRMED -> {
                        mAdapter.submitList(viewModel.stateConfirmedList)
                    }
                    STATE_MOVING -> {
                        mAdapter.submitList(viewModel.stateMovingList)
                    }
                    STATE_CANCEL -> {
                        mAdapter.submitList(viewModel.stateCancelList)
                    }
                }
            }
        })

        binding.recyclerview.apply {
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}