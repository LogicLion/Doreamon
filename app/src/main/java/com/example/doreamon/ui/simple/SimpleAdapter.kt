package com.example.doreamon.ui.simple

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2022/10/26
 */
class SimpleAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycler_simple) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_text, item)
    }
}