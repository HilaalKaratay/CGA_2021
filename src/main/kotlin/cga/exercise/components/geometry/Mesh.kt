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

        // Aufgabe 1.2.2:

        // 1. Erstellen und Binden von VAO
        //Wie legt man Daten auf der GPU an? 3 Schritte
        //1. Eine ID erzeugen
        //2. Dann ID aktivieren
        //3. Dann Daten hochladen

        vao = glGenVertexArrays()
        glBindBuffer(GL_ARRAY_BUFFER, vao)                                  //glBindBuffer: Man aktivert und bindet den Buffer, GL_ARRAY_BUFFER: in den Daten stehen drinnen (z.B. Position in Vertex)
        glBufferData(GL_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)

        vbo = glGenBuffers()  //glGenBuffers: Man generiert eine ID und gibt diese zurück
        glBindBuffer(GL_ARRAY_BUFFER, vbo)                                  //glBindBuffer: Man aktivert und bindet den Buffer, GL_ARRAY_BUFFER: in den Daten stehen drinnen (z.B. Position in Vertex)
        glBufferData(GL_ARRAY_BUFFER, vertexdata, GL_STATIC_DRAW)           //glBufferData: Man ladet die Daten in die GPU, GL_STATIC_DRAW: inizialisert die Daten einmal und verändert diese nicht mehr, rendert mehrmals
        //glEnableVertexAttribArray(0)
        //glVertexAttribPointer(0, 3, GL_FLOAT, false, 24,0)

        ibo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, ibo)                                  //glBindBuffer: Man aktivert und bindet den Buffer, GL_ARRAY_BUFFER: in den Daten stehen drinnen (z.B. Position in Vertex)
        glBufferData(GL_ARRAY_BUFFER, indexdata, GL_STATIC_DRAW)            //glBufferData: Man ladet die Daten in die GPU, GL_STATIC_DRAW: inizialisert die Daten einmal und verändert diese nicht mehr, rendert mehrmals

        //4. VertexAttribute aktivieren & definieren
        for (i in attributes.indices){

            glEnableVertexAttribArray(i)                                    // glEnableVertexAttribArray:  sagt OpenGL welches VertexAttribut im Moment aktiv ist
            glVertexAttribPointer(i,attributes[i].n, attributes[i].type, false, attributes[i].stride, attributes[i].offset.toLong() )   // glVertexAttribPointer: Legt fest wie die verschiedenen Vertex Attribute zu definieren sind

        }

        indexcount = indexdata.size

        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindVertexArray(0)

        // todo: place your code here
        // todo: generate IDs
        // todo: bind your objects
        // todo: upload your mesh data
    }

       /* // todo: bind your objects
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


        */
    }

    /**
     * renders the mesh
     */
    fun render() {
        // todo: place your code here
        // call the rendering method every frame

        //VAO binden
        glBindVertexArray(vao)

        //Elemente zeichen
        glDrawElements(GL_TRIANGLES,5, GL_UNSIGNED_INT,0)

        //Lösen der Bindung
        glBindVertexArray(0) //reset
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