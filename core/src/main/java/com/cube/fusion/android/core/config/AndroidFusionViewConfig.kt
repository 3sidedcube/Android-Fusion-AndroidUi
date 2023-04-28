package com.cube.fusion.android.core.config

import com.cube.fusion.android.core.actions.FusionAndroidActionHandler
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
import com.cube.fusion.android.core.preprocessor.FusionModelPreprocessor

/**
 * Convenience class for storing configuration objects needed by ViewHolders in a Fusion Android setup
 *
 * Created by JR Mitchell on 27/April/2023.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param actionHandler The config's action handler
 * @param imageLoader The config's image loader
 */
class AndroidFusionViewConfig(
	val actionHandler: FusionAndroidActionHandler,
	val imageLoader: FusionAndroidImageLoader?,
	val modelPreprocessors: List<FusionModelPreprocessor<*>>
)