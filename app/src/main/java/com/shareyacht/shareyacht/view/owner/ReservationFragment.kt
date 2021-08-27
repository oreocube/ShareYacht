package com.shareyacht.shareyacht.view.owner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.shareyacht.shareyacht.adapter.ClickListener
import com.shareyacht.shareyacht.adapter.OwnerReservationAdapter
import com.shareyacht.shareyacht.databinding.FragmentReservationStateBinding
import com.shareyacht.shareyacht.utils.Constants
import com.shareyacht.shareyacht.utils.Constants.STATE_CANCEL
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.viewmodel.OwnerReserveViewModel

class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {
    private lateinit var getContent: ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        getContent =
            registry.register("key", owner, ActivityResultContracts.StartActivityForResult()) {
                Log.d(Constants.TAG, "액티비티 RESULT_OK")
            }
    }

    fun navigateToReservationDetail(context: Context, reservationID: String) {
        val intent = Intent(context, OwnerReservationDetailActivity::class.java)
        intent.putExtra(Keyword.RESERVATION_ID, reservationID)
        getContent.launch(intent)
    }
}

/* [사업자] 예약현황 - 상태별 목록을 보여줄 Fragment (Pager adapter 에서 사용) */
class ReservationFragment(val state: Int) : Fragment() {
    private var mBinding: FragmentReservationStateBinding? = null
    private val viewModel: OwnerReserveViewModel by activityViewModels()
    private lateinit var mAdapter: OwnerReservationAdapter
    private lateinit var observer: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observer = MyLifecycleObserver(requireActivity().activityResultRegistry)
        lifecycle.addObserver(observer)
    }

//    private val reservationDetailActivityResultLauncher =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            //  if (result.resultCode == RESULT_OK) {
//            Log.d(Constants.TAG, "액티비티 RESULT_OK")
//            viewModel.getReservationStatus()
//            //  }
//        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReservationStateBinding.inflate(inflater, container, false)

        mBinding = binding

        mAdapter = OwnerReservationAdapter(ClickListener { reservation ->
//            val intent =
//                Intent(context, OwnerReservationDetailActivity::class.java)
//            intent.putExtra(Keyword.RESERVATION_ID, reservation.id)
//            reservationDetailActivityResultLauncher.launch(intent)
            observer.navigateToReservationDetail(requireContext(), reservationID = reservation.id)
        })

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