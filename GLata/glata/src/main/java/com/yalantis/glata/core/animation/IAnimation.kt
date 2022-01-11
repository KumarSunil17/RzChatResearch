package com.yalantis.glata.core.animation

import com.yalantis.glata.core.RendererParams
import com.yalantis.glata.core.model.ModelParams
import com.yalantis.glata.core.scene.SceneParams

interface IAnimation {
    fun animate(rendererParams: RendererParams,
                modelParams: ModelParams,
                sceneParams: SceneParams) : Boolean

    fun isFinished() : Boolean
}