package com.example.doreamon.ui.topic

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.doreamon.R
import com.example.doreamon.base.BaseFragment
import com.example.doreamon.base.BaseViewModel
import com.example.doreamon.databinding.FragmentBitmapInfoDemoBinding

/**
 * bitmap信息
 * @author wzh
 * @date 2022/10/10
 */
class BitmapInfoDemoFragment : BaseFragment<BaseViewModel>() {
    override fun setupLayoutId() = R.layout.fragment_bitmap_info_demo

    override fun initView() {

        val binding = getViewBinding<FragmentBitmapInfoDemoBinding>()
        val options = BitmapFactory.Options()
        options.inPreferredConfig

        //采样率，如果设置的值>1，会请求解码器对原始图像进行下采样，（从而）返回较小的图像以节省内存。
        //样本大小指的是（在宽与高）任一维中，与所解码位图中的单个像素相对应的像素数。
        //例如， inSampleSize == 4 时所返回的图像宽度/高度均为原始图像的 1/4，像素数为（为原始图像的）1/16。任何值<= 1都与1相同。
        //注意：解码器使用基于2的次幂的最终值，任何其他值都将四舍五入到最近的2的次幂。
        options.inSampleSize=1

        //inJustDecodeBounds属性表示「只解码边界」，此处设置为true后，会返回一个「空的Bitmap对象」，
        //但会设置outWidth、outHeight等值，这样就可以「查询Bitmap的宽高等信息，而无需为其像素实际分配内存」了，避免一开始就因加载超大尺寸图片而使内存暴涨。
        options.inJustDecodeBounds=false
        val bitmap =
            BitmapFactory.decodeResource(requireActivity().resources, R.drawable.pic_ganyu,options)

        //Bitmap提供了两个方法用于获取「系统为该Bitmap存储像素所分配的内存大小」，分别为：
        //public int getByteCount ()
        //public int getAllocationByteCount ()
        //一般情况下，两个方法返回的值是相同的。但如果我们手动重新配置了Bitmap的属性(宽、高、Bitmap.Config等)，
        //或者将BitmapFactory.Options.inBitmap属性设为true以支持其他更小的Bitmap复用其内存时，
        //那么getAllocationByteCount ()返回的值就有可能会大于getByteCount()。

        //位图内存 = 位图宽度* 位图高度* 每个像素的大小  = 270*252*4=
        val byteCount = bitmap.byteCount
        binding.tvImageInfo.text =
            "图片占用大小：${byteCount}byte\n图片解码模式：${bitmap.config}\n宽：${bitmap.width}\n高：${bitmap.height}"



    }
}