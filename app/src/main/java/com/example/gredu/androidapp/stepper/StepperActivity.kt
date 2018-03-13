package com.example.gredu.androidapp.stepper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.gredu.androidapp.R
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import kotlinx.android.synthetic.main.activity_stepper.*

class StepperActivity : AppCompatActivity(),StepperLayout.StepperListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stepper)
        stepper.setAdapter(MyStepperAdapter(supportFragmentManager,this))
        stepper.setListener(this)
    }

    override fun onStepSelected(newStepPosition: Int) {
        Toast.makeText(this,newStepPosition.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onError(verificationError: VerificationError?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReturn() {
        finish()
    }

    override fun onCompleted(completeButton: View?) {
        Toast.makeText(this,"Complete",Toast.LENGTH_SHORT).show()
    }
}
