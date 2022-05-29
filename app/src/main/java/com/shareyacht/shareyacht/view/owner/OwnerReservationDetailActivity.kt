package com.shareyacht.shareyacht.view.owner

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityOwnerReservationDetailBinding
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.STATE_MOVING
import com.shareyacht.shareyacht.utils.Constants.STATE_WAIT
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import com.shareyacht.shareyacht.utils.formatter
import com.shareyacht.shareyacht.viewmodel.OwnerReserveDetailViewModel

class OwnerReservationDetailActivity : AppCompatActivity() {
    private var mBinding: ActivityOwnerReservationDetailBinding? = null
    private val viewModel: OwnerReserveDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityOwnerReservationDetailBinding>(
            this, R.layout.activity_owner_reservation_detail
        )
        mBinding = binding
        initToolbar()

        val userType = SharedPreferenceManager.instance.getInt(Preference.SP_USERTYPE, 0)
        val reservationID = intent.getStringExtra(Keyword.RESERVATION_ID)

        if (reservationID != null) {
            viewModel.getReserveDetail(userType = userType, reservationID = reservationID)
        }

        binding.viewModel = viewModel

        // 예약내역
        viewModel.selectedYacht.observe(this, {
            if (it != null) {
                binding.reservation = it
                binding.yachtOverview.yacht = it.yacht
            }
        })

        // 이미지 셋팅
        viewModel.imageUrl.observe(this, { url ->
            if (url != null) {
                Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .into(binding.yachtOverview.yachtImage)
            }
        })

        // 결제 금액
        viewModel.totalPrice.observe(this, {
            if (it != null) {
                binding.price = formatter.format(it)
            }
        })

        // 상태에 맞는 버튼 표출
        viewModel.status.observe(this, { status ->
            when (status) {
                STATE_WAIT -> {
                    binding.apply {
                        // 직접 운전 버튼
                        driveSelfButton.visibility = View.VISIBLE
                        // 운전자 필요 버튼
                        acceptButton.visibility = View.VISIBLE
                        // 거절 버튼
                        refuseButton.visibility = View.VISIBLE
                    }
                }
                STATE_CONFIRMED -> {
                    binding.apply {
                        // 승선 명부 등록 버튼
                        scanQrButton.visibility = View.VISIBLE
                        scanQrButton.setOnClickListener {
                            navigateToPassengersList()
                        }
                        // 경로 설정 버튼
                        setPathButton.visibility = View.VISIBLE
                        setPathButton.setOnClickListener {
                            navigateToSetMap()
                        }
                        // 출항 버튼
                        leaveButton.visibility = View.VISIBLE
                        leaveButton.setOnClickListener {
                            navigateToMapView()
                            viewModel!!.leave()
                        }
                    }
                }
                STATE_MOVING -> {
                    binding.apply {
                        // 입항 버튼
                        enterButton.visibility = View.VISIBLE
                    }
                }
            }
        })

        viewModel.updateEvent.observe(this, {
            setResult(RESULT_OK)
            finish()
        })
    }

    private fun initToolbar() {
        val toolbar = mBinding?.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    // 탑승자 목록 화면으로 이동
    private fun navigateToPassengersList() {
        val intent = Intent(this, PassengersListActivity::class.java)
        intent.putExtra(Keyword.RESERVE_ID, viewModel.reservationID)
        startActivity(intent)
    }

    // 경로 설정 화면으로 이동
    private fun navigateToSetMap() {
        val intent = Intent(this, SetMapActivity::class.java)
        intent.putExtra(Keyword.RESERVATION_ID, viewModel.reservationID)
        startActivity(intent)
    }

    private fun navigateToMapView() {
        val intent = Intent(this, MapViewActivity::class.java)
        intent.putExtra(Keyword.RESERVATION_ID, viewModel.reservationID)
        startActivity(intent)
    }
}