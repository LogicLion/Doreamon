package com.example.doreamon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2022/5/6
 */
class LoopAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_loop) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_title, item)
    }
}