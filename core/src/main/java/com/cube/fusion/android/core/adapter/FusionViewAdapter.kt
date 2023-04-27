package com.cube.fusion.android.core.adapter

import android.os.Parcelable
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.config.AndroidFusionViewConfig
import com.cube.fusion.android.core.holder.ActionHandlingViewHolder
import com.cube.fusion.android.core.holder.FusionViewHolder
import com.cube.fusion.android.core.holder.ImageLoadingViewHolder
import com.cube.fusion.android.core.holder.factory.FusionViewHolderFactory
import com.cube.fusion.android.core.resolver.AndroidViewResolver
import com.cube.fusion.core.model.Model
import com.cube.fusion.core.model.views.Screen
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * The base adapter used for displaying Fusion views in a list.
 * Using an adapter to do such a task has the benefit of view recycling which makes the content smooth to scroll.
 *
 * This adapter only supports [Model] classes which have a defined [FusionViewHolder] counter-class.
 *
 * **Problems**
 * Problems can arise with this method of rendering content, specifically with render efficiency where
 * the views are not being recycled because there is only 1 of its view type in the list. This is the
 * equivalent of having all of the views inflated into a [android.widget.ScrollView]. The smoothness
 * of the scrolling (depending on how much content there is) diminishes with the amount of unique content
 * that the list is rendering.
 *
 * @param viewConfig The configuration required for ViewHolders
 * @param resolvers The view resolvers to use to construct the UI
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class FusionViewAdapter(
	private val viewConfig: AndroidFusionViewConfig,
	private val resolvers: Map<String, AndroidViewResolver>
) : RecyclerView.Adapter<FusionViewHolder<*>>() {

	constructor(androidConfig: AndroidFusionConfig): this(androidConfig.viewConfig, androidConfig.resolvers)

	/**
	 * Temporary store for adapter state
	 * {@hide}
	 */
	@Parcelize
	class AdapterState(val items: @RawValue ArrayList<Model>, val itemsTypes: ArrayList<Class<out FusionViewHolderFactory>?>) : Parcelable

	/**
	 * The list of models of the views we are rendering in the list. This is a 1 dimensional representation
	 * of a multi-dimensional 'sub listed' array set which is outlined by the json. When setting the items
	 * in this list, the models have to be traversed in order to build the 1 dimensional list for the
	 * adapter to work correctly.
	 */
	private var items = ArrayList<Model>()

	/**
	 * The different unique item types. This is used to tell the adapter how many unique views we're
	 * going to be rendering so it knows what and when to recycle. The list is just for index based
	 * convenience, the object type in the list is a reference to the view holder class we will use
	 * to render said view.
	 */
	private var itemTypes = ArrayList<Class<out FusionViewHolderFactory>?>()

	constructor(androidConfig: AndroidFusionConfig, items: Collection<Model>?) : this(androidConfig) {
		setItems(items)
	}

	fun saveState(): AdapterState {
		return AdapterState(items, itemTypes)
	}

	fun restoreState(state: AdapterState?) {
		if (state != null) {
			items = ArrayList(state.items)
			itemTypes = ArrayList(state.itemsTypes)
			notifyDataSetChanged()
		}
	}

	/**
	 * Sets the items in the collection. Filters out any model that does not have a defined [ViewHolder]
	 *
	 * @param items The new items to set. Can be null to clear the list.
	 */
	fun setItems(items: Collection<Model>?) {
		if (items != null) {
			this.items = ArrayList(items.size)
			itemTypes = ArrayList()
			for (item in items) {
				addItem(item)
			}
		}
		else {
			this.items = ArrayList()
			itemTypes = ArrayList()
		}
		notifyDataSetChanged()
	}

	/**
	 * Adds an item to the list, only if a holder class is found
	 *
	 * @param item The model to add to the list
	 */
	private fun addItem(item: Model) {
		addItem(items.size, item)
	}

	/**
	 * Adds an item to the list, only if a holder class is found
	 *
	 * @param index The index to where to add the item
	 * @param item The model to add to the list
	 */
	private fun addItem(index: Int, item: Model) {
		if (item is Screen) {
			item.children.removeAll(setOf<Any?>(null))
			if (item.children.size > 0) {
				// Add children
				for (subItem in item.children) {
					addItem(subItem)
				}
			}
		}
		else {
			val holderClass = resolvers[item.`class`]?.resolveViewHolder()
			if (holderClass != null) {
				items.add(index, item)
			}
			if (!itemTypes.contains(holderClass)) {
				itemTypes.add(holderClass)
			}
		}
	}

	private fun getItem(position: Int): Model {
		return items[position]
	}

	override fun getItemId(position: Int): Long {
		return getItem(position).hashCode().toLong()
	}

	override fun getItemCount(): Int {
		return items.size
	}

	override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FusionViewHolder<*> {
		val holder: FusionViewHolder<*>
		try {
			val holderFactory = itemTypes[viewType]!!.getConstructor().newInstance()
			holder = holderFactory.createViewHolder(viewGroup)!!
		}
		catch (e: Exception) {
			throw IllegalStateException("Could not instantiate a new holder" + e.message, e)
		}
		return holder
	}

	override fun onBindViewHolder(viewHolder: FusionViewHolder<*>, position: Int) {
		(viewHolder as? ActionHandlingViewHolder)?.actionHandler = viewConfig.actionHandler
		(viewHolder as? ImageLoadingViewHolder)?.imageLoader = viewConfig.imageLoader
		try {
			viewHolder.populateViewFromModel(getItem(position))
		}
		catch (e: Exception) {
			e.printStackTrace()
		}
	}

	override fun getItemViewType(position: Int): Int {
		val view = items[position]
		return itemTypes.indexOf((resolvers[view.`class`] ?: error("")).resolveViewHolder())
	}
}