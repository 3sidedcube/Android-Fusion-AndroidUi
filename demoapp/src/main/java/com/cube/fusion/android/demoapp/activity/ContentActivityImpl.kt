package com.cube.fusion.android.demoapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.lifecycleScope
import com.cube.fusion.android.activity.FusionContentActivity
import com.cube.fusion.android.activity.actions.DefaultActivityActionHandlers
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.databinding.ContentFragmentViewBinding
import com.cube.fusion.android.core.databinding.ToolbarViewBinding
import com.cube.fusion.android.core.helper.ViewHelper
import com.cube.fusion.android.core.resolver.DefaultViewResolver
import com.cube.fusion.android.demoapp.databinding.ActivityFusionImplBinding
import com.cube.fusion.android.demoapp.holder.CardViewHolder
import com.cube.fusion.android.demoapp.images.PicassoImageLoader
import com.cube.fusion.android.demoapp.model.Card
import com.cube.fusion.populator.coroutinesourcecache.source.AssetsPageSource
import com.cube.fusion.populator.retrofit.RetrofitDisplayPopulator

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
		 * @param baseUrl the base URL of the Fusion API
		 * @param link the URL or slug link of the Fusion screen to display
		 *
		 * @return an intent to [ContentActivityImpl] with the provided [link]
		 */
		fun getIntent(context: Context, baseUrl: String?, link: String?) = getIntent(context, link, ContentActivityImpl::class.java).apply {
			putExtra(BASE_URL_EXTRA_KEY, baseUrl)
		}

		private const val BASE_URL_EXTRA_KEY = "BASE_URL_EXTRA_KEY"

		// Named for the great developer who started this project
		private const val NATIVE_ACTION_KEY = "nikos"
	}

	lateinit var binding: ActivityFusionImplBinding
	override lateinit var contentBinding: ContentFragmentViewBinding
	private lateinit var toolbarBinding: ToolbarViewBinding
	override val fusionConfig: AndroidFusionConfig get() {
		val baseUrl = intent.getStringExtra(BASE_URL_EXTRA_KEY) ?: ""
		val resolvers = ViewHelper.getDefaultViewResolvers().apply {
			put("Card", DefaultViewResolver(Card::class.java, CardViewHolder.Factory::class.java))
		}
		val localSource = AssetsPageSource(this, { it }, resolvers.values)
		return AndroidFusionConfig(
			populator = RetrofitDisplayPopulator(this::lifecycleScope, baseUrl, resolvers.values, localSource),
			actionHandler = DefaultActivityActionHandlers { view, action ->
				getIntent(view.context, baseUrl, action.extractClick())
			}.apply {
				registerNativeActionForLink(NATIVE_ACTION_KEY) { view, _ ->
					view.context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
				}
			},
			imageLoader = PicassoImageLoader,
			resolvers = resolvers
		)
	}

	override fun setTitle(title: CharSequence?) {
		super.setTitle(title)
		toolbarBinding.screenTitle.text = title
	}


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityFusionImplBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setUpSubBindings()

		toolbarBinding.backAction.setOnClickListener {
			finish()
		}

		displayActivityPage(savedInstanceState)
	}

	@Suppress("TYPE_MISMATCH")
	/**
	 * Set up the sub-bindings needed for interacting with the whole view structure
	 * NOTE: Android Studio incorrectly types binding properties due to being part of the repo with the view in.
	 * The @Suppress annotation would not be needed on an actual project.
	 */
	private fun setUpSubBindings() {
		contentBinding = ContentFragmentViewBinding.bind(binding.pageContent)
		toolbarBinding = ToolbarViewBinding.bind(binding.toolbar)
	}
}