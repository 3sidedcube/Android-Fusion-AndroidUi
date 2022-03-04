package com.cube.fusion.android.core.holder

import com.cube.fusion.android.core.images.FusionAndroidImageLoader

/**
 * Interface for a ViewHolder which uses image loading functionality (including ViewHolders whose child views use image loading)
 *
 * Created by JR Mitchell on 23/December/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @property imageLoader the loader to use to load images into this ViewHolder
 */
interface ImageLoadingViewHolder {
	var imageLoader : FusionAndroidImageLoader?
}