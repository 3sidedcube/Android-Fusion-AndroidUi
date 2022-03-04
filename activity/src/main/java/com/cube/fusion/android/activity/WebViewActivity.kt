package com.cube.fusion.android.activity

import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.cube.fusion.android.core.databinding.WebViewBinding
import com.cube.fusion.core.utils.Constants


/**
 * This class is responsible to load the url, in the app.
 * TODO: abstract this behaviour for greater flexibility
 * TODO: using setJavaScriptEnabled can introduce vulnerabilities; ensure that this is opt-in -
 *  additionally, Fusion link classes could include a 'trusted' flag
 *
 * Created by Nikos Rapousis on 07/May/2020.
 * Copyright ® 3SidedCube. All rights reserved.
 */
class WebViewActivity : AppCompatActivity() {
	companion object {
		private const val URL_EXTRA_KEY = "URL_EXTRA_KEY"
		fun getIntent(context: Context, url: String?) = Intent(context, WebViewActivity::class.java).apply {
			putExtra(URL_EXTRA_KEY, url)
		}
	}

	lateinit var binding: WebViewBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = WebViewBinding.inflate(layoutInflater)
		setContentView(binding.root)

		binding.toolbar.backAction.setOnClickListener {
			finish()
		}
		binding.toolbar.screenTitle.text = getString(R.string.app_name)
		// We enable supportMultipleWindows so the chrome web client gets onCreateWindow callbacks when a link wants to open in a new tab / window
		binding.webView.settings.setSupportMultipleWindows(true)
		binding.webView.settings.javaScriptEnabled = true
		binding.webView.settings.domStorageEnabled = true
		binding.webView.webViewClient = object : WebViewClient() {
			override fun onPageFinished(view: WebView, url: String) {
				super.onPageFinished(view, url)
				binding.loadingIndicator.root.visibility = ProgressBar.GONE
			}

			override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
				handleUrl(binding.webView, Uri.parse(url))
				return true
			}

			@TargetApi(Build.VERSION_CODES.N)
			override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
				handleUrl(binding.webView, request.url)
				return true
			}
		}
		binding.webView.webChromeClient = object : WebChromeClient() {
			override fun onCreateWindow(view: WebView, dialog: Boolean, userGesture: Boolean, resultMsg: Message): Boolean {
				val result = view.hitTestResult
				val browserIntent = getIntent(view.context, result.extra)
				view.context.startActivity(browserIntent)
				return false
			}
		}
		val url = intent.getStringExtra(URL_EXTRA_KEY)
		if (url != null) {
			handleUrl(binding.webView, Uri.parse(url))
		}
		else {
			Toast.makeText(this, R.string.failed_to_load_website, Toast.LENGTH_LONG).show()
		}
	}

	/***
	 * Handle web actions (Tel, Mail, open file or open new webpage)
	 */
	private fun handleUrl(webView: WebView?, uri: Uri) {
		when {
			uri.toString().startsWith(Constants.MAIL_TO, true) -> {
				//Handle mail Urls
				startActivity(Intent(Intent.ACTION_SENDTO, uri))
			}
			uri.toString().startsWith(Constants.TEL_TO, true) -> {
				val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse(Constants.TEL_TO))
				if (dialIntent.resolveActivity(packageManager) != null) {
					//Handle calls
					//TODO: do we need to add a <queries> declaration for the above?
					startActivity(Intent(Intent.ACTION_DIAL, uri))
				}
				else {
					Toast.makeText(this, getString(R.string.call_not_allowed), Toast.LENGTH_LONG).show()
				}
			}
			uri.toString().endsWith(Constants.PDF, true) -> {
				try {
					//Handle PDF file open
					val intent = Intent(Intent.ACTION_VIEW)
					intent.setDataAndType(Uri.parse(uri.toString()), Constants.PDF_TO)
					startActivity(intent)
					finish()
				}
				catch (e: ActivityNotFoundException) {
					Toast.makeText(this, getString(R.string.pdf_not_supported), Toast.LENGTH_LONG).show()
				}
			}
			else -> {
				//Handle Web Urls
				webView!!.loadUrl(uri.toString())
			}
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == android.R.id.home) {
			try {
				NavUtils.navigateUpFromSameTask(this)
			}
			catch (e: Exception) {
				finish()
			}
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	override fun onResume() {
		super.onResume()
		binding.webView.onResume()
	}

	public override fun onPause() {
		super.onPause()
		binding.webView.onPause()
	}

	override fun onDestroy() {
		super.onDestroy()
		binding.webView.destroy()
	}

	override fun onBackPressed() {
		if (binding.webView.canGoBack()) {
			binding.webView.goBack()
		}
		else {
			super.onBackPressed()
		}
	}
}