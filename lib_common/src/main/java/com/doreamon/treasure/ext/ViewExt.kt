package com.doreamon.treasure.ext

import android.content.res.Resources
import android.util.TypedValue

/**
 * 知识点：
 * 两种Resources：
 * Context.getResources()和Resources.getSystem()
 *
 * 相同之处：
 * 都是取得 Resources 对象然后加载相应的资源。
 * 不同之处：
 * 1、Context.getResources()用在有context的地方，没有context的地方和静态类中是不能用的（也有开发者通过一些方式对context进行封装用在静态类中），
 * 而且getResources()只能用于获取应用本身的资源
 * 2、Resources.getSystem() 可以在任何地方进行使用，但是有一个局限，只能获取系统本身的资源。
 *
 *
 * 防踩坑：
 * 上面2种Resources都可以获取到各自的displayMetrics，一般情况下获取displayMetrics里的配置（如：density、scaledDensity）都是一样的
 * 假如使用了类似今日头条的适配方案可能会修改density,scaledDensity,这时候Context.getResources().displayMetrics会被修改，
 *
 * 这种情况下面方法里的Resources.getSystem().displayMetrics要改成Context.getResources().displayMetrics
 *
 */
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Int.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
