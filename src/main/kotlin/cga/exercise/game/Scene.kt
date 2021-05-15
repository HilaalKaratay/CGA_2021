package cga.exercise.game

import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.GameWindow
import org.lwjgl.opengl.GL11.*


/**
 * Created by Fabian on 16.09.2017.
 */
class Scene(private val window: GameWindow) {
    private val staticShader: ShaderProgram

    //scene setup
    init {
        staticShader = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")


        //initial opengl state
        glClearColor(0.6f, 1.0f, 1.0f, 1.0f); GLError.checkThrow()
        glDisable(GL_CULL_FACE); GLError.checkThrow()
        //glFrontFace(GL_CCW); GLError.checkThrow()
        //glCullFace(GL_BACK); GLError.checkThrow()
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(GL_LESS); GLError.checkThrow()


        //Aufgabe 1.2.1:
        // Vertices (position) und indices definiert mit float und int Array
        val vertices = floatArrayOf(
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f,  0.5f, 0.0f,
            0.0f,  1.0f, 0.0f,
            -0.5f,  0.5f, 0.0f
        )

        val indices = intArrayOf(
            0, 1, 2,
            0, 2, 4,
            4, 2, 3
        )

        val vertexpos = VertexAttribute(3, GL_FLOAT, 24, 0)     //Attribut für Position
        val vertexcol = VertexAttribute(3, GL_FLOAT, 24, 12)    //Attribut für Farbe

        val vertexzusammen = arrayOf(vertexpos, vertexcol)                    //Array für Position und Farbe

        mesh = Mesh(vertexdata = vertices, indexdata = indices, attributes = vertexzusammen)

        // Aufgabe 1.2.4:
        /**val vertices = floatArrayOf(
        -0.5f, -1.0f,  0.0f,
        0.5f, -1.0f,  0.0f,
        0.5f,  0.0f,  0.0f,
        -0.5f, -0.5f,  0.0f,
        -0.5f,  1.0f,  0.0f,
        -0.5f, -0.5f,  0.0f,
        0.5f,  0.0f,  0.0f,
        0.5f,  0.0f,  0.0f,
        -0.5f,  1.0f,  0.0f
        1.0f,  0.5f,  0.0f)**/

        /**val indices = intArrayOf(
        0, 1, 2,
        0, 4, 2,
        2, 1, 3,
        4, 2, 6,
        4, 7, 6,
        6, 5, 2

        )**/


        //Aufgabe 1.2.5:
        //glEnable ( GL_CULL_FACE )
        //glFrontFace ( GL_CCW )
        //glCullFace ( GL_BACK )


        // Aufgabe 1.3 Object Handeling

        // 1.3.1 a):
        // Objekt laden und ein Mesh erzeugen
        val res: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/models/sphere.obj")  //

        // 1.3.1 b):
        //Erstes Mesh vom ersten Objekt erhalten
        val objMesh: OBJLoader.OBJMesh = res.objects[0].meshes[0]

        // 1.3.3 d):
        //Mesh erstellen und VertexAttribute definieren, alle 3 Attribute anlegen damit Shader es nutzen kann
        val attrPos =   VertexAttribute(3, GL_FLOAT, 8 * 4, 0)           //position
        val attrTC =    VertexAttribute(2, GL_FLOAT, 8 * 4, 3 * 4)      //textureCoordinate
        val attrNorm =  VertexAttribute(3, GL_FLOAT, 8 * 4, 5 * 4)      //normalval

        val vertexAttributes = arrayOf<VertexAttribute>(attrPos, attrTC, attrNorm)

        //1.3.1 c):
        //Vertex- und Indexdaten als Arrays definieren
        val vertexData = objMesh.vertexData //get vertexdata
        val indexData = objMesh.indexData   //get indexddata

        //use plain data arrays to create a mesh
        val mesh2 = Mesh(objMesh.vertexData, objMesh.indexData, vertexAttributes)
    }

    fun render(dt: Float, t: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        // Aufgabe 1.2.3:
        staticShader.use()

        mesh.render()

    }

    fun update(dt: Float, t: Float) {}

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {}


    fun cleanup() {}
}
