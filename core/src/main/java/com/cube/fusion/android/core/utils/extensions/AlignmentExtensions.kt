package com.cube.fusion.android.core.utils.extensions

import android.view.Gravity
import com.cube.fusion.core.model.TextAlignment

/**
 * Set of extensions relevant to the correct alignment of text
 *
 * Created by JR Mitchell on 03/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */

/**
 * Utility method to convert a [TextAlignment] from a Model parameter to an Android View Gravity [Int]
 *
 * TODO: Justified alignment currently is not justified
 *
 * @return the gravity [Int] associated with the given [TextAlignment]
 */
fun TextAlignment.asGravity(): Int = when (this) {
	TextAlignment.START -> Gravity.START
	TextAlignment.CENTER -> Gravity.CENTER
	TextAlignment.END -> Gravity.END
	TextAlignment.JUSTIFIED -> Gravity.CENTER_HORIZONTAL
}