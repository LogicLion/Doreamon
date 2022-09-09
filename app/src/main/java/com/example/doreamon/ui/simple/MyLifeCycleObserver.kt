package com.example.doreamon.ui.simple

import android.util.Log
import androidx.lifecycle.*
import org.jetbrains.annotations.NotNull


/**
 * @author wzh
 * @date 2022/6/7
 */
class MyLifeCycleObserver : LifecycleEventObserver {

    companion object {
        const val TAG = "MyLifeCycleObserver"
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Log.v(TAG, "ON_CREATE")
            }
            Lifecycle.Event.ON_START -> {
                Log.v(TAG, "ON_START")
            }
            Lifecycle.Event.ON_RESUME -> {
                Log.v(TAG, "ON_RESUME")
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.v(TAG, "ON_PAUSE")
            }

            Lifecycle.Event.ON_STOP -> {
                Log.v(TAG, "ON_STOP")
            }

            Lifecycle.Event.ON_DESTROY -> {
                Log.v(TAG, "ON_DESTROY")
            }
        }

    }
}

//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    fun onAny(owner: LifecycleOwner) {
//        Log.e(TAG,"onAny_ON_ANY-->%s", owner.lifecycle.currentState)
//    }
