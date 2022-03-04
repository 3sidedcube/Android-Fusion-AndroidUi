package com.cube.fusion.android.core.holder

import com.cube.fusion.android.core.actions.FusionAndroidActionHandler

/**
 * Interface for a ViewHolder which uses action handling functionality (including ViewHolders whose child views use action handling)
 *
 * Created by JR Mitchell on 24/December/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @property actionHandler the handler to use to handle actions on this ViewHolder
 */
interface ActionHandlingViewHolder {
	var actionHandler: FusionAndroidActionHandler?
}