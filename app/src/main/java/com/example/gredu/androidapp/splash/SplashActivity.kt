package com.example.gredu.androidapp.splash

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.gredu.androidapp.R
import com.example.gredu.androidapp.agenda.AgendaActivity

import kotlinx.android.synthetic.main.activity_splash.*
import android.os.Handler


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AnimnationSplash()
    }

    private fun AnimnationSplash(){
        val objectAnimator = ObjectAnimator.ofObject(
                background,
                "backgroundColor",
                ArgbEvaluator(),
                ContextCompat.getColor(this,R.color.color2),
                ContextCompat.getColor(this,R.color.color5),
                ContextCompat.getColor(this,R.color.color7),
                ContextCompat.getColor(this,R.color.color6))

        objectAnimator.repeatCount = 0.5.toInt()
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = 2500L
        objectAnimator.start()

        val handler = Handler()
        handler.postDelayed(Runnable {
            objectAnimator.cancel()
            val intent = Intent(this,AgendaActivity::class.java)
            startActivity(intent)
        }, 3000)
    }

}
