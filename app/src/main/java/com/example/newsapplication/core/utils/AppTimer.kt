package com.example.newsapplication.core.utils

import android.os.CountDownTimer
import com.example.newsapplication.core.actions.WaitAction
import javax.inject.Inject


class AppTimer @Inject constructor() {

     fun waitFor(seconds: Long, waitAction: WaitAction) {
        val timer = object : CountDownTimer(seconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                waitAction.onFinish();
            }
        }
        timer.start()
    }

}