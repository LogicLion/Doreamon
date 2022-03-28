package com.example.doreamon.ui

import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentHomePageBinding
import com.example.doreamon.entity.VideoLearnData
import com.example.doreamon.viewmodel.HomePageViewModel
import com.haibin.calendarview.Calendar

/**
 * @author wzh
 * @date 2022/2/23
 */
class HomePageFragment : BaseFragment<HomePageViewModel>() {

    override fun setupLayoutId() = R.layout.fragment_home_page

    override fun initView() {
        val binding = getViewBinding<FragmentHomePageBinding>()
//        binding.rv.adapter = HomeAdapter()
//        binding.rv.layoutManager = LinearLayoutManager(mContext)

        getViewModel()

        val calendarView1 = binding.calendarView
        val map = hashMapOf<String, Calendar>()

        map.put("2022-3-22",getSchemeCalendar(2022,3,22,"2022-3-22"))
        calendarView1.setSchemeDate(map)


    }


    private fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        text: String
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.scheme = text
        return calendar
    }

}