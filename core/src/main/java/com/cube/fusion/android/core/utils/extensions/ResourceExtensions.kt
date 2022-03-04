package com.cube.fusion.android.core.utils.extensions

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.DimenRes

/**
 * Set of convenience extensions relating to Android [Resources]
 *
 * Created by JR Mitchell on 03/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */

/**
 * Utility method to get the value of a dimension resource in DP
 *
 * @param resId the ID of the dimension resource
 * @return the value of the dimension resource, in units of DP
 */
fun Resources.getDimensionInDP(@DimenRes resId: Int): Float {
	val unit = dpToPx(1f)
	return getDimension(resId) / unit
}

/**
 * Convenience method to convert a value in DP to PX
 *
 * @param dp the dp value to convert
 * @return the number of pixels that [dp] is equivalent to with current display metrics
 */
fun Resources.dpToPx(dp: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics)

/**
 * Get a dimension value that may be defined directly as a float or otherwise, treating it as a value in ems
 * e.g: <item name="fusion_default_text_view_letter_spacing" type="dimen" format="float">0.0</item>
 *
 * @param resId the resource ID for the dimension
 */
fun Resources.getDimenOrEms(@DimenRes resId: Int) = try {
	getDimension(resId)
}
catch (e: Resources.NotFoundException) {
	val typedValue = TypedValue()
	getValue(resId, typedValue, true)
	typedValue.float
}