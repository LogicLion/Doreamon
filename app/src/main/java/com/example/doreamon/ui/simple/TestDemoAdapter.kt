package com.example.doreamon.ui.simple

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R
import com.example.doreamon.entity.ChatMessage
import com.example.doreamon.widget.expandabletext.ExpandFoldTextView
import com.example.doreamon.widget.expandabletext.ExpandTextView

/**
 * @author wzh
 * @date 2023/3/16
 */
class TestDemoAdapter :
    BaseQuickAdapter<ChatMessage, BaseViewHolder>(R.layout.item_recycler_test_demo) {
    override fun convert(holder: BaseViewHolder, item: ChatMessage) {
        holder.setIsRecyclable(false)
        val tvFold = holder.getView<TextView>(R.id.tv_fold_unfold)
        val tvQuoteMsg = holder.getView<ExpandTextView>(R.id.tv_quote_msg)
        val tvQuoteMsgCopy = holder.getView<TextView>(R.id.tv_quote_msg_copy)

        tvQuoteMsg.isSuggestionsEnabled
        tvQuoteMsg.setText(item.quoteMsg, item.isExpand, object : ExpandTextView.Callback {
            override fun onExpand() {
                tvFold.visibility = View.VISIBLE
                tvFold.text = "收起"
            }

            override fun onCollapse() {
                tvFold.visibility = View.VISIBLE
                tvFold.text = "展开"
            }

            override fun onLoss() {
                tvFold.visibility = View.GONE
            }

        })

        tvFold.setOnClickListener {
            item.isExpand = !item.isExpand
            tvQuoteMsg.setChanged(item.isExpand)
        }
//        tvQuoteMsg.text = item.quoteMsg
//        tvQuoteMsgCopy.text = item.quoteMsg
//        tvQuoteMsgCopy.post {
//            val preLineCount = tvQuoteMsgCopy.lineCount
//            if (preLineCount <= 3) {
//                tvQuoteMsg.setLines(preLineCount)
//                tvFold.visibility = View.GONE
//                return@post
//            } else {
//                tvFold.visibility = View.VISIBLE
//            }
//            val fold = item.isFold
//            if (fold) {
//                tvQuoteMsg.setLines(3)
//                tvFold.text = "展开"
//            } else {
//                tvQuoteMsg.setLines(preLineCount)
//                tvFold.text = "收起"
//            }
//        }

//        tvQuoteMsg.setOriginalText(item.quoteMsg)
//        if (item.isFold) {
//            tvQuoteMsg.fold()
//            tvFold.text = "展开"
//        } else {
//            tvQuoteMsg.expand()
//            tvFold.text = "收起"
//        }

//        tvFold.setOnClickListener {
//            item.isFold = !item.isFold
//            val preLineCount = tvQuoteMsgCopy.lineCount
//            if (item.isFold) {
//                tvQuoteMsg.setLines(3)
//            } else {
//                tvQuoteMsg.setLines(preLineCount)
//            }
////            item.isFold = !item.isFold
////            if (item.isFold) {
////                tvQuoteMsg.fold()
////                tvFold.text = "展开"
////            } else {
////                tvQuoteMsg.expand()
////                tvFold.text = "收起"
////            }
//            notifyItemChanged(holder.position)
//        }
    }

}