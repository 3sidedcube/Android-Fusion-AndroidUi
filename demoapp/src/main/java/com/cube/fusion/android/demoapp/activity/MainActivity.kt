package com.cube.fusion.android.demoapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cube.fusion.android.demoapp.R
import com.cube.fusion.populator.legacy.api.APIFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Main activity for the demo of Fusion AndroidUi
 *
 * Created by JR Mitchell on 03/March/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class MainActivity : AppCompatActivity() {
	companion object {
		private const val DEMO_URL = "Put your URL here!"
		private const val START_SCREEN = "Put your starting screen slug/URL here!"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		lifecycleScope.launch {
			// Display for 1 seconds
			delay(1000)

			// Set up the legacy API factory
			APIFactory.url = DEMO_URL

			// Redirect to the content activity
			val intent = ContentActivityImpl.getIntent(baseContext, START_SCREEN)
			startActivity(intent)

			// Close this activity
			finish()
		}
	}
}
