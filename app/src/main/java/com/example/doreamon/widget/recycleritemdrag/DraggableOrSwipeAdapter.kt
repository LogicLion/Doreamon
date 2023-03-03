package com.example.doreamon.widget.recycleritemdrag

/**
 * @author wzh
 * @date 2023/2/28
 */
interface DraggableOrSwipeAdapter {
    /**
     * 当item被移动时调用
     * @param fromPosition 被操作的item的起点
     * @param toPosition   被操作的item的终点
     */
    fun onItemMove(fromPosition: Int, toPosition: Int)

    /**
     * 当item被侧滑时调用
     * @param position 被侧滑的item的position
     */
    fun onItemDismiss(position: Int)

}