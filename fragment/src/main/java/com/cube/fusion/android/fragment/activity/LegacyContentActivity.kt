package com.cube.fusion.android.fragment.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.cube.fusion.android.activity.actions.DefaultActivityActionHandlers
import com.cube.fusion.android.core.databinding.ContentActivityViewBinding
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.helper.ViewHelper
import com.cube.fusion.android.core.images.ImageLoadingManager
import com.cube.fusion.android.fragment.FusionContentFragment
import com.cube.fusion.android.fragment.R
import com.cube.fusion.android.fragment.factory.FusionContentFragmentFactory
import com.cube.fusion.populator.legacy.LegacyDisplayPopulator

/**
 *
 * This class is responsible to display the Fusion content.
 * Activity holder of Fusion screens
 *
 * TODO remove this! Replace with an implementation that navigates between Fragments, and doesn't rely on specific populator / activity dependencies.
 *
 * Created by Nikos Rapousis on 18/November/2020.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class LegacyContentActivity : AppCompatActivity() {
	companion object {
		const val EXTRA_URL = "EXTRA_URL"
	}

	private lateinit var binding: ContentActivityViewBinding
	private val fusionConfig = AndroidFusionConfig(
		populator = LegacyDisplayPopulator(ViewHelper.viewResolvers.values),
		actionHandler = DefaultActivityActionHandlers(LegacyContentActivity::class.java),
		imageLoader = ImageLoadingManager.imageLoader
	)
	private val fragmentFactory = FusionContentFragmentFactory(fusionConfig)
	override fun onCreate(savedInstanceState: Bundle?) {
		supportFragmentManager.fragmentFactory = fragmentFactory
		super.onCreate(savedInstanceState)
		binding = ContentActivityViewBinding.inflate(layoutInflater)
		setContentView(binding.root)

		if (savedInstanceState == null) {
			val link = intent.getStringExtra(EXTRA_URL)
			addFragment(link, supportFragmentManager)
		}

		binding.toolbar.backAction.setOnClickListener {
			finish()
		}
	}

	private fun addFragment(link: String?, fragmentManager: FragmentManager) {
		fragmentManager.beginTransaction().replace(R.id.fragment, FusionContentFragment::class.java, FusionContentFragment.getBundle(link)).commit()
	}

	/**
	 * Sets the title UI of the activity to a given [String]
	 *
	 * @param title the [String] to set the title to
	 */
	fun setTitle(title: String) {
		binding.toolbar.screenTitle.text = title
	}
}