package com.example.doreamon.ui.topic

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.doreamon.R
import com.example.doreamon.databinding.ActivityCameraPickerBinding
import com.example.module_base.base.BaseActivity
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2024/10/9
 */
class CameraPickerActivity : BaseActivity<BaseViewModel>() {

    lateinit var mBinding: ActivityCameraPickerBinding

    lateinit var imageCapture: ImageCapture
    override fun setupLayoutId() = R.layout.activity_camera_picker

    private var isFrontCamera = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = getViewBinding<ActivityCameraPickerBinding>()

        mBinding.tvTakePicture.setOnClickListener {
            takePhoto()
        }

        mBinding.tvSwitchCamera.setOnClickListener {
            isFrontCamera = !isFrontCamera
            setupCamera()
        }

        mBinding.viewFinder.post {
            val rotation = mBinding.viewFinder.display.rotation
            imageCapture = ImageCapture.Builder().setTargetRotation(rotation).build()
            setupCamera()
        }


    }


    private fun setupCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val rotation = mBinding.viewFinder.display.rotation
            // Preview
            val preview = Preview.Builder()

                .setTargetRotation(rotation)
                .build()
                .also {
                    it.setSurfaceProvider(mBinding.viewFinder.surfaceProvider)
                }

//            preview.targetRotation= mBinding.viewFinder.display.rotation

            // Select back camera as a default
            val cameraSelector =
                if (isFrontCamera) CameraSelector.DEFAULT_FRONT_CAMERA else CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))

    }


    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
//        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
//            .format(System.currentTimeMillis())
//        val contentValues = ContentValues().apply {
//            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
//            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
//            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
//                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
//            }
//        }

        // Create output options object which contains file + metadata
//        val outputOptions = ImageCapture.OutputFileOptions
//            .Builder(contentResolver,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                contentValues)
//            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
//                }
//
//                override fun
//                        onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val msg = "Photo capture succeeded: ${output.savedUri}"
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, msg)
//                }
//            }
//        )

        imageCapture.takePicture(ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(imageProxy: ImageProxy) {
                    mBinding.ivImagePreview.visibility = View.VISIBLE

                    Log.i("", "旋转角度:${imageProxy.imageInfo.rotationDegrees}")

                    imageProxy

                    // 从ImageProxy中获取Bitmap
                    val bitmap = imageProxy?.let { proxy ->
                        val planeBuffer = proxy.planes[0].buffer
                        val bytes = ByteArray(planeBuffer.remaining())
                        planeBuffer.get(bytes)

                        val matrix = Matrix()

                        BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//                        matrix.postRotate(-imageProxy.imageInfo.rotationDegrees.toFloat(), bitmap.width / 2f, bitmap.height / 2f)
//                        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                    }

                    bitmap?.let {
                        mBinding.ivImagePreview.setImageBitmap(it)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    exception.printStackTrace()

                    Toast.makeText(this@CameraPickerActivity, "拍照失败", Toast.LENGTH_SHORT).show()
                }
            }

        )
    }


}