package com.cube.fusion.android.core.decorator

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.cube.fusion.android.core.holder.ShadowViewHolder
import com.cube.fusion.android.core.utils.shadow.ShadowAnchor

/**
 * [RecyclerView] item decoration implementation for drawing fake drop shadows behind all visible ViewHolders implementing [ShadowViewHolder]
 *
 * Created by JR Mitchell on 10/November/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class DropShadowDecorator : RecyclerView.ItemDecoration() {
	/**
	 * Paint for drawing the shadows
	 * Is mutated in the process of drawing, but initialised here to avoid overhead of creating a new paint for every shadow
	 */
	private val paint = Paint()

	override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
		c.save()
		parent.children.forEach { view ->
			(parent.getChildViewHolder(view) as? ShadowViewHolder)?.drawToCanvas(c, ShadowAnchor.fromAnchorView(view), paint)
		}
		c.restore()
	}
}