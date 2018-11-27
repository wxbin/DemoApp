package com.demo.demoapp.opengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import android.opengl.GLES20
import android.util.Log
import android.opengl.GLES31


/**
 * Create by wxbin on 2018/11/27
 *
 */
object OpenGLUtil {
    private const val TAG = "OpenGLUtil"

    @JvmStatic
    fun convertToFloatBuffer(arr: FloatArray): FloatBuffer {
        val buffer = ByteBuffer.allocateDirect(arr.size * 4)
        // 数组排列用nativeOrder
        buffer.order(ByteOrder.nativeOrder())
        // 从ByteBuffer创建一个浮点缓冲区
        val retBuffer = buffer.asFloatBuffer()
        // 将坐标添加到FloatBuffer
        retBuffer.put(arr)
        // 设置缓冲区来读取第一个坐标
        retBuffer.position(0)
        return retBuffer
    }

    @JvmStatic
    fun convertToShortBuffer(arr: ShortArray): ShortBuffer {
        val buffer = ByteBuffer.allocateDirect(arr.size * 2)
        buffer.order(ByteOrder.nativeOrder())
        val retBuffer = buffer.asShortBuffer()
        retBuffer.put(arr)
        retBuffer.position(0)
        return retBuffer
    }

    @JvmStatic
    fun loadShader(type: Int, shaderCode: String): Int {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        val shader = GLES20.glCreateShader(type)
        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode)
        GLES20.glCompileShader(shader)
        checkGlError("load Shader")
        Log.d(TAG, "loadShader end, shader: $shader")
        return shader
    }

    fun checkGlError(glOperation: String) {
        var error: Int = GLES20.glGetError()
        if ((error != GLES20.GL_NO_ERROR)) {
            Log.e(TAG, "$glOperation: glError $error")
            throw RuntimeException("$glOperation: glError $error")
        }
    }
}