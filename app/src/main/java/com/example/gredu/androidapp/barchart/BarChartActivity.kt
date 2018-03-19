package com.example.gredu.androidapp.barchart

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.gredu.androidapp.R
import kotlinx.android.synthetic.main.activity_bar_chart.*

class BarChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar_chart)
        progressBar.max = 100f
        progressBar.progress = 60f
        progressBar.secondaryProgress = 70f

    }
}
