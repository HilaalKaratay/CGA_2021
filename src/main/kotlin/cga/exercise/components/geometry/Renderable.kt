package cga.exercise.components.geometry

import cga.exercise.components.shader.ShaderProgram

/** 2.3.*/
class Renderable : Transformable() {

    val renderableList: MutableList<Mesh>? = null


    /** 2.3.1*/

    override fun render(shaderProgram: ShaderProgram)  {

    }
}