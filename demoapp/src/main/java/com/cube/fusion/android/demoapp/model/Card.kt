package com.cube.fusion.android.demoapp.model

import com.cube.fusion.core.model.ImageSource
import com.cube.fusion.core.model.Model

/**
 * Example custom view [Model] for a card with title, subtitle and image
 * Demonstrates setting up custom views
 *
 * Created by Nikos Rapousis on 16/March/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @property title the title of the card
 * @property subtitle the subtitle of the card
 * @property image the image to display for the card
 */
class Card : Model() {
	val title: String? = null
	val subtitle: String? = null
	val type: String? = null
	val image: ImageSource? = null
}
