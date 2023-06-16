package com.cube.fusion.android.core.resolver

import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.core.model.Model

/**
 * Default implementation of [AndroidViewResolver]
 * Resolves view and holder with references to the relevant classes
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param view the view to resolve to
 * @param viewHolder the holder to resolve to
 */
class DefaultViewResolver(var view: Class<out Model?>, viewHolder: Class<out FusionViewHolderFactory?>?) : AndroidViewResolver {
	override val viewHolderFactory = viewHolder?.newInstance()

	override fun resolveView(): Class<out Model?> {
		return view
	}
}