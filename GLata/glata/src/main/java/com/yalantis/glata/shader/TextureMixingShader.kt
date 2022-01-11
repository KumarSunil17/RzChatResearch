package com.yalantis.glata.shader

import android.opengl.GLES20
import com.yalantis.glata.core.RendererParams
import com.yalantis.glata.core.model.ModelParams
import com.yalantis.glata.core.scene.SceneParams
import com.yalantis.glata.core.shader.BaseShader
import com.yalantis.glata.util.Utils

class TextureMixingShader : BaseShader() {

    private var uTexture2Handle: Int = 0
    private var uMixModifierHandle: Int = 0

    override fun initShaders() {
        attributes = arrayOf("a_Position", "a_TexCoordinate")

        vertexShader =
                "uniform mat4 u_MVPMatrix; \n" + // A constant representing the combined model/view/projection matrix.
                "attribute vec4 a_Position; \n" + // Per-vertex position information we will pass in.
                "attribute vec2 a_TexCoordinate; \n" + // Per-vertex texture coordinate information we will pass in.
                "varying vec2 v_TexCoordinate; \n" + // This will be passed into the fragment shader.

                "void main() { \n" +
                "	  v_TexCoordinate = a_TexCoordinate; \n" + // Pass through the texture coordinate.
                "    gl_Position = u_MVPMatrix * a_Position; \n" +
                "} \n"


        fragmentShader =
                "precision mediump float; \n" +
                "uniform sampler2D u_Texture; \n" + // The input texture.
                "uniform sampler2D u_Texture2; \n" + // The input texture.
                "uniform float u_MixModifier; \n" +
                "varying vec2 v_TexCoordinate; \n" + // Interpolated texture coordinate per fragment.

                "void main() { \n" +
                "		vec4 col1 = texture2D(u_Texture, v_TexCoordinate); \n" +
                "		vec4 col2 = texture2D(u_Texture2, v_TexCoordinate); \n" +
                "		gl_FragColor = mix(col1, col2, u_MixModifier); \n" +
                "}"
    }

    override fun setShaderParams(
            rendererParams: RendererParams, modelParams: ModelParams, sceneParams: SceneParams) {
        setMvpMatrixHandle(modelParams, sceneParams)
        modelParams.shaderVars?.let { setTextureMixAmount(it.mixAmount) }
    }

    fun setTextureMixAmount(mixAmount: Float) {
        GLES20.glUniform1f(uMixModifierHandle, Utils.clamp(0f, 1f, mixAmount))
    }

    override fun setVariableHandles() {
        // OpenGL has own texture numeration, you can bind up to 50 textures at once. Here we tell
        // it that texture with index 0 will correspond to uTextureHandle and texture with index 1
        // will correspond to uTexture2Handle
        uTextureHandle = GLES20.glGetUniformLocation(programHandle, "u_Texture")
        GLES20.glUniform1i(uTextureHandle, 0)
        uTexture2Handle = GLES20.glGetUniformLocation(programHandle, "u_Texture2")
        GLES20.glUniform1i(uTexture2Handle, 1)

        uMvpMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix")
        uMixModifierHandle = GLES20.glGetUniformLocation(programHandle, "u_MixModifier")
        aPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position")
        aTexCoordinateHandle = GLES20.glGetAttribLocation(programHandle, "a_TexCoordinate")
    }
}