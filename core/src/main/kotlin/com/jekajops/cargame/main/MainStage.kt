/*
 * Copyright (c) 2017. Marius Reimer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jekajops.cargame.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Array
import com.jekajops.cargame.abstract.AbstractStretchStage
import com.jekajops.cargame.actors.BackgroundActor
import com.jekajops.cargame.actors.Frame
import com.jekajops.cargame.actors.SpawnCircle
import com.jekajops.cargame.actors.scene2d.GameOverTable
import com.jekajops.cargame.enums.GameState
import com.jekajops.cargame.menu.GameMenu
import com.jekajops.cargame.screens.MenuScreen
import com.jekajops.cargame.utils.*

/**
 * The main logic for the game. The Stage may has many actors and a world, handles events, ...
 *
 * Created by Marius Reimer on 10-Jun-16.
 */
class MainStage : AbstractStretchStage {

    private var renderer: Box2DDebugRenderer = Box2DDebugRenderer()

    private var accumulator = 0f
    private var time = 0f

    private var started = false
    private var gameOver = false

    constructor() : super() {
        clear()
        addBackground()
        GameManager.listener?.showAd()
        setupWorld()
        GameManager.reset()
        setupMenu()
        createTouchControlAreas()
        setupFrame()
        AudioUtils.playBackgroundMusic()
    }

    /**
     * Un-Comment this method to add the debug render
     */
    //    override fun draw() {
//        super.draw()
//        renderer.render(GameManager.world, camera.combined)
//    }

    private fun addBackground() {
        addActor(BackgroundActor(AssetsManager.textureMap[Resources.RegionNames.BACKGROUND_NAME.name]))
    }

    private fun setupWorld() {
        GameManager.world = WorldFactory.createWorld()
    }

    private fun setupFrame() {
        addActor(Frame(WorldFactory.createFrame(GameManager.world, Vector2(0f, 0f), GameSettings.WIDTH.toInt(), 1))) // bottom
        addActor(Frame(WorldFactory.createFrame(GameManager.world, Vector2(0f, 0f), 1, GameSettings.HEIGHT.toInt()))) // left
        addActor(Frame(WorldFactory.createFrame(GameManager.world, Vector2(0f, (GameSettings.HEIGHT).toFloat() - (GameSettings.WIDTH * 0.08f)), GameSettings.WIDTH.toInt(), 1))) // top
        addActor(Frame(WorldFactory.createFrame(GameManager.world, Vector2((GameSettings.WIDTH), 0f), 1, GameSettings.HEIGHT.toInt()))) // right
    }

    private fun setupMenu() {
        val menu = GameMenu()
        addActor(menu.table)
        GameManager.menu = menu
    }

    private fun createTouchControlAreas() {
        Gdx.input.inputProcessor = this
    }

    private fun update(body: Body) {

    }

    override fun act(delta: Float) {
        super.act(delta)

        if (GameManager.gameState == GameState.OVER && !gameOver) {
            gameOver = true
            onGameOver()
        }

        if (GameManager.gameState != GameState.RUNNING) {
            return
        }

        time += delta

        for (b in GameManager.bodiesToRemove) {
            GameManager.destroyBody(b)
        }

        val bodies: Array<Body> = Array(GameManager.world.bodyCount)
        GameManager.world.getBodies(bodies)

        for (b in bodies) {
            update(b)
        }

        // increment a step in our game
        accumulator += delta
        while (accumulator >= delta) {
            GameManager.world.step(GameSettings.TIME_STEP, 6, 2)
            accumulator -= GameSettings.TIME_STEP
        }

        if (time >= 1f && !started) {
            started = true
            spawnCircle(20)
        }

        for (a in GameManager.actorsToAdd) {
            addActor(a)
            GameManager.actorsToAdd = GameManager.actorsToAdd.minus(a)
        }
    }

    private fun spawnCircle(number: Int = 1) {
        for (i in 1..number) {
            addActor(SpawnCircle(WorldFactory.createCircle(), null, true))
        }
    }

    private fun onGameOver() {
        clear()
        addBackground()
        GameManager.listener?.submitScore()
        GameManager.onGameOver()
        GameManager.listener?.hideAd()
        AudioUtils.playGameOverSound()
        addActor(GameOverTable())
    }

    override fun keyDown(keyCode: Int): Boolean {
        when (keyCode) {
            Input.Keys.BACK -> MainGame.screen = MenuScreen()
        }
        return super.keyDown(keyCode)
    }

    override fun dispose() {
        super.dispose()
        renderer.dispose()
        GameManager.world.dispose()
    }
}
