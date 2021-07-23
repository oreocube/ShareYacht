package com.shareyacht.shareyacht

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shareyacht.shareyacht.databinding.FragmentYachtReserveBinding

class YachtReserveFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentYachtReserveBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_yacht_reserve, container, false)
        context ?: return binding.root

        return binding.root
    }
}