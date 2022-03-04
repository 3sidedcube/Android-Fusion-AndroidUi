package com.cube.fusion.android.core.images

import android.widget.ImageView
import com.cube.fusion.core.model.views.Image

/**
 * Interface for loading images into Android views
 *
 * Created by JR Mitchell on 23/December/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
interface FusionAndroidImageLoader {
	/**
	 * Load an image specified by its [Image] into an [ImageView]
	 *
	 * @param image the data to load
	 * @param view the view to load the data into
	 */
	fun loadImage(image: Image?, view: ImageView)
}