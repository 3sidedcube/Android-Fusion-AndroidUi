package com.cube.fusion.android.demoapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.cube.fusion.android.activity.FusionContentActivity
import com.cube.fusion.android.activity.actions.DefaultActivityActionHandlers
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.databinding.ContentFragmentViewBinding
import com.cube.fusion.android.core.helper.ViewHelper
import com.cube.fusion.android.demoapp.databinding.ActivityFusionImplBinding
import com.cube.fusion.android.demoapp.images.PicassoImageLoader
import com.cube.fusion.populator.legacy.LegacyDisplayPopulator

/**
 * Content Activity implementation for the demo of Fusion AndroidUi
 *
 * Created by JR Mitchell on 03/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class ContentActivityImpl : FusionContentActivity() {
	companion object {
		/**
		 * Get an intent to launch [ContentActivityImpl] with
		 *
		 * @param context the Android content to launch from
		 * @param link the URL or slug link of the Fusion screen to display
		 *
		 * @return an intent to [ContentActivityImpl] with the provided [link]
		 */
		fun getIntent(context: Context, link: String?) = getIntent(context, link, ContentActivityImpl::class.java)

		// Named for the great developer who started this project
		private const val NATIVE_ACTION_KEY = "nikos"
	}

	lateinit var binding: ActivityFusionImplBinding
	override val contentBinding: ContentFragmentViewBinding get() = binding.pageContent
	override val fusionConfig: AndroidFusionConfig = AndroidFusionConfig(
		populator = LegacyDisplayPopulator,
		actionHandler = DefaultActivityActionHandlers { view, action ->
			getIntent(view.context, action.extractClick())
		}.apply {
			registerNativeActionForLink(NATIVE_ACTION_KEY) { view, _ ->
				view.context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
			}
		},
		imageLoader = PicassoImageLoader
	)

	override fun setTitle(title: CharSequence?) {
		super.setTitle(title)
		binding.toolbar.screenTitle.text = title
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityFusionImplBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.toolbar.backAction.setOnClickListener {
			finish()
		}

		displayActivityPage(savedInstanceState)
	}
}