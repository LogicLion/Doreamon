package com.example.doreamon.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2022/7/21
 */
class BitmapView @JvmOverloads constructor(
    private val mContext: Context,
    private val attr: AttributeSet? = null,
    val defStyle: Int = 0
) : View(mContext, attr) {
    var bitmap1: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.pic_ganyu)

    val paint = Paint()
    var progressPaint = Paint()

    val progressColor=Color.parseColor("#ff0000")
    val progressEndColor=Color.parseColor("#00ff00")

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val shader: Shader = LinearGradient(
            0f,
            0f,
            100f,
            0f,
            progressColor,
            progressEndColor,
            Shader.TileMode.CLAMP
        )
        progressPaint.color=progressColor
        progressPaint.shader = shader

        canvas.drawRect(0f,0f,100f,20f,progressPaint)
    }
}