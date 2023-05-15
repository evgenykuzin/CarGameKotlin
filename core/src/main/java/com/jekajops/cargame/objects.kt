package com.jekajops.cargame

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle

class Car(private val texture: TextureRegion, var x: Float, var y: Float) {
    var velocity = 0f
    var acceleration = 500f
    var deceleration = 1000f
    var maxVelocity = 400f
    var turnSpeed = 200f

    val bounds = Rectangle(x, y, texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
    private var angle = 0f

    fun draw(batch: SpriteBatch) {
        batch.draw(texture, x, y, bounds.width, bounds.height)
    }

    fun update(deltaTime: Float) {
        val accelerationAmount = if (velocity < maxVelocity) acceleration else 0f
        val decelerationAmount = if (velocity > 0) deceleration else 0f
        velocity = MathUtils.clamp(velocity + accelerationAmount * deltaTime - decelerationAmount * deltaTime, 0f, maxVelocity)

        y += velocity * deltaTime

        // угол поворота автомобиля
        val angleTarget = if (velocity > 0) {
            if (Gdx.input.isTouched && Gdx.input.x < Gdx.graphics.width / 2) -20f
            else if (Gdx.input.isTouched && Gdx.input.x > Gdx.graphics.width / 2) 20f
            else 0f
        } else {
            0f
        }

        val deltaAngle = angleTarget - angle
        angle += deltaAngle * turnSpeed * deltaTime
        //bounds. = angle

        bounds.setPosition(x, y)
    }

    fun accelerate() {
        TODO("Not yet implemented")
    }

    fun slowdown() {
        TODO("Not yet implemented")
    }

    fun turnLeft() {
        TODO("Not yet implemented")
    }

    fun turnRight() {
        TODO("Not yet implemented")
    }
}

class EnemyCar(private val texture: TextureRegion, var x: Float, var y: Float) {
    var velocity = 0f
    var acceleration = 500f
    var deceleration = 1000f
    var maxVelocity = 400f
    var turnSpeed = 200f

    val bounds = Rectangle(x, y, texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
    private var angle = 0f

    fun draw(batch: SpriteBatch) {
        batch.draw(texture, x, y, bounds.width, bounds.height)
    }

    fun update(deltaTime: Float) {
        val accelerationAmount = if (velocity < maxVelocity) acceleration else 0f
        val decelerationAmount = if (velocity > 0) deceleration else 0f
        velocity = MathUtils.clamp(velocity + accelerationAmount * deltaTime - decelerationAmount * deltaTime, 0f, maxVelocity)

        y += velocity * deltaTime

        // угол поворота автомобиля
        val angleTarget = if (velocity > 0) {
            if (Gdx.input.isTouched && Gdx.input.x < Gdx.graphics.width / 2) -20f
            else if (Gdx.input.isTouched && Gdx.input.x > Gdx.graphics.width / 2) 20f
            else 0f
        } else {
            0f
        }

        val deltaAngle = angleTarget - angle
        angle += deltaAngle * turnSpeed * deltaTime
        //bounds. = angle

        bounds.setPosition(x, y)
    }
}

class Road(private val texture: TextureRegion, var x: Float, var y: Float) {
    var velocity = 0f
    var acceleration = 500f
    var deceleration = 1000f
    var maxVelocity = 400f
    var turnSpeed = 200f

    val bounds = Rectangle(x, y, texture.regionWidth.toFloat(), texture.regionHeight.toFloat())
    private var angle = 0f

    fun draw(batch: SpriteBatch) {
        batch.draw(texture, x, y, bounds.width, bounds.height)
    }

    fun update(deltaTime: Float) {
        val accelerationAmount = if (velocity < maxVelocity) acceleration else 0f
        val decelerationAmount = if (velocity > 0) deceleration else 0f
        velocity = MathUtils.clamp(velocity + accelerationAmount * deltaTime - decelerationAmount * deltaTime, 0f, maxVelocity)

        y += velocity * deltaTime

        // угол поворота автомобиля
        val angleTarget = if (velocity > 0) {
            if (Gdx.input.isTouched && Gdx.input.x < Gdx.graphics.width / 2) -20f
            else if (Gdx.input.isTouched && Gdx.input.x > Gdx.graphics.width / 2) 20f
            else 0f
        } else {
            0f
        }

        val deltaAngle = angleTarget - angle
        angle += deltaAngle * turnSpeed * deltaTime
        //bounds. = angle

        bounds.setPosition(x, y)
    }
}

