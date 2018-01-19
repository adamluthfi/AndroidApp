package com.example.gredu.androidapp

import android.os.Handler
import android.os.HandlerThread

/**
 * Created by gredu on 11/01/18.
 */
class DbWorkerThread(threadName: String): HandlerThread(threadName){
    private lateinit var mWorkerHandler : Handler

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkerHandler = Handler(looper)
    }

    fun postTask(task: Runnable){
        mWorkerHandler.post(task)
    }
}