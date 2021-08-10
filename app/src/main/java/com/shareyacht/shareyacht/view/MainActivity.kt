package com.shareyacht.shareyacht.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.adapter.YachtListAdapter
import com.shareyacht.shareyacht.databinding.ActivityMainBinding
import com.shareyacht.shareyacht.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val adapter = YachtListAdapter()

        mBinding.apply {
            recyclerview.setHasFixedSize(true)
            recyclerview.adapter = adapter
        }

        viewModel.results.observe(this) { it ->
            adapter.submitList(it)
        }

        initMainActionBar()
        initNavigationDrawer()
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
                R.id.drawer_qr -> {
                    val intent = Intent(applicationContext, QrViewActivity::class.java)
                    startActivity(intent)
                }
                R.id.drawer_reserve -> {
                    val intent =
                        Intent(applicationContext, ReservationActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }
}