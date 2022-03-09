package com.cube.fusion.android.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.cube.fusion.android.core.adapter.FusionViewAdapter
import com.cube.fusion.android.core.config.AndroidFusionConfig
import com.cube.fusion.android.core.databinding.ContentFragmentViewBinding
import com.cube.fusion.android.core.decorator.DropShadowDecorator
import com.cube.fusion.android.core.exception.EmptyUrlError
import com.cube.fusion.android.core.utils.ErrorMessage
import com.cube.fusion.core.display.FusionDisplayTarget
import com.cube.fusion.core.display.FusionLoadingIndicator
import com.cube.fusion.core.exception.UnsuccessfulResponseError
import com.cube.fusion.core.model.Page
import java.net.ConnectException
import java.net.ProtocolException
import java.net.UnknownHostException

/**
 * Abstract class for Fusion content activities which directly display a page's content (i.e not via a Fragment)
 *
 * Created by JR Mitchell on 04/January/2022.
 * Copyright ® 3SidedCube. All rights reserved.
 *
 * @property contentBinding the viewbinding within which the content should be displayed
 * @property fusionConfig the configuration to use for displaying Fusion config
 */
abstract class FusionContentActivity : AppCompatActivity(), FusionDisplayTarget, FusionLoadingIndicator {

	companion object {
		private const val BUNDLED_PAGE_KEY = "BUNDLED_PAGE"
		private const val LINK_EXTRA_KEY = "LINK_EXTRA_KEY"
		/**
		 * Get an intent to launch [FusionContentActivity] with
		 *
		 * @param context the Android content to launch from
		 * @param link the URL or slug link of the Fusion screen to display
		 * @param clazz the inheriting class to launch to
		 *
		 * @return an intent to [FusionContentActivity] with the provided [link]
		 */
		@JvmStatic
		protected fun getIntent(context: Context, link: String?, clazz: Class<out FusionContentActivity>) = Intent(context, clazz).apply {
			putExtra(LINK_EXTRA_KEY, link)
		}
	}

	abstract val contentBinding: ContentFragmentViewBinding
	private lateinit var adapter: FusionViewAdapter
	abstract val fusionConfig: AndroidFusionConfig

	protected var currPage: Page? = null

	/**
	 * Attempts to load and display the page specified either in the [savedInstanceState] bundle or by the URL in the activity extras
	 * Should only be called once views are initialised, i.e in [onCreate] after inflating the binding and setting root
	 */
	protected fun displayActivityPage(savedInstanceState: Bundle?) {
		val savedPageData = getPageFromBundle(savedInstanceState)
		if(savedPageData == null) {
			loadPageFromExtraUrl()
		} else {
			displayPage(savedPageData)
		}
	}

	/**
	 * Attempts to retrieve a stored page from the specified [Bundle]
	 */
	protected fun getPageFromBundle(bundle: Bundle?) = bundle?.getParcelable<Page>(BUNDLED_PAGE_KEY)

	/**
	 * Attempts to load and display the page specified by the URL in the activity extras
	 * Should only be called once views are initialised, i.e in [onCreate] after inflating the binding and setting root
	 */
	protected fun loadPageFromExtraUrl() {
		val link = intent.getStringExtra(LINK_EXTRA_KEY)
		if (link.isNullOrEmpty()) {
			displayError(EmptyUrlError())
		}
		else {
			fusionConfig.populator.populateDisplayFromUri(link, this, this)
		}
	}

	override fun displayPage(page: Page) {
		if (!this::adapter.isInitialized) {
			adapter = FusionViewAdapter(fusionConfig)
			contentBinding.recyclerView.adapter = adapter
			contentBinding.recyclerView.addItemDecoration(DropShadowDecorator())
		}
		setLoadingState(false)
		title = page.title
		adapter.setItems(page.screen?.children)
		currPage = page
	}

	/**
	 * Displays an error in the activity, hiding the loading UI
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
		ErrorMessage.display(this, errorMessage)
	}

	override fun setLoadingState(isLoading: Boolean) {
		contentBinding.loadingIndicator.root.isVisible = isLoading
	}

	override fun onSaveInstanceState(outState: Bundle) {
		super.onSaveInstanceState(outState)
		currPage?.let {
			outState.putParcelable(BUNDLED_PAGE_KEY, it)
		}
	}
}