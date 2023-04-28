package com.cube.fusion.android.core.utils.extensions

import com.cube.fusion.android.core.preprocessor.FusionModelPreprocessor

/**
 * Object containing extension functions relevant to [Collection]s for the Fusion AndroidUi library
 *
 * Created by JR Mitchell on 28/April/2023.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object CollectionExtensions {
	fun <ModelType> List<FusionModelPreprocessor<ModelType>>.preprocess(model: ModelType): ModelType {
		var outModel: ModelType = model
		forEach {
			outModel = it.processModel(outModel)
		}
		return outModel
	}

	inline fun <reified PreprocessorType: FusionModelPreprocessor<ModelType>, ModelType> List<FusionModelPreprocessor<*>>.preprocessWithType(model: ModelType): ModelType {
		return filterIsInstance<PreprocessorType>().preprocess(model)
	}
}