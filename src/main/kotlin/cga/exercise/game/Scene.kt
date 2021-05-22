package cga.exercise.game

import cga.exercise.components.geometry.Mesh
import cga.exercise.components.geometry.VertexAttribute
import cga.exercise.components.shader.ShaderProgram
import cga.framework.GLError
import cga.framework.GameWindow
import cga.framework.OBJLoader
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20


/**
 * Created by Fabian on 16.09.2017.
 */
class Scene(private val window: GameWindow) {
    private val staticShader: ShaderProgram
    var meshhaus : Mesh


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


        //1.2.1
        // Vertices (position) und indices definiert mit float und int Array
        val vertexData = floatArrayOf(
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f,
            0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            -0.5f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f
        )

        val ibo = intArrayOf(
            0, 1, 2,
            0, 2, 4,
            4, 2, 3
        )

        val vertexpos = VertexAttribute(3, GL20.GL_FLOAT, 24, 0)     //Attribut für Position
        val vertexcol = VertexAttribute(3, GL20.GL_FLOAT, 24, 12)    //Attribut für Farbe

        val vertexzusammen = arrayOf(vertexpos, vertexcol)

        meshhaus = Mesh(vertexData, ibo, vertexzusammen);

    }

    fun render(dt: Float, t: Float) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        //1.2.3
        staticShader.use();
        meshhaus.render()

    }

    fun update(dt: Float, t: Float) {}

    fun onKey(key: Int, scancode: Int, action: Int, mode: Int) {}

    fun onMouseMove(xpos: Double, ypos: Double) {}

    fun cleanup() {}
}

