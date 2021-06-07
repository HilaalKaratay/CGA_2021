package cga.exercise.components.geometry

import cga.exercise.game.Scene
import org.joml.Matrix4f
import org.joml.Vector3f


open class Transformable(var modelMatrix: Matrix4f = Matrix4f(), var parent: Transformable? = null) {

    /**
     * Rotates object around its own origin.
     * @param pitch radiant angle around x-axis ccw
     * @param yaw radiant angle around y-axis ccw
     * @param roll radiant angle around z-axis ccw
     */
    open fun rotateLocal(pitch: Float, yaw: Float, roll: Float) {
        modelMatrix.rotateX(Math.toRadians(pitch.toDouble()).toFloat())
        modelMatrix.rotateY(Math.toRadians(yaw.toDouble()).toFloat())
        modelMatrix.rotateZ(Math.toRadians(roll.toDouble()).toFloat())
        throw NotImplementedError()
    }

    /**
     * Rotates object around given rotation center.
     * @param pitch radiant angle around x-axis ccw
     * @param yaw radiant angle around y-axis ccw
     * @param roll radiant angle around z-axis ccw
     * @param altMidpoint rotation center
     */
    fun rotateAroundPoint(pitch: Float, yaw: Float, roll: Float, altMidpoint: Vector3f) {
        val temp = Matrix4f()
        temp.translate(altMidpoint)
        temp.rotateXYZ(Vector3f(Math.toRadians(pitch.toDouble()).toFloat(),
            Math.toRadians(yaw.toDouble()).toFloat(), Math.toRadians(roll.toDouble()).toFloat()))
        temp.translate(Vector3f(altMidpoint).negate())
        temp.mul(modelMatrix, modelMatrix)
    }

    /**
     * Translates object based on its own coordinate system.
     * @param deltaPos delta positions
     */
    fun translateLocal(deltaPos: Vector3f) {
        modelMatrix.translate(deltaPos)
        throw NotImplementedError()
    }

    /**
     * Translates object based on its parent coordinate system.
     * Hint: global operations will be left-multiplied
     * @param deltaPos delta positions (x, y, z)
     */
    fun translateGlobal(deltaPos: Vector3f) {
        val temp = Matrix4f()
        temp.translate(deltaPos)
        temp.mul(modelMatrix, modelMatrix)
        throw NotImplementedError()
    }

    /**
     * Scales object related to its own origin
     * @param scale scale factor (x, y, z)
     */
    fun scaleLocal(scale: Vector3f) {
        modelMatrix.scale(scale)
        throw NotImplementedError()
    }

    /**
     * Returns position based on aggregated translations.
     * Hint: last column of model matrix
     * @return position
     */
    fun getPosition(): Vector3f {
        return Vector3f(modelMatrix.m30(), modelMatrix.m31(), modelMatrix.m32())
    }

    /**
     * Returns position based on aggregated translations incl. parents.
     * Hint: last column of world model matrix
     * @return position
     */
    fun getWorldPosition(): Vector3f {
        val world = getWorldModelMatrix()
        return Vector3f(world.m30(), world.m31(), world.m32())
    }

    /**
     * Returns x-axis of object coordinate system
     * Hint: first normalized column of model matrix
     * @return x-axis
     */
    fun getXAxis(): Vector3f {
        val xAxis = Vector3f(modelMatrix.m00(), modelMatrix.m01(), modelMatrix.m02())
        xAxis.normalize()
        return xAxis
    }

    /**
     * Returns y-axis of object coordinate system
     * Hint: second normalized column of model matrix
     * @return y-axis
     */
    fun getYAxis(): Vector3f {
        val yAxis = Vector3f(modelMatrix.m10(), modelMatrix.m11(), modelMatrix.m12())
        yAxis.normalize()
        return yAxis
    }

    /**
     * Returns z-axis of object coordinate system
     * Hint: third normalized column of model matrix
     * @return z-axis
     */
    fun getZAxis(): Vector3f {

        val zAxis = Vector3f(modelMatrix.m20(), modelMatrix.m21(), modelMatrix.m22())
        zAxis.normalize()
        return zAxis
    }

    /**
     * Returns x-axis of world coordinate system
     * Hint: first normalized column of world model matrix
     * @return x-axis
     */
    fun getWorldXAxis(): Vector3f {
        val world = getWorldModelMatrix()
        val xAxis = Vector3f(world.m00(), world.m01(), world.m02())
        return xAxis.normalize()
    }

    /**
     * Returns y-axis of world coordinate system
     * Hint: second normalized column of world model matrix
     * @return y-axis
     */
    fun getWorldYAxis(): Vector3f {
        val world = getWorldModelMatrix()
        val yAxis = Vector3f(world.m10(), world.m11(), world.m12())
        return yAxis.normalize()
    }

    /**
     * Returns z-axis of world coordinate system
     * Hint: third normalized column of world model matrix
     * @return z-axis
     */
    fun getWorldZAxis(): Vector3f {
        val world = getWorldModelMatrix()
        val zAxis = Vector3f(world.m20(), world.m21(), world.m22())
        return zAxis.normalize()
    }

    /**
     * Returns multiplication of world and object model matrices.
     * Multiplication has to be recursive for all parents.
     * Hint: scene graph
     * @return world modelMatrix
     */
    fun getWorldModelMatrix(): Matrix4f {
        val world = Matrix4f(modelMatrix)
        if (parent != null) {
            parent!!.getWorldModelMatrix().mul(modelMatrix, world)
        }
        return world

    }

    /**
     * Returns object model matrix
     * @return modelMatrix
     */
    fun getLocalModelMatrix(): Matrix4f {
        return modelMatrix
    }
}