package com.cube.fusion.android.core.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.view.isVisible
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.databinding.ImageViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.core.model.views.Image

/**
 * [FusionViewHolder] implementation to represent the [Image] view
 *
 * Created by Nikos Rapousis on 11/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class ImageViewHolder(private val binding: ImageViewBinding, viewConfig: AndroidFusionViewConfig) : ChildViewHolder<Image>(binding.root, viewConfig) {
	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): ImageViewHolder {
			val binding = ImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return ImageViewHolder(binding, viewConfig)
		}
	}

	/**
	 * Common functionality for both overridden [populateView] and [populateChildView]
	 *
	 * @param image the model to update UI state from, or null
	 *  if null, should set UI to default state
	 * @param defaultBgColour the default background colour to set if no other background colour is specified on the model
	 */
	private fun populateView(image: Image?, @ColorRes defaultBgColour: Int) {
		binding.image.apply {
			isVisible = image?.src?.url != null
			viewConfig.imageLoader?.loadImage(image, this)

			//Apply padding
			setPadding(image?.baseProperties?.padding)

			//Apply corner radius to inner image as well
			val cornerRadius = image?.baseProperties?.cornerRadius?.let {
				resources.dpToPx(it)
			} ?: resources.getDimension(R.dimen.fusion_default_image_view_corner_radius)
			val appearanceModel = shapeAppearanceModel.withCornerSize(cornerRadius)
			shapeAppearanceModel = appearanceModel
		}

		populateBaseView(
			binding.imageContainer,
			image?.baseProperties,
			defaultBgColour,
			R.dimen.fusion_default_image_view_corner_radius
		)
	}

	override fun populateView(model: Image) = populateView(model, R.color.fusion_default_image_view_background_colour)
	override fun populateChildView(model: Image?) = populateView(model, android.R.color.transparent)
}