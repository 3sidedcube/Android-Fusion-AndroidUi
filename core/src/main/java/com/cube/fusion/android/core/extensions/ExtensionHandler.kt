package com.cube.fusion.android.core.extensions

import com.cube.fusion.android.core.holder.FusionViewHolder
import com.cube.fusion.core.model.Model

abstract class ExtensionHandler {
	abstract fun handleExtension(holder: FusionViewHolder<*>, extension: Model?)
}