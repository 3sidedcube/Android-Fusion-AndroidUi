package com.cube.fusion.android.core.utils.shadow

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.ColorInt

/**
 * Data class representing specification for a single rounded rectangle to make up part of a shadow
 *
 * Created by JR Mitchell on 11/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param colour the colour of the rounded rectangle
 * @param cornerRadius the corner radius of the rounded rectangle
 * @param insetLeft
 */
data class ShadowRectSpec(@ColorInt val colour: Int, val cornerRadius: Float, val insetLeft: Float, val insetTop: Float, val insetRight: Float, val insetBottom: Float) {
	/**
	 * Draw a rounded rectangle to a canvas based on this ShadowRectSpec
	 * WARNING: this method mutates [paint]
	 *
	 * @param canvas the [Canvas] to draw to
	 * @param anchor the anchor for the shadow
	 * @param paint the paint to draw the rounded rectangle with
	 */
	fun drawToCanvas(canvas: Canvas, anchor: ShadowAnchor, paint: Paint) {
		paint.color = colour
		val rect = RectF(anchor.left + insetLeft, anchor.top + insetTop, anchor.right - insetRight, anchor.bottom - insetBottom)
		if (rect.height() > 0 && rect.width() > 0) {
			canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
		}
	}
}