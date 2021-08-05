package com.shareyacht.shareyacht.retrofit

import android.util.Log
import com.shareyacht.shareyacht.model.*
import com.shareyacht.shareyacht.utils.API
import com.shareyacht.shareyacht.utils.Constants.TAG
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
        completion: (code: Int, message: String?) -> Unit
    ) {
        val req = ReqLogin(email = email, password = password, userType = userType)
        val call = service?.requestLogin(req) ?: return

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

        call.enqueue(object : Callback<BaseResponse<ResUploadImage>> {
            override fun onResponse(
                call: Call<BaseResponse<ResUploadImage>>,
                response: Response<BaseResponse<ResUploadImage>>
            ) {
                when (response.code()) {
                    200 -> {
                        Log.d(TAG, response.raw().toString())
                        if (response.body()?.error == false) {
                            // 에러가 없으면 imageID 받음
                            val imageID = response.body()!!.data.imageid
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

            override fun onFailure(call: Call<BaseResponse<ResUploadImage>>, t: Throwable) {
                completion(-1, t.toString(), null)
            }

        })
    }

    // 요트 등록
    fun requestAddYacht(
        yacht: Yacht,
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
}