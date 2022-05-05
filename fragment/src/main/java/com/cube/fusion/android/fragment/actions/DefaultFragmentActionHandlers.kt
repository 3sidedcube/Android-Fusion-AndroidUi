package com.cube.fusion.android.fragment.actions

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.actions.DefaultAndroidActionHandlers
import com.cube.fusion.android.core.actions.FusionAndroidActionHandler
import com.cube.fusion.android.core.actions.MutableListMultiActionHandler
import com.cube.fusion.android.fragment.FusionContentFragment
import com.cube.fusion.android.fragment.WebViewFragment
import com.cube.fusion.core.model.action.LinkAction
import com.cube.fusion.core.model.action.PageAction

/**
 * [FusionAndroidActionHandler] implementation for the default handling of actions by fragment transactions
 * Also contains each of the individual default child handlers as static methods
 *
 * Created by JR Mitchell on 05/May/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param fragmentManager the [FragmentManager] to use in order to display new fragments when handling actions
 * @param containerViewId the resource ID of the view used to contain the fragments displayed when handling actions
 */
class DefaultFragmentActionHandlers(fragmentManager: FragmentManager, @IdRes containerViewId: Int = R.id.fragment) : MutableListMultiActionHandler() {

	companion object {
		/**
		 * Get a default handler for [PageAction]s using fragments to display page data
		 *
		 * @param fragmentManager the [FragmentManager] to use in order to display a page fragment
		 * @param containerViewId the resource ID of the view used to contain a displayed fragment for a page
		 * @return a [FusionAndroidActionHandler] that adds a new fragment to the [fragmentManager] each [PageAction], passing the page URL as an extra
		 */
		fun defaultPageActivityActionHandler(fragmentManager: FragmentManager, @IdRes containerViewId: Int = R.id.fragment) = FusionAndroidActionHandler { _, action ->
			if (action is PageAction) {
				fragmentManager.beginTransaction()
					.replace(containerViewId, FusionContentFragment::class.java, FusionContentFragment.getBundle(action.extractClick()))
					.addToBackStack(null)
					.commit()
				true
			}
			else false
		}

		/**
		 * Get a default handler for in-app [LinkAction]s, i.e ones with [LinkAction.inApp] true, using fragments to display webpage data
		 *
		 * @param fragmentManager the [FragmentManager] to use in order to display a link fragment
		 * @param containerViewId the resource ID of the view used to contain a displayed fragment for a link
		 * @return a [FusionAndroidActionHandler] that adds a new [WebViewFragment] to the [fragmentManager] for in-app [LinkAction]s, passing the link URL as an extra
		 */
		fun defaultInAppLinkActivityActionHandler(fragmentManager: FragmentManager, @IdRes containerViewId: Int = R.id.fragment) = FusionAndroidActionHandler { _, action ->
			if (action is LinkAction && action.inApp) {
				fragmentManager.beginTransaction()
					.replace(containerViewId, WebViewFragment::class.java, WebViewFragment.getBundle(action.extractClick()))
					.addToBackStack(null)
					.commit()
				true
			}
			else false
		}
	}

	override val childHandlers = mutableListOf(
		defaultPageActivityActionHandler(fragmentManager, containerViewId),
		defaultInAppLinkActivityActionHandler(fragmentManager, containerViewId),
		DefaultAndroidActionHandlers.defaultExternalLinkActivityActionHandler(),
		DefaultAndroidActionHandlers.defaultEmailActivityActionHandler()
	)
}