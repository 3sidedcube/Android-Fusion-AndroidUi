package com.cube.fusion.android.core.utils.extensions

import android.content.Intent
import android.view.View

/**
 * Set of extensions relevant to intents and intent handling
 *
 * Created by JR Mitchell on 05/May/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */

/**
 * Convenience method to handle a nullable intent created during action handling by starting an activity if not-null
 *
 * @param view the view to handle the nullable intent with
 * @return true if the intent is non-null, and therefore startActivity has been called; false otherwise
 */
fun Intent?.handledByView(view: View) = this?.let {
	view.context.startActivity(it)
	true
} ?: false