package com.example.doreamon.ui.simple

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.doreamon.treasure.utils.ImageUtil
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2023/9/21
 */
class LoopViewPagerAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_loop_view_pager) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val iv = holder.getView<ImageView>(R.id.iv)
        ImageUtil.loadUrl(iv, item)
    }
}