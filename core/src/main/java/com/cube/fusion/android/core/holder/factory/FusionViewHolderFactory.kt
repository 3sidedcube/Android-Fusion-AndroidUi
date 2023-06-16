package com.cube.fusion.android.core.holder.factory

import android.view.ViewGroup
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.holder.FusionViewHolder
import com.cube.fusion.core.model.Model

/**
 * Interface for the creation of new [FusionViewHolder]s for Fusion views
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param T The type of model that the factory creates a [FusionViewHolder] for
 */
interface FusionViewHolderFactory <T: Model> {
	/**
	 * Create a new [FusionViewHolder] within a given [ViewGroup]
	 *
	 * @param parent The [ViewGroup] to create the [FusionViewHolder] in
	 * @param viewConfig A reference to the [AndroidFusionViewConfig] instance used for configuring views
	 */
	fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): FusionViewHolder<T>?
}