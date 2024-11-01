package com.example.doreamon.ui.topic

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.doreamon.R
import com.example.doreamon.databinding.FragmentImageProgressViewDemoBinding
import com.example.doreamon.ui.simple.CustomBottomEditTextDialog
import com.example.module_base.base.BaseFragment
import com.example.module_base.base.BaseViewModel
import java.lang.ref.WeakReference
import kotlin.random.Random

/**
 * 图片进度条自定义view实现
 * @author wzh
 * @date 2023/8/2
 */
class ImageProgressViewDemoFragment : BaseFragment<BaseViewModel>() {
    var isInterrupt = true
    var animationDuration: Int = 2000

    var elapsedTime: Long = 0L
    var lastTimeMillis = 0L

    override fun setupLayoutId() = R.layout.fragment_image_progress_view_demo

    override fun initView() {
        val binding = getViewBinding<FragmentImageProgressViewDemoBinding>()
        binding.tvUpdateProgress.setOnClickListener {
            binding.progressView.progress = getRandomData()
            binding.frogProgress.progress = getRandomData()
            binding.targetProgressBar.progress = getRandomData()
            binding.targetPieView.progress = getRandomData()
        }


        binding.frogProgress.onAnimUpdateListener = {
            binding.tvProgress.text = "进度：${it}"
        }

        binding.niuSeekBar.setProgressRange(1, 1)
    }

    private fun getRandomData(): Int {
        return Random.nextInt(0, 100)
    }

    private val mHandler: TimeHandler = TimeHandler(this)

    private class TimeHandler(activity: ImageProgressViewDemoFragment) :
        Handler(Looper.getMainLooper()) {
        private val weakReference: WeakReference<ImageProgressViewDemoFragment> =
            WeakReference(activity)

        override fun handleMessage(msg: Message) {
            val currentTimeMillis = System.currentTimeMillis()
            val lastTimeMillis = weakReference.get()?.lastTimeMillis
            if (lastTimeMillis != 0L) {
//                weakReference.get()?.elapsedTime += (currentTimeMillis - lastTimeMillis!!)
            }
            sendEmptyMessageDelayed(666, 16)
        }
    }

    val appId = "123456"
    val token = "BOZHI"


    private fun showDialog() {
        val dialog = CustomBottomEditTextDialog(requireActivity())
        dialog.show()
    }


    private fun handlerTest() {
        val handler = MyHandler(Looper.getMainLooper())
        handler.sendMessageWithSyncBarrier()

    }


    class MyHandler(looper: Looper) : Handler(looper) {
        private val MESSAGE_TYPE_1 = 1
        private val MESSAGE_TYPE_2 = 2

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_TYPE_1 -> {
                    // 处理消息类型 1
                    Log.v("MyHandler", "MESSAGE_TYPE_1")
                }
                MESSAGE_TYPE_2 -> {
                    // 处理消息类型 2
                    Log.v("MyHandler", "MESSAGE_TYPE_2")
                }
                else -> super.handleMessage(msg)
            }
        }

        fun sendMessageWithSyncBarrier() {
            removeMessages(MESSAGE_TYPE_1)
            removeMessages(MESSAGE_TYPE_2)

            // 在消息队列中插入同步屏障
            val syncMessage = obtainMessage()
            syncMessage.what = 0 // 任意值
            sendMessageAtFrontOfQueue(syncMessage)

            // 发送其他消息
            sendMessage(obtainMessage(MESSAGE_TYPE_1))
            sendMessage(obtainMessage(MESSAGE_TYPE_2))
        }
    }
}