package com.cube.fusion.android.core.resolver

import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.core.resolver.ViewResolver

/**
 * [ViewResolver] extension that pairs the View with a given fusion ViewHolder factory
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
interface AndroidViewResolver : ViewResolver {
	fun resolveViewHolder(): Class<out FusionViewHolderFactory?>?
}
