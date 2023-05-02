package com.cube.fusion.android.core.config

import com.cube.fusion.android.core.resolver.AndroidViewResolver
import com.cube.fusion.core.display.FusionDisplayPopulator

/**
 * Convenience class for storing all configuration objects needed for a Fusion Android setup
 *
 * Created by JR Mitchell on 01/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param populator The config's display populator
 * @param resolvers The map of class names to view resolvers to resolve views with
 * @param viewConfig The configuration required for ViewHolders
 */
open class AndroidFusionConfig(
	val populator: FusionDisplayPopulator,
	val resolvers: MutableMap<String, AndroidViewResolver>,
	val viewConfig: AndroidFusionViewConfig
)