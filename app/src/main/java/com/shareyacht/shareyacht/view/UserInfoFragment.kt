package com.shareyacht.shareyacht.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentUserInfoBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false)
        context ?: return binding.root

        binding.apply {

            toolbar.apply {
                title = getString(R.string.title_user_info)
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }
            }
        }

        return binding.root
    }
}