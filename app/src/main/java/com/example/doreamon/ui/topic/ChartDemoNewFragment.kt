package com.example.doreamon.ui.topic

import android.graphics.Color
import android.util.Log
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentChartDemoNewBinding
import com.example.doreamon.entity.ChartData
import com.example.doreamon.entity.PieData
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import kotlin.random.Random

/**
 * 图表demo
 * @author wzh
 * @date 2023/1/3
 */
class ChartDemoNewFragment : BaseFragment<BaseViewModel>() {

    override fun setupLayoutId() = R.layout.fragment_chart_demo_new

    override fun initView() {
        val binding = getViewBinding<FragmentChartDemoNewBinding>()

        setChartData()

        binding.tvUpdate.setOnClickListener {
            setChartData()

        }

    }


    private fun setChartData() {
        val binding = getViewBinding<FragmentChartDemoNewBinding>()

        binding.ivLock.setImageResource(R.drawable.ic_reading_exercise_lock)
        binding.ivLock.setColorFilter(Color.RED)

        binding.customSeekBar.setProgressRange(0, 100)
        binding.customSeekBar.setProgress(50)
        binding.customSeekBar.onProgressChanged = { progress, fromUser ->
            if (fromUser) {
                Log.i("ChartDemoNewFragment", "progress:$progress")
            }
        }

        binding.ringPieView.setTitle("各类型学习时长\n占比图 (%)")
        val pieDataList = arrayListOf<PieData>()
        pieDataList.add(
            PieData(
                value = getRandomData(),
                color = Color.parseColor("#5AC1F8"),
                "智能测评"
            )
        )
        pieDataList.add(
            PieData(
                value = getRandomData(),
                color = Color.parseColor("#0ED3D5"),
                "课程学习"
            )
        )
        pieDataList.add(PieData(value = 2, color = Color.parseColor("#FF7A8E"), "精准练习"))
        pieDataList.add(PieData(value = 1, color = Color.parseColor("#FFD233"), "其他"))

        pieDataList.add(PieData(value = 30, color = Color.parseColor("#FF7ccc"), "精准练习"))
        pieDataList.add(PieData(value = 5, color = Color.parseColor("#FF561c"), "其他"))

        pieDataList.add(PieData(value = 30, color = Color.parseColor("#dc95dc"), "精准练习"))
        pieDataList.add(PieData(value = 5, color = Color.parseColor("#09651c"), "其他"))

        binding.ringPieView.setPieDataList(pieDataList)

        val chartList2 = arrayListOf<ChartData>()
        chartList2.add(ChartData("周一", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        chartList2.add(ChartData("周二", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        chartList2.add(ChartData("周三", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        chartList2.add(ChartData("周四", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        chartList2.add(ChartData("周五", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        chartList2.add(ChartData("周六", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        chartList2.add(ChartData("周日", y = getRandomData(), y1 = getRandomData(), yUnit = "分钟"))
        binding.brokenLineView.setChartList(chartList2)


        val chartList3 = arrayListOf<ChartData>()
        chartList3.add(ChartData("第1次", y = getRandomData(), yUnit = "分"))
        chartList3.add(ChartData("第2次", y = getRandomData(), yUnit = "分"))
        chartList3.add(ChartData("第3次", y = getRandomData(), yUnit = "分"))
        chartList3.add(ChartData("第4次", y = getRandomData(), yUnit = "分"))
        chartList3.add(ChartData("第5次", y = getRandomData(), yUnit = "分"))
        binding.scoreBrokenLineView.setChartList(chartList3)

        binding.correctPercentView.setPercent(getRandomData())
        binding.correctPercentView.setText("答题正确率")

//        binding.circleRatioView.setPercent(getRandomData())

        binding.circleRatioView.setMarkColor1(Color.parseColor("#25A6FB"))
        binding.circleRatioView.setMarkColor2(Color.parseColor("#FFA40A"))
        binding.circleRatioView.setProgressBarColor1(
            intArrayOf(
                Color.parseColor("#25A6FB"),
                Color.parseColor("#64C1FF"),
                Color.parseColor("#25A6FB")
            )
        )

        binding.circleRatioView.setProgressBarColor2(
            intArrayOf(
                Color.parseColor("#FFA933"),
                Color.parseColor("#FD8241"),
                Color.parseColor("#FFA933")
            )
        )
        binding.circleRatioView.setRatio(getRandomData(), getRandomData())
        binding.circleRatioView.setText("掌握率")


    }


    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }
}