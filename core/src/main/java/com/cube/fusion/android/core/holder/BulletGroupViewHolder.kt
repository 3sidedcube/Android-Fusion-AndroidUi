package com.cube.fusion.android.core.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.databinding.BulletGroupViewBinding
import com.cube.fusion.android.core.databinding.BulletViewBinding
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.utils.PaddingUtils.setPadding
import com.cube.fusion.core.model.views.BulletGroup

/**
 * [FusionViewHolder] implementation to represent the [BulletGroup] view
 *
 * Created by Nikos Rapousis on 16/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class BulletGroupViewHolder(val binding: BulletGroupViewBinding, viewConfig: AndroidFusionViewConfig) :
	FusionViewHolder<BulletGroup>(binding.root, viewConfig) {
	class Factory : FusionViewHolderFactory<BulletGroup> {
		override fun createViewHolder(parent: ViewGroup, viewConfig: AndroidFusionViewConfig): BulletGroupViewHolder {
			val binding = BulletGroupViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
			return BulletGroupViewHolder(binding, viewConfig)
		}
	}

	private val delegate = BulletGroupAccessibilityDelegateCompat(0)

	init {
		ViewCompat.setAccessibilityDelegate(binding.root, delegate)
	}

	private class BulletGroupAccessibilityDelegateCompat(var count: Int) : AccessibilityDelegateCompat() {
		override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
			super.onInitializeAccessibilityNodeInfo(host, info)
			info?.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(count, 1, false))
		}
	}

	override fun populateView(model: BulletGroup) {
		val context = itemView.context
		binding.root.unregisterAllChildViewHolders()
		binding.bulletGroupContainer.removeAllViews()

		delegate.count = model.children.size

		for (index in 0 until model.children.size) {
			val annotateView = BulletViewHolder(
				BulletViewBinding.inflate(
					LayoutInflater.from(context),
					null,
					false
				),
				viewConfig
			)
			val annotation = model.children[index]
			annotation.order = index + 1
			annotateView.populateViewFromModel(annotation)
			binding.bulletGroupContainer.addView(annotateView.itemView)
			binding.root.registerChildViewHolder(annotateView)
		}

		populateBaseView(
			binding.root,
			model.baseProperties,
			R.color.fusion_default_bullet_group_view_background_colour,
			R.dimen.fusion_default_bullet_group_view_corner_radius
		)

		//Apply padding
		binding.bulletGroupContainer.setPadding(model.baseProperties.padding)
	}
}