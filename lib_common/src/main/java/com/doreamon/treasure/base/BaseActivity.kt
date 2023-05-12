package com.doreamon.treasure.base

import android.content.pm.ActivityInfo
import android.os.Bundle


/**
 * @author wzh
 * @date 2021/12/9
 */
abstract class BaseActivity<VM : BaseViewModel> : DataBindingActivity<VM>() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setScreenOrientation()
        super.onCreate(savedInstanceState)
        val viewModel = getViewModel()
//        viewModel.isLoading.observe(this, {
//            if (it) {
//                loadDialog.show()
//            } else {
//                loadDialog.dismiss()
//            }
//        })
//
//        viewModel.alertDialogModel.observe(this, {
//            CommonConfirmDialog(this).setTitle(it.title).setMessage(it.content).setNegativeButtonGone(it.cancelBtnGone)
//                .setPositiveButton(it.actionText, it.onAction).show()
//        })

        initBar()

    }

    /**
     * 设置屏幕方向,默认锁竖屏
     */
    protected open fun setScreenOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    protected open fun initBar() {
//        ImmersionBar.with(this)
//            .fitsSystemWindows(true)
//            .statusBarColor(R.color.white)
//            .statusBarDarkFont(true)
//            .init()
    }



}