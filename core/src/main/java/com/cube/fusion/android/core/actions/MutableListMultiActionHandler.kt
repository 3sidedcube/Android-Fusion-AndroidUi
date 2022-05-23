package com.cube.fusion.android.core.actions

import android.view.View
import com.cube.fusion.core.model.action.NativeAction

/**
 * Convenience abstract [MultiActionHandler] implementation where [childHandlers] is a [MutableList], with public methods for registering additional native actions
 *
 * Created by JR Mitchell on 05/May/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
abstract class MutableListMultiActionHandler : MultiActionHandler() {
	abstract override val childHandlers: MutableList<FusionAndroidActionHandler>

	/**
	 * Register a new handler for [NativeAction]s
	 *
	 * @param handler the handling method to call for any native action.
	 * 	Should return true if the action is successfully handled, and false otherwise.
	 */
	fun registerNativeAction(handler: (View, NativeAction) -> Boolean) {
		childHandlers.add(FusionAndroidActionHandler { view, action ->
			if (action is NativeAction) {
				handler(view, action)
			}
			else false
		})
	}

	/**
	 * Register a new handler for [NativeAction]s with a specific link, which returns true and calls [onLinkMatch] whenever the action link exactly matches [link]
	 *
	 * @param link the exact link to handle
	 * @param onLinkMatch the method to call whenever a [NativeAction] with the given link is handled
	 */
	fun registerNativeActionForLink(link: String, onLinkMatch: (View, NativeAction) -> Unit) = registerNativeAction { view, nativeAction ->
		if (nativeAction.link == link) {
			onLinkMatch(view, nativeAction)
			true
		}
		else false
	}
}