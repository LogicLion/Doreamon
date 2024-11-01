package com.example.doreamon.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R

/**
 * 图片进度条控件
 * @author wzh
 * @date 2023/8/2
 */
class ImageProgressView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {
    private lateinit var originalBitmap: Bitmap

    private var colorfulBitmap: Bitmap? = null
    private var grayScaleBitmap: Bitmap? = null

    private val cornerRadius = 50f.dp

    //srcRect 定义了原始图片的截取区域，这里我们截取了原始图片的左边。
    private lateinit var srcRect: Rect

    //destRect 定义了绘制时的目标区域,在这个需求里面，srcRect和destRect应该是一样的
    private lateinit var destRect: Rect

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private lateinit var roundRect: RectF

    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#60999999")
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textSize = 60f.dp
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
        color = Color.parseColor("#51c4d3")
    }

    private val metrics: Paint.FontMetrics by lazy {
        textPaint.fontMetrics
    }

    var progress: Int = 0
        set(value) {
            field = value
            animator.start()
        }

    @Keep
    private var animRate = 0
        set(rate) {
            field = rate
            invalidate()
        }

    private val animator: Animator by lazy {
        ObjectAnimator.ofInt(this, "animRate", 0, 100).setDuration(1000)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        roundRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
        originalBitmap =
            decodeSampledBitmapFromResource(resources, R.drawable.pic_liyue_dajia, w, h)
        colorfulBitmap = createColorfulBitmap(w, h, true)
        grayScaleBitmap = createColorfulBitmap(w, h, false)
        originalBitmap.recycle()

        // 设置源区域，截取左边的一半
        srcRect = Rect(0, 0, w / 2, h/2)

        // 设置目标区域，绘制到整个View上
        destRect = Rect(0, 0, w / 2, h/2)
    }

    private fun createColorfulBitmap(w: Int, h: Int, isColorful: Boolean): Bitmap {

        // 创建一个与图片大小相同的 Bitmap，并在上面绘制圆角矩形
        val roundedBitmap = Bitmap.createBitmap(
            w, h, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(roundedBitmap)
        canvas.drawRoundRect(roundRect, cornerRadius, cornerRadius, paint)

        // 使用PorterDuffXfermode来实现裁剪效果，只绘制图片与圆角矩形的交集部分
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        if (!isColorful) {
            // 创建一个去饱和度的 ColorMatrix
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)

            // 将 ColorMatrix 应用到 Paint 上
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            paint.colorFilter = colorFilter
        }

        canvas.drawBitmap(
            originalBitmap,
            Rect(0, 0, originalBitmap.width, originalBitmap.height),
            Rect(0, 0, w, h),
            paint
        )
        paint.xfermode = null

        return roundedBitmap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        grayScaleBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
        }

        colorfulBitmap?.let {
            srcRect.right = progress * animRate * width / 10000
            destRect.right = progress * animRate * width / 10000
            canvas.drawBitmap(it, srcRect, destRect, null)
        }

        canvas.drawRoundRect(roundRect, cornerRadius, cornerRadius, maskPaint)

        val text = "${progress * animRate / 100}%"
        textPaint.getFontMetrics(metrics)
        //居中绘制，这里descent和ascent应该理解为偏移量，因为文字的绘制x的初始点还是在baseline位置
        canvas.drawText(
            text,
            width.toFloat() / 2,
            height.toFloat() / 2 - (metrics.descent + metrics.ascent) / 2,
            textPaint
        )
    }


    /**
     * 按需要的大小加载图片
     * 可以使用 BitmapFactory.Options 对象来控制图片的加载方式，从而减少内存的占用。
     * 其中一个关键参数是 inSampleSize，它用于指定图片的缩放比例，从而减小图片的尺寸和内存占用。
     */
    fun decodeSampledBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}