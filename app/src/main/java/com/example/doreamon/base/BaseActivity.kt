package com.example.doreamon.base

import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.doreamon.common.CommonConfirmDialog
import com.example.doreamon.widget.ViewLoadingFix
import com.gyf.immersionbar.BuildConfig
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
//        ImmersionBar.with(this)
//            .fitsSystemWindows(true)
//            .statusBarColor(R.color.white)
//            .statusBarDarkFont(true)
//            .init()
    }


    /**
     * 设置状态栏文字图标颜色为深色
     */
    fun setStatusBarTextDark() {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//
////            window.insetsController?.setSystemBarsAppearance(0,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
//            WindowInsetsControllerCompat(window,window.decorView).isAppearanceLightStatusBars = true
//
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //设置状态栏文字图标为黑色
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
    }

    /**
     * 设置状态栏文字图标颜色为浅色
     */
    fun setStatusBarTextLight() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
////            window.insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
//            WindowInsetsControllerCompat(window,window.decorView).isAppearanceLightStatusBars = false
//        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //设置状态栏文字图标为黑色
        val flags = window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.decorView.systemUiVisibility = flags xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
    }


    /**
     * 设置状态栏不占位
     */
    fun setStatusBarNotPlaceHolder() {
        val flags = window.decorView.systemUiVisibility
        window.statusBarColor=Color.TRANSPARENT
        window.decorView.systemUiVisibility = flags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }


    /**
     * 改变状态栏文字颜色
     * 只有2种选择,白色与黑色
     */
    protected fun changeStatusFountColor(isLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //android R (android 11, API 30) 使用下面的新api
            /*
             传入0则是清理状态,恢复高亮
             */
            val state = if (isLight) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0
            window?.insetsController?.setSystemBarsAppearance(state, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
        } else {
            //低于android R 使用兼容模式
            ViewCompat.getWindowInsetsController(window.decorView).let { controller ->
                controller?.isAppearanceLightStatusBars = isLight
            }
        }
    }
}