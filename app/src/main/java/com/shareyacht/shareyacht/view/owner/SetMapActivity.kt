package com.shareyacht.shareyacht.view.owner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shareyacht.shareyacht.R
import android.graphics.Color
import androidx.fragment.app.FragmentManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.PathOverlay
import com.shareyacht.shareyacht.databinding.ActivitySetMapBinding

class SetMapActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var mBinding: ActivitySetMapBinding
    private lateinit var naverMap: NaverMap
    private val points: MutableList<LatLng> = mutableListOf()
    private val path = PathOverlay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_map)

        mBinding = ActivitySetMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val fm: FragmentManager = supportFragmentManager
        var mapFragment: MapFragment? = fm.findFragmentById(R.id.map) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction().add(R.id.map, mapFragment).commit()
        }
        mapFragment!!.getMapAsync(this)

        initPath()
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.setOnMapClickListener { point, coord ->
            val marker = Marker()
            marker.position = LatLng(coord.latitude, coord.longitude)
            marker.map = naverMap
            marker.onClickListener = this
            points.add(coord)

            updatePath()
        }

    }

    override fun onClick(overlay: Overlay): Boolean {
        if (overlay is Marker) {
            overlay.map = null
            points.remove(overlay.position)
            updatePath()
            return true
        }
        return false
    }

    private fun initPath() {
        path.outlineWidth = 3
        //path.patternImage = OverlayImage.fromResource(R.drawable.ic_path_pattern)
        path.patternInterval = 10
        path.outlineColor = Color.BLUE
        path.color = Color.BLUE
    }

    private fun updatePath() {
        if (points.size < 2) {
            hideButton()
            path.map = null
        } else {
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