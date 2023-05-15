package com.cube.fusion.android.core.config

import com.cube.fusion.android.core.actions.FusionAndroidActionHandler
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
import com.cube.fusion.core.processor.FusionDataPreprocessorCollection

/**
 * Convenience class for storing configuration objects needed by ViewHolders in a Fusion Android setup
 *
 * Created by JR Mitchell on 27/April/2023.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param actionHandler The config's action handler
 * @param imageLoader The config's image loader
 * @param preprocessors The collection of pre-processors to use to process data directly before populating the ViewHolder UI
 */
class AndroidFusionViewConfig(
	val actionHandler: FusionAndroidActionHandler,
	val imageLoader: FusionAndroidImageLoader?,
	val preprocessors: FusionDataPreprocessorCollection
)