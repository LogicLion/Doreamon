package com.example.doreamon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import com.doreamon.treasure.ext.dp
import com.example.doreamon.R

/**
 * @author wzh
 * @date 2024/12/21
 */
class BadgeListLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_linear_badge_list, this)

        val progressView = findViewById<CustomProgressBar>(R.id.progress_view)
        progressView.setProgress(50)

        val llBadgeList = findViewById<LinearLayout>(R.id.ll_badge_list)
        for (i in 0..9) {
            val badgeItemView =
                LayoutInflater.from(context).inflate(R.layout.item_badge_info, null)

            val params = LinearLayout.LayoutParams(
                65.dp,
                65.dp
            )
            badgeItemView.layoutParams = params

            val tvBadgeTip = badgeItemView.findViewById<TextView>(R.id.tv_badge_tip)
            val clDate = badgeItemView.findViewById<View>(R.id.cl_date)
            val tvToggle = badgeItemView.findViewById<TextView>(R.id.tv_toggle)
            tvBadgeTip.setOnClickListener {
                clDate.visibility = View.VISIBLE
            }

            tvToggle.setOnClickListener {
                clDate.visibility = View.INVISIBLE
            }



            llBadgeList.addView(badgeItemView)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }
}