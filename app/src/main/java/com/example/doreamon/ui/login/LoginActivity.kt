package com.example.doreamon.ui.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseActivity
import com.example.doreamon.databinding.ActivityLoginBinding
import com.doreamon.treasure.ext.startTargetActivity
import com.example.doreamon.global.UserInfoData
import com.example.doreamon.ui.topic.FragmentToggleActivity
import com.doreamon.treasure.utils.AppManager
import com.example.doreamon.viewmodel.LoginViewModel

/**
 * @author wzh
 * @date 2022/1/21
 */
class LoginActivity : BaseActivity<LoginViewModel>() {
    override fun setupLayoutId() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 关闭除登录页外所有界面
        AppManager.finishAllWithout(LoginActivity::class.java)

        //清除登录信息
        UserInfoData.value = null
        val viewModel = getViewModel()

        val binding = getViewBinding<ActivityLoginBinding>()
        binding.btnLogin.setOnClickListener {
            viewModel.loginByPassword {
                startActivity(Intent(this@LoginActivity, FragmentToggleActivity::class.java))
                finish()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

    }

    override fun initBar() {
        window.statusBarColor = Color.TRANSPARENT

        val clRootView = findViewById<ConstraintLayout>(R.id.cl_root_view)
//        clRootView.systemUiVisibility=(SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)


        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = ViewCompat.getWindowInsetsController(clRootView).let {

        }


//        ViewCompat.setOnApplyWindowInsetsListener(clRootView
//        ) { v, insets ->
//            val layoutParams = v.layoutParams as FrameLayout.LayoutParams
//            layoutParams.topMargin = insets.systemWindowInsetTop
//
//            insets
//        }

//        ivBg.fitsSystemWindows=true
//        clRootView.fitsSystemWindows = true

    }

    companion object {

        /**
         * 使用 [context] 打开 [LoginActivity] 界面
         * > 栈堆已有登录页时不会重复打开
         */
        fun actionStart(context: Context) {
            if (AppManager.contains(LoginActivity::class.java)) {
                // 堆栈中已有登录页，返回
                return
            }
            context.startTargetActivity<LoginActivity>()
        }
    }
}