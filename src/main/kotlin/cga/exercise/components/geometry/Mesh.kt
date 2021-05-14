package cga.exercise.components.geometry

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.opengl.GL30.*

/**
 * Creates a Mesh object from vertexdata, indexdata and a given set of vertex attributes
 *
 * @param vertexdata plain float array of vertex data
 * @param indexdata  index data
 * @param attributes vertex attributes contained in vertex data
 * @throws Exception If the creation of the required OpenGL objects fails, an exception is thrown
 *
 */
class Mesh(vertexdata: FloatArray, indexdata: IntArray, attributes: Array<VertexAttribute>) {
    //private data
    private var vao = 0
    private var vbo = 0
    private var ibo = 0
    private var indexcount = 0

    init {
        // todo: place your code here
        // todo: generate IDs
        vao = glGenVertexArrays()
        vbo = glGenBuffers()
        ibo = glGenBuffers();

        // todo: bind your objects
        glBindVertexArray(vao)
        glBindBuffer(GL_ARRAY_BUFFER,vbo)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo)

        // todo: upload your mesh data
        GL30.glBufferData(GL_ARRAY_BUFFER,vertexdata, GL_STATIC_DRAW)
        GL30.glBufferData(GL_ELEMENT_ARRAY_BUFFER,indexdata, GL_STATIC_DRAW)

       /* for (i in attributes){
            glEnableVertexAttribArray(i)
            GL30.glVertexAttribPointer(i,attributes[i].n,attributes[i].type,false, attributes[i].stride, attributes[i].offset)
        }*/

    }

    /**
     * renders the mesh
     */
    fun render() {
        // todo: place your code here
        // call the rendering method every frame
        glBindVertexArray(vao)
        glDrawElements(GL_TRIANGLES,indexcount, GL_UNSIGNED_INT,0)
        glBindVertexArray(0)
    }

    /**
     * Deletes the previously allocated OpenGL objects for this mesh
     */
    fun cleanup() {
        if (ibo != 0) GL30.glDeleteBuffers(ibo)
        if (vbo != 0) GL30.glDeleteBuffers(vbo)
        if (vao != 0) GL30.glDeleteVertexArrays(vao)
    }
}