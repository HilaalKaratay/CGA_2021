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

        /**1.2.2
        // 1. Erstellen und Binden von VAO
        //Wie legt man Daten auf der GPU an? 3 Schritte
        //1. Eine ID erzeugen
        //2. Dann ID aktivieren
        //3. Dann Daten hochladen */


        // 1.2.2

        indexcount = indexdata.size

        // VAO generieren und aktiviren
        vao = glGenVertexArrays()   //vao
        glBindVertexArray(vao)

        // VBO generieren, aktiviren und Datei hochladen
        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertexdata, GL_STATIC_DRAW) //vbo
        //VBO in VAO beschreiben und aktiviren
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
        glEnableVertexAttribArray(1);

        // IBO generieren, aktiviren und Datei hochladen
        ibo = glGenBuffers()        //ibo
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)


        //for (i in attributes.indices) {
            //glEnableVertexAttribArray(i)
          //  glVertexAttribPointer(i, attributes[i].n, attributes[i].type, false, attributes[i].stride, attributes[i].offset.toLong())
        //}

    }

    //1.2.2
    /**renders the mesh*/
    fun render() {

        //binden Vertex Array damit gerendert werden kann
        glBindVertexArray(vao)

        //Element Zeichnen
        glDrawElements(GL_TRIANGLES, indexcount, GL_UNSIGNED_INT, 0)

        //Lösen der Bindung
        glBindVertexArray(0)
    }

    /** Deletes the previously allocated OpenGL objects for this mesh*/
    fun cleanup() {
        if (ibo != 0)
            glDeleteBuffers(ibo)
        if (vbo != 0)
            glDeleteBuffers(vbo)
        if (vao != 0)
            glDeleteVertexArrays(vao)
    }
}