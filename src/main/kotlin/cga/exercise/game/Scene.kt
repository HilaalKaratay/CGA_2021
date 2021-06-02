package cga.exercise.game

import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.GameWindow
import cga.framework.OBJLoader
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
import org.lwjgl.opengl.GL11.*


class Scene(private val window: GameWindow) {
    val staticShader: ShaderProgram = ShaderProgram("assets/shaders/simple_vert.glsl", "assets/shaders/simple_frag.glsl")
    val staticTron: ShaderProgram = ShaderProgram("assets/shaders/tron_vert.glsl","assets/shaders/tron_frag.glsl")
    var meshhaus : Mesh
    var meshname : Mesh
    var meshkreis : Mesh
    var boden : Mesh
    //var boden: Mesh
    private val m4Boden = Matrix4f()
    private var m4Kugel = Matrix4f()


    init {

        //initial opengl state + Blauer Hintergrund
        //glClearColor(0.6f, 1.0f, 1.0f, 1.0f); GLError.checkThrow()

        //schwarzer Hintergrund
        glClearColor (0.0f, 0.0f, 0.0f, 1.0f); GLError . checkThrow ()

        glDisable(GL_CULL_FACE); GLError.checkThrow()
        //glFrontFace(GL_CCW); GLError.checkThrow()
        //glCullFace(GL_BACK); GLError.checkThrow()
        glEnable(GL_DEPTH_TEST); GLError.checkThrow()
        glDepthFunc(GL_LESS); GLError.checkThrow()

        //1.2.1 Vertices (position) und indices definiert mit float und int Array
        val vertexDataInitialien = floatArrayOf(
            0.0f,  1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 1.0f,
            0.0f,  0.0f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f)

        val vertexDataHaus = floatArrayOf(
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f,  1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.5f,  0.5f, 0.0f, 0.0f, 1.0f, 0.0f)

        val iboHaus = intArrayOf(
            0, 1, 2,
            0, 2, 4,
            4, 2, 3)

        val iboInitialien = intArrayOf(
            0, 1, 2,
            2, 3, 4)

        val vertexpos = VertexAttribute(3, GL_FLOAT, 24, 0)     //Attribut für Position
        val vertexcol = VertexAttribute(3, GL_FLOAT, 24, 12)    //Attribut für Farbe
        val vertexzusammen = arrayOf(vertexpos, vertexcol)

        meshhaus = Mesh(vertexDataHaus, iboHaus, vertexzusammen)
        meshname = Mesh(vertexDataInitialien, iboInitialien, vertexzusammen)

        data class Vertex(val position: Vector3f, val texCoord: Vector2f, val normal: Vector3f)

        //Aufgabe 1.2.5: Gegen den Uhrzeigersinn def. Dreiecke rendern
        /*glEnable (GL_CULL_FACE)
        glFrontFace (GL_CCW)
        glCullFace (GL_BACK)*/


        // Aufgabe 1.3 Object Handeling
        // 1.3.1 a) Objekt laden und ein Mesh erzeugen
        val sphere: OBJLoader.OBJResult = OBJLoader.loadOBJ("assets/models/sphere.obj", false, false)
        val ground: OBJLoader.OBJResult =OBJLoader.loadOBJ("assets/models/ground.obj",false,false)


        // 1.3.1 b) Erstes Mesh vom ersten Objekt erhalten
        val objSphere: OBJLoader.OBJMesh = sphere.objects[0].meshes[0]
        val objSphereList: MutableList<OBJLoader.OBJMesh> = sphere.objects[0].meshes

        val objGround: OBJLoader.OBJMesh= ground.objects[0].meshes[0]
        val objGroundList: MutableList<OBJLoader.OBJMesh> =ground.objects[0].meshes

        OBJLoader.reverseWinding(objSphere)
        OBJLoader.recalculateNormals(objSphere)

       /* OBJLoader.reverseWinding(objGround)
        OBJLoader.recalculateNormals(objGround)*/

        // 1.3.3 d) Mesh erstellen und VertexAttribute definieren, alle 3 Attribute anlegen damit Shader diese nutzen kann
        val attrPos =   VertexAttribute(3, GL_FLOAT, 8*4, 0)       //position
        val attrTC =    VertexAttribute(2, GL_FLOAT, 8*4, 12)      //textureCoordinate
        val attrNorm =  VertexAttribute(3, GL_FLOAT, 8*4, 20)      //normalval

        val vertexAttributes = arrayOf<VertexAttribute>(attrPos, attrTC, attrNorm)

        //1.3.1 c): Vertex- und Indexdaten als Arrays definieren
        var vertexDataKreis = objSphere.vertexData //get vertexdata
        var indexDataKreis = objSphere.indexData   //get indexddata

        var vertexDataGround = objGround.vertexData //get vertexdata
        var indexDataGround= objGround.indexData   //get indexddata

        //1.3.1 d)
        // use plain data arrays to create a mesh
        meshkreis = Mesh(objSphere.vertexData,objSphere.indexData,vertexAttributes)
        boden = Mesh(objGround.vertexData,objGround.indexData,vertexAttributes)

        m4Boden.scale(0.3f)
        m4Boden.rotateX(90.0f)

        m4Kugel.scale(0.5f)
    }

    fun render(dt: Float, t: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        //1.2.3 welcher Shader = staticShader
        //staticShader.use()
        staticTron.use()

        //meshhaus.render()
        //meshname.render()

        //boden.render()
        meshkreis.render()
        staticTron.setUniform("model", m4Boden, false)

        boden.render()
        staticTron.setUniform("model_matrix", m4Kugel, false)
        //1.3.1 e)



    }

    fun update(dt: Float, t: Float) {}

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {}

    fun cleanup() {}
}
