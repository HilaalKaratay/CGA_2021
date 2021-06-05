package cga.exercise.components.geometry

import cga.exercise.components.shader.ShaderProgram
import cga.framework.OBJLoader

interface IRenderable {
    fun render(shaderProgram: ShaderProgram)

}

