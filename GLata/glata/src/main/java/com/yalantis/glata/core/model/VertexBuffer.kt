package com.yalantis.glata.core.model

import android.opengl.GLES20
import com.yalantis.glata.util.Logger
import java.lang.RuntimeException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * A wrapper class for FloatBuffer to use this as Vertex Buffer Object (VBO) for vertices,
 * texture coordinates and normals.
 */
class VertexBuffer() {

    /**
     * renderArrayType is GLES20.GL_ARRAY_BUFFER for vertices, texture coords and normals and
     * GLES20.GL_ELEMENT_ARRAY_BUFFER for indices. This parameter is used when binding VBOs in
     * video memory to use them for drawing.
     */
    private val renderArrayType: Int = GLES20.GL_ARRAY_BUFFER

    /**
     * drawMode and be GLES20.GL_STATIC_DRAW, GLES20.GL_DYNAMIC_DRAW or GLES20.GL_STREAM_DRAW.
     * Our models will mostly be simple rectangles so simple static draw is okay for us.
     */
    private val drawMode: Int = GLES20.GL_STATIC_DRAW

    /**
     * size of int in bytes
     */
    val bytesPerElement: Int = 4

    /**
     * Buffer holds all needed coordinates of the model and can be uploaded to video memory.
     */
    var buffer: FloatBuffer? = null
        private set

    /**
     * As we are creating mostly rectangles, it is simplier to store them in an array at first and
     * then we will load it to the buffer.
     */
    private var array: FloatArray? = null

    /**
     * This variable holds buffer's ID in video memory.
     */
    private var handle: Int = -1

    /**
     * Element count in array or buffer.
     */
    var size: Int = 0
        private set


    constructor(size: Int): this() {
        this.size = size
    }

    constructor(array: FloatArray): this() {
        this.size = array.size
        this.array = array
    }

    fun allocate() {
        if (size == 0) throw RuntimeException("Cannot allocate zero-sized buffer.")

        buffer = ByteBuffer.allocateDirect(size * bytesPerElement)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
    }

    fun initBuffer() {
        if (buffer == null) throw RuntimeException("Buffer is null")

        val buff = IntArray(1)
        GLES20.glGenBuffers(1, buff, 0)
        handle = buff[0]

        if (handle < 0) Logger.log("Error creating buffer object. Handle is $handle.")

        buffer?.let{buffer ->
            buffer.position(0)
            GLES20.glBindBuffer(renderArrayType, handle)
            GLES20.glBufferData(renderArrayType, size * bytesPerElement, buffer, drawMode)
            GLES20.glBindBuffer(renderArrayType, 0)
        } ?: throw RuntimeException("Buffer is null")
    }

    fun bind() {
        GLES20.glBindBuffer(renderArrayType, handle)
    }

    fun put(array: FloatArray) {
        buffer?.put(array)
    }

    fun put(buffer: FloatBuffer) {
        this.buffer?.put(buffer)
    }

    fun position(index: Int) {
        buffer?.position(index)
    }

    fun position(): Int = buffer?.position() ?: -1

    fun deleteArray() {
        array = null
    }

    fun putFromInnerArray() {
        buffer?.put(array)
        buffer?.position(0)
    }
}