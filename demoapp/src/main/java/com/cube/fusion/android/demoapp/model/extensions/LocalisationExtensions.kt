package com.cube.fusion.android.demoapp.model.extensions

import com.cube.fusion.core.model.Model
import kotlinx.parcelize.Parcelize

@Parcelize
class LocalisationExtension(
	val localisedText: String? = null
): Model()