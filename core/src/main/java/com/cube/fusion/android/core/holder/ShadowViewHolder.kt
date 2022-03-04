package com.cube.fusion.android.core.holder

import android.graphics.Canvas
import android.graphics.Paint
import com.cube.fusion.android.core.utils.shadow.ShadowAnchor
import com.cube.fusion.android.core.utils.shadow.ShadowRectSpec

/**
 * Interface for any RecyclerView.ViewHolder that can display a drop shadow behind it
 *
 * Created by JR Mitchell on 11/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
interface ShadowViewHolder {
	/**
	 * The specification for the ViewHolder's current shadow
	 * Note that a val can be overridden by a var, allowing this value to be changed by the specific implementation
	 */
	val shadowRectSpec: List<ShadowRectSpec>?

	/**
	 * Draw a shadow to a canvas based on this ViewHolder's current [shadowRectSpec]
	 * WARNING: this method mutates [paint]
	 *
	 * @param canvas the [Canvas] to draw to
	 * @param anchor the anchor for the shadow
	 * @param paint the paint to draw the rounded rectangle with
	 */
	fun drawToCanvas(canvas: Canvas, anchor: ShadowAnchor, paint: Paint) {
		shadowRectSpec?.forEach {
			it.drawToCanvas(canvas, anchor, paint)
		}
	}
}