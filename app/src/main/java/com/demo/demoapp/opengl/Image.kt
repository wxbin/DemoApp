package com.demo.demoapp.opengl

import android.opengl.GLES20
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * Create by wxbin on 2018/11/27
 *
 */
class Image {

    // number of coordinates per vertex in this array
    private var vertexBuffer: FloatBuffer
    private var colorBuffer: FloatBuffer
    private var coordBuffer: FloatBuffer
    private var drawListBuffer: ShortBuffer
    private var mProgramId = 0
    private val COORDS_PER_VERTEX = 3
    private val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    private var squareCoords = floatArrayOf(
            -0.5f, 0.5f, 0.0f, // top left
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f, // bottom right
            0.5f, 0.5f, 0.0f) // top right

    private var imageCoords = floatArrayOf(
            0f, 0f,
            0f, 1f,
            1f, 1f,
            1f, 0f
    )
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3) // order to draw vertices

    init {
        vertexBuffer = OpenGLUtil.convertToFloatBuffer(squareCoords)
        coordBuffer = OpenGLUtil.convertToFloatBuffer(imageCoords)
        colorBuffer = OpenGLUtil.convertToFloatBuffer(color)
        drawListBuffer = OpenGLUtil.convertToShortBuffer(drawOrder)

        val vertexShader = OpenGLUtil.loadShader(GLES20.GL_VERTEX_SHADER, GLSL.vertexImageShaderCode)
        val fragmentShader = OpenGLUtil.loadShader(GLES20.GL_FRAGMENT_SHADER, GLSL.fragmentImageShaderCode)

        mProgramId = GLES20.glCreateProgram()
        GLES20.glAttachShader(mProgramId, vertexShader)
        GLES20.glAttachShader(mProgramId, fragmentShader)
        GLES20.glLinkProgram(mProgramId)
    }

    fun draw(matrix: FloatArray, textureId: Int) {
        GLES20.glUseProgram(mProgramId)
        val matrixHandle = GLES20.glGetUniformLocation(mProgramId, "vMatrix")
        GLES20.glUniformMatrix4fv(matrixHandle, 1, false, matrix, 0)
        OpenGLUtil.checkGlError("glUniformMatrix4fv")
        val positionHandle = GLES20.glGetAttribLocation(mProgramId, "vPosition")
        GLES20.glEnableVertexAttribArray(positionHandle)
        OpenGLUtil.checkGlError("getAttribLocation")
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT,
                false, COORDS_PER_VERTEX * 4, vertexBuffer)

        val vCoordinator = GLES20.glGetAttribLocation(mProgramId, "vCoordinate")
        OpenGLUtil.checkGlError("get vCoordinate")
        GLES20.glEnableVertexAttribArray(vCoordinator)
        GLES20.glVertexAttribPointer(vCoordinator, 2, GLES20.GL_FLOAT, false, 0, coordBuffer)
        val colorHandle = GLES20.glGetUniformLocation(mProgramId, "vColor")
        GLES20.glUniform4fv(colorHandle, 1, colorBuffer)
        OpenGLUtil.checkGlError("getAttribLocation")
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
        // Draw the square
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.size,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer)
        OpenGLUtil.checkGlError("glDrawElements")
        GLES20.glDisableVertexAttribArray(positionHandle)
        GLES20.glDisableVertexAttribArray(vCoordinator)
    }


}