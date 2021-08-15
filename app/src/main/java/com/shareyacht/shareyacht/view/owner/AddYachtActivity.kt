package com.shareyacht.shareyacht.view.owner

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.shareyacht.shareyacht.R
import com.shareyacht.shareyacht.databinding.ActivityAddYachtBinding
import com.shareyacht.shareyacht.utils.Constants
import com.shareyacht.shareyacht.viewmodel.AddYachtViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class AddYachtActivity : AppCompatActivity() {
    private val viewModel: AddYachtViewModel by viewModels()
    private lateinit var uri: Uri

    private lateinit var mBinding: ActivityAddYachtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityAddYachtBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_add_yacht
        )

        mBinding = binding

        // viewModel 바인딩
        binding.viewModel = viewModel
        binding.addPhotoButton.setOnClickListener(addPhotoButtonClicked)

        subscribeImageStatus()
        subscribeConnectionStatus()
    }

    // 통신 중인 경우 버튼 비활성화
    private fun subscribeConnectionStatus() {
        viewModel.busy.observe(this, { busy ->
            mBinding.addYachtButton.isEnabled = !busy
            mBinding.addPhotoButton.isEnabled = !busy
        })
    }

    // 이미지 업로드 버튼 클릭 리스너
    private val addPhotoButtonClicked = View.OnClickListener {
        when {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                // 권한이 있는 경우
                selectImage()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showPermissionContextPopup()
            }

            else -> {
                // 권한이 없는 경우
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    // 갤러리에서 사진 선택 화면으로 이동
    private fun selectImage() {
        getContent.launch("image/*")
    }

    // 갤러리에서 사진 선택한 후
    private val getContent = registerForActivityResult(GetContent()) { selectedImageUri ->
        if (selectedImageUri != null) {
            // uri 가 null이 아닌 경우 파일 생성 및 서버에 이미지 업로드 요청
            try {
                val inputStream = contentResolver.openInputStream(selectedImageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val imageFile = getImageFile(bitmap)

                uri = selectedImageUri
                // 서버에 이미지 업로드 요청
                viewModel.uploadImage(imageFile)

            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 이미지 업로드에 성공하면 이미지 입히기
    private fun subscribeImageStatus() {
        viewModel.uploadImageSuccess.observe(this, { status ->
            if (status)
                setImageOnButton(uri)

        })
    }

    private fun setImageOnButton(uri: Uri) {
        mBinding.addPhotoButton.setImageURI(uri)
    }

    // bitmap -> file
    private fun getImageFile(bitmap: Bitmap): File {
        val filesDir = applicationContext.filesDir
        val imageFile = File(filesDir, "itemphoto.jpg")

        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e(Constants.TAG, "Error writing Bitmap", e)
        }

        return imageFile
    }

    // 권한체크 런처
    private val permissionLauncher = registerForActivityResult(
        RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // 권한이 허용된 경우
            selectImage()
        } else {
            // 아닌 경우
            showPermissionContextPopup()
        }
    }

    // 권한 요청 팝업
    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setMessage("앱에서 사진을 가져오기 위해서는 저장소 권한이 필요합니다.")
            .setPositiveButton("확인") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000
                )
            }
            .setNegativeButton("취소") { _, _ -> }
            .create().show()
    }
}