package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * @author wzh
 * @date 2022/3/14
 */
class ExpandableLinearLayout : LinearLayout {

    constructor(context: Context) : super(context) {
        initView()
    }


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        initView()
    }


    private fun initView() {
        orientation = VERTICAL
    }

}