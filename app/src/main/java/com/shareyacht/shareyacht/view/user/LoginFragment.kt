package com.shareyacht.shareyacht.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.FragmentLoginBinding
import com.shareyacht.shareyacht.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLoginBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        context ?: return binding.root

        binding.apply {
            loginViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner

            // 회원가입 버튼 클릭된 경우 화면 이동
            navSignUpButton.setOnClickListener { navigateToSignUp() }

            // 뷰모델의 체크박스 상태가 변경된 경우 체크박스 ui 업데이트
            viewModel.normal.observe(viewLifecycleOwner, { normal ->
                normalUserCheck.isChecked = normal
            })
            viewModel.corp.observe(viewLifecycleOwner, { corp ->
                corpUserCheck.isChecked = corp
            })
        }

        return binding.root
    }

    private fun navigateToSignUp() {
        val direction = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        findNavController().navigate(direction)
    }
}