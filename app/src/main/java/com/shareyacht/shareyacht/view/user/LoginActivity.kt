package com.shareyacht.shareyacht.view.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityLoginBinding
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import com.shareyacht.shareyacht.utils.UserType
import com.shareyacht.shareyacht.view.DriverActivity
import com.shareyacht.shareyacht.view.normal.MainActivity
import com.shareyacht.shareyacht.view.owner.OwnerActivity
import com.shareyacht.shareyacht.viewmodel.LoginViewModel
import com.shareyacht.shareyacht.viewmodel.SignUpViewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_login
        )

        // viewModel 바인딩
        binding.loginViewModel = loginViewModel

        // SharedPreference init
        SharedPreferenceManager().init(applicationContext)

        // 로그인 성공 시 메인화면으로 이동 후 로그인 액티비티 종료
        loginViewModel.loginResult.observe(this, { result ->
            if (result) {
                navigateToMainActivity()
            }
        })
        signUpViewModel.loginResult.observe(this, { result ->
            if (result) {
                navigateToMainActivity()
            }
        })

        // 로그인 실패 시 메시지 출력
        loginViewModel.failMessage.observe(this, { message ->
            printFailMessage(message)
        })
        signUpViewModel.failMessage.observe(this, { message ->
            printFailMessage(message)
        })
        signUpViewModel.errorMessage.observe(this, { message ->
            printFailMessage(message)
        })
    }

    private fun navigateToMainActivity() {
        when (SharedPreferenceManager.instance.getInt(Preference.SP_USERTYPE, 0)) {
            UserType.NORMAL -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            UserType.OWNER -> {
                val intent = Intent(this, OwnerActivity::class.java)
                startActivity(intent)
                finish()
            }
            UserType.DRIVER -> {
                val intent = Intent(this, DriverActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    private fun printFailMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}