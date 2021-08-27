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
    ): Call<BaseResponse<String>>

    /* 이미지 */
    @Multipart
    @POST(API.IMAGE_UPLOAD)
    fun requestUploadImage(
        @Part image: MultipartBody.Part
    ): Call<BaseResponse<Long?>>

    /* 사업자 */
    // 내 요트
    @POST(API.OWNER_MY_YACHT)
    fun requestMyYacht(
        @Body body: BaseRequest
    ): Call<BaseResponse<Yacht>>

    // 요트 등록, 수정
    @PUT(API.OWNER_MY_YACHT)
    fun requestAddYacht(
        @Body body: ReqAddYacht
    ): Call<BaseResponse<Int>>

    // 예약내역 목록
    @POST(API.OWNER_RESERVE)
    fun requestOwnerReserve(
        @Body body: BaseRequest
    ): Call<BaseResponse<List<OwnerYachtReservation>>>

    // 예약내역 목록
    @POST(API.OWNER_RESERVE)
    fun requestReservationStatus(
        @Body body: BaseRequest
    ): Call<ReqGetStatus>

    // 예약내역 상세
    @POST(API.OWNER_RESERVE_VIEW)
    fun requestOwnerReserveView(
        @Body body: ReqOwnerReserveView
    ): Call<BaseResponse<OwnerYachtReservation>>

    // 예약 승인 거절
    @POST(API.OWNER_RESERVE_DECISION)
    fun requestOwnerReserveDecision(
        @Body body: ReqOwnerDecision
    ): Call<BaseResponse<Int>>

    // 탑승자 조회
    @POST(API.OWNER_EMBARK)
    fun requestGetPassenger(
        @Body body: ReqGetPassenger
    ): Call<BaseResponse<List<Passenger>>>

    // 탑승자 등록
    @PUT(API.OWNER_EMBARK)
    fun requestAddPassenger(
        @Body body: ReqAddPassenger
    ): Call<BaseResponse<Int>>

    // 출항
    @POST(API.OWNER_LEAVE)
    fun requestOwnerLeave(
        @Body body: ReqOwnerLeave
    ): Call<BaseResponse<Int>>

    // 입항
    @POST(API.OWNER_ENTER)
    fun requestOwnerEnter(
        @Body body: ReqOwnerEnter
    ): Call<BaseResponse<Int>>

    // 경로 불러오기
    @POST(API.MAP_YACHT)
    fun requestGetPath(
        @Body body: ReqGetPath
    ): Call<BaseResponse<String>>

    // 경로 설정
    @PUT(API.MAP_YACHT)
    fun requestAddPath(
        @Body body: ReqAddPath
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

    // 예약 내역 목록
    @POST(API.CONSUMER_RESERVE)
    fun requestReservationList(
        @Body body: ReqReservationList
    ): Call<BaseResponse<List<YachtReservation>>>
}