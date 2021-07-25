package com.shareyacht.shareyacht.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSignUpBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        context ?: return binding.root

        binding.apply {
            navInfoButton.setOnClickListener{ navigateToUserInfo()}

            toolbar.apply {
                title = getString(R.string.title_signup)
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }
            }
        }

        return binding.root
    }

    private fun navigateToUserInfo() {
        val direction = SignUpFragmentDirections.actionSignUpFragmentToUserInfoFragment()
        findNavController().navigate(direction)
    }
}