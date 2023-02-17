package com.example.doreamon.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.doreamon.R
import com.example.doreamon.widget.RingChartView
import kotlin.random.Random

/**
 * @author wzh
 * @date 2023/2/16
 */
class RingChartAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recycler_ring_chart) {
    override fun convert(holder: BaseViewHolder, item: String) {

        val ringChartView = holder.getView<RingChartView>(R.id.ring_chart_view)
        ringChartView.setPercent(getRandomData())
    }

    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }
}