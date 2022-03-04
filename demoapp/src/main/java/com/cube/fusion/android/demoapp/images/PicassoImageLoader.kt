package com.cube.fusion.android.demoapp.images

import android.widget.ImageView
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
import com.cube.fusion.android.demoapp.R
import com.cube.fusion.core.model.views.Image
import com.squareup.picasso.Picasso

/**
 * [FusionAndroidImageLoader] implementation based off of the [Picasso] image loading library
 * Other image loading libs for Android are available
 *
 * Created by JR Mitchell on 23/December/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object PicassoImageLoader : FusionAndroidImageLoader {
	override fun loadImage(image: Image?, view: ImageView) {
		Picasso.get().load(image?.src?.url).placeholder(R.mipmap.ic_placeholder).error(R.mipmap.ic_placeholder).into(view)
	}
}