package com.example.doreamon.ui.topic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.doreamon.R
import com.example.doreamon.base.AppI
import com.example.doreamon.databinding.FragmentCameraDemoBinding
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel

/**
 * @author wzh
 * @date 2024/10/9
 */
class CameraDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_camera_demo

    override fun initView() {
        val binding = getViewBinding<FragmentCameraDemoBinding>()
        binding.tvCamera.setOnClickListener {
            // Request camera permissions
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }

        }
    }


    private fun startCamera() {
        startActivity(Intent(requireActivity(), CameraPickerActivity::class.java))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            AppI.instance, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}