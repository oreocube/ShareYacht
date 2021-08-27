package com.shareyacht.shareyacht.view.owner

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.adapter.PassengerAdapter
import com.shareyacht.shareyacht.databinding.ActivityPassengersListBinding
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.viewmodel.PassengerViewModel

class PassengersListActivity : AppCompatActivity() {
    private val viewModel by viewModels<PassengerViewModel>()
    private lateinit var mBinding: ActivityPassengersListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val reservationID = intent.getStringExtra(Keyword.RESERVE_ID)

        mBinding = ActivityPassengersListBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initToolbar()

        viewModel.getPassengerList(reservationID!!)

        val adapter = PassengerAdapter()
        mBinding.recyclerview.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.list.observe(this) {
            adapter.submitList(it.toMutableList())
        }
        // 메시지 구독
        viewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        // QR 코드 스캔 버튼 클릭
        mBinding.scanQrButton.setOnClickListener {
            navigateToScanQrActivity()
        }

        // 탑승자 등록 후 새로 불러오기
        viewModel.passengerUpdateEvent.observe(this, {
            viewModel.getPassengerList(reservationID)
        })
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    // QR 코드 스캔 화면으로 이동
    private fun navigateToScanQrActivity() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setOrientationLocked(false)
        intentIntegrator.setPrompt("QR 코드를 스캔해주세요.")
        zxingActivityResultLauncher.launch(intentIntegrator.createScanIntent())
    }

    // QR 코드 처리
    private val zxingActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)

            if (result.contents == null) {
                Toast.makeText(this, "QR 코드가 없습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val token = result.contents.split(":")

                if (token[0] == getString(R.string.app_name)) {
                    // 탑승자 등록 요청
                    viewModel.addPassenger(token[1])
                }
            }
        }
}