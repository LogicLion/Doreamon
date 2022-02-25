package com.example.doreamon.base

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType
import com.example.doreamon.BR
import java.util.*


abstract class DataBindingActivity<VM : BaseViewModel> : AppCompatActivity() {
    private lateinit var mActivityProvider: ViewModelProvider
    private var mFactory: ViewModelProvider.Factory? = null
    private var mBinding: ViewDataBinding? = null
    private lateinit var vm: VM
    protected val TAG = this.javaClass.simpleName

    /**
     * 布局id,ViewModel在布局里绑定的id请统一设置成"vm"
     */
    protected abstract fun setupLayoutId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ViewDataBinding =
            DataBindingUtil.setContentView(this, setupLayoutId())
        binding.lifecycleOwner = this

        val className = Objects.requireNonNull(javaClass.genericSuperclass)
        if (className is ParameterizedType && className.actualTypeArguments.isNotEmpty()) {
            val vmClass = className.actualTypeArguments[0] as Class<VM>
            vm = getViewModel(vmClass)
            binding.setVariable(BR.vm, vm)
        }

        mBinding = binding
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

    val isDebug: Boolean
        get() = applicationContext.applicationInfo != null &&
                (applicationContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

    protected fun showLongToast(text: String?) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    }

    protected fun showShortToast(text: String?) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    protected fun showLongToast(stringRes: Int) {
        showLongToast(applicationContext.getString(stringRes))
    }

    protected fun showShortToast(stringRes: Int) {
        showShortToast(applicationContext.getString(stringRes))
    }


    protected open fun <T : ViewModel?> getViewModel(modelClass: Class<T>): T {
        if (!::mActivityProvider.isInitialized) {
            mActivityProvider = ViewModelProvider(this)
        }
        return mActivityProvider[modelClass]
    }

}

