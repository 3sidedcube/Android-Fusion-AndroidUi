package com.cube.fusion.android.core.utils.shadow

import android.view.View
import androidx.annotation.Px

/**
 * Class representing an anchor on a canvas around which to draw a shadow
 *
 * Created by JR Mitchell on 11/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param left the left side of the anchor
 * @param top the top side of the anchor
 * @param right the right side of the anchor
 * @param bottom the bottom of the anchor
 */
data class ShadowAnchor(@Px val left: Int, @Px val top: Int, @Px val right: Int, @Px val bottom: Int) {
	companion object {
		/**
		 * Generate an instance of [ShadowAnchor] based on a direct descendant view of the root view's canvas
		 *
		 * @param anchorView the view to anchor the shadow around
		 */
		fun fromAnchorView(anchorView: View) = ShadowAnchor(anchorView.left, anchorView.top, anchorView.right, anchorView.bottom)

		/**
		 * Generate an instance of [ShadowAnchor] based on an indirect descendant view of the root view's canvas
		 * Note: this method will have unexpected behaviour if [childView] is not a child view of [rootView]
		 *
		 * @param childView the child view to anchor the shadow around
		 * @param rootView the root view that [childView]'s relative position should be calculated from
		 */
		fun fromViewsRelative(childView: View, rootView: View): ShadowAnchor {
			var left = childView.left
			var top = childView.top
			var right = childView.right
			var bottom = childView.bottom
			val parent = childView.parent
			if (parent is View && parent != rootView) {
				val recurse = fromViewsRelative(parent, rootView)
				left += recurse.left
				top += recurse.top
				right += recurse.left
				bottom += recurse.top
			}
			return ShadowAnchor(left, top, right, bottom)
		}
	}
}