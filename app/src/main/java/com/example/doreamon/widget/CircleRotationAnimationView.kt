package com.example.doreamon.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.cos
import kotlin.math.sin

/**
 * SurfaceView实现一个小圆围绕大圆旋转的动画效果
 *
 * @author wzh
 * @date 2023/5/8
 */
class CircleRotationAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var animationThread: AnimationThread? = null
    private var rotationSpeed = 1f
    private var isAnimationRunning = false

    init {
        // 注册 SurfaceHolder 回调
        holder.addCallback(this)
        setWillNotDraw(false)

        // 禁用硬件加速的话会导致画面不显示，未解决
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    fun startAnimation() {
        if (!isAnimationRunning) {
            isAnimationRunning = true

            // 创建并启动动画线程
            animationThread = AnimationThread(holder)
            animationThread?.start()
        }
    }

    fun stopAnimation() {
        isAnimationRunning = false

        // 停止动画线程
        animationThread?.interrupt()
        animationThread = null
    }

    fun setAnimationDuration(duration: Long) {
        rotationSpeed = 360f / duration
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // 不需要处理
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopAnimation()
    }

    private inner class AnimationThread(private val surfaceHolder: SurfaceHolder) : Thread() {
        private var isRunning = true

        override fun run() {
            var rotationAngle = 0f

            while (isRunning) {
                var canvas: Canvas? = null

                try {
                    // 获取 Canvas 对象并绘制动画
                    canvas = surfaceHolder.lockCanvas(null)
                    canvas?.let { drawAnimation(it, rotationAngle) }

                    // 更新旋转角度
                    rotationAngle += rotationSpeed
                    if (rotationAngle >= 360f) {
                        rotationAngle -= 360f
                    }
                } finally {
                    if (canvas != null) {
                        // 提交绘制结果并解锁 Canvas
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }
                }

                // 控制动画的帧率
                try {
                    sleep(16) // 控制帧率为 60fps
                } catch (e: InterruptedException) {
                    // 处理线程中断异常
                    isRunning = false
                }
            }
        }

        private fun drawAnimation(canvas: Canvas, rotationAngle: Float) {
            val centerX = canvas.width / 2f
            val centerY = canvas.height / 2f
            val radius = (canvas.width / 4).toFloat()

            // 清空画布
            canvas.drawColor(Color.WHITE)

            // 绘制大圆
            val bigCirclePaint = Paint()
            bigCirclePaint.color = Color.RED
            bigCirclePaint.style =Paint.Style.FILL
            canvas.drawCircle(centerX, centerY, radius, bigCirclePaint)

            // 绘制小圆
            val smallCirclePaint = Paint()
            smallCirclePaint.color = Color.BLUE
            smallCirclePaint.style = Paint.Style.FILL

            // 计算小圆的位置
            val smallCircleX = centerX + radius * cos(Math.toRadians(rotationAngle.toDouble())).toFloat()
            val smallCircleY = centerY + radius * sin(Math.toRadians(rotationAngle.toDouble())).toFloat()

            canvas.drawCircle(smallCircleX, smallCircleY, radius / 2, smallCirclePaint)
        }
    }
}