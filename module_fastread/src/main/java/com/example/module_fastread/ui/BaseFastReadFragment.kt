package com.example.module_fastread.ui

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.doreamon.treasure.ext.toast
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.example.module_fastread.R
import com.example.module_fastread.databinding.FastreadFragmentBaseFastReadBinding


/**
 * @author wzh
 * @date 2023/5/29
 */
abstract class BaseFastReadFragment<V : ViewDataBinding> : BaseFragment<BaseViewModel>() {
    protected lateinit var mGameBinding: V
    protected lateinit var gameConfig: GameConfig
    private lateinit var binding: FastreadFragmentBaseFastReadBinding

    override fun setupLayoutId() = R.layout.fastread_fragment_base_fast_read

    protected abstract fun setupGameLayoutId(): Int

    override fun initView() {

        binding = getViewBinding()
        mGameBinding =
            DataBindingUtil.inflate(layoutInflater, setupGameLayoutId(), binding.fl, true)

        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.tvSwitchScreenOrientation.text = "横屏"
        } else {
            binding.tvSwitchScreenOrientation.text = "竖屏"
        }

        binding.tvSwitchScreenOrientation.setOnClickListener {
            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }

        // 绑定生命周期管理
        mGameBinding.lifecycleOwner = this

        val config = arguments?.getSerializable("gameConfig") as? GameConfig

        if (config == null) {
            "缺少游戏参数".toast()
        } else {
            gameConfig = config
            initGameView()
        }

    }

    abstract fun initGameView()

    fun getTvPauseResume(): TextView {
        return binding.tvPauseResume
    }

    fun getTvSubTitle(): TextView {
        return binding.tvSubtitle
    }

}