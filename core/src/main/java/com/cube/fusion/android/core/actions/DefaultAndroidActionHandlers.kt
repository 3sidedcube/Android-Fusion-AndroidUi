package com.cube.fusion.android.core.actions

import android.content.Intent
import android.net.Uri
import com.cube.fusion.android.core.R
import com.cube.fusion.android.core.utils.extensions.handledByView
import com.cube.fusion.core.model.action.EmailAction
import com.cube.fusion.core.model.action.LinkAction
import com.cube.fusion.core.utils.Constants

/**
 * Object containing useful default implementations for handling Fusion actions in an Android framework
 *
 * Created by JR Mitchell on 05/May/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object DefaultAndroidActionHandlers {
	/**
	 * Get a default handler for [EmailAction]s
	 * @return a [FusionAndroidActionHandler] that handles [EmailAction]s by launching an email app selector with the relevant email extras
	 */
	fun defaultEmailActivityActionHandler() = FusionAndroidActionHandler { view, action ->
		if (action is EmailAction) {
			val selectorIntent = Intent(Intent.ACTION_SENDTO).apply {
				data = Uri.parse(Constants.MAIL_TO)
			}
			val emailIntent = Intent(Intent.ACTION_SEND).apply {
				putExtra(Intent.EXTRA_EMAIL, action.to)
				putExtra(Intent.EXTRA_BCC, action.bcc)
				putExtra(Intent.EXTRA_CC, action.cc)
				putExtra(Intent.EXTRA_SUBJECT, action.subject)
				putExtra(Intent.EXTRA_TEXT, action.body)
				selector = selectorIntent
			}
			Intent.createChooser(emailIntent, view.context.getString(R.string.send_email_header)).handledByView(view)
		}
		else false
	}

	/**
	 * Get a default handler for external [LinkAction]s; i.e ones with [LinkAction.inApp] false
	 * @return a [FusionAndroidActionHandler] that handles out-of-app links by launching an [Intent.ACTION_VIEW] intent
	 */
	fun defaultExternalLinkActivityActionHandler() = FusionAndroidActionHandler { view, action ->
		if(action is LinkAction && !action.inApp) {
			Intent(Intent.ACTION_VIEW).apply {
				data = Uri.parse(action.extractClick())
			}.handledByView(view)
		}
		else false
	}
}