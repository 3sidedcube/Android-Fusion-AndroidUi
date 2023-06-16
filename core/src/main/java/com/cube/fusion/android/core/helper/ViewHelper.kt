package com.cube.fusion.android.core.helper

import com.cube.fusion.android.core.resolver.AndroidViewResolver
import com.cube.fusion.android.core.resolver.DefaultViewResolver
import com.cube.fusion.android.core.holder.*
import com.cube.fusion.core.model.views.*

/**
 * Singleton class with the list of all supported view types,
 * 	their model classes,
 * 	and their corresponding view holder classes
 * This list should not be modified or overridden
 *
 * Created by Nikos Rapousis on 12/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object ViewHelper {
	fun getDefaultViewResolvers(): MutableMap<String, AndroidViewResolver> = hashMapOf(
		"Screen" to DefaultViewResolver(Screen::class.java, null),
		"Text" to DefaultViewResolver(Text::class.java, TextViewHolder.Factory()),
		"Image" to DefaultViewResolver(Image::class.java, ImageViewHolder.Factory()),
		"Divider" to DefaultViewResolver(Divider::class.java, DividerViewHolder.Factory()),
		"ListItem" to DefaultViewResolver(ListItem::class.java, ListItemViewHolder.Factory()),
		"Button" to DefaultViewResolver(Button::class.java, ButtonViewHolder.Factory()),
		"BulletGroup" to DefaultViewResolver(BulletGroup::class.java, BulletGroupViewHolder.Factory()),
		"Bullet" to DefaultViewResolver(Bullet::class.java, BulletViewHolder.Factory())
	)
}