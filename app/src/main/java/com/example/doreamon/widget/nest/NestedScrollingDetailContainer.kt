package com.example.doreamon.widget.nest

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.NestedScrollingParent3

/**
 * @author wzh
 * @date 2022/10/12
 */
class NestedScrollingDetailContainer @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) :ViewGroup(mContext, attr){
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

}