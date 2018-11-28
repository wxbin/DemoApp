package com.demo.demoapp.opengl

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20

import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.demo.demoapp.R
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
        setRenderer(MyRender(context!!))
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    class MyRender(val context: Context) : Renderer {

        private lateinit var triangle: Triangle
        private lateinit var square: Square
        private lateinit var image: Image
        private val mMVPMatrix = FloatArray(16)
        private val mProjectionMatrix = FloatArray(16)
        private val mViewMatrix = FloatArray(16)
        private var angle = 0f
        override fun onDrawFrame(gl: GL10) {
            Log.d(TAG, "onDrawFrame Thread-${Thread.currentThread().name}")
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
            Matrix.setLookAtM(mViewMatrix, 0, 0f,0f,-3f,0f,0f,0f,0f,1f,0f)
            Matrix.multiplyMM(mMVPMatrix,0, mProjectionMatrix, 0, mViewMatrix, 0)
            Matrix.rotateM(mMVPMatrix, 0, angle++, 0f, 0f, -1f)
//            triangle.draw(mMVPMatrix)
//            square.draw(mMVPMatrix)
            val textureId = OpenGLUtil.createTexture(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
            image.draw(mMVPMatrix, textureId)
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
            Log.d(TAG, "onSurfaceChanged, Thread-${Thread.currentThread().name} width: $width height: $height")
            gl.glViewport(0, 0, width, height)
            val ratio = width.toFloat() / height
            Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            Log.d(TAG, "onSurfaceCreated, Thread-${Thread.currentThread().name} config: $config")
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
            triangle = Triangle()
            square = Square()
            image = Image()
        }

    }
}