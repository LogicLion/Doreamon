package com.example.doreamon.ui.topic

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.adapter.RingChartAdapter
import com.example.doreamon.databinding.FragmentChartDemoBinding
import com.example.doreamon.entity.ChartData
import kotlin.random.Random

/**
 * 图表demo
 * @author wzh
 * @date 2023/1/3
 */
class ChartDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_chart_demo

    override fun initView() {
        val binding = getViewBinding<FragmentChartDemoBinding>()

        val chartList = arrayListOf<ChartData>()
        chartList.add(ChartData("每日10题", getRandomData(), "分钟"))
        chartList.add(ChartData("智能测评", getRandomData(), "分钟"))
        chartList.add(ChartData("同步练习", getRandomData(), "分钟"))
        chartList.add(ChartData("强化训练", getRandomData(), "分钟"))
        binding.histogramView.setChartList(chartList)


        val chartList1 = arrayListOf<ChartData>()
        chartList1.add(ChartData("周一", getRandomData(), "分钟"))
        chartList1.add(ChartData("周二", getRandomData(), "分钟"))
        chartList1.add(ChartData("周三", getRandomData(), "分钟"))
        chartList1.add(ChartData("周四", getRandomData(), "分钟"))
        chartList1.add(ChartData("周五", getRandomData(), "分钟"))
        chartList1.add(ChartData("周六", getRandomData(), "分钟"))
        chartList1.add(ChartData("周日", getRandomData(), "分钟"))
        binding.brokenLineView.setChartList(chartList1)


        val adapter = RingChartAdapter()
        binding.rv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rv.adapter = adapter
        val list = listOf("", "", "", "", "")
        adapter.setList(list)

        binding.ivUpdate.setOnClickListener {
            val chartList = arrayListOf<ChartData>()
            chartList.add(ChartData("每日10题", getRandomData(), "分钟"))
            chartList.add(ChartData("智能测评", getRandomData(), "分钟"))
            chartList.add(ChartData("同步练习", getRandomData(), "分钟"))
            chartList.add(ChartData("强化训练", getRandomData(), "分钟"))
            binding.histogramView.setChartList(chartList)


            val chartList1 = arrayListOf<ChartData>()
            chartList1.add(ChartData("周一", getRandomData(), "分钟"))
            chartList1.add(ChartData("周二", getRandomData(), "分钟"))
            chartList1.add(ChartData("周三", getRandomData(), "分钟"))
            chartList1.add(ChartData("周四", getRandomData(), "分钟"))
            chartList1.add(ChartData("周五", getRandomData(), "分钟"))
            chartList1.add(ChartData("周六", getRandomData(), "分钟"))
            chartList1.add(ChartData("周日", getRandomData(), "分钟"))
            binding.brokenLineView.setChartList(chartList1)

            adapter.notifyItemChanged(0)

        }


    }


    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }
}