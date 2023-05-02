package com.cube.fusion.android.core.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.databinding.ListItemViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.core.model.views.BaseViewProperties
import com.cube.fusion.core.model.views.ListItem

/**
 * [FusionViewHolder] implementation to represent the [ListItem] view
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class ListItemViewHolder(private val binding: ListItemViewBinding, viewConfig: AndroidFusionViewConfig) : FusionViewHolder<ListItem>(binding.root, viewConfig) {
	private val titleViewHolder = TextViewHolder(binding.title, viewConfig)
	private val subtitleViewHolder = TextViewHolder(binding.subtitle, viewConfig)
	private val imageViewHolder = ImageViewHolder(binding.listItemImage, viewConfig)

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
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): ListItemViewHolder {
			val binding = ListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return ListItemViewHolder(binding, viewConfig)
		}
	}

	override fun populateView(unprocessedModel: ListItem) {
		imageViewHolder.populateChildView(unprocessedModel.image)
		titleViewHolder.populateChildView( unprocessedModel.title)
		subtitleViewHolder.populateChildView(unprocessedModel.subtitle)

		populateBaseView(
			cardView = binding.cardContainer,
			unprocessedProperties = unprocessedModel.baseProperties,
			preprocessors = viewConfig.preprocessors.filterIsInstance<BaseViewProperties.Preprocessor>(),
			defaultBackgroundColourResId = R.color.fusion_default_list_item_view_background_colour,
			defaultCornerRadiusResId = R.dimen.fusion_default_list_item_view_corner_radius
		)

		//Apply padding
		binding.listItemContainer.setPadding(unprocessedModel.baseProperties.padding)

		binding.cardContainer.setOnClickListener {
			viewConfig.actionHandler.handleAction(it, unprocessedModel.action)
		}
		binding.cardContainer.isClickable = unprocessedModel.action != null
		binding.cardContainer.isEnabled = unprocessedModel.action != null
		binding.chevron.isVisible = unprocessedModel.action != null
	}
}