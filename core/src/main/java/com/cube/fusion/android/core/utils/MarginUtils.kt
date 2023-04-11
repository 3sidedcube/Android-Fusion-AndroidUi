package com.cube.fusion.android.core.utils

import android.content.res.Resources
import android.view.ViewGroup
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.android.core.utils.extensions.getDimensionInDP
import com.cube.fusion.core.model.Margin
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Object containing utility methods for displaying [Margin] in Android UI
 *
 * Created by JR Mitchell on 02/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object MarginUtils {
	/**
	 * Get a default instance of [Margin] based on the default margin dimensions specified in [resources]
	 *
	 * @param resources the app's current resources
	 * @return the default instance of [Margin]
	 */
	fun Margin.Companion.default(resources: Resources) = Margin(
		left = resources.getDimensionInDP(R.dimen.fusion_default_margin_left),
		top = resources.getDimensionInDP(R.dimen.fusion_default_margin_top),
		right = resources.getDimensionInDP(R.dimen.fusion_default_margin_right),
		bottom = resources.getDimensionInDP(R.dimen.fusion_default_margin_bottom)
	)

	/**
	 * Convenience method to get either [this] if [this] is non-null, or [default] if [this] is null
	 */
	fun Margin?.orDefault(resources: Resources) = this ?: Margin.default(resources)

	/**
	 * Converts a DP value to margin pixels
	 * Current implementation converts values in the range (0px, 1px] to 1px, so that only 0dp returns 0px
	 *
	 * @param dp the DP value to convert to margin pixels
	 */
	private fun Resources.dpToMargin(dp: Float) = ceil(dpToPx(dp)).roundToInt()

	/**
	 * Convenience extension fun to set the margin on a [ViewGroup]'s layout params based on an instance of [Margin]
	 *
	 * @param margin the [Margin] to set the view's layout params with
	 * @param resources the app's current resources
	 */
	fun ViewGroup.MarginLayoutParams.setMargin(margin: Margin, resources: Resources) {
		setMargins(
			resources.dpToMargin(margin.left),
			resources.dpToMargin(margin.top),
			resources.dpToMargin(margin.right),
			resources.dpToMargin(margin.bottom)
		)
	}
}