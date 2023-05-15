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

package com.jekajops.cargame.actors

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.jekajops.cargame.utils.GameSettings

/**
 * Created by Marius Reimer on 08-Jul-16.
 */
class BackgroundActor(val textureRegion: TextureRegion?) : Actor() {

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = color
        batch.color.a *= parentAlpha
        batch.draw(textureRegion, 0f, 0f, GameSettings.WIDTH, GameSettings.HEIGHT)
        batch.color = Color.BLACK
        super.draw(batch, parentAlpha)
    }
}
