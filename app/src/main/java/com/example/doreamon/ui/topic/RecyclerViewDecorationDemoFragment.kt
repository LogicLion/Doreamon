package com.example.doreamon.ui.topic

import androidx.recyclerview.widget.GridLayoutManager
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R
import com.example.doreamon.common.GridItemDecoration
import com.example.doreamon.common.GridSectionAverageGapItemDecoration
import com.example.doreamon.databinding.FragmentRecyclerviewDecorationDemoBinding
import com.example.doreamon.ui.simple.SimpleAdapter
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.github.gzuliyujiang.wheelpicker.DatimePicker
import com.github.gzuliyujiang.wheelpicker.annotation.DateMode
import com.github.gzuliyujiang.wheelpicker.annotation.TimeMode
import com.github.gzuliyujiang.wheelpicker.entity.DatimeEntity
import java.util.*

/**
 * @author wzh
 * @date 2023/6/20
 */
class RecyclerViewDecorationDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_recyclerview_decoration_demo

    override fun initView() {
        val binding = getViewBinding<FragmentRecyclerviewDecorationDemoBinding>()
//
        val layoutManager = GridLayoutManager(requireActivity(), 4)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return if (position == 0 || position == 5 || position == 10) {
//                    2
//                } else if(position==11){
//                    3
//                } else {
//                    1
//                }
//            }
//
//        }
        val itemDecoration =
            GridItemDecoration(15,15)
        binding.rv.layoutManager = layoutManager
        binding.rv.addItemDecoration(itemDecoration)
        val adapter = SimpleAdapter()
        adapter.setList(
            mutableListOf(
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA",
                "AAAAAAAA"
            )
        )
        binding.rv.adapter = adapter
//        binding.tvDatePicker.setOnClickListener {
//            popPicker()
//        }
//
//
//        val wheelLayout = binding.timeWheel
////        picker.setOnDatimePickedListener { year, month, day, hour, minute, second ->
////            var text = "$year-$month-$day $hour:$minute:$second"
////            text += if (wheelLayout.timeWheelLayout.isAnteMeridiem) " 上午" else " 下午"
////        }
//        wheelLayout.setOnDatimeSelectedListener { year, month, day, hour, minute, second ->
//
//        }
//        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY)
//        wheelLayout.setTimeMode(TimeMode.HOUR_24_NO_SECOND)
//        wheelLayout.setRange(DatimeEntity.yearOnFuture(-1), DatimeEntity.yearOnFuture(1))
//        wheelLayout.setDateLabel("-", "-", "")
//        wheelLayout.setTimeLabel(":", "", "")
//        wheelLayout.yearLabelView.setTextColor(Color.parseColor("#2ac1ae"))
//        wheelLayout.yearLabelView.typeface = Typeface.DEFAULT_BOLD
//        wheelLayout.yearLabelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
//        wheelLayout.monthLabelView.setTextColor(Color.parseColor("#2ac1ae"))
//        wheelLayout.monthLabelView.typeface = Typeface.DEFAULT_BOLD
//        wheelLayout.monthLabelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
//        wheelLayout.hourLabelView.setTextColor(Color.parseColor("#2ac1ae"))
//        wheelLayout.hourLabelView.typeface = Typeface.DEFAULT_BOLD
//        wheelLayout.hourLabelView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
//        wheelLayout.hourLabelView.gravity = Gravity.CENTER
//        wheelLayout.hourLabelView.setPadding(0, 0, 0, 3.dp)
//        wheelLayout.setDefaultValue(DatimeEntity.now())


    }


    private fun popPicker() {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        calendar.time = date
        calendar[Calendar.DAY_OF_WEEK] = calendar.firstDayOfWeek
        val startYear = calendar.get(Calendar.YEAR)
        val startMonth = calendar.get(Calendar.MONTH) + 1
        val startDay = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatimePicker(requireActivity())
        val wheelLayout = picker.wheelLayout
        picker.setOnDatimePickedListener { year, month, day, hour, minute, second ->
            var text = "$year-$month-$day $hour:$minute:$second"
            text += if (wheelLayout.timeWheelLayout.isAnteMeridiem) " 上午" else " 下午"
        }
        wheelLayout.setDateMode(DateMode.YEAR_MONTH_DAY)
        wheelLayout.setTimeMode(TimeMode.HOUR_24_NO_SECOND)
        wheelLayout.setRange(DatimeEntity.yearOnFuture(-1), DatimeEntity.yearOnFuture(1))
        wheelLayout.setDateLabel("—", "—", "")
        wheelLayout.setTimeLabel(":", "", "")
        wheelLayout.setDefaultValue(DatimeEntity.now())
        picker.show()
    }
}