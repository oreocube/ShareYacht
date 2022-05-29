package com.shareyacht.shareyacht.retrofit

import android.util.Log
import com.shareyacht.shareyacht.model.*
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Constants.STATE_CONFIRMED
import com.shareyacht.shareyacht.utils.Constants.TAG
import com.shareyacht.shareyacht.utils.Preference
import com.shareyacht.shareyacht.utils.SharedPreferenceManager
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 로그인
    fun requestLogin(
        email: String, password: String, userType: Int,
        completion: (code: Int, message: String?, data: String?) -> Unit
    ) {
        val req = ReqLogin(email = email, password = password, userType = userType)
        val call = service?.requestLogin(req) ?: return

        call.enqueue(object : Callback<BaseResponse<String>> {
            override fun onResponse(
                call: Call<BaseResponse<String>>,
                response: Response<BaseResponse<String>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 이미지 업로드
    fun requestUploadImage(
        file: File,
        completion: (code: Int, message: String?, imageID: Long?) -> Unit
    ) {

        val fileBody = MultipartBody.Part.createFormData(
            "image", file.name, file.asRequestBody("image/jpeg".toMediaType())
        )

        val call = service?.requestUploadImage(
            fileBody
        ) ?: return

        call.enqueue(object : Callback<BaseResponse<Long?>> {
            override fun onResponse(
                call: Call<BaseResponse<Long?>>,
                response: Response<BaseResponse<Long?>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            // 에러가 없으면 imageID 받음
                            val imageID = response.body()!!.data
                            completion(0, null, imageID)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<Long?>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    /* 사업자 */
    // 요트 등록
    fun requestAddYacht(
        yacht: ReqAddYacht,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val call = service?.requestAddYacht(yacht) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 내 요트 조회
    fun requestMyYacht(
        completion: (code: Int, message: String?, yacht: Yacht?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val call = service?.requestMyYacht(BaseRequest(ownerID!!)) ?: return

        call.enqueue(object : Callback<BaseResponse<Yacht>> {
            override fun onResponse(
                call: Call<BaseResponse<Yacht>>,
                response: Response<BaseResponse<Yacht>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<Yacht>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 예약현황 조회
    fun requestReservationStatus(
        completion: (code: Int, message: String?, ReqStatus?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val call = service?.requestReservationStatus(BaseRequest(ownerID!!)) ?: return

        call.enqueue(object : Callback<ReqGetStatus> {
            override fun onResponse(call: Call<ReqGetStatus>, response: Response<ReqGetStatus>) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<ReqGetStatus>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 예약내역 조회
    fun requestOwnerReserve(
        completion: (code: Int, message: String?, yachtList: List<OwnerYachtReservation>?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val call = service?.requestOwnerReserve(BaseRequest(ownerID!!)) ?: return

        call.enqueue(object : Callback<BaseResponse<List<OwnerYachtReservation>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<OwnerYachtReservation>>>,
                response: Response<BaseResponse<List<OwnerYachtReservation>>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<List<OwnerYachtReservation>>>,
                t: Throwable
            ) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 예약내역 상세
    fun requestOwnerReserveView(
        id: String,
        completion: (code: Int, message: String?, reservation: OwnerYachtReservation?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqOwnerReserveView(ownerID!!, id)
        val call = service?.requestOwnerReserveView(req) ?: return

        call.enqueue(object : Callback<BaseResponse<OwnerYachtReservation>> {
            override fun onResponse(
                call: Call<BaseResponse<OwnerYachtReservation>>,
                response: Response<BaseResponse<OwnerYachtReservation>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<OwnerYachtReservation>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 예약 승인/거절
    fun requestOwnerReserveDecision(
        reservationID: String, status: Int,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqOwnerDecision(
            ownerID = ownerID!!, reservationID = reservationID, status = status
        )
        val call = service?.requestOwnerReserveDecision(req) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 출항
    fun requestOwnerLeave(
        reservationID: String, status: Int, leaveTime: String,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val userType =
            getUserTypeString(SharedPreferenceManager.instance.getInt(Preference.SP_USERTYPE, 0))
        val req = ReqOwnerLeave(
            ownerID = ownerID!!,
            reservationID = reservationID,
            status = status,
            leaveTime = leaveTime
        )
        val call = service?.requestOwnerLeave(req, userType = userType) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    private fun getUserTypeString(type: Int): String = when (type) {
        1 -> "consumer"
        2 -> "owner"
        3 -> "driver"
        else -> ""
    }


    // 입항
    fun requestOwnerEnter(
        reservationID: String, status: Int, enterTime: String,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val userType =
            getUserTypeString(SharedPreferenceManager.instance.getInt(Preference.SP_USERTYPE, 0))
        val req = ReqOwnerEnter(
            ownerID = ownerID!!,
            reservationID = reservationID,
            status = status,
            enterTime = enterTime
        )
        val call = service?.requestOwnerEnter(req, userType = userType) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 탑승자 조회
    fun requestGetPassenger(
        reservationID: String,
        completion: (code: Int, message: String?, data: List<Passenger>?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqGetPassenger(
            ownerID = ownerID!!,
            reserveID = reservationID
        )
        val call = service?.requestGetPassenger(req) ?: return

        call.enqueue(object : Callback<BaseResponse<List<Passenger>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Passenger>>>,
                response: Response<BaseResponse<List<Passenger>>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Passenger>>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 탑승자 등록
    fun requestAddPassenger(
        reservationID: String, userID: String, embarkTime: String,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val ownerID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqAddPassenger(
            ownerID = ownerID!!,
            reserveID = reservationID,
            userID = userID,
            embarkTime = embarkTime
        )
        val call = service?.requestAddPassenger(req) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 경로 불러오기
    fun requestGetPath(
        reservationID: String,
        completion: (code: Int, message: String?, data: String?) -> Unit
    ) {
        val userID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val call = service?.requestGetPath(ReqGetPath(userID = userID!!, reserveid = reservationID))
            ?: return

        call.enqueue(object : Callback<BaseResponse<String>> {
            override fun onResponse(
                call: Call<BaseResponse<String>>,
                response: Response<BaseResponse<String>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 경로 설정
    fun requestAddPath(
        data: String,
        reservationID: String,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val userID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val call = service?.requestAddPath(
            ReqAddPath(
                userID = userID!!,
                data = data,
                reserveid = reservationID
            )
        ) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    /* 일반 */
    // 요트 목록 조회
    fun requestYachtList(
        pageNum: Int,
        completion: (code: Int, message: String?, yachtList: List<Yacht>?) -> Unit
    ) {
        val call = service?.requestYachtList(page = pageNum) ?: return

        call.enqueue(object : Callback<BaseResponse<List<Yacht>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<Yacht>>>,
                response: Response<BaseResponse<List<Yacht>>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<Yacht>>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })

    }

    // 요트 상세 조회
    fun requestGetYachtDetail(
        yachtID: Int,
        completion: (code: Int, message: String?, yacht: Yacht?) -> Unit
    ) {
        val call = service?.requestGetYachtDetail(yachtID) ?: return

        call.enqueue(object : Callback<BaseResponse<Yacht>> {
            override fun onResponse(
                call: Call<BaseResponse<Yacht>>,
                response: Response<BaseResponse<Yacht>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<Yacht>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }
        })
    }

    // 요트 예약신청
    fun requestReserveYacht(
        departure: String, arrival: String, embarkCount: Int, yachtID: String,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val lenderID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqReserve(
            departure = departure,
            arrival = arrival,
            embarkCount = embarkCount.toString(),
            yachtID = yachtID,
            lenderID = lenderID!!
        )
        val call = service?.requestReserveYacht(req) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 예약내역 목록
    fun requestReservationList(
        pageNum: Int,
        completion: (code: Int, message: String?, reservationList: List<YachtReservation>?) -> Unit
    ) {
        val lenderID = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")

        val call = service?.requestReservationList(
            ReqReservationList(
                lenderID = lenderID!!,
                page = pageNum
            )
        ) ?: return

        call.enqueue(object : Callback<BaseResponse<List<YachtReservation>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<YachtReservation>>>,
                response: Response<BaseResponse<List<YachtReservation>>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<YachtReservation>>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }
        })
    }

    /* 운전자 */
    // 리스트
    fun requestDriverList(
        pageNum: Int,
        completion: (code: Int, message: String?, yachtList: List<OwnerYachtReservation>?) -> Unit
    ) {
        val call = service?.requestDriverList(ReqDriverList(page = pageNum)) ?: return

        call.enqueue(object : Callback<BaseResponse<List<OwnerYachtReservation>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<OwnerYachtReservation>>>,
                response: Response<BaseResponse<List<OwnerYachtReservation>>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, null)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<List<OwnerYachtReservation>>>,
                t: Throwable
            ) {
                completion(-1, t.toString(), null)
            }
        })
    }

    // 아이템 상세조회
    fun requestDriverListView(
        reservationID: String,
        completion: (code: Int, message: String?, data: OwnerYachtReservation?) -> Unit
    ) {
        val call = service?.requestDriverListView(
            ReqDriverListView(reservationID = reservationID)
        ) ?: return

        call.enqueue(object : Callback<BaseResponse<OwnerYachtReservation>> {
            override fun onResponse(
                call: Call<BaseResponse<OwnerYachtReservation>>,
                response: Response<BaseResponse<OwnerYachtReservation>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, response.body()?.data)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<OwnerYachtReservation>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })

    }

    // 운항 예약
    fun requestDriverMatch(
        reservationID: String,
        completion: (code: Int, message: String?) -> Unit
    ) {
        val id = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val call = service?.requestDriverMatch(
            ReqDriverMatch(
                reservationID = reservationID,
                driverID = id!!,
                status = STATE_CONFIRMED
            )
        ) ?: return

        call.enqueue(object : Callback<BaseResponse<Int>> {
            override fun onResponse(
                call: Call<BaseResponse<Int>>,
                response: Response<BaseResponse<Int>>
            ) {
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

    // 예약내역 조회
    fun requestDriverReserve(
        pageNum: Int,
        completion: (code: Int, message: String?, data: List<OwnerYachtReservation>?) -> Unit
    ) {
        val id = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqDriverReserve(page = pageNum, driverID = id!!)
        val call = service?.requestDriverReserveList(req) ?: return

        call.enqueue(object : Callback<BaseResponse<List<OwnerYachtReservation>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<OwnerYachtReservation>>>,
                response: Response<BaseResponse<List<OwnerYachtReservation>>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, response.body()?.data)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<List<OwnerYachtReservation>>>,
                t: Throwable
            ) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 예약내역 상세 조회
    fun requestDriverReserveView(
        reservationID: String,
        completion: (code: Int, message: String?, data: OwnerYachtReservation?) -> Unit
    ) {
        val id = SharedPreferenceManager.instance.getString(Preference.SP_EMAIL, "")
        val req = ReqDriverReserveView(reservationID = reservationID, driverID = id!!)
        val call = service?.requestDriverReserveView(req) ?: return

        call.enqueue(object : Callback<BaseResponse<OwnerYachtReservation>> {
            override fun onResponse(
                call: Call<BaseResponse<OwnerYachtReservation>>,
                response: Response<BaseResponse<OwnerYachtReservation>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            completion(0, null, response.body()!!.data)
                        } else {
                            completion(-1, response.body()?.message, response.body()?.data)
                        }
                    }
                    else -> {
                        completion(response.code(), null, null)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<OwnerYachtReservation>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }
        })
    }
}