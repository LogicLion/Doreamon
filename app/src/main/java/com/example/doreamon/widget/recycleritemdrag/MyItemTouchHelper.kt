package com.example.doreamon.widget.recycleritemdrag

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * @author wzh
 * @date 2023/2/28
 */
class MyItemTouchHelper(val mAdapter: DraggableOrSwipeAdapter) : ItemTouchHelper.Callback() {


    //返回可拖拽的方向
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        // 返回可滑动方向，通过使用一个int，在各个bit位标记来记录。
        // 这里drag支持上下方向，swipe支持左右方向。
        val dragFlag = ItemTouchHelper.UP or ItemTouchHelper.DOWN

        //向左侧滑，若有其他需求同理
        val swipeFlag = ItemTouchHelper.LEFT

        return makeMovementFlags(dragFlag, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        //若返回false则表示不支持上下拖拽
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }
}