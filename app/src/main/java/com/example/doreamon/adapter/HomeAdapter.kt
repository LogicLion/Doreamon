package com.example.doreamon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R
import com.example.doreamon.entity.ArticleListEntity

/**
 * @author wzh
 * @date 2022/3/1
 */
class HomeAdapter : BaseQuickAdapter<ArticleListEntity,BaseViewHolder>(R.layout.item_home_list) {
    override fun convert(holder: BaseViewHolder, item: ArticleListEntity) {

    }
}