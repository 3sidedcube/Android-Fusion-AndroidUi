package com.cube.fusion.android.demoapp.model

import com.cube.fusion.core.model.ImageSource
import com.cube.fusion.core.model.Model
import com.cube.fusion.core.model.views.BaseViewProperties
import com.fasterxml.jackson.annotation.JsonUnwrapped
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class Card(
	val title: String? = null,
	val subtitle: String? = null,
	val type: String? = null,
	val image: ImageSource? = null,
	@field:JsonUnwrapped val baseProperties: BaseViewProperties = BaseViewProperties()
): Model()
