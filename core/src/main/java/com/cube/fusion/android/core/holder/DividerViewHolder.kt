package com.cube.fusion.android.core.holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.databinding.DividerViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.core.model.views.BaseViewProperties
import com.cube.fusion.core.model.views.Divider
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * [FusionViewHolder] implementation to represent the [Divider] view
 *
 * Created by Nikos Rapousis on 11/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class DividerViewHolder(private val binding: DividerViewBinding, viewConfig: AndroidFusionViewConfig) : FusionViewHolder<Divider>(binding.root, viewConfig) {
	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): DividerViewHolder {
			val binding = DividerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return DividerViewHolder(binding, viewConfig)
		}
	}

	/**
	 * Note: horizontal & vertical padding don't really do anything here, as there is nothing to pad around
	 * Therefore, the interpretation is that horizontal padding does nothing, and vertical padding increases the height of the divider
	 * This is equivalent to if there was a zero-size view in the middle and the specified amount of padding -
	 * 	since width is MATCH_PARENT, horizontal padding would do nothing, but vertical padding would increase container size
	 */
	override fun populateView(model: Divider) {
		val height = model.strokeWidth?.let {
			itemView.resources.dpToPx(it)
		} ?: itemView.resources.getDimension(R.dimen.fusion_default_divider_height)
		val verticalPadding = model.baseProperties.padding?.let {
			itemView.resources.dpToPx(it.top + it.bottom)
		} ?: itemView.resources.let { it.getDimension(R.dimen.fusion_default_padding_top) + it.getDimension(R.dimen.fusion_default_padding_bottom) }
		binding.divider.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ceil(height + verticalPadding).roundToInt())

		populateBaseView(
			cardView = binding.divider,
			unprocessedProperties = model.baseProperties,
			preprocessors = viewConfig.preprocessors.filterIsInstance<BaseViewProperties.Preprocessor>(),
			defaultBackgroundColourResId = R.color.fusion_default_divider_view_background_colour,
			defaultCornerRadiusResId = R.dimen.fusion_default_divider_view_corner_radius
		)
	}
}