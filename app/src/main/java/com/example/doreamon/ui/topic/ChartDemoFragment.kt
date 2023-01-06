package com.example.doreamon.ui.topic

import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentChartDemoBinding
import com.example.doreamon.entity.ChartData

/**
 * 图表demo
 * @author wzh
 * @date 2023/1/3
 */
class ChartDemoFragment :BaseFragment<BaseViewModel>() {
    override fun setupLayoutId()= R.layout.fragment_chart_demo

    override fun initView() {
        val binding = getViewBinding<FragmentChartDemoBinding>()

        val chartList = arrayListOf<ChartData>()
        chartList.add(ChartData("周一",20))
        chartList.add(ChartData("周二",50))
        chartList.add(ChartData("周三",90))
        chartList.add(ChartData("周四",6))
        chartList.add(ChartData("周五",100))
        chartList.add(ChartData("周六",30))
        chartList.add(ChartData("周日",50))
        binding.histogramView.setChartList(chartList)
    }
}