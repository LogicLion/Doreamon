package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.GridLayoutManager
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.adapter.CircleChartAdapter
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

        setChartData()

        val adapter = CircleChartAdapter()
        binding.rv.layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rv.adapter = adapter
        val list =
            listOf("#2AC1AE", "#2AC1AE", "#2AC1AE", "#FEAE54", "#FF86A6", "#FED667")
        adapter.setList(list)

        binding.tvUpdate.setOnClickListener {
            setChartData()
            adapter.notifyDataSetChanged()
        }


    }


    private fun setChartData() {
        val binding = getViewBinding<FragmentChartDemoBinding>()
        val chartList = arrayListOf<ChartData>()
        chartList.add(ChartData("每日10题", getRandomData(), "分钟"))
        chartList.add(ChartData("智能测评", getRandomData(), "分钟"))
        chartList.add(ChartData("同步练习", getRandomData(), "分钟"))
        chartList.add(ChartData("强化训练", getRandomData(), "分钟"))
        binding.histogramView.setChartList(chartList)


        val chartList1 = arrayListOf<ChartData>()
        chartList1.add(ChartData("周一", getRandomData(), "题"))
        chartList1.add(ChartData("周二", getRandomData(), "题"))
        chartList1.add(ChartData("周三", getRandomData(), "题"))
        chartList1.add(ChartData("周四", getRandomData(), "题"))
        chartList1.add(ChartData("周五", getRandomData(), "题"))
        chartList1.add(ChartData("周六", getRandomData(), "题"))
        chartList1.add(ChartData("周日", getRandomData(), "题"))
        binding.histogramView1.setChartList(chartList1)


        val chartList2 = arrayListOf<ChartData>()
        chartList2.add(ChartData("周一", getRandomData(), "分钟"))
        chartList2.add(ChartData("周二", getRandomData(), "分钟"))
        chartList2.add(ChartData("周三", getRandomData(), "分钟"))
        chartList2.add(ChartData("周四", getRandomData(), "分钟"))
        chartList2.add(ChartData("周五", getRandomData(), "分钟"))
        chartList2.add(ChartData("周六", getRandomData(), "分钟"))
        chartList2.add(ChartData("周日", getRandomData(), "分钟"))
        binding.brokenLineView.setChartList(chartList2)


        val chartList3 = arrayListOf<ChartData>()
        chartList3.add(ChartData("周一", getRandomData(), "分钟", "10/10"))
        chartList3.add(ChartData("周二", getRandomData(), "分钟", "10/11"))
        chartList3.add(ChartData("周三", getRandomData(), "分钟", "10/12"))
        chartList3.add(ChartData("周四", getRandomData(), "分钟", "10/13"))
        chartList3.add(ChartData("周五", getRandomData(), "分钟", "10/14"))
        chartList3.add(ChartData("周六", getRandomData(), "分钟", "10/15"))
        chartList3.add(ChartData("周日", getRandomData(), "分钟", "10/16"))
        binding.brokenLineView1.setChartList(chartList3)


        binding.targetProgressBar1.setRate(10, 30)
        binding.targetProgressBar2.setRate(20, 30)
        binding.targetProgressBar3.setRate(3, 3)

        binding.targetProgressBar4.setRate(getRandomData(), 100)
        binding.targetProgressBar5.setRate(getRandomData(), 100)
        binding.targetProgressBar6.setRate(getRandomData(), 100)
        binding.targetProgressBar7.setRate(getRandomData(), 100)


        val randomData = getRandomData()
        binding.circlePercentView.setProportion(randomData, "闯关答题", 100 - randomData, "观看视频")

    }


    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }
}