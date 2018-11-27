package com.demo.demoapp.opengl

import android.content.Context

import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Create by wxbin on 2018/11/27
 *
 */
private const val TAG = "MyGLSurfaceView"

class MyGLSurfaceView(context: Context?) : GLSurfaceView(context) {

    init {
        setEGLContextClientVersion(2)
        setRenderer(MyRender())
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    class MyRender : Renderer {

        private lateinit var triangle: Triangle
        private lateinit var square: Square
        private val mMVPMatrix = FloatArray(16)
        private val mProjectionMatrix = FloatArray(16)
        private val mViewMatrix = FloatArray(16)

        override fun onDrawFrame(gl: GL10) {
            Log.d(TAG, "onDrawFrame Thread-${Thread.currentThread().name}")
            triangle.draw()
            square.draw()
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceChanged, Thread-${Thread.currentThread().name} width: $width height: $height")
            gl.glViewport(0, 0, width, height)
            val ratio = width.toFloat() / height
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            Log.d(TAG, "onSurfaceCreated, Thread-${Thread.currentThread().name} config: $config")
            triangle = Triangle()
            square = Square()
        }

    }
}