package com.example.doreamon.widget

import androidx.recyclerview.widget.RecyclerView

/**
 * 仿探探卡片效果
 * @author wzh
 */
class TantanCardLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)

        //移除所有的子view

        //
    }
}