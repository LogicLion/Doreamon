package com.example.doreamon.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.example.doreamon.R
import com.example.doreamon.common.CommonConfirmDialog
import com.example.doreamon.widget.ViewLoadingFix
import com.gyf.immersionbar.ImmersionBar


/**
 * @author wzh
 * @date 2021/12/9
 */
abstract class BaseActivity<VM : BaseViewModel> : DataBindingActivity<VM>() {

    private val loadDialog: ViewLoadingFix by lazy {
        ViewLoadingFix(this, "加载中", false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {


        setScreenOrientation()
        super.onCreate(savedInstanceState)
        val viewModel = getViewModel()
        viewModel.isLoading.observe(this, {
            if (it) {
                loadDialog.show()
            } else {
                loadDialog.dismiss()
            }
        })

        viewModel.alertDialogModel.observe(this, {
            CommonConfirmDialog(this).setTitle(it.title).setMessage(it.content).setNegativeButtonGone(it.cancelBtnGone)
                .setPositiveButton(it.actionText, it.onAction).show()
        })

        initBar()



    }

    /**
     * 设置屏幕方向,默认锁竖屏
     */
    protected open fun setScreenOrientation(){
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected open fun initBar() {
        ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.white)
            .statusBarDarkFont(true)
            .init()
    }
}