package com.example.doreamon.common

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doreamon.treasure.ext.dp

/**
 * @author wzh
 * @date 2023/10/20
 */
class GridItemDecoration(
    horizontalSpacing: Int,
    verticalSpacing: Int,
    private val includeEdge: Boolean = false
) : RecyclerView.ItemDecoration() {

    private val verticalSpacing: Int
    private val horizontalSpacing: Int

    init {
        this.horizontalSpacing = horizontalSpacing.dp
        this.verticalSpacing = verticalSpacing.dp
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            // 计算当前行的列数
            val spanSizeLookup = layoutManager.spanSizeLookup
            //获取行索引
            val spanRow = spanSizeLookup.getSpanGroupIndex(position, spanCount)
            //获取列索引
            val spanColumn = spanSizeLookup.getSpanIndex(position, spanCount)
            // 获取当前item占用spanSize
            val spanSize = spanSizeLookup.getSpanSize(position)

//            val totalSpanSize = spanSizeLookup.getSpanGroupIndex(position, spanCount) + 1
//            val columnCount = spanCount / spanSize

            //当SpanSize不确定的时候，这里的columnSize没有确定，也就是每行item个数，实在不行，根据具体item的spanSize设置方式来计算
            val columnSize = spanCount

//            Log.v(
//                "GridItemDecoration",
//                "position:${position},spanRow:${spanRow},SpanColumn:${spanColumn},spanSize:${spanSize}"
//            )

            if (includeEdge) {
                outRect.left = horizontalSpacing - column * horizontalSpacing / spanCount
                outRect.right = (column + 1) * horizontalSpacing / spanCount

                if (position < spanCount) {
                    outRect.top = verticalSpacing
                }
                outRect.bottom = verticalSpacing
            } else {
                outRect.left = spanColumn * horizontalSpacing / columnSize
                outRect.right =
                    horizontalSpacing - (spanColumn + 1) * horizontalSpacing / columnSize
                if (position >= spanCount) {
                    outRect.top = verticalSpacing
                }

//                val left = spanColumn * horizontalSpacing / columnSize
//                val right = horizontalSpacing - (spanColumn + 1) * horizontalSpacing / columnSize
//                Log.v(
//                    "GridItemDecoration",
//                    "position:${position},column:${spanColumn},left:${left},right:${right}"
//                )

//                val left = column * horizontalSpacing / spanCount
//                val right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount
//                Log.v(
//                    "GridItemDecoration",
//                    "position:${position},column:${column},left:${left},right:${right}"
//                )
            }
        }


    }
}