package com.cube.fusion.android.demoapp.images

import android.widget.ImageView
import coil.load
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
import com.cube.fusion.android.demoapp.R
import com.cube.fusion.core.model.views.Image

/**
 * [FusionAndroidImageLoader] implementation based off of the Coil image loading library
 * Other image loading libs for Android are available
 *
 * Created by JR Mitchell on 12/June/2023.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object CoilImageLoader: FusionAndroidImageLoader {
	override fun loadImage(image: Image?, view: ImageView) {
		view.load(image?.src?.url) {
			placeholder(R.mipmap.ic_placeholder)
			error(R.mipmap.ic_placeholder)
		}
	}
}
