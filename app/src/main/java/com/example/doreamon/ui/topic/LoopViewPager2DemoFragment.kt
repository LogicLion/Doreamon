package com.example.doreamon.ui.topic

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.doreamon.treasure.ext.dp
import com.doreamon.treasure.ext.toast
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentLoopViewpager2DemoBinding
import com.example.doreamon.ui.simple.LoopViewPagerAdapter
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import java.text.SimpleDateFormat
import java.util.*


/**
 * 可左右都能无限循环的viewpager2
 * @author wzh
 * @date 2023/9/21
 */
class LoopViewPager2DemoFragment : BaseFragment<BaseViewModel>() {

    private val picList = mutableListOf(
        "https://img0.baidu.com/it/u=2769111648,1830853668&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=537",
        "https://p5.itc.cn/images01/20220501/5058ededd47d4757b142659aebd5ce60.jpeg",
        "https://p1.itc.cn/images01/20220622/64150be722444ed5a19a1efc7dc2f8e1.jpeg"
    )

    override fun setupLayoutId() = R.layout.fragment_loop_viewpager2_demo

    override fun initView() {
        val binding = getViewBinding<FragmentLoopViewpager2DemoBinding>()
        val viewPager = binding.viewPager
        val pagerAdapter = LoopViewPagerAdapter()

        val list = mutableListOf<String>()
        list.addAll(picList)
        list.add(0, picList[picList.size - 1])
        list.add(0, picList[picList.size - 2])
        list.add(picList[0])
        list.add(picList[1])
        pagerAdapter.data = list
        viewPager.setPageTransformer(MyPageTransformer())
        viewPager.apply {
            // Set offscreen page limit to at least 1, so adjacent pages are always laid out
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = 50.dp
                // setting padding on inner RecyclerView puts overscroll effect in the right place
                // TODO: expose in later versions not to rely on getChildAt(0) which might break
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = pagerAdapter
        }
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
//                val lastPage: Int = pagerAdapter.itemCount - 1
//                Log.v("position", "position:${position}")
//                Log.v("CurrentItem", "CurrentItem:${viewPager.currentItem}")
//                if (position == 0) {
//                    // 用户从第一页向右滑动，将页面切换到最后一页
//                    viewPager.setCurrentItem(lastPage, false) // 使用 false 禁用平滑滚动
//                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                //其他页面切太快的时候不一定会有Idle状态，但第一页和最后一页一定会有
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val lastPage: Int = pagerAdapter.itemCount - 1
                    if (viewPager.currentItem == 0) {
                        // 用户从第一页向右滑动，将页面切换到最后一页
                        viewPager.setCurrentItem(lastPage - 1, false) // 使用 false 禁用平滑滚动
                    } else if (viewPager.currentItem == lastPage) {
                        viewPager.setCurrentItem(1, false)
                    }
                    Log.v("viewPager", "SCROLL_STATE_IDLE")
                } else if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
                    Log.v("viewPager", "SCROLL_STATE_DRAGGING")
                }
            }

//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                //页面切换时，偏移量会从 0（当前页面完全可见）到 1（下一个页面完全可见）变化。
//                Log.v(
//                    "viewPager",
//                    "positionOffset：${positionOffset},positionOffsetPixels：${positionOffsetPixels}"
//                )
//                if (positionOffset == 0f && positionOffsetPixels == 0) {
//                    // 页面完全切换时，执行你的操作
//                    val lastPage: Int = pagerAdapter.itemCount - 1
//                    if (viewPager.currentItem == 0) {
//                        // 用户从第一页向右滑动，将页面切换到最后一页
//                        viewPager.setCurrentItem(lastPage - 1, false) // 使用 false 禁用平滑滚动
//                    } else if (viewPager.currentItem == lastPage) {
//                        viewPager.setCurrentItem(1, false)
//                    }
//
//                    Log.v("viewPager", "当前位置：${position},切换页面：${viewPager.currentItem}")
//                }
//            }
        })

        viewPager.setCurrentItem(2, false)


        val banner = binding.banner
        banner.setAdapter(object : BannerImageAdapter<String>(picList) {
            override fun onBindView(
                holder: BannerImageHolder,
                data: String,
                position: Int,
                size: Int
            ) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                    .load(data)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    .into(holder.imageView)
            }
        })
            .addBannerLifecycleObserver(this) //添加生命周期观察者

        banner.viewPager2.setPageTransformer(MyPageTransformer())

        banner.viewPager2.apply {
            // Set offscreen page limit to at least 1, so adjacent pages are always laid out
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = 50.dp
                // setting padding on inner RecyclerView puts overscroll effect in the right place
                // TODO: expose in later versions not to rely on getChildAt(0) which might break
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
            adapter = pagerAdapter
        }

//        betaApkLimit()
    }


    private class MyPageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(@NonNull page: View, position: Float) {

            //position，它表示当前页面的位置相对于 ViewPager2 的中心页面的偏移。
            //
            //具体来说，position 参数的取值范围通常为 -1 到 1，其中：
            //
            //当 position 为 -1 时，表示页面位于 ViewPager2 左侧，即当前页面在中心页面的左侧。
            //当 position 为 0 时，表示页面位于 ViewPager2 中心，即当前页面在中心位置。
            //当 position 为 1 时，表示页面位于 ViewPager2 右侧，即当前页面在中心页面的右侧。

            val pageWidth: Int = page.getWidth()
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor
//            if (position < -1) { // 页面已经滑出屏幕左侧
////                page.setAlpha(0f)
//            } else if (position <= 0) { // 页面在屏幕内，但是在中心页面左侧或覆盖中心页面
//                // 缩放页面以及渐变透明度
//                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
//                page.setScaleX(scaleFactor)
//                page.setScaleY(scaleFactor)
////                page.setAlpha(1 - Math.abs(position))
//            } else if (position <= 1) { // 页面在屏幕内，但是在中心页面右侧或覆盖中心页面
//                // 缩放页面以及渐变透明度
//                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
//                page.setScaleX(scaleFactor)
//                page.setScaleY(scaleFactor)
////                page.setAlpha(1 - Math.abs(position))
//            } else {
//            // 页面已经滑出屏幕右侧
////                page.setAlpha(0f)
//            }
        }

        companion object {
            private const val MIN_SCALE = 0.8f
        }
    }


    private val mHandler: MyHandler = MyHandler()

    private class MyHandler() : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            "内测已结束，请到应用商店下载分之道APP".toast()
            System.exit(0)
        }
    }

    private fun betaApkLimit() {
        // 获取当前时间
        // 获取当前时间
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentTime = Date()

        // 固定时间字符串
        val deadLineTimeString = "2023-10-24 23:59:59"

        try {
            // 将当前时间和固定时间解析为 Date 对象
//            val currentTime: Date = sdf.parse(currentTimeString)
            val deadLineTime: Date = sdf.parse(deadLineTimeString)
            if (currentTime.after(deadLineTime)) {
                Log.i(TAG, "晚于截止时间")
                "内测已结束，请到应用商店下载分之道APP".toast()
                System.exit(0)
            } else {
                Log.i(TAG, "早于截止时间")
                // 计算时间差（以毫秒为单位）
                val timeDifference: Long = deadLineTime.getTime() - currentTime.getTime()
                Log.i(TAG, "距离截止时间${timeDifference}毫秒")
                mHandler.sendEmptyMessageDelayed(0, timeDifference)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            // 处理异常
        }
    }


}