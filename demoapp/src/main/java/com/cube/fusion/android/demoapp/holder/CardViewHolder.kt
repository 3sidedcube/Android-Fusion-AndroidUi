package com.cube.fusion.android.demoapp.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.cube.fusion.android.core.databinding.TextViewBinding
import com.cube.fusion.android.core.holder.ChildViewHolder
import com.cube.fusion.android.core.holder.FusionViewHolder
import com.cube.fusion.android.core.holder.TextViewHolder
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
class CardViewHolder(private val binding: CardViewBinding) : FusionViewHolder<Card>(binding.root) {
	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup): CardViewHolder {
			val binding = CardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return CardViewHolder(binding)
		}
	}

	private val titleViewHolder = TextViewHolder(TextViewBinding.bind(binding.title as View))
	private val subtitleViewHolder = TextViewHolder(TextViewBinding.bind(binding.subtitle as View))

	init {
		binding.root.registerChildViewHolder(titleViewHolder)
		binding.root.registerChildViewHolder(subtitleViewHolder)
	}

	override fun populateView(model: Card) {
		//Custom properties
		titleViewHolder.populateChildView(model.title)
		subtitleViewHolder.populateChildView(model.subtitle)

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