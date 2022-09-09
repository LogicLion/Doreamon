package com.doreamon.wanandroid

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val tvClick = findViewById<TextView>(R.id.tv_click)
        tvClick.setOnClickListener {
            ARouter.getInstance().build("/test/activity").navigation()
        }
    }
}