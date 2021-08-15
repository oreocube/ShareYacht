package com.shareyacht.shareyacht.view.normal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        mBinding.recyclerview.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    // 현재 화면에 보이는 아이템 중 마지막 위치
                    val lastVisibleItemPosition: Int =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                    // 마지막 아이템 위치
                    val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                    // 스크롤이 마지막인 경우 더 불러오기
                    if (lastVisibleItemPosition == itemTotalCount) {
                        viewModel.getYachtList()
                    }
                }
            })
        }

        viewModel.getYachtList()
        viewModel.results.observe(this) {
            adapter.submitList(it.toMutableList())
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
            false
        }
    }
}