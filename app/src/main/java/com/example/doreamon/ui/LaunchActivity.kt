package com.example.doreamon.ui

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.doreamon.R
import com.example.doreamon.base.App
import com.example.doreamon.base.BaseActivity
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.ext.startTargetActivity
import com.example.doreamon.ui.topic.FragmentToggleActivity
import com.example.doreamon.utils.dip2px
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author wzh
 * @date 2022/1/21
 */
class LaunchActivity : BaseActivity<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.activity_launch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ivLogo = findViewById<ShapeableImageView>(R.id.iv_logo)

        //设置阴影
//        val shapePathModel = ShapeAppearanceModel.builder()
//            .setAllCorners(RoundedCornerTreatment())
//            .setAllCornerSizes(dip2px(16))
//            .build()
//        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
//            setTint(Color.RED)
//            paintStyle = Paint.Style.FILL
//            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS
//            initializeElevationOverlay(this@LaunchActivity)
//            elevation = dip2px(6)
//            setShadowColor(Color.RED)
//        }
//        (ivLogo.parent as? ViewGroup)?.clipChildren = false
//        ivLogo.background = backgroundDrawable
        val shapeModel = ShapeAppearanceModel.builder().setAllCorners(CornerFamily.ROUNDED, dip2px(30)).build()

        ivLogo.shapeAppearanceModel = shapeModel

        ivLogo.strokeColor= ColorStateList.valueOf(ContextCompat.getColor(App.instance,R.color.color_primary))
        ivLogo.strokeWidth= dip2px(3)

        lifecycleScope.launch {
            delay(500)
//            if (UserInfoData.value == null) {
//                startTargetActivity<LoginActivity>()
//            } else {
                startTargetActivity<MainActivity>()
//            }

            finish()

        }
    }
}