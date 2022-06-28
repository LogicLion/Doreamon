package com.example.doreamon.test

import android.util.Log
import com.blankj.utilcode.util.CollectionUtils.collect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

/**
 * @author wzh
 * @date 2022/6/10
 */
class TestSimple {

    @Test
    public fun testFlow(){
        Assert.assertEquals(3,3)

        runBlocking {
            flow{
                println("flow start${Thread.currentThread().name}")
                repeat(3){
                    kotlinx.coroutines.delay(1000)
                    emit(it)
//                    throw Exception("发生异常")
                }
            }
                .flowOn(Dispatchers.IO)
                .onStart {
                println("onStart")
            }.onCompletion { exception->

                if (exception != null) {
                    println("onCompletion 接收到异常${exception.message}")
                }else{
                    println("onCompletion${Thread.currentThread().name}")
                }
            }.collect {
                println("collect")
                println(it)
            }
        }
    }

}