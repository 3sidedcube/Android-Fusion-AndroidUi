package com.cube.fusion.android.core.holder

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.databinding.TextViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.core.model.views.BaseViewProperties
import com.cube.fusion.core.model.views.Button

/**
 * [FusionViewHolder] implementation to represent the [Button] view
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class ButtonViewHolder(val binding: TextViewBinding, viewConfig: AndroidFusionViewConfig) : ChildViewHolder<Button>(binding.root, viewConfig) {
	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): ButtonViewHolder {
			val binding = TextViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return ButtonViewHolder(binding, viewConfig)
		}
	}

	override fun populateView(unprocessedModel: Button) {
		TextViewHolder.populateView(
			textView = binding.text,
			textModel = unprocessedModel.baseProperties,
			defaultTextSize = R.dimen.fusion_default_button_view_text_size,
			defaultTextColour = R.color.fusion_default_button_view_text_colour,
			defaultLetterSpacing = R.dimen.fusion_default_button_view_letter_spacing,
			defaultGravity = Gravity.CENTER
		)
		populateBaseView(
			cardView = binding.textContainer,
			unprocessedProperties = unprocessedModel.baseProperties.baseProperties,
			preprocessors = viewConfig.preprocessors.filterIsInstance<BaseViewProperties.Preprocessor>(),
			defaultBackgroundColourResId = R.color.fusion_default_button_view_background_colour,
			defaultCornerRadiusResId = R.dimen.fusion_default_button_view_corner_radius
		)
		populateClickHandler(unprocessedModel)
	}

	override fun populateChildView(unprocessedModel: Button?) {
		TextViewHolder.populateView(
			textView = binding.text,
			textModel = unprocessedModel?.baseProperties,
			defaultTextSize = R.dimen.fusion_default_button_view_text_size,
			defaultTextColour = R.color.fusion_default_button_view_text_colour,
			defaultLetterSpacing = R.dimen.fusion_default_button_view_letter_spacing,
			defaultGravity = Gravity.CENTER
		)
		populateBaseView(
			cardView = binding.textContainer,
			unprocessedProperties = unprocessedModel?.baseProperties?.baseProperties,
			preprocessors = viewConfig.preprocessors.filterIsInstance<BaseViewProperties.Preprocessor>(),
			defaultBackgroundColourResId = android.R.color.transparent,
			defaultCornerRadiusResId = R.dimen.fusion_default_button_view_corner_radius
		)
		populateClickHandler(unprocessedModel)
	}

	private fun populateClickHandler(model: Button?) {
		binding.textContainer.setOnClickListener { v ->
			viewConfig.actionHandler.handleAction(v, model?.action)
		}
		binding.textContainer.isClickable = model?.action != null
	}
}
