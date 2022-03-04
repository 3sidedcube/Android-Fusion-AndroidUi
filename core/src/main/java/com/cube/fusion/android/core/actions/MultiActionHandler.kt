package com.cube.fusion.android.core.actions

import android.view.View
import com.cube.fusion.core.model.action.Action

/**
 * Abstract [FusionAndroidActionHandler] implementation for an action handler that handles any action with the first of its children to return true whilst handling
 *
 * Created by JR Mitchell on 06/January/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @property childHandlers the collection of child handlers, in order, to try and handle any action with
 */
abstract class MultiActionHandler : FusionAndroidActionHandler {
	protected abstract val childHandlers: Collection<FusionAndroidActionHandler>
	override fun handleAction(view: View, action: Action?) = childHandlers.any { it.handleAction(view, action) }
}