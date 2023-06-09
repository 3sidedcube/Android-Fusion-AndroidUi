package com.cube.fusion.android.core.utils

import android.content.res.Resources
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.core.model.Size
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Object containing utility methods relating to [Size] in Android UI
 *
 * Created by JR Mitchell on 09/June/2023.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object SizeUtils {
	/**
	 * Converts a DP value to size pixels
	 * Current implementation converts values in the range (0px, 1px] to 1px, so that only 0dp returns 0px
	 *
	 * @param dp the DP value to convert to size pixels
	 */
	private fun Resources.dpToSize(dp: Float) = ceil(dpToPx(dp)).roundToInt()

	/**
	 * Gets the width and height of a [Size] in screen pixels, interpreting its values as initially in DP
	 * @param resources The resources to use in order to convert DP to pixels
	 */
	fun Size.fromDpToPx(resources: Resources): android.util.Size {
		return android.util.Size(resources.dpToSize(width), resources.dpToSize(height))
	}
}
