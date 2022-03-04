package com.cube.fusion.android.core.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import com.cube.fusion.android.core.holder.FusionViewHolder
import com.cube.fusion.android.core.utils.shadow.ShadowAnchor
import com.google.android.material.card.MaterialCardView

/**
 * [MaterialCardView] implementation for Fusion views that also permits the drawing of the shadows of child views on its surface
 *
 * Created by JR Mitchell on 11/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class FusionCardView(context: Context?, attrs: AttributeSet?) : MaterialCardView(context, attrs) {
	/**
	 * Current list of child [FusionViewHolder] references
	 */
	private val childViewHolders: MutableList<FusionViewHolder<*>> = mutableListOf()

	/**
	 * Paint for drawing the shadows
	 * Is mutated in the process of drawing, but initialised here to avoid overhead of creating a new paint for every shadow
	 */
	private val paint = Paint()

	/**
	 * Registers the [FusionViewHolder] of a child view of this [FusionCardView] so that its shadow can be drawn
	 * Warning: if [child]'s itemView is not a descendant of this [FusionCardView], the shadows cast may be unpredictable
	 *
	 * @param child the [FusionViewHolder] for the child view
	 */
	fun registerChildViewHolder(child: FusionViewHolder<*>) = childViewHolders.add(child)

	/**
	 * Unregisters a [FusionViewHolder] from the rendering of shadows for this [FusionCardView]
	 *
	 * @param child the [FusionViewHolder] to unregister
	 */
	fun unregisterChildViewHolder(child: FusionViewHolder<*>) = childViewHolders.remove(child)

	/**
	 * Unregisters all [FusionViewHolder]s from the rendering of shadows for this [FusionCardView]
	 */
	fun unregisterAllChildViewHolders() = childViewHolders.clear()

	override fun onDraw(canvas: Canvas?) {
		super.onDraw(canvas)
		if (canvas != null) {
			childViewHolders.forEach {
				it.drawToCanvas(canvas, ShadowAnchor.fromViewsRelative(it.itemView, this), paint)
			}
		}
	}
}