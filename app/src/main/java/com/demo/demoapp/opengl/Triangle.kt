package com.demo.demoapp.opengl

import android.opengl.GLES20
import android.util.Log
import java.nio.FloatBuffer

/**
 * Create by wxbin on 2018/11/27
 *
 */
class Triangle {
    private val TAG = "Triangle"
    // number of coordinates per vertex in this array
    private var vertexBuffer: FloatBuffer
    private var colorBuffer: FloatBuffer
    private var mProgramId: Int = -1
    private val COORDS_PER_VERTEX = 3
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    private var triangleCoords = floatArrayOf(
            0f, 0.8f, 0.0f, // top
            -0.8f, 0f, 0.0f, // bottom left
            0.8f, 0f, 0.0f // bottom right
    )

    init {
        val startTime = System.currentTimeMillis()
        vertexBuffer = OpenGLUtil.convertToFloatBuffer(triangleCoords)
        colorBuffer = OpenGLUtil.convertToFloatBuffer(color)
        val vertexShader = OpenGLUtil.loadShader(GLES20.GL_VERTEX_SHADER, GLSL.vertexShaderCode)
        val fragmentShader = OpenGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, GLSL.fragmentShaderCode)

        mProgramId = GLES20.glCreateProgram()

        GLES20.glAttachShader(mProgramId, vertexShader)
        GLES20.glAttachShader(mProgramId, fragmentShader)
        GLES20.glLinkProgram(mProgramId)

        Log.d(TAG, "initEnd time ${System.currentTimeMillis() - startTime}")
    }

    fun draw() {
        GLES20.glUseProgram(mProgramId)
        val positionHandle = GLES20.glGetAttribLocation(mProgramId, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, COORDS_PER_VERTEX * 4, vertexBuffer)
        val colorHandle = GLES20.glGetUniformLocation(mProgramId, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, colorBuffer)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)
        GLES20.glDisableVertexAttribArray(positionHandle)
    }


}