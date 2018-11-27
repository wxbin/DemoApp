package com.demo.demoapp.opengl

import android.opengl.GLES20
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Create by wxbin on 2018/11/27
 *
 */
class Square {

    // number of coordinates per vertex in this array
    private var vertexBuffer: FloatBuffer
    private var colorBuffer: FloatBuffer
    private var drawListBuffer: ShortBuffer
    private var mProgramId = 0
    private val COORDS_PER_VERTEX = 3
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    private var squareCoords = floatArrayOf(
            -0.5f, 0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f, 0.5f, 0.0f) // top right

    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices

    init {
        vertexBuffer = OpenGLUtil.convertToFloatBuffer(squareCoords)
        colorBuffer = OpenGLUtil.convertToFloatBuffer(color)
        drawListBuffer = OpenGLUtil.convertToShortBuffer(drawOrder)

        val vertexShader = OpenGLUtil.loadShader(GLES20.GL_VERTEX_SHADER, GLSL.vertexShaderCode)
        val fragmentShader = OpenGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, GLSL.fragmentShaderCode)

        mProgramId = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgramId, vertexShader)
        GLES20.glAttachShader(mProgramId, fragmentShader)
        GLES20.glLinkProgram(mProgramId)
    }

    fun draw() {
        GLES20.glUseProgram(mProgramId)
        val positionHandle = GLES20.glGetAttribLocation(mProgramId, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        OpenGLUtil.checkGlError("getAttribLocation")
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, COORDS_PER_VERTEX * 4, vertexBuffer)
        val colorHandle = GLES20.glGetUniformLocation(mProgramId, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, colorBuffer)
        OpenGLUtil.checkGlError("getAttribLocation")
        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.size,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer)
        OpenGLUtil.checkGlError("glDrawElements")
        GLES20.glDisableVertexAttribArray(positionHandle)
    }


}