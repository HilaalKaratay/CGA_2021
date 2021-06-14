package cga.exercise.components.geometry

import cga.exercise.components.shader.ShaderProgram
import cga.framework.OBJLoader
import java.util.ArrayList

/** 2.3.*///noch umzuändern in mesh
class Renderable(val meshList: MutableList<Mesh>) : Transformable(),IRenderable {

    /** 2.3.1*/
    override fun render(shaderProgram: ShaderProgram)  {
        shaderProgram.setUniform("model_matrix", getWorldModelMatrix(), false)
        for (m in meshList) {
            m.render(shaderProgram)
        }
    }

    override fun rotateLocal(pitch: Float, yaw: Float, roll: Float) {
        TODO("Not yet implemented")
    }


}

