package com.cube.fusion.android.core.utils

import android.app.Activity
import android.widget.Toast

/**
 * Message to display on encountering an error
 * TODO: error messaging needs to be handled with a better approach
 *
 * Created by Nikos Rapousis on 08/October/2019.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object ErrorMessage {
	fun display(activity: Activity?, message: String) {
		activity?.runOnUiThread { Toast.makeText(activity.baseContext, message, Toast.LENGTH_LONG).show() }
	}
}