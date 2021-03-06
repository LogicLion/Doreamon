package com.example.doreamon.ui

import com.example.doreamon.ui.simple.TestFragment
import com.example.doreamon.ui.topic.*
import java.io.Serializable

/**
 * 学习专题
 * @author wzh
 * @date 2022/5/7
 */
class StudyTopic(
    val title:String,
    val fragmentTag:String
) :Serializable{

    companion object{

        val List = listOf(

            StudyTopic("测试", TestFragment::class.java.name),
            StudyTopic("图片大图预览", ImagePreviewFragment::class.java.name),
            StudyTopic("嵌套滑动", NestedScrollDemoFragment::class.java.name),
            StudyTopic("ViewPager2实践",ViewPager2DemoFragment::class.java.name),
            StudyTopic("WebView使用",WebViewDemoFragment::class.java.name),
            StudyTopic("刮刮卡效果",ScratchViewDemoFragment::class.java.name),
//            StudyTopic("循环列表",LoopRecyclerViewFragment::class.java.name),
            StudyTopic("自定义进度条",ProgressViewDemoFragment::class.java.name),
//            StudyTopic("Banner",BannerFragment::class.java.name)
        )




    }
}