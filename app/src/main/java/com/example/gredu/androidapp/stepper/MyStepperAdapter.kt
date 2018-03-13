package com.example.gredu.androidapp.stepper

import android.content.Context
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import android.support.annotation.NonNull
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.example.gredu.androidapp.R
import com.stepstone.stepper.Step


class MyStepperAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {

    override fun createStep(position: Int): Step {
        val step = StepFragmentSample()
        val b = Bundle()
        b.putInt("CURRENT_STEP_POSITION_KEY", position)
        step.arguments = b
        return step
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getViewModel(position: Int): StepViewModel {
        return StepViewModel.Builder(context)
                .setTitle("tab title")
                .create()
    }
}