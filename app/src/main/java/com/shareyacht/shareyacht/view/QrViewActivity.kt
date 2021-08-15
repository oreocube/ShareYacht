package com.shareyacht.shareyacht.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityQrViewBinding
import com.shareyacht.shareyacht.utils.Constants
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager

class QrViewActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityQrViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityQrViewBinding>(this, R.layout.activity_qr_view)
        mBinding = binding

        initToolbar()

        val lenderID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "") ?: ""

        setUpQr(lenderID)
    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayShowTitleEnabled(false)
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUpQr(lenderID: String) {
        val multiFormatWriter = MultiFormatWriter()
        if (lenderID != "") {
            val data = "${getString(R.string.app_name)}:$lenderID"
            try {
                // qr코드 인코딩
                val bitMatrix =
                    multiFormatWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.createBitmap(bitMatrix)
                mBinding.qrCode.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e(Constants.TAG, e.toString())
            }
        }
    }
}