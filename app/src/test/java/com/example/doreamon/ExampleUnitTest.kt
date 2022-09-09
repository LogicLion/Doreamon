package com.example.doreamon

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

    }


    fun simple(): Flow<Int> = flow {
        println("Flow started")
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }
    suspend fun performRequest(request: Int): String {
        delay(1000) // 模仿长时间运行的异步工作
        return "response $request"
    }
    @Test
    fun main() = runBlocking<Unit> {
        // kotlin
//        (1..3).asFlow().map { performRequest(it) }
//            .collect { println(it) }

        printList(mutableListOf("asb","sdg"))
    }

    private fun  printList(list:MutableList<Any>) {
        println(list.toString())
    }
}

