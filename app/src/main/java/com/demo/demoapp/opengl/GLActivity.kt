package com.demo.demoapp.opengl

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class GLActivity : Activity() {
    private lateinit var mainView: GLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainView = MyGLSurfaceView(this)
        setContentView(mainView)
    }

    override fun onResume() {
        super.onResume()
        mainView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mainView.onPause()
    }
}
