package com.example.doreamon.ui.topic

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.doreamon.R
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.databinding.FragmentGestureBinding

/**
 * 手势
 * @author wzh
 * @date 2022/9/19
 * https://www.gcssloop.com/customview/gestruedector.html
 */
class GestureFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_gesture

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        val binding = getViewBinding<FragmentGestureBinding>()


        /**
         * 手势监听器
         */
        val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
            var isLongPressEvent: Boolean = false
            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)

                //长按事件
                binding.tvCheckGesture.text = "检测到:长按"
                Log.i(TAG, "检测到:长按")
                isLongPressEvent = true
            }


            override fun onDoubleTap(e: MotionEvent): Boolean {
                //双击事件
                //在第二次按下手指时触发，即第二次按下手指时即便不抬起也会触发 onDoubleTap 和 onDoubleTapEvent 的 down
                //双击事件首先是 onDoubleTap 被触发，之后依次触发 onDoubleTapEvent 的 down、move、up 等信息
                binding.tvCheckGesture.text = "检测到:双击，第二次点击按下即触发"
                Log.i(TAG, "检测到:双击，第二次点击按下即触发")
                return super.onDoubleTap(e)
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                //双击事件，并且区分down、move、up 等信息
                if (e.actionMasked == MotionEvent.ACTION_UP && !isLongPressEvent) {
                    //因为双击第二次点击也会触发长按事件，如果业务需要同时响应长按事件的话，这里加了判断如果触发了长按就不触发双击了
                    binding.tvCheckGesture.text = "检测到:双击，第二次点击抬起触发"
                    Log.i(TAG, "检测到:双击，第二次点击抬起触发")
                }
                return super.onDoubleTapEvent(e)
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {

                //单击确认事件（与双击互斥），是一个延时调用，在双击事件不成立的时候才可能调用
                //onSingleTapConfirmed在触发双击时不会触发，如果业务场景需要同时检测单击和双击时最好监听这个
                binding.tvCheckGesture.text = "检测到:单击onSingleTapConfirmed"
                Log.i(TAG, "检测到:单击")
                return super.onSingleTapConfirmed(e)
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                //单击抬起
                //在双击的第一次抬起时也会触发一次
                binding.tvCheckGesture.text = "检测到:单击onSingleTapUp"
                Log.i(TAG, "检测到:单击onSingleTapUp")
                return super.onSingleTapUp(e)
            }

            override fun onShowPress(e: MotionEvent) {
                super.onShowPress(e)

                //它是用户按下时的一种回调，主要作用是给用户提供一种视觉反馈，可以在监听到这种事件时可以让控件换一种颜色，或者产生一些变化，
                // 告诉用户他的动作已经被识别。
                //不过这个消息和 onSingleTapConfirmed 类似，也是一种延时回调，延迟时间是 180 ms，
                // 假如用户手指按下后立即抬起或者事件立即被拦截，时间没有超过 180 ms的话，这条消息会被 remove 掉，也就不会触发这个回调。

                Log.i(TAG, "检测到:onShowPress")
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {

                //Fling 中文直接翻译过来就是一扔、抛、甩，
                // 最常见的场景就是在 ListView 或者 RecyclerView 上快速滑动时手指抬起后它还会滚动一段时间才会停止。
                // onFling 就是检测这种手势的。

                //e1:手指按下时的 Event
                //e2:手指抬起时的 Event
                //velocityX:在 X 轴上的运动速度(像素／秒)
                //velocityY:在 Y 轴上的运动速度(像素／秒)
                return super.onFling(e1, e2, velocityX, velocityY)
            }
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                //onScroll 就是监听滚动事件的，它看起来和 onFling 比较像，不同的是，onSrcoll 后两个参数不是速度，而是滚动的距离。
                return super.onScroll(e1, e2, distanceX, distanceY)
            }





            override fun onDown(e: MotionEvent): Boolean {
                //down 在事件分发体系中是一个较为特殊的事件，为了保证事件被唯一的 View 消费，哪个 View 消费了 down 事件，
                // 后续的内容就会传递给该 View。如果我们想让一个 View 能够接收到事件，
                // 有两种做法：
                //1、让该 View 可以点击，因为可点击状态会默认消费 down 事件。即把View的clickable属性设置 为 true
                //2、手动消费掉 down 事件。
                Log.i(TAG, "检测到:按下")
                isLongPressEvent = false
                return true
            }


        }

        val gestureDetector by lazy {
            val detector = GestureDetector(requireActivity(), gestureListener)

            //判断当前是否允许触发长按事件，true 表示允许，false 表示不允许。
            val longpressEnabled = detector.isLongpressEnabled

            //通过布尔值设置是否允许触发长按事件，true 表示允许，false 表示不允许。默认是true
            detector.setIsLongpressEnabled(true)

            detector
        }


        binding.clGestureDetector.setOnTouchListener { v, event ->
            return@setOnTouchListener gestureDetector.onTouchEvent(event)
        }
    }


}