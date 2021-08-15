package com.shareyacht.shareyacht.retrofit

import com.shareyacht.shareyacht.model.*
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Keyword
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
    ): Call<BaseResponse<Long?>>

    @POST(API.OWNER_MY_YACHT)
    fun requestMyYacht(
        @Body body: BaseRequest
    ): Call<BaseResponse<Yacht>>

    // 요트 등록, 수정
    @PUT(API.OWNER_MY_YACHT)
    fun requestAddYacht(
        @Body body: ReqAddYacht
    ): Call<BaseResponse<Int>>


    /* 일반 */
    // 요트 목록 조회
    @GET(API.CONSUMER_YACHT)
    fun requestYachtList(
        @Query(Keyword.PAGE_NUM) page: Int
    ): Call<BaseResponse<List<Yacht>>>

    // 요트 상세 조회
    @GET("${API.CONSUMER_YACHT}/{id}")
    fun requestGetYachtDetail(
        @Path("id") id: Int
    ): Call<BaseResponse<Yacht>>

    // 요트 예약
    @POST(API.CONSUMER_YACHT_RESERVE)
    fun requestReserveYacht(
        @Body body: ReqReserve
    ): Call<BaseResponse<Int>>
}