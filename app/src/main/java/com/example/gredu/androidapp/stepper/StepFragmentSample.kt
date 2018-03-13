package com.example.gredu.androidapp.stepper

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gredu.androidapp.R
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError

class StepFragmentSample :Fragment(),Step{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.step,container,false)
        return  v
    }

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        return null
    }

    override fun onError(error: VerificationError) {
    }

}