package cga.exercise.components.geometry

import cga.exercise.components.shader.ShaderProgram
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
        /* Aufgabe 1.2.2
        1. Erstellen und Binden von VAO  Wie legt man Daten auf der GPU an? 3 Schritte
        1. Eine ID erzeugen 2. Dann ID aktivieren 3. Dann Daten hochladen
        glBufferData: Man ladet die Daten in die GPU, GL_STATIC_DRAW: inizialisert die Daten einmal und verändert diese nicht mehr, rendert mehrmal*/

        //TODO:  Place your code here. Generate IDs, bind oyur objects and upload your Mesh data.
        indexcount = indexdata.size
        vao = glGenVertexArrays()
        glBindVertexArray(vao)

        vbo = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, vertexdata, GL_STATIC_DRAW)

        ibo = glGenBuffers()
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)

        //4. VertexAttribute aktivieren & definieren
        for (i in attributes.indices) {
            glVertexAttribPointer(i,
                attributes[i].n,
                attributes[i].type,
                false,
                attributes[i].stride,
                attributes[i].offset.toLong())   // glVertexAttribPointer: Legt fest wie die verschiedenen Vertex Attribute zu definieren sind
            glEnableVertexAttribArray(i)         // glEnableVertexAttribArray:  sagt OpenGL welches VertexAttribut im Moment aktiv ist
        }

        glBindVertexArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    //renders the mesh
    fun render(shaderProgram: ShaderProgram) {
        // todo: place your code here call the rendering method every frame

        //VAO binden
        glBindVertexArray(vao)

        //Elemente zeichen
        glDrawElements(GL_TRIANGLES,indexcount, GL_UNSIGNED_INT,0)

        //Lösen der Bindung
        glBindVertexArray(0) //reset
    }


    fun cleanup() {
        if (ibo != 0) glDeleteBuffers(ibo)
        if (vbo != 0) glDeleteBuffers(vbo)
        if (vao != 0) glDeleteVertexArrays(vao)
    }
}