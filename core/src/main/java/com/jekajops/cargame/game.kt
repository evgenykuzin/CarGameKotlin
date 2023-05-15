package com.jekajops.cargame

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.MathUtils

class CarGame : Game() {
    private lateinit var batch: SpriteBatch
    private lateinit var textureAtlas: TextureAtlas
    private lateinit var car: Car
    private lateinit var road: Road
    private var enemyCars = mutableListOf<EnemyCar>()

    override fun create() {
        batch = SpriteBatch()
        textureAtlas = TextureAtlas(Gdx.files.internal("textures/textures.atlas"))
        car = Car(textureAtlas.findRegion("car"), 0f, 0f)
        road = Road(textureAtlas.findRegion("road"), 0f, 0f)
        spawnEnemy()
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        batch.begin()
        road.draw(batch)
        car.draw(batch)
        for (enemy in enemyCars) {
            enemy.draw(batch)
        }
        batch.end()

        car.update(Gdx.graphics.deltaTime)
        for (enemy in enemyCars) {
            enemy.update(Gdx.graphics.deltaTime)
            if (enemy.y < -enemy.bounds.height) {
                enemyCars.remove(enemy)
                spawnEnemy()
            }
            if (enemy.bounds.overlaps(car.bounds)) {
                Gdx.app.exit()
            }
        }
    }

    override fun resize(width: Int, height: Int) {
            car.x = (width - car.bounds.width) / 2f
            car.y = height * 0.1f
            road.bounds.width = width.toFloat()
            road.bounds.height = height.toFloat()
            super.resize(width, height)
        }

    override fun dispose() {
        batch.dispose()
        textureAtlas.dispose()
    }

    private fun spawnEnemy() {
        val enemy = EnemyCar(textureAtlas.findRegion("enemy"), 0f, 0f)
        enemy.x = MathUtils.random(0f, Gdx.graphics.width.toFloat() - enemy.bounds.width)
        enemy.y = Gdx.graphics.height.toFloat()
        enemyCars.add(enemy)
    }

    private inner class InputHandler : InputProcessor {
        override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            return true
        }

        override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            when {
                screenY > Gdx.graphics.height * 0.7f -> {
                    car.accelerate()
                }
                screenY < Gdx.graphics.height * 0.3f -> {
                    car.slowdown()
                }
                screenX < Gdx.graphics.width * 0.5f -> {
                    car.turnLeft()
                }
                screenX > Gdx.graphics.width * 0.5f -> {
                    car.turnRight()
                }
            }
            return true
        }

        override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
            return true
        }

        override fun keyDown(keycode: Int): Boolean {
            return true
        }

        override fun keyUp(keycode: Int): Boolean {
            return true
        }

        override fun keyTyped(character: Char): Boolean {
            return true
        }

        override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
            return true
        }

        override fun scrolled(amountX: Float, amountY: Float): Boolean {
            return true
        }
    }
}
