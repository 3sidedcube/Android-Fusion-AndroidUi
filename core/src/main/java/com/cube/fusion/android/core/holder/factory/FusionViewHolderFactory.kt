package com.cube.fusion.android.core.holder.factory

import android.view.ViewGroup
import com.cube.fusion.android.core.holder.FusionViewHolder

/**
 * Interface for the creation of new [FusionViewHolder]s for Fusion views
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
interface FusionViewHolderFactory {
	/**
	 * Create a new [FusionViewHolder] within a given [ViewGroup]
	 *
	 * @param parent the [ViewGroup] to create the [FusionViewHolder] in
	 */
	fun createViewHolder(parent: ViewGroup): FusionViewHolder<*>?
}