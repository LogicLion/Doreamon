package com.example.doreamon

import com.example.doreamon.ui.topic.CollapseFragment
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
            StudyTopic("刮刮卡效果",ScratchViewDemoFragment::class.java.name),
            StudyTopic("Chart图表", ChartDemoFragment::class.java.name),
            StudyTopic("RecyclerView原理研究", RecyclerViewDemoFragment::class.java.name),
            StudyTopic("自定义LayoutManager", LayoutManagerDemoFragment::class.java.name),
            StudyTopic("文本宽度自适应的TextView", AutoFitWidthTextViewDemoFragment::class.java.name),
            StudyTopic("可任意拖拽View实践", DraggableViewDemoFragment::class.java.name),
            StudyTopic("封装倒计时", CountDownTimerDemoFragment::class.java.name),
            StudyTopic("ScrollerView定制", ScrollViewStateFragment::class.java.name),
            StudyTopic("ViewDragHelper实践", ViewDragHelperDemoFragment::class.java.name),
            StudyTopic("嵌套滑动实践", NestedScrollDemoFragment::class.java.name),
            StudyTopic("Bitmap的基础信息", BitmapInfoDemoFragment::class.java.name),
            StudyTopic("实现View添加和移除时的动画", LayoutTransitionDemoFragment::class.java.name),
            StudyTopic("ViewStub的简单使用", ViewStubDemoFragment::class.java.name),
            StudyTopic("菜单折叠收起", CollapseFragment::class.java.name),
            StudyTopic("基础手势检测", GestureFragment::class.java.name),
            StudyTopic("图片大图预览", ImagePreviewFragment::class.java.name),
            StudyTopic("嵌套滑动", NestedScrollDemoFragment::class.java.name),
            StudyTopic("ViewPager2实践",ViewPager2DemoFragment::class.java.name),
            StudyTopic("WebView使用",WebViewDemoFragment::class.java.name),

//            StudyTopic("循环列表",LoopRecyclerViewFragment::class.java.name),
            StudyTopic("自定义进度条",ProgressViewDemoFragment::class.java.name),
//            StudyTopic("Banner",BannerFragment::class.java.name)
        )
    }
}