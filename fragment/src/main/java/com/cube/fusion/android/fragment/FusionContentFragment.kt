package com.cube.fusion.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cube.fusion.android.core.adapter.FusionViewAdapter
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.utils.ErrorMessage
import com.cube.fusion.android.core.databinding.ContentFragmentViewBinding
import com.cube.fusion.android.core.decorator.DropShadowDecorator
import com.cube.fusion.android.core.exception.EmptyUrlError
import com.cube.fusion.core.display.FusionDisplayTarget
import com.cube.fusion.core.display.FusionLoadingIndicator
import com.cube.fusion.core.exception.UnsuccessfulResponseError
import com.cube.fusion.core.model.Page
import java.net.ConnectException
import java.net.ProtocolException
import java.net.UnknownHostException

/**
 * Loads the cms content from a URL
 *
 * Created by Nikos Rapousis on 6/November/2020.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @param fusionConfig the configuration to use for displaying Fusion content
 */

class FusionContentFragment(private val fusionConfig: AndroidFusionConfig) : Fragment(), FusionDisplayTarget, FusionLoadingIndicator {
	companion object {
		private const val LINK_EXTRA_KEY = "LINK_EXTRA_KEY"
		fun getBundle(link: String?) = Bundle().apply {
			putString(LINK_EXTRA_KEY, link)
		}
	}

	lateinit var adapter: FusionViewAdapter
	private var _binding: ContentFragmentViewBinding? = null

	// This property is only valid between onCreateView and onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
		_binding = ContentFragmentViewBinding.inflate(inflater, container, false)
		return binding.root
	}

	//TODO: onActivityCreated() is deprecated, move to onViewCreated() and/or onCreate()
	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		val url = arguments?.getString(LINK_EXTRA_KEY)
		if (url.isNullOrEmpty()) {
			displayError(EmptyUrlError())
		}
		else {
			adapter = FusionViewAdapter().apply {
				actionHandler = fusionConfig.actionHandler
				imageLoader = fusionConfig.imageLoader
			}
			binding.recyclerView.adapter = adapter
			binding.recyclerView.addItemDecoration(DropShadowDecorator())
			loadPages(url)
		}
	}

	/**
	 * Load a page from a given screen link URL
	 *
	 * @param screenLink the URL of the screen to load, relative to the base URL
	 */
	private fun loadPages(screenLink: String) {
		fusionConfig.populator.populateDisplayFromUri(screenLink, this, this)
	}

	/**
	 * Displays a successfully loaded [Page] in the fragment, updating the page title and content
	 *
	 * @param page the [Page] to display
	 */
	override fun displayPage(page: Page) {
		setLoadingState(false)
		activity?.title = page.title
		adapter.setItems(page.screen?.children)
	}

	/**
	 * Displays an error in the fragment, hiding the loading UI
	 * TODO: make error displaying more flexible
	 *
	 * @param throwable the thrown error to display, or null for a generic error message
	 */
	override fun displayError(throwable: Throwable?) {
		setLoadingState(false)
		val errorMessage = when (throwable) {
			is IllegalStateException -> "Error ${throwable.message}" //TODO register as a format string
			is UnknownHostException -> getString(R.string.internet_submit)
			is ProtocolException -> getString(R.string.user_authentication)
			is ConnectException -> getString(R.string.internet_submit)
			is UnsuccessfulResponseError -> getString(R.string.error) //TODO make this more specific
			is EmptyUrlError -> getString(R.string.error) //TODO make this more specific
			else -> getString(R.string.error) //Generic case
		}
		ErrorMessage.display(activity, errorMessage)
	}

	override fun setLoadingState(isLoading: Boolean) {
		binding.loadingIndicator.root.isVisible = isLoading
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}