package cga.exercise.components.camera

import cga.exercise.components.geometry.Transformable
import cga.exercise.components.shader.ShaderProgram
import org.joml.Matrix4f

/** 2.4.1 */
abstract class TronCamera (var fieldOfView : Float, var aspect: Float, var nearPlane: Float, var farPlane: Float) :Transformable(), ICamera {

    fun TronCam() {
        this.fieldOfView = Math.toRadians(90.0).toFloat()
        aspect = 16.0f / 9.0f
        nearPlane = 0.1f
        farPlane = 100f
    }

    override var viewMat: Matrix4f
        get() = TODO("Not yet implemented")
        set(value) {}
    override var projMat: Matrix4f
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun calculateViewMatrix(): Matrix4f {
       viewMat.identity()
       viewMat = Matrix4f().lookAt(getWorldPosition(),getWorldPosition().sub(getWorldZAxis()),getWorldYAxis())
       return viewMat
   }

   override fun getCalculateProjectionMatrix(): Matrix4f {
        projMat.identity()
        projMat.perspective(fieldOfView, aspect, nearPlane, farPlane)
        return projMat
    }

    override fun bind(shaderProgram: ShaderProgram) {
        shaderProgram.setUniform("view_matrix", calculateViewMatrix(), false)
        shaderProgram.setUniform("projection_matrix", getCalculateProjectionMatrix(), false)
    }





}

