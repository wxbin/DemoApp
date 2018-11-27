package com.demo.demoapp

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_frecso.*

class FrecsoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_frecso)


//        image.setActualImageResource(R.drawable.test)
        val uri = Uri.parse("https://pic4.zhimg.com/03b2d57be62b30f158f48f388c8f3f33_b.png")
        image.setImageURI(uri)
    }
}
