package cga.exercise.components.shader

import org.joml.*
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20.*
import java.nio.FloatBuffer
import java.nio.file.Files
import java.nio.file.Paths

class ShaderProgram(vertexShaderPath: String, fragmentShaderPath: String) {
    private var programID: Int = 0
    //Matrix buffers for setting matrix uniforms. Prevents allocation for each uniform
    private val m4x4buf: FloatBuffer = BufferUtils.createFloatBuffer(16)
    /**
     * Sets the active shader program of the OpenGL render pipeline to this shader
     * if this isn't already the currently active shader
     */
    fun use() {
        val curprog = GL11.glGetInteger(GL_CURRENT_PROGRAM)
        if (curprog != programID) glUseProgram(programID)

    }

    /**
     * Frees the allocated OpenGL objects
     */
    fun cleanup() {
        glDeleteProgram(programID)
    }



    //setUniform() functions are added later during the course
    // float vector uniforms
    /**
     * Sets a single float uniform
     * @param name  Name of the uniform variable in the shader
     * @param value Value
     * @return returns false if the uniform was not found in the shader
     */
    fun setUniform(name: String, value: Float ): Boolean {
        if (programID == 0) return false
        val loc = glGetUniformLocation(programID, name)
        if (loc != -1) {
            glUniform1f(loc, value)

            return true
        }
        return false
    }


    /** 2.1.2, eigene Methode erstellt */
    fun setUniform(name: String, value: Matrix4f, transpose: Boolean): Boolean {
        if (programID == 0) return false
        val loc = glGetUniformLocation(programID, name)
        if (loc != -1) {
            value.get(m4x4buf)
            glUniformMatrix4fv(loc,transpose, m4x4buf)
            return true
        }
        return false
    }


    /**
     * Creates a shader object from vertex and fragment shader paths
     * @param vertexShaderPath      vertex shader path
     * @param fragmentShaderPath    fragment shader path
     * @throws Exception if shader compilation failed, an exception is thrown
     */
    init {
        val vPath = Paths.get(vertexShaderPath)
        val fPath = Paths.get(fragmentShaderPath)
        val vSource = String(Files.readAllBytes(vPath))
        val fSource = String(Files.readAllBytes(fPath))
        val vShader = glCreateShader(GL_VERTEX_SHADER)
        if (vShader == 0) throw Exception("Vertex shader object couldn't be created.")
        val fShader = glCreateShader(GL_FRAGMENT_SHADER)
        if (fShader == 0) {
            glDeleteShader(vShader)
            throw Exception("Fragment shader object couldn't be created.")
        }
        glShaderSource(vShader, vSource)
        glShaderSource(fShader, fSource)
        glCompileShader(vShader)
        if (glGetShaderi(vShader, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            val log = glGetShaderInfoLog(vShader)
            glDeleteShader(fShader)
            glDeleteShader(vShader)
            throw Exception("Vertex shader compilation failed:\n$log")
        }
        glCompileShader(fShader)
        if (glGetShaderi(fShader, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            val log = glGetShaderInfoLog(fShader)
            glDeleteShader(fShader)
            glDeleteShader(vShader)
            throw Exception("Fragment shader compilation failed:\n$log")
        }
        programID = glCreateProgram()
        if (programID == 0) {
            glDeleteShader(vShader)
            glDeleteShader(fShader)
            throw Exception("Program object creation failed.")
        }
        glAttachShader(programID, vShader)
        glAttachShader(programID, fShader)
        glLinkProgram(programID)
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL11.GL_FALSE) {
            val log = glGetProgramInfoLog(programID)
            glDetachShader(programID, vShader)
            glDetachShader(programID, fShader)
            glDeleteShader(vShader)
            glDeleteShader(fShader)
            throw Exception("Program linking failed:\n$log")
        }
        glDetachShader(programID, vShader)
        glDetachShader(programID, fShader)
        glDeleteShader(vShader)
        glDeleteShader(fShader)
    }
}