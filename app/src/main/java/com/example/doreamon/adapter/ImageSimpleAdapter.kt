package com.example.doreamon.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R
import com.example.doreamon.widget.recycleritemdrag.CustomItemTouchHelper
import com.example.doreamon.widget.recycleritemdrag.DraggableOrSwipeAdapter
import java.util.*

/**
 * @author wzh
 * @date 2023/2/17
 */
class ImageSimpleAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycler_image_simple),
    DraggableOrSwipeAdapter {
    override fun convert(holder: BaseViewHolder, item: String) {

        holder.setText(R.id.tv_index, holder.absoluteAdapterPosition.toString())
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(data, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val itemTouchHelper = ItemTouchHelper(CustomItemTouchHelper(this))
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}