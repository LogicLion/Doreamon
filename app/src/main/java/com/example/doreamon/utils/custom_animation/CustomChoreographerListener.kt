package com.example.doreamon.utils.custom_animation
/**
 * @author wzh
 * @date 2023/5/12
 */
interface CustomChoreographerListener {

    fun onAnimationStart()
    fun onAnimationPause()
    fun onAnimationResume()
    fun onAnimationEnd()
    fun onTimeCountDown(second: Int)

}