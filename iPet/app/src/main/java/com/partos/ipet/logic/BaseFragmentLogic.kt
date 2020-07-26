package com.partos.ipet.logic

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.partos.ipet.R

class BaseFragmentLogic(val rootView: View) {

    private var looperThread = TimerThread()
    private lateinit var image: ImageView
    private lateinit var soundPool: SoundPool
    private var soundBark = 0

    fun initFragment() {
        looperThread.start()
        initSoundPool()
        image = rootView.findViewById(R.id.dog_image)
        Handler().postDelayed({
            animateDog()
        }, 300)
    }

    private fun initSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()

        soundBark = soundPool.load(rootView.context, R.raw.bark, 1)
    }

    private fun animateDog() {
        var threadHandler = Handler(looperThread.looper)
        var position = 0
        threadHandler.post(object : Runnable {
            override fun run() {
                when (position) {
                    0, 2, 4 -> image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_1))
                    1, 3, 6 -> image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                    5, 7 -> {
                        image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_bark))
                        soundPool.play(soundBark, 1F, 1F, 0, 0, 1F)
                    }
                    8 -> {
                        image.setImageDrawable(rootView.context.getDrawable(R.drawable.normal_2))
                        position = 0
                    }
                }
                position++
                threadHandler.postDelayed(this, 400)
            }
        })
    }

}