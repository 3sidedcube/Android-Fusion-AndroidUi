package com.cube.fusion.android.core.config

import com.cube.fusion.android.core.actions.FusionAndroidActionHandler
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
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
 */
open class AndroidFusionConfig(
	val populator: FusionDisplayPopulator,
	val actionHandler: FusionAndroidActionHandler,
	val imageLoader: FusionAndroidImageLoader?
)