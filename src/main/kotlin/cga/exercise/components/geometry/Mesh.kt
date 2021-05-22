package cga.exercise.components.geometry

import org.lwjgl.opengl.ARBVertexArrayObject.*
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30

/**
 * Creates a Mesh object from vertexdata, intexdata and a given set of vertex attributes
 *
 * @param vertexdata plain float array of vertex data
 * @param indexdata  index data
 * @param attributes vertex attributes contained in vertex data
 * @throws Exception If the creation of the required OpenGL objects fails, an exception is thrown
 *
 * Created by Fabian on 16.09.2017.
 */
class Mesh(vertexdata: FloatArray, indexdata: IntArray, attributes: Array<VertexAttribute>) {
    //private data
    private var vao = 0
    private var vbo = 0
    private var ibo = 0
    private var indexcount = 0

    init {

        indexcount = indexdata.size

        //1.Schritt Generiere ID
        vao = glGenVertexArrays()
        vbo = glGenBuffers()
        ibo = glGenBuffers()

        //2.Schritt Aktiviere ID
        glBindVertexArray(vao)
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)

        // Daten in Buffer hochladen
        glBufferData(GL_ARRAY_BUFFER, vertexdata, GL_STATIC_DRAW)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)


        for (i in attributes.indices) {
            glEnableVertexAttribArray(i)
            glVertexAttribPointer(
                i,
                attributes[i].n,
                attributes[i].type,
                false,
                attributes[i].stride,
                attributes[i].offset.toLong()
            )
        }

        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)

    }


    /**
     * renders the mesh
     */
    fun render() {

        //binden Vertex Array damit gerendert werden kann
        glBindVertexArray(vao)

        //Element Zeichnen
        glDrawElements(GL_TRIANGLES, indexcount, GL_UNSIGNED_INT, 0)

        //Lösen der Bindung
        glBindVertexArray(0)
    }

    /**
     * Deletes the previously allocated OpenGL objects for this mesh
     */
    fun cleanup() {
        if (ibo != 0)
            glDeleteBuffers(ibo)
        if (vbo != 0)
            glDeleteBuffers(vbo)
        if (vao != 0)
            glDeleteVertexArrays(vao)
    }
}