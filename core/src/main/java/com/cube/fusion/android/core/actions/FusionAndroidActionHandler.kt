package com.cube.fusion.android.core.actions

import android.view.View
import com.cube.fusion.core.model.action.Action

/**
 * Interface for handling actions for Android views
 *
 * Created by JR Mitchell on 24/December/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
fun interface FusionAndroidActionHandler {
	/**
	 * Handle an action triggered by an Android view
	 *
	 * @param view the view from which the action was triggered
	 * @param action the action data associated with the triggering view
	 * @return whether the action was successfully handled
	 */
	fun handleAction(view: View, action: Action?) : Boolean
}