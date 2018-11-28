package com.demo.demoapp.opengl

import android.graphics.Bitmap
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import android.opengl.GLES20
import android.util.Log
import android.opengl.GLES31
import android.opengl.GLUtils


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

    fun createTexture(bitmap: Bitmap): Int {
        val texture = IntArray(1)
        if (!bitmap.isRecycled) {
            //生成纹理
            GLES20.glGenTextures(1, texture, 0)
            //生成纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0])
            //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
            //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            return texture[0]
        }
        return 0
    }
}