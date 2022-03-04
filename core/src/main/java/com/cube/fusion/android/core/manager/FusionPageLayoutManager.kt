package com.cube.fusion.android.core.manager

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * [LinearLayoutManager] implementation for a page of Fusion content
 *
 * Created by JR Mitchell on 01/December/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class FusionPageLayoutManager : LinearLayoutManager {
	constructor(context: Context?) : super(context)
	constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)
	constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

	/**
	 * Overriding [onInitializeAccessibilityNodeInfo] to ensure that collection info (e.g "item 1 of 5") is not spoken
	 */
	override fun onInitializeAccessibilityNodeInfo(recycler: RecyclerView.Recycler, state: RecyclerView.State, info: AccessibilityNodeInfoCompat) {
		super.onInitializeAccessibilityNodeInfo(recycler, state, info)
		val prevCollectionInfo = info.collectionInfo
		val collectionInfo = AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(0, 0, prevCollectionInfo.isHierarchical, prevCollectionInfo.selectionMode)
		info.setCollectionInfo(collectionInfo)
	}
}