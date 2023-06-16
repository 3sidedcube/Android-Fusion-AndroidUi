package com.cube.fusion.android.core.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.databinding.BulletViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.core.model.views.Bullet

/**
 * [FusionViewHolder] implementation to represent the [Bullet] view
 *
 * Created by Nikos Rapousis on 16/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class BulletViewHolder(val binding: BulletViewBinding, viewConfig: AndroidFusionViewConfig) : FusionViewHolder<Bullet>(binding.root, viewConfig) {
	private val titleViewHolder = TextViewHolder(binding.title, viewConfig)
	private val subtitleViewHolder = TextViewHolder(binding.subtitle, viewConfig)
	private val delegate = BulletAccessibilityDelegateCompat(null)

	init {
		binding.cardContainer.registerChildViewHolder(titleViewHolder)
		binding.cardContainer.registerChildViewHolder(subtitleViewHolder)
		ViewCompat.setAccessibilityDelegate(binding.cardContainer, delegate)
	}

	class Factory : FusionViewHolderFactory<Bullet> {
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): BulletViewHolder {
			val binding =
				BulletViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return BulletViewHolder(binding, viewConfig)
		}
	}

	private class BulletAccessibilityDelegateCompat(var model: Bullet?) : AccessibilityDelegateCompat() {
		override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
			super.onInitializeAccessibilityNodeInfo(host, info)
			if (info != null) {
				info.contentDescription = listOfNotNull(model?.title?.content, model?.subtitle?.content).joinToString()
				val collectionInfo = model?.let {
					AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(it.order - 1, 1, 0, 0, false)
				}
				info.setCollectionItemInfo(collectionInfo)
			}
		}
	}

	override fun populateView(model: Bullet) {
		titleViewHolder.populateChildView(model.title)
		subtitleViewHolder.populateChildView(model.subtitle)

		binding.order.text = model.order.toString()
		delegate.model = model

		populateBaseView(
			binding.cardContainer,
			model.baseProperties,
			R.color.fusion_default_bullet_view_background_colour,
			R.dimen.fusion_default_bullet_view_corner_radius
		)

		//Apply padding
		binding.bulletViewContainer.setPadding(model.baseProperties.padding)
	}
}