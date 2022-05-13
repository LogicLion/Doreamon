package com.example.doreamon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R
import com.example.doreamon.entity.StudyTopic
import com.example.doreamon.ui.PracticeActivity

/**
 * @author wzh
 * @date 2022/5/9
 */
class TopicAdapter : BaseQuickAdapter<StudyTopic, BaseViewHolder>(R.layout.item_recycler_topic) {

    init {
        setOnItemClickListener { adapter, view, position ->
            val item = getItem(position)
            PracticeActivity.actionStart(context, item)
        }
    }

    override fun convert(holder: BaseViewHolder, item: StudyTopic) {
        holder.setText(R.id.tv_title, item.title)
    }
}