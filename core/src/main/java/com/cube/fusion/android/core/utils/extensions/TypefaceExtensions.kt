package com.cube.fusion.android.core.utils.extensions

import android.graphics.Typeface
import android.os.Build
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.cube.fusion.core.model.Font

/**
 * Set of extensions relevant to the correct rendering of text, font, typefaces etc.
 *
 * Created by Uchenna Okafor on 17/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */

/**
 * An extension method that converts the Text models font weight property to a Typeface weight
 *
 * @return The Typeface's weight
 */
fun Enum<Font.Weight>.toTypeface(): Int {
	return when (this) {
		Font.Weight.HEAVY, Font.Weight.BOLD, Font.Weight.SEMIBOLD -> Typeface.BOLD
		Font.Weight.REGULAR, Font.Weight.LIGHT -> Typeface.NORMAL
		Font.Weight.ITALIC -> Typeface.ITALIC
		else -> Typeface.NORMAL
	}
}

/**
 * Resolves a typeface for the TextView based on the Font Weight value
 *
 * @param textView The target TextView to resolve the typeface for
 * @return The resolved instance
 */
@RequiresApi(Build.VERSION_CODES.P)
fun Enum<Font.Weight>.resolveAsTypeface(textView: TextView): Typeface {
	val fontWeight = when (this) {
		Font.Weight.HEAVY -> 900
		Font.Weight.BOLD -> 700
		Font.Weight.SEMIBOLD -> 600
		Font.Weight.REGULAR -> 400
		Font.Weight.LIGHT -> 300
		else -> 400
	}

	val isItalic = this == Font.Weight.ITALIC
	return Typeface.create(textView.typeface, fontWeight, isItalic)
}