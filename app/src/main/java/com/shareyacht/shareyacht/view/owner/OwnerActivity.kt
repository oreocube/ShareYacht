package com.shareyacht.shareyacht.view.owner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityOwnerBinding
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

        initMainActionBar()
        initNavigationDrawer()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.apply {
            myYachtFrame.addYachtButton.setOnClickListener {
                val intent = Intent(this@OwnerActivity, AddYachtActivity::class.java)
                startActivity(intent)
            }
        }
        viewModel.apply {
            requestMyYacht()
            _message.observe(this@OwnerActivity, { message ->
                Toast.makeText(this@OwnerActivity, message, Toast.LENGTH_SHORT).show()
                Log.d("!!!!!!!!!!", message)
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
                if(yacht!= null) {
                    binding.myYachtFrame.myYacht = yacht
                }
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
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.drawer_add_yacht -> {
                    val intent = Intent(applicationContext, AddYachtActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}