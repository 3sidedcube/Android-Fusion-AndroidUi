package com.cube.fusion.android.core.holder

import android.view.View
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.core.model.Model

/**
 * Base class for a [FusionViewHolder] that can be used as a sub-view of another view based upon a nullable model property
 *
 * Created by JR Mitchell on 09/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
abstract class ChildViewHolder<T : Model>(itemView: View, viewConfig: AndroidFusionViewConfig) : FusionViewHolder<T>(itemView, viewConfig) {
	/**
	 * Updates the UI of the view based on a nullable [unprocessedModel] of type [T]
	 * Should behave as similarly to [populateView] as possible, however, there may be cases (such as with background colour) where behaviour should differ
	 *
	 * @param unprocessedModel The unprocessed model to update UI from, or null
	 *  if null, should set the view to its default UI state
	 */
	abstract fun populateChildView(unprocessedModel: T?)
}