package com.cube.fusion.android.core.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.actions.FusionAndroidActionHandler
import com.cube.fusion.android.core.databinding.ListItemViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.images.FusionAndroidImageLoader
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.core.model.views.ListItem

/**
 * [FusionViewHolder] implementation to represent the [ListItem] view
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class ListItemViewHolder(private val binding: ListItemViewBinding) : FusionViewHolder<ListItem>(binding.root), ActionHandlingViewHolder, ImageLoadingViewHolder {
	override var actionHandler: FusionAndroidActionHandler? = null
	override var imageLoader: FusionAndroidImageLoader? = null
		set(value) {
			field = value
			imageViewHolder.imageLoader = value
		}
	private val titleViewHolder = TextViewHolder(binding.title)
	private val subtitleViewHolder = TextViewHolder(binding.subtitle)
	private val imageViewHolder = ImageViewHolder(binding.listItemImage).apply {
		imageLoader = this@ListItemViewHolder.imageLoader
	}

	init {
		binding.listItemImage.image.apply {
			maxHeight = resources.getDimensionPixelSize(R.dimen.fusion_default_list_item_view_image_max_height)
			maxWidth = resources.getDimensionPixelSize(R.dimen.fusion_default_list_item_view_image_max_width)
		}
		binding.cardContainer.registerChildViewHolder(titleViewHolder)
		binding.cardContainer.registerChildViewHolder(subtitleViewHolder)
		binding.cardContainer.registerChildViewHolder(imageViewHolder)
	}

	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup): ListItemViewHolder {
			val binding = ListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return ListItemViewHolder(binding)
		}
	}

	override fun populateView(model: ListItem) {
		imageViewHolder.populateChildView(model.image)
		titleViewHolder.populateChildView(model.title)
		subtitleViewHolder.populateChildView(model.subtitle)

		populateBaseView(
			binding.cardContainer,
			model.baseProperties,
			R.color.fusion_default_list_item_view_background_colour,
			R.dimen.fusion_default_list_item_view_corner_radius
		)

		//Apply padding
		binding.listItemContainer.setPadding(model.baseProperties.padding)

		binding.cardContainer.setOnClickListener {
			actionHandler?.handleAction(it, model.action)
		}
		binding.cardContainer.isClickable = model.action != null
		binding.cardContainer.isEnabled = model.action != null
		binding.chevron.isVisible = model.action != null
	}
}