package com.shareyacht.shareyacht.view.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityLoginBinding
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import com.shareyacht.shareyacht.view.MainActivity
import com.shareyacht.shareyacht.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_login
        )

        // viewModel 바인딩
        binding.loginViewModel = viewModel

        // SharedPreference init
        SharedPreferenceManager().init(applicationContext)

        // 로그인 성공 시 메인화면으로 이동 후 로그인 액티비티 종료
        // TODO userType 별 다른 화면 이동 필요
        viewModel.loginResult.observe(this, { result ->
            if (result) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        // 로그인 실패 시 메시지 출력
        viewModel.failMessage.observe(this, { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
    }
}