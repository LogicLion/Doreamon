package com.doreamon.wanandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author wzh
 * @date 2022/9/9
 */
@Route(path = "/wanandroid/secondActivity")
class SecondActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }
}