package com.shareyacht.shareyacht.retrofit

import com.shareyacht.shareyacht.model.*
import com.shareyacht.shareyacht.utils.API
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

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

    /* 이미지 */
    @Multipart
    @POST(API.IMAGE_UPLOAD)
    fun requestUploadImage(
        @Part image: MultipartBody.Part
    ): Call<BaseResponse<ResUploadImage>>

    // 요트 등록, 수정
    @PUT(API.OWNER_MY_YACHT)
    fun requestAddYacht(
        @Body body: Yacht
    ): Call<BaseResponse<Int>>
}