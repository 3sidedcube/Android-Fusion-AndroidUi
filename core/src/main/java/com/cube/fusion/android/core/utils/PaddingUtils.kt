package com.cube.fusion.android.core.utils

import android.content.res.Resources
import android.view.View
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.android.core.utils.extensions.getDimensionInDP
import com.cube.fusion.core.model.Padding
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Object containing utility methods for displaying [Padding] in Android UI
 *
 * Created by JR Mitchell on 02/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object PaddingUtils {
	/**
	 * Get a default instance of [Padding] based on the default padding dimensions specified in [resources]
	 *
	 * @param resources the app's current resources
	 * @return the default instance of [Padding]
	 */
	fun Padding.Companion.default(resources: Resources) = Padding().apply {
		left = resources.getDimensionInDP(R.dimen.fusion_default_padding_left)
		top = resources.getDimensionInDP(R.dimen.fusion_default_padding_top)
		right = resources.getDimensionInDP(R.dimen.fusion_default_padding_right)
		bottom = resources.getDimensionInDP(R.dimen.fusion_default_padding_bottom)
	}

	/**
	 * Converts a DP value to padding pixels
	 * Current implementation converts values in the range (0px, 1px] to 1px, so that only 0dp returns 0px
	 *
	 * @param dp the DP value to convert to padding pixels
	 */
	private fun Resources.dpToPadding(dp: Float) = ceil(dpToPx(dp)).roundToInt()

	/**
	 * Convenience extension fun to set the padding of a [View] based on an instance of [Padding]
	 *
	 * @param padding the [Padding] to set the view's padding with, or null
	 * 	if null, then [default] will be used
	 */
	fun View.setPadding(padding: Padding?) {
		val paddingOrDef = padding ?: Padding.default(resources)
		setPadding(
			resources.dpToPadding(paddingOrDef.left),
			resources.dpToPadding(paddingOrDef.top),
			resources.dpToPadding(paddingOrDef.right),
			resources.dpToPadding(paddingOrDef.bottom)
		)
	}
}