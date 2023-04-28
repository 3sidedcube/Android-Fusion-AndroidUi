package com.cube.fusion.android.core.holder

import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.Px
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.helper.ColourHelper
import com.cube.fusion.android.core.helper.ShadowHelper
import com.cube.fusion.android.core.preprocessor.FusionBasePropertiesPreprocessor
import com.cube.fusion.android.core.utils.MarginUtils.orDefault
import com.cube.fusion.android.core.utils.MarginUtils.setMargin
import com.cube.fusion.android.core.utils.extensions.CollectionExtensions.preprocess
import com.cube.fusion.android.core.utils.extensions.dpToPx
import com.cube.fusion.android.core.utils.shadow.ShadowBasicInset
import com.cube.fusion.android.core.utils.shadow.ShadowRectSpec
import com.cube.fusion.core.model.Margin
import com.cube.fusion.core.model.Model
import com.cube.fusion.core.model.views.BaseViewProperties
import com.google.android.material.card.MaterialCardView
import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Base class that any ViewHolder for a fusion view should inherit (either directly or indirectly)
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param viewConfig A reference to the [AndroidFusionViewConfig] instance used for configuring views
 */
abstract class FusionViewHolder<T : Model>(itemView: View, protected val viewConfig: AndroidFusionViewConfig) : RecyclerView.ViewHolder(itemView),
	ShadowViewHolder {
	/**
	 * Called when the view needs to be populated
	 * Note: unchecked cast from Model to T; should never cause issues provided all registered Views are named and registered correctly.
	 *
	 * @param model The model to populate the view with
	 */
	@Suppress("UNCHECKED_CAST")
	fun populateViewFromModel(model: Model) = populateView(model as T)

	/**
	 * Updates the UI of the view based on a [model] of type [T]
	 */
	protected abstract fun populateView(model: T)

	override var shadowRectSpec: List<ShadowRectSpec>? = null
		protected set

	/**
	 * Convenience method to populate a base [MaterialCardView] instance with general properties of a [Model]
	 * May be called in the [populateView] method to ensure common behaviour with these properties -
	 *  ensure you either call this method or implement your own handling of these properties
	 * Handles the following properties:
	 *  - backgroundColor
	 *  - cornerRadius
	 *  - margin
	 *  - border
	 *  - shadow
	 * Does not handle padding, as this is strongly coupled with the particular view implementation (e.g the content it holds)
	 * Padding should therefore be handled separately in the [populateView] method
	 *
	 * @param cardView the base [MaterialCardView] that the view is built upon
	 * @param baseProperties the base view properties to populate the base UI with, or null - if null, uses all defaults
	 * @param defaultBackgroundColourResId the resource ID for the default background colour of this view
	 * @param defaultCornerRadiusResId the resource ID for the default corner radius of this view
	 */
	protected fun populateBaseView(
		cardView: MaterialCardView,
		baseProperties: BaseViewProperties?,
		modelPreprocessors: List<FusionBasePropertiesPreprocessor>,
		@ColorRes defaultBackgroundColourResId: Int,
		@DimenRes defaultCornerRadiusResId: Int
	) {
		val theme = cardView.context.theme
		val resources = cardView.resources

		val baseProperties = baseProperties?.let { modelPreprocessors.preprocess(it) }

		// Background colour
		val bgColour = baseProperties?.backgroundColor?.let {
			try {
				ColourHelper.parseColour(it)
			}
			catch (e: IllegalArgumentException) {
				null
			}
		} ?: resources.getColor(defaultBackgroundColourResId, theme)
		cardView.setCardBackgroundColor(bgColour)

		// Corner radius
		val cornerRadius = baseProperties?.cornerRadius?.let {
			resources.dpToPx(it)
		} ?: resources.getDimension(defaultCornerRadiusResId)
		cardView.radius = cornerRadius

		// Margin
		val marginOrDefault = baseProperties?.margin.orDefault(resources)
		cardView.updateLayoutParams {
			(this as? ViewGroup.MarginLayoutParams)?.setMargin(marginOrDefault, resources)
		}

		// Border
		@Px val borderWidth: Int
		@ColorInt val borderColour: Int
		val border = baseProperties?.border
		if (border != null) {
			borderWidth = ceil(resources.dpToPx(border.strokeWidth)).roundToInt()
			borderColour = try {
				ColourHelper.parseColour(border.color)
			}
			catch (e: IllegalArgumentException) {
				resources.getColor(R.color.fusion_default_border_colour, theme)
			}
		}
		else {
			borderWidth = resources.getDimensionPixelSize(R.dimen.fusion_default_border_width)
			borderColour = resources.getColor(R.color.fusion_default_border_colour, theme)
		}
		cardView.strokeWidth = borderWidth
		cardView.strokeColor = borderColour

		// Shadow
		shadowRectSpec = baseProperties?.shadow?.let {
			val shadowColour = try {
				ColourHelper.parseColour(it.color)
			}
			catch (e: Throwable) {
				resources.getColor(R.color.fusion_default_shadow_colour, theme)
			}

			val cardBaseMargin = if (cardView == itemView) {
				Margin.zeroMargin()
			}
			else {
				marginOrDefault
			}
			val shadowInset = ShadowBasicInset(cardBaseMargin, it, resources)
			ShadowHelper.generateShadowV3(
				shadowColour,
				it.alpha.toDouble(),
				it.blur.toDouble(),
				cornerRadius,
				shadowInset
			)
		}
	}
}