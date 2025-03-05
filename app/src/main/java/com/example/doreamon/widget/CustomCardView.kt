package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import com.doreamon.treasure.ext.dp
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * @author wzh
 * @date 2024/12/5
 */
class CustomCardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : MaterialCardView(context, attrs) {

    init {
        // 创建一个自定义的 ShapeAppearanceModel
        val shapeAppearance = ShapeAppearanceModel.builder(context, attrs, 0, 0)
            .setTopLeftCorner(CornerFamily.ROUNDED, 30f.dp) // 设置左上角圆角
            .setBottomRightCorner(CornerFamily.ROUNDED, 30f.dp) // 设置右下角圆角
            .setTopRightCorner(CornerFamily.ROUNDED, 0f) // 右上角不裁剪
            .setBottomLeftCorner(CornerFamily.ROUNDED, 0f) // 左下角不裁剪
            .build()

        // 设置 CardView 的形状外观
        this.shapeAppearanceModel = shapeAppearance
    }
}