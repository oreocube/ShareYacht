package com.shareyacht.shareyacht.retrofit

import android.util.Log
import com.shareyacht.shareyacht.model.BaseResponse
import com.shareyacht.shareyacht.model.ReqLogin
import com.shareyacht.shareyacht.model.User
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val service: RetrofitService? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitService::class.java)

    // 회원가입
    fun requestSignup(
        user: User,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val call = service?.requestSignup(user) ?: return

        call.enqueue(object : Callback<BaseResponse<User>> {
            override fun onResponse(call: Call<BaseResponse<User>>, response: Response<BaseResponse<User>>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null)
                        } else {
                            completion(-1, response.body()?.message)
                        }
                    }
                    else -> {
                        completion(response.code(), null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<User>>, t: Throwable) {
                completion(-1, t.toString())
            }

        })
    }

    // 로그인
    fun requestLogin(
        email: String, password: String, userType: Int,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val req = ReqLogin(email = email, password = password, userType = userType)
        val call = service?.requestLogin(req) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(call: Call<BaseResponse<Int>>, response: Response<BaseResponse<Int>>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null)
                        } else {
                            completion(-1, response.body()?.message)
                        }
                    }
                    else -> {
                        completion(response.code(), null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<Int>>, t: Throwable) {
                completion(-1, t.toString())
            }

        })
    }
}