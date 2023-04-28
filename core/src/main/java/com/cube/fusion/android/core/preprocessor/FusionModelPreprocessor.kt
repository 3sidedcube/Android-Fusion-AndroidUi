package com.cube.fusion.android.core.preprocessor

import com.cube.fusion.core.model.views.BaseViewProperties
import com.cube.fusion.core.model.views.Button
import com.cube.fusion.core.model.views.Text

interface FusionModelPreprocessor<T> {
	fun processModel(model: T): T
}

interface FusionTextPreprocessor: FusionModelPreprocessor<Text>

interface FusionButtonPreprocessor: FusionModelPreprocessor<Button>

interface FusionBasePropertiesPreprocessor: FusionModelPreprocessor<BaseViewProperties>