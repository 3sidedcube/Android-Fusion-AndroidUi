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
 * @param view The class of the view to resolve to
 * @param viewHolderFactory The factory to use in order to create views for the view
 */
class DefaultViewResolver(val view: Class<out Model?>, override val viewHolderFactory: FusionViewHolderFactory?) : AndroidViewResolver {
	override fun resolveView(): Class<out Model?> {
		return view
	}
}