package com.shareyacht.shareyacht.retrofit

import com.shareyacht.shareyacht.model.BaseResponse
import com.shareyacht.shareyacht.model.ReqLogin
import com.shareyacht.shareyacht.model.User
import com.shareyacht.shareyacht.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {
    /* 사용자 */
    // 회원가입
    @Headers("content-type: application/json")
    @POST(API.USER_SIGNUP)
    fun requestSignup(
        @Body body: User
    ): Call<BaseResponse>

    // 로그인
    @Headers("content-type: application/json")
    @POST(API.USER_LOGIN)
    fun requestLogin(
        @Body body: ReqLogin
    ): Call<BaseResponse>
}