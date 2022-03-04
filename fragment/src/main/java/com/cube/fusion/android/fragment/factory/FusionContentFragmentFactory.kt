package com.cube.fusion.android.fragment.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.fragment.FusionContentFragment

/**
 * [FragmentFactory] implementation for allowing injection of dependencies into [FusionContentFragment] instances
 *
 * @param fusionConfig the configuration to use in new [FusionContentFragment]s for displaying Fusion content
 *
 * Created by JR Mitchell on 01/03/2022
 * Copyright ® 3SidedCube. All rights reserved.
 */
class FusionContentFragmentFactory(private val fusionConfig: AndroidFusionConfig) : FragmentFactory() {
	override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
		if(className == FusionContentFragment::class.java.name) {
			return FusionContentFragment(fusionConfig)
		}
		return super.instantiate(classLoader, className)
	}
}