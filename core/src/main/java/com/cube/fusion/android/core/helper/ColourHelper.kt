package com.cube.fusion.android.core.helper

import android.graphics.Color
import androidx.annotation.ColorInt

/**
 * Singleton object for helping to correctly parse colour strings
 *
 * Created by JR Mitchell on 16/November/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object ColourHelper {
	/**
	 * Enum class specifying ways to format colours to strings
	 */
	enum class ColourFormat {
		/**
		 * Colour strings in the format #RRGGBB or #RRGGBBAA
		 */
		RGBA,

		/**
		 * Colour strings in the format #RRGGBB or #AARRGGBB
		 */
		ARGB;
	}

	/**
	 * The colour format to use when parsing colour strings
	 * Statamic uses [ColourFormat.RGBA] format, hence this is used as the default value
	 */
	var colourParseFormat: ColourFormat = ColourFormat.RGBA

	/**
	 * Parses a string representation of a colour to a [ColorInt] based on the current value of [colourParseFormat]
	 *
	 * @param colourString the colour string to parse
	 */
	@ColorInt
	fun parseColour(colourString: String?) = when (colourParseFormat) {
		ColourFormat.RGBA -> {
			if (colourString != null && colourString[0] == '#' && colourString.length == 9) //#RGBA, need to reorder
			{
				val newStr = colourString.substring(0, 1) + colourString.substring(7, 9) + colourString.substring(1, 7)
				Color.parseColor(newStr)
			}
			else {
				Color.parseColor(colourString)
			}
		}
		ColourFormat.ARGB -> Color.parseColor(colourString)
	}
}