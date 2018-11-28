package com.demo.demoapp.opengl

/**
 * Create by wxbin on 2018/11/27
 *
 */
object GLSL {
    const val vertexShaderCode =
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}"

    const val fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}"

    const val vertexImageShaderCode =
            """
            attribute vec4 vPosition;
            attribute vec2 vCoordinate;
            uniform mat4 vMatrix;

            varying vec2 aCoordinate;

            void main(){
                gl_Position=vMatrix*vPosition;
                aCoordinate=vCoordinate;
            }

            """

    const val fragmentImageShaderCode =
            """
            precision mediump float;

            uniform sampler2D vTexture;
            varying vec2 aCoordinate;

            void main(){
                gl_FragColor=texture2D(vTexture,aCoordinate);
            }
            """

}