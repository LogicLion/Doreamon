package com.example.doreamon.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doreamon.BR
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 * @author wzh
 * @date 2022/2/23
 */
abstract class DataBindingFragment<VM : BaseViewModel> :Fragment() {
    private lateinit var mActivityProvider: ViewModelProvider

    /** 根布局对象 */
    protected var rootView: View? = null

    /** DataBinding 对象 */
    protected lateinit var mBinding: ViewDataBinding

    /** 当前界面 Context 对象*/
    protected lateinit var mContext: FragmentActivity

    protected val TAG = this.javaClass.simpleName

    private lateinit var vm: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 保存当前 Context 对象
        mContext = requireActivity()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (null == rootView) {
            // 初始化 DataBinding
            mBinding = DataBindingUtil.inflate(inflater, setupLayoutId(), container, false)

            // 绑定生命周期管理
            mBinding.lifecycleOwner = this

            // 绑定 ViewModel
            val className = Objects.requireNonNull(javaClass.genericSuperclass)
            if (className is ParameterizedType && className.actualTypeArguments.isNotEmpty()) {
                val vmClass = className.actualTypeArguments[0] as Class<VM>
                vm = getViewModel(vmClass)
                mBinding.setVariable(BR.vm, vm)
            }

            rootView = mBinding.root

            // 初始化布局
            initView()
        } else {
            (rootView?.parent as? ViewGroup?)?.removeView(rootView)
        }

        return rootView
    }

    fun <T : ViewDataBinding> getViewBinding(): T {
        return mBinding as T
    }

    fun getViewModel(): VM {
        if (!::vm.isInitialized) {
            Log.e(TAG, "未绑定viewModel")
        }
        return vm
    }

    protected open fun <T : ViewModel?> getViewModel(modelClass: Class<T>): T {
        if (!::mActivityProvider.isInitialized) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider[modelClass]
    }

    /**
     * 布局id,ViewModel在布局里绑定的id请统一设置成"vm"
     */
    protected abstract fun setupLayoutId(): Int

    /**
     * 初始化布局
     */
    abstract fun initView()


}