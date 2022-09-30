package com.example.doreamon.ui.simple

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2022/9/16
 */
class MenuAdapter :BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_recycler_menu) {
    override fun convert(holder: BaseViewHolder, item: String) {
    }
}