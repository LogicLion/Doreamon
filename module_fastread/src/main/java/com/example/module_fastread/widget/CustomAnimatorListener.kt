package com.example.module_fastread.widget

/**
 * @author wzh
 * @date 2023/5/12
 */
interface CustomAnimatorListener {

    fun onAnimationStart()
    fun onAnimationPause()
    fun onAnimationResume()
    fun onAnimationEnd()
    fun onTimeCountDown(second: Int)
}