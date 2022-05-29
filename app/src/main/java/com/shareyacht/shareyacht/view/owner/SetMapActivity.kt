package com.shareyacht.shareyacht.view.owner

import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.PathOverlay
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivitySetMapBinding
import com.shareyacht.shareyacht.utils.Keyword
import com.shareyacht.shareyacht.viewmodel.OwnerPathViewModel

class SetMapActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var mBinding: ActivitySetMapBinding
    private lateinit var naverMap: NaverMap
    private var points: MutableList<LatLng> = mutableListOf()
    private val path = PathOverlay()
    private val viewModel by viewModels<OwnerPathViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySetMapBinding.inflate(layoutInflater)

        mBinding = binding
        setContentView(binding.root)
        initToolbar()

        val fm: FragmentManager = supportFragmentManager
        var mapFragment: MapFragment? = fm.findFragmentById(R.id.map) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction().add(R.id.map, mapFragment).commit()
        }
        mapFragment!!.getMapAsync(this)

        mBinding.submitButton.setOnClickListener {
            viewModel.addMyPath(points)
        }
        viewModel.finishEvent.observe(this, {
            finish()
        })

    }

    private fun initToolbar() {
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    // onMapReady Callback
    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

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

                // 경로 표시
                updateMarkerPath(points)
                updatePath()

                // 설정된 경로의 첫번째 좌표로 카메라 이동
                val cameraUpdate = CameraUpdate.scrollTo(points[0])
                naverMap.moveCamera(cameraUpdate)
            }
        })

        // 지도의 어떤 지점이 클릭되었을 때
        naverMap.setOnMapClickListener { _, coord ->
            // 지도에 마커 표시하기
            val marker = Marker()
            marker.position = LatLng(coord.latitude, coord.longitude)
            marker.map = naverMap

            // 생성된 마커에 클릭 리스너 - 클릭 시 제거
            marker.onClickListener = this

            // 선택된 지점을 리스트에 추가
            points.add(coord)

            // 경로 업데이트
            updatePath()
        }

    }

    private fun updateMarkerPath(points: MutableList<LatLng>) {
        for (i in points) {
            val marker = Marker()
            marker.position = i
            marker.map = naverMap

            // 생성된 마커에 클릭 리스너 - 클릭 시 제거
            marker.onClickListener = this
        }
    }

    // 오버레이(마커 등) 클릭되었을 때
    override fun onClick(overlay: Overlay): Boolean {
        // 마커가 클릭되면
        if (overlay is Marker) {
            // 지도에서 지우기
            overlay.map = null
            // 리스트에서도 제거
            points.remove(overlay.position)
            // 경로 갱신
            updatePath()
            return true
        }
        return false
    }

    // 경로 커스텀
    private fun initPath() {
        path.outlineWidth = 3
        //path.patternImage = OverlayImage.fromResource(R.drawable.ic_path_pattern)
        path.patternInterval = 10
        path.outlineColor = Color.BLUE
        path.color = Color.BLUE
    }

    // 경로 업데이트
    private fun updatePath() {
        // 경로는 포인트가 2개 이상일 때만 표시
        if (points.size < 2) {
            // 경로 설정 버튼, 경로 숨김
            hideButton()
            path.map = null
        } else {
            // 경로 설정 버튼, 경로 표시
            showButton()
            path.coords = points
            path.map = naverMap
        }
    }

    private fun hideButton() {
        mBinding.submitButton.isEnabled = false
    }

    private fun showButton() {
        mBinding.submitButton.isEnabled = true
    }
}