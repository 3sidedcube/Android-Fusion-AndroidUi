package com.cube.fusion.android.core.holder

import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.databinding.TextViewBinding
import com.cube.fusion.android.core.helper.ColourHelper
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.android.core.utils.extensions.asGravity
import com.cube.fusion.android.core.utils.extensions.getDimenOrEms
import com.cube.fusion.android.core.utils.extensions.resolveAsTypeface
import com.cube.fusion.android.core.utils.extensions.toTypeface
import com.cube.fusion.core.model.views.Text

/**
 * [FusionViewHolder] implementation to represent the [Text] view
 *
 * Created by Nikos Rapousis on 10/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class TextViewHolder(private val binding: TextViewBinding) : ChildViewHolder<Text>(binding.root) {
	class Factory : FusionViewHolderFactory {
		override fun createViewHolder(parent: ViewGroup): TextViewHolder {
			val binding = TextViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return TextViewHolder(binding)
		}
	}

	companion object {
		/**
		 * Common functionality for updating a [TextView] with properties from the [Text] model
		 *
		 * @param textModel the model to update UI state from, or null
		 *  if null, should set UI to default state
		 * @param defaultTextSize the resource ID for the default text size to set if no other text size is specified on the model
		 * @param defaultTextColour the resource ID for the default text colour to set if no other text colour is specified on the model
		 * @param defaultLetterSpacing the resource ID for the default letter spacing to set if no other letter spacing is specified on the model
		 * @param defaultGravity the default gravity to set if no other gravity is specified on the model
		 */
		fun populateView(textView: TextView, textModel: Text?, @DimenRes defaultTextSize: Int, @ColorRes defaultTextColour: Int, @DimenRes defaultLetterSpacing: Int, defaultGravity: Int) {
			textView.apply {
				textModel?.font?.size?.let {
					setTextSize(TypedValue.COMPLEX_UNIT_SP, it)
				} ?: setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(defaultTextSize))

				isVisible = textModel?.content != null
				textModel?.content?.let {
					text = it
				}

				gravity = textModel?.textAlignment?.asGravity() ?: defaultGravity

				textModel?.font?.name?.let {
					val font = context.resources.getIdentifier(it, "font", context.packageName)
					typeface = ResourcesCompat.getFont(context, font)
				} ?: run {
					typeface = Typeface.DEFAULT
				}

				textModel?.font?.weight?.let {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
						typeface = it.resolveAsTypeface(this)
					}
					else {
						setTypeface(typeface, it.toTypeface())
					}
				} ?: setTypeface(typeface, Typeface.NORMAL)

				val textColour = textModel?.textColor?.let {
					try {
						ColourHelper.parseColour(it)
					}
					catch (e: IllegalArgumentException) {
						null
					}
				} ?: resources.getColor(defaultTextColour, context.theme)
				setTextColor(textColour)

				maxLines = textModel?.numberOfLines ?: Int.MAX_VALUE

				letterSpacing = textModel?.letterSpacing ?: resources.getDimenOrEms(defaultLetterSpacing)

				//Apply padding
				setPadding(textModel?.baseProperties?.padding)
			}
		}
	}

	/**
	 * Common functionality for updating the [TextViewBinding] with properties of the [Text] model
	 *
	 * @param textModel the model to update UI state from, or null
	 *  if null, should set UI to default state
	 * @param defaultBgColour the default background colour to set if no other background colour is specified on the model
	 * @param defaultCornerRadius the resource ID for the default corner radius to set if no other corner radius is specified on the model
	 * @param defaultTextSize the resource ID for the default text size to set if no other text size is specified on the model
	 * @param defaultTextColour the resource ID for the default text colour to set if no other text colour is specified on the model
	 * @param defaultLetterSpacing the resource ID for the default letter spacing to set if no other letter spacing is specified on the model
	 * @param defaultGravity the default gravity to set if no other gravity is specified on the model
	 */
	fun populateView(textModel: Text?, @ColorRes defaultBgColour: Int, @DimenRes defaultCornerRadius: Int, @DimenRes defaultTextSize : Int, @ColorRes defaultTextColour : Int, @DimenRes defaultLetterSpacing : Int, defaultGravity: Int) {
		populateView(
			textView = binding.text,
			textModel = textModel,
			defaultTextSize = defaultTextSize,
			defaultTextColour = defaultTextColour,
			defaultLetterSpacing = defaultLetterSpacing,
			defaultGravity = defaultGravity
		)

		populateBaseView(
			cardView = binding.textContainer,
			baseProperties = textModel?.baseProperties,
			defaultBackgroundColourResId = defaultBgColour,
			defaultCornerRadiusResId = defaultCornerRadius
		)
	}

	override fun populateView(model: Text) = populateView(
		textModel = model,
		defaultBgColour = R.color.fusion_default_text_view_background_colour,
		defaultCornerRadius = R.dimen.fusion_default_text_view_corner_radius,
		defaultTextSize = R.dimen.fusion_default_text_view_text_size,
		defaultTextColour = R.color.fusion_default_text_view_text_colour,
		defaultLetterSpacing = R.dimen.fusion_default_text_view_letter_spacing,
		defaultGravity = Gravity.START
	)

	override fun populateChildView(model: Text?) = populateView(
		textModel = model,
		defaultBgColour = android.R.color.transparent,
		defaultCornerRadius = R.dimen.fusion_default_text_view_corner_radius,
		defaultTextSize = R.dimen.fusion_default_text_view_text_size,
		defaultTextColour = R.color.fusion_default_text_view_text_colour,
		defaultLetterSpacing = R.dimen.fusion_default_text_view_letter_spacing,
		defaultGravity = Gravity.START
	)
}