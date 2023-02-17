package com.example.doreamon.widget

import androidx.recyclerview.widget.RecyclerView

/**
 * @author wzh
 * @date 2022/4/6
 */
class LoopLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }


    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {
        super.onMeasure(recycler, state, widthSpec, heightSpec)
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        super.onLayoutChildren(recycler, state)

        detachAndScrapAttachedViews(recycler)
        var totalSpace = width - paddingRight
        val currentPosition = 0
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0

        while (totalSpace < 0 && currentPosition < state.itemCount) {

            val view = recycler.getViewForPosition(currentPosition)
            addView(view)
            val measuredWidth = getDecoratedMeasuredWidth(view)
            val measuredHeight = getDecoratedMeasuredHeight(view)
        }


    }
}