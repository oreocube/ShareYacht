package com.shareyacht.shareyacht.view.owner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityOwnerBinding
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import com.shareyacht.shareyacht.viewmodel.OwnerMainViewModel

class OwnerActivity : AppCompatActivity() {
    private val viewModel: OwnerMainViewModel by viewModels()
    private lateinit var mBinding: ActivityOwnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityOwnerBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_owner
        )

        mBinding = binding

        // SharedPreference Init
        SharedPreferenceManager().init(applicationContext)

        initMainActionBar()
        initNavigationDrawer()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.apply {
            // 요트 등록하기
            myYachtFrame.addYachtButton.setOnClickListener {
                val intent = Intent(this@OwnerActivity, AddYachtActivity::class.java)
                addYachtActivityResultLauncher.launch(intent)
            }
            // 요트 현황
            myYachtStatusFrame.root.setOnClickListener {
                val intent = Intent(this@OwnerActivity, ReservationStateActivity::class.java)
                yachtStateActivityResultLauncher.launch(intent)
            }
        }
        viewModel.apply {
            _message.observe(this@OwnerActivity, { message ->
                Toast.makeText(this@OwnerActivity, message, Toast.LENGTH_SHORT).show()
            })
            hasYacht.observe(this@OwnerActivity, { hasYacht ->
                if (hasYacht) { // 등록된 요트가 있는 경우
                    binding.myYachtFrame.apply {
                        addYachtButton.visibility = View.GONE
                        myYachtLayout.visibility = View.VISIBLE
                    }
                }
            })
            imageUrl.observe(this@OwnerActivity, { url ->
                Glide.with(this@OwnerActivity)
                    .load(url)
                    .centerCrop()
                    .into(binding.myYachtFrame.yachtImage)
            })
            myYacht.observe(this@OwnerActivity, { yacht ->
                if (yacht != null) {
                    binding.myYachtFrame.myYacht = yacht
                }
            })
            myStatus.observe(this@OwnerActivity, { data ->
                if (data != null)
                    binding.myYachtStatusFrame.status = data
            })
        }
    }

    // 메인 툴바 - APP title, DrawerToggle
    private fun initMainActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.nav_app_bar_open_drawer_description,
            R.string.nav_app_bar_open_drawer_description
        )
        drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.syncState()
    }

    private fun initNavigationDrawer() {
        val navigationView = mBinding.navView
        val headerView = navigationView.getHeaderView(0)

        // 이름, 이메일 설정
        val id = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val name = SharedPreferenceManager.instance.getString(Preference.SP_NAME, "")
        headerView.findViewById<TextView>(R.id.emailTextView).text = id
        headerView.findViewById<TextView>(R.id.nameTextView).text = name
    }

    // 요트 등록
    private val addYachtActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.requestMyYacht()
            }
        }

    // 요트 현황
    private val yachtStateActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                viewModel.requestMyStatus()
            }
        }
}