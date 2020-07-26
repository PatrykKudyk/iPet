package com.partos.ipet.logic

import android.os.Handler
import android.os.Looper

class TimerThread(): Thread() {

    lateinit var handler: Handler
    lateinit var looper: Looper

    override fun run() {
        Looper.prepare()
        looper = Looper.myLooper()!!
        handler = Handler()
        Looper.loop()
    }
}