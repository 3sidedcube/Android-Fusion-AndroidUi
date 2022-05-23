package com.cube.fusion.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cube.fusion.android.core.databinding.WebViewBinding

/**
 * Fragment intended to display in-app links in a webview
 * TODO: this is a stub; add logic
 * TODO: do not duplicate common logic between this and WebViewActivity
 * TODO: the webview binding currently includes a toolbar - this should not be the case for this fragment. Resolve this.
 * TODO: abstract this behaviour for greater flexibility
 * TODO: using setJavaScriptEnabled can introduce vulnerabilities; ensure that this is opt-in -
 *  additionally, Fusion link classes could include a 'trusted' flag
 *
 * Created by JR Mitchell on 05/May/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class WebViewFragment : Fragment() {
	companion object {
		private const val URL_EXTRA_KEY = "URL_EXTRA_KEY"
		fun getBundle(url: String?) = Bundle().apply {
			putString(URL_EXTRA_KEY, url)
		}
	}

	private var binding: WebViewBinding? = null

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = WebViewBinding.inflate(inflater, container, false)
		return binding?.root
	}
}