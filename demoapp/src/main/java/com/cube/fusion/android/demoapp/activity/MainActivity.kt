package com.cube.fusion.android.demoapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cube.fusion.android.demoapp.R
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
		/*
		 * These currently point to the locally defined demo JSON data in the most recent release of this lib
		 * Feel free to locally update this URL and start screen to your own endpoints if you wish
		 */
		private const val DEMO_URL = "https://raw.githubusercontent.com/3sidedcube/Android-Fusion-AndroidUi/main/demoapp/src/main/assets/"
		private const val START_SCREEN = "root.json"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		lifecycleScope.launch {
			// Display for 1 seconds
			delay(1000)

			// Redirect to the content activity
			val intent = ContentActivityImpl.getIntent(baseContext, DEMO_URL, START_SCREEN)
			startActivity(intent)

			// Close this activity
			finish()
		}
	}
}
