package com.shareyacht.shareyacht.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.FragmentSignUpBinding
import com.shareyacht.shareyacht.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentSignUpBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        context ?: return binding.root

        binding.apply {
            signUpViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner

            toolbar.apply {
                title = getString(R.string.title_signup)
                setNavigationIcon(R.drawable.ic_back)
                setNavigationOnClickListener { view ->
                    view.findNavController().navigateUp()
                }
            }

            // 뷰모델의 체크박스 상태가 변경된 경우 체크박스 ui 업데이트
            viewModel.normal.observe(viewLifecycleOwner, { normal ->
                normalUserCheck.isChecked = normal
            })
            viewModel.corp.observe(viewLifecycleOwner, { corp ->
                corpUserCheck.isChecked = corp
            })
        }

        subscribeNavStatus()

        return binding.root
    }

    // navStatus 구독 - true로 변경 시 화면 이동
    private fun subscribeNavStatus() {
        viewModel.navStatus.observe(viewLifecycleOwner, { status ->
            if (status) {
                navigateToUserInfo()
                viewModel.navStatus.value = false
            }
        })
    }

    private fun navigateToUserInfo() {
        val direction = SignUpFragmentDirections.actionSignUpFragmentToUserInfoFragment()
        findNavController().navigate(direction)
    }
}