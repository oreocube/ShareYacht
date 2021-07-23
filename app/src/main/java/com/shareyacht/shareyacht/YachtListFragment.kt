package com.shareyacht.shareyacht

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shareyacht.shareyacht.databinding.FragmentYachtListBinding

class YachtListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentYachtListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_yacht_list, container, false)
        context ?: return binding.root

        return binding.root
    }
}