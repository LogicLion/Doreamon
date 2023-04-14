package com.example.doreamon.ui.topic

import android.graphics.BitmapFactory
import android.graphics.Point
import com.doreamon.treasure.base.BaseFragment
import com.doreamon.treasure.base.BaseViewModel
import com.example.doreamon.R
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
        options.inSampleSize = 1

        //inJustDecodeBounds属性表示「只解码边界」，此处设置为true后，会返回一个「空的Bitmap对象」，
        //但会设置outWidth、outHeight等值，这样就可以「查询Bitmap的宽高等信息，而无需为其像素实际分配内存」了，避免一开始就因加载超大尺寸图片而使内存暴涨。
        options.inJustDecodeBounds = false
        val bitmap =
            BitmapFactory.decodeResource(requireActivity().resources, R.drawable.pic_ganyu, options)

        //Bitmap提供了两个方法用于获取「系统为该Bitmap存储像素所分配的内存大小」，分别为：
        //public int getByteCount ()
        //public int getAllocationByteCount ()
        //一般情况下，两个方法返回的值是相同的。但如果我们手动重新配置了Bitmap的属性(宽、高、Bitmap.Config等)，
        //或者将BitmapFactory.Options.inBitmap属性设为true以支持其他更小的Bitmap复用其内存时，
        //那么getAllocationByteCount ()返回的值就有可能会大于getByteCount()。


        //一张 ARGB_8888 的 Bitmap 占用内存的计算公式
        //bitmapInRam = (bitmapWidth*inDensity/inTargetDensity)*(bitmapHeight*inDensity/inTargetDensity) *4 bytes
        //「inDensity」代表的是「Bitmap的像素密度」，取决于原始图片资源所存放的密度目录
        //「inTargetDensity」代表的是「Bitmap将绘制到的目标的像素密度」，通常就是指屏幕的像素密度
        //最终图片加载进内存所占据的大小会不一样，因为系统在加载 res 目录下的资源图片时，
        //会根据图片存放的不同目录做一次分辨率的转换，而转换的规则是：新图的高度 = 原图高度 * (设备的 dpi / 目录对应的 dpi )
        //假设宽高360*336的图片放在xxxhdpi文件夹下（640dpi），手机屏幕dpi为480dpi)
        //bitmapInRam = (360*640/480)*(336*640/480) *4 bytes=272160bytes
        val byteCount = bitmap.byteCount
        binding.tvImageInfo.text =
            "图片占用大小：${byteCount}byte\n图片解码模式：${bitmap.config}\n宽：${bitmap.width}\n高：${bitmap.height}"


    }
}