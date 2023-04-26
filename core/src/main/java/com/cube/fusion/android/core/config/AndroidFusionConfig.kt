package com.cube.fusion.android.core.config

import com.cube.fusion.android.core.actions.FusionAndroidActionHandler
import com.cube.fusion.android.core.extensions.ExtensionHandler
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
import com.cube.fusion.android.core.resolver.AndroidViewResolver
import com.cube.fusion.core.display.FusionDisplayPopulator

/**
 * Convenience class for storing all configuration objects needed for a Fusion Android setup
 *
 * Created by JR Mitchell on 01/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param populator the config's display populator
 * @param actionHandler the config's action handler
 * @param imageLoader the config's image loader
 * @param resolvers the map of class names to view resolvers to resolve views with
 */
open class AndroidFusionConfig(
	val populator: FusionDisplayPopulator,
	val actionHandler: FusionAndroidActionHandler,
	val imageLoader: FusionAndroidImageLoader?,
	val resolvers: MutableMap<String, AndroidViewResolver>,
	val extensionHandlers: List<ExtensionHandler>
)