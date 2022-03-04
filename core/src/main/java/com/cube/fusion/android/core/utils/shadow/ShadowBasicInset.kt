package com.cube.fusion.android.core.utils.shadow

import android.content.res.Resources
import androidx.annotation.Px
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.core.model.Margin
import com.cube.fusion.core.model.Shadow

/**
 * Class representing the basic inset of a shadow in Px for a given view
 *
 * Created by JR Mitchell on 11/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param cardBaseMargin the margin between the cardview of the particular view and its root view
 * @param shadow the shadow to show behind the view
 * @param resources the view's current resources
 *
 * @property left the inset, in Px, from the left of the view
 * @property top the inset, in Px, from the top of the view
 * @property right the inset, in Px, from the right of the view
 * @property bottom the inset, in Px, from the bottom of the view
 */
class ShadowBasicInset(cardBaseMargin: Margin, shadow: Shadow, resources: Resources) {
	@Px
	val left = resources.dpToPx(cardBaseMargin.left + shadow.x - shadow.spread)

	@Px
	val top = resources.dpToPx(cardBaseMargin.top + shadow.y - shadow.spread)

	@Px
	val right = resources.dpToPx(cardBaseMargin.right - shadow.x - shadow.spread)

	@Px
	val bottom = resources.dpToPx(cardBaseMargin.bottom - shadow.y - shadow.spread)
}