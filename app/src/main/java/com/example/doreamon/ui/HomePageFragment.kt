package com.example.doreamon.ui

import android.graphics.Color
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.databinding.FragmentHomePageBinding
import com.example.doreamon.entity.VideoLearnData
import com.example.doreamon.utils.dip2px
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

        getViewModel()

        val planView = binding.planView

        planView.setProgressColor(Color.parseColor("#45C4ED"))
        planView.setBgColor(Color.parseColor("#A7E3FF"))

        planView.setProgressAndTotal(20, 30)


    }


}