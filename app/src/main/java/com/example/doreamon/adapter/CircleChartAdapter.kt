package com.example.doreamon.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R
import com.example.doreamon.widget.CircleProgressView
import kotlin.random.Random

/**
 * @author wzh
 * @date 2023/2/16
 */
class CircleChartAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycler_circle_progress) {
    override fun convert(holder: BaseViewHolder, item: String) {

        val circleProgressView = holder.getView<CircleProgressView>(R.id.circle_progress_view)

        when (holder.absoluteAdapterPosition) {
            0 -> {
                circleProgressView.chartType = CircleProgressView.Type.FILL_PERCENT
            }
            1 -> {
                circleProgressView.chartType = CircleProgressView.Type.FILL_TITLE
            }
            else -> {
                circleProgressView.chartType = CircleProgressView.Type.DEFAULT
                circleProgressView.setProgressBarColor(Color.parseColor(item))
            }
        }

        circleProgressView.setPercent(getRandomData())

    }

    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }
}