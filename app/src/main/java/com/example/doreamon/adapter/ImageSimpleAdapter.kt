package com.example.doreamon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2023/2/17
 */
class ImageSimpleAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycler_image_simple) {
    override fun convert(holder: BaseViewHolder, item: String) {

    }
}