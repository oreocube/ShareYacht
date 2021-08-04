package com.shareyacht.shareyacht.retrofit

import com.shareyacht.shareyacht.model.BaseResponse
import com.shareyacht.shareyacht.model.ReqLogin
import com.shareyacht.shareyacht.model.User
import com.shareyacht.shareyacht.model.Yacht
import com.shareyacht.shareyacht.utils.API
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface RetrofitService {
    /* 사용자 */
    // 회원가입
    @POST(API.USER_SIGNUP)
    fun requestSignup(
        @Body body: User
    ): Call<BaseResponse<Int>>

    // 로그인
    @POST(API.USER_LOGIN)
    fun requestLogin(
        @Body body: ReqLogin
    ): Call<BaseResponse<Int>>

    // 요트 등록, 수정
    @PUT(API.OWNER_MY_YACHT)
    fun requestAddYacht(
        @Body body: Yacht
    ): Call<BaseResponse<Int>>
}