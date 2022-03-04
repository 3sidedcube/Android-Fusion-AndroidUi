package com.cube.fusion.android.demoapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import com.cube.fusion.android.activity.FusionContentActivity
import com.cube.fusion.android.activity.actions.DefaultActivityActionHandlers
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.databinding.ContentFragmentViewBinding
import com.cube.fusion.android.core.databinding.ToolbarViewBinding
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
	@Suppress("TYPE_MISMATCH") // Note: Android Studio incorrectly types this due to being part of the repo with the view in. This annotation would not be needed on an actual project.
	override val contentBinding: ContentFragmentViewBinding by lazy { ContentFragmentViewBinding.bind(binding.pageContent) }
	@Suppress("TYPE_MISMATCH") // Note: Android Studio incorrectly types this due to being part of the repo with the view in. This annotation would not be needed on an actual project.
	private val toolbarBinding: ToolbarViewBinding by lazy { ToolbarViewBinding.bind(binding.toolbar) }
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
		toolbarBinding.screenTitle.text = title
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityFusionImplBinding.inflate(layoutInflater)
		setContentView(binding.root)

		toolbarBinding.backAction.setOnClickListener {
			finish()
		}

		displayActivityPage(savedInstanceState)
	}
}