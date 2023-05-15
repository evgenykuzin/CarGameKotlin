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

package com.jekajops.cargame.actors.scene2d

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.jekajops.cargame.enums.GameState
import com.jekajops.cargame.utils.AssetsManager
import com.jekajops.cargame.utils.GameManager
import com.jekajops.cargame.utils.GameSettings

/**
 * Created by Marius Reimer on 06-Oct-16.
 */
class PointsLabel : Actor {

    private var removeTimer = 0f
    private var font: BitmapFont
    private var displayString = ""

    constructor(x: Float, y: Float, displayString: String) : super() {
        this.displayString = displayString

        font = AssetsManager.smallFont
        font.color = Color.GOLDENROD

        setBounds(x, y + GameSettings.RADIUS * 1.5f, 0f, 0f)
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        super.draw(batch, parentAlpha)

        if (removeTimer >= 0.3f) {
            remove()
            return
        }

        if (GameManager.gameState == GameState.RUNNING) {
            removeTimer += Gdx.graphics.deltaTime
        }

        font.draw(batch, displayString, x, y)
    }
}
