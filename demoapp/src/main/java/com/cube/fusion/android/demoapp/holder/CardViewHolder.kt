package com.cube.fusion.android.demoapp.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.holder.FusionViewHolder
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.android.demoapp.R
import com.cube.fusion.android.demoapp.databinding.CardViewBinding
import com.cube.fusion.android.demoapp.model.Card
import com.squareup.picasso.Picasso

/**
 * Example custom [FusionViewHolder] for the [Card] example custom view
 * TODO: update to use child viewholders for title, subtitle, text, and use image loading correctly.
 *
 * Created by Nikos Rapousis on 16/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class CardViewHolder(private val binding: CardViewBinding, viewConfig: AndroidFusionViewConfig) : FusionViewHolder<Card>(binding.root, viewConfig) {
	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): CardViewHolder {
			val binding = CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return CardViewHolder(binding, viewConfig)
		}
	}

	override fun populateView(model: Card) {
		//Custom properties
		binding.title.isVisible = model.title != null
		binding.title.text = model.title ?: ""

		binding.subtitle.isVisible = model.subtitle != null
		binding.subtitle.text = model.subtitle ?: ""

		binding.image.isVisible = model.image?.url != null
		model.image?.url?.let {
			Picasso.get().load(it).placeholder(R.mipmap.ic_placeholder).error(R.mipmap.ic_placeholder).into(binding.image)
		}

		//Padding
		binding.cardViewLinearLayout.setPadding(model.baseProperties.padding)

		//Base properties
		populateBaseView(
			binding.root,
			model.baseProperties,
			android.R.color.transparent,
			R.dimen.card_view_default_corner_radius
		)
	}
}