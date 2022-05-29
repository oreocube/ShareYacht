package com.shareyacht.shareyacht.view.owner

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.PathOverlay
import com.naver.maps.map.util.FusedLocationSource
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityMapViewBinding
import com.shareyacht.shareyacht.utils.Constants.ADB_TAG
import com.shareyacht.shareyacht.utils.Constants.TAG
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.viewmodel.OwnerPathViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mBinding: ActivityMapViewBinding
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null
    private var points: MutableList<LatLng> = mutableListOf()
    private val path = PathOverlay()
    private val viewModel by viewModels<OwnerPathViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMapViewBinding.inflate(layoutInflater)
        mBinding = binding
        setContentView(binding.root)

        checkLocationPermission()
        locationSource =
            FusedLocationSource(this, 1000)
        val fm: FragmentManager = supportFragmentManager
        var mapFragment: MapFragment? = fm.findFragmentById(R.id.map) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction().add(R.id.map, mapFragment).commit()
        }
        mapFragment!!.getMapAsync(this)

    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                // 권한이 허용되어 있는 경우

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

                locationRequest = LocationRequest.create().apply {
                    interval = TimeUnit.SECONDS.toMillis(2)
                    fastestInterval = TimeUnit.SECONDS.toMillis(1)
                    maxWaitTime = TimeUnit.SECONDS.toMillis(3)

                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }

                locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        val now = System.currentTimeMillis()
                        val date = Date(now)
                        val sdf = SimpleDateFormat("MM/dd hh:mm:ss", Locale.KOREA)
                        currentLocation = locationResult.lastLocation

                        if(currentLocation!= null) {
                            val data = "Date : ${sdf.format(date)}, lat : ${currentLocation!!.latitude}, lon : ${currentLocation!!.longitude}," +
                                    "bearing : ${currentLocation!!.bearing}, speed : ${currentLocation!!.speed} m/sec"

                            Log.d(ADB_TAG, data)
                        }
                    }
                }

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Log.d(TAG, "ACCESS_FINE_LOCATION 퍼미션 필요")
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ), 1000
                )
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                Log.d(TAG, "ACCESS_COARSE_LOCATION 퍼미션 필요")
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 1000
                )
            }
            else -> {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ), 1000
                )
            }
        }
    }


    fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // 경로 커스텀
        initPath()

        // reservationID 가져오기
        val reservationID = intent.getStringExtra(Keyword.RESERVATION_ID)

        // 경로 불러오기
        if (reservationID != null) {
            viewModel.getMyPath(reservationID)
        }

        viewModel.getMyPathCompleted.observe(this, {
            // 설정된 경로가 있는 경우
            if (viewModel.myPath.size > 0) {
                points = viewModel.myPath

                path.coords = points
                path.map = naverMap

                // 설정된 경로의 첫번째 좌표로 카메라 이동
                val cameraUpdate = CameraUpdate.scrollTo(points[0])
                naverMap.moveCamera(cameraUpdate)
            }
        })

    }
    // 경로 커스텀
    private fun initPath() {
        path.outlineWidth = 3
        path.outlineColor = Color.BLUE
        path.color = Color.BLUE
    }
}