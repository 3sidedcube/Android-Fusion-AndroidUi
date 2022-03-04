package com.cube.fusion.android.core.helper

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Px
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.cube.fusion.android.core.utils.shadow.ShadowBasicInset
import com.cube.fusion.android.core.utils.shadow.ShadowRectSpec
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Object containing methods to help with the rendering of drop shadows
 *
 * Created by JR Mitchell on 10/November/2021.
 * Copyright ® 3SidedCube. All rights reserved.
 */
object ShadowHelper {
	/**
	 * Generates a list of [ShadowRectSpec] for a view based on assumptions / approximations
	 * Doesn't work as well if the shadow is larger than the available space on the screen, or the blur is greater than the size of the view
	 *
	 * @param colour the base colour of the shadow - note that the alpha component is ignored
	 * @param alpha the alpha of the shadow
	 * @param blur the blur radius of the shadow, in Px
	 * @param cornerRadius the corner radius of the shadow, in Px
	 * @param inset the basic inset for the shadow
	 */
	fun generateShadowV3(@ColorInt colour: Int, alpha: Double, @Px blur: Double, @Px cornerRadius: Float, inset: ShadowBasicInset): List<ShadowRectSpec>? {
		//Base RGB components
		val red = colour.red
		val green = colour.green
		val blue = colour.blue
		when {
			alpha == 0.0 -> {
				//No shadow needed
				return null
			}
			blur == 0.0 -> {
				//No blur needed - just a solid rounded rect with given inset
				return listOf(ShadowRectSpec(Color.argb((alpha * 255).roundToInt(), red, green, blue), cornerRadius, inset.left, inset.top, inset.right, inset.bottom))
			}
			else -> {
				val erf2 = erfApprox(2.0)
				val pixelDistance = ceil(blur * 2).roundToInt()
				var currSumAlpha = 0
				var currMaxAlpha = 0
				val premultipliedErrors = mutableListOf(0.0, 0.0, 0.0) //Error in premultiplied values for R, G, B
				return (-pixelDistance..pixelDistance).map {
					if (currSumAlpha >= 255) {
						return@map null
					}
					/*
					 * Base the blur off an approximation of the error function
					 * This is the integral of the Gaussian blur under the approximation that the shape sides are infinitely long
					 * This approximation obviously falls apart near/on the rounded corners of the shape, but it works sufficiently well for V1
					 */
					val targetSumAlpha = alpha * 0.5 * (1 - erfApprox(-it / blur) / erf2)
					/*
					 * Because the resulting alpha of overlaying alpha a_2 onto alpha a_1 is calculated as:
					 * 	a_o(a_2, a_1) = a_2 + a_1(1 - a_2)
					 * this can be rearranged to
					 * a_1 = (a_o - a_2)/(1 - a_2)
					 * Here, we aim to get the total current alpha to a_o by overlaying another layer of alpha a_1
					 */
					val layerAlpha = (targetSumAlpha - (currSumAlpha / 255.0)) / (1 - (currSumAlpha / 255.0))
					val layerAlphaCoerced = layerAlpha.coerceIn(0.0, alpha)

					//Since the underlying graphics calculations will be done with ints, we round to ints and discard any zero-alpha layers for better smoothing
					val layerAlphaInt = (layerAlphaCoerced * 255).roundToInt()
					currMaxAlpha = maxOf(currMaxAlpha, layerAlphaInt)
					val resultantAlpha = (layerAlphaInt / 255.0) + (1.0 - (layerAlphaInt / 255.0)) * (currSumAlpha / 255.0)
					val resultantAlphaInt = (resultantAlpha * 255).roundToInt()

					if (layerAlphaInt != 0 && resultantAlphaInt != currSumAlpha) {
						//Ensure the current total alpha is updated
						currSumAlpha = resultantAlphaInt
						val layerColours = mutableListOf(red, green, blue)
						if (currMaxAlpha <= 11 && currSumAlpha <= 127) {
							/*
							 * Adjust the lower opacity layers to ensure correct premultiplied colours
							 * See https://en.wikipedia.org/wiki/Alpha_compositing#Straight_versus_premultiplied
							 * Note that this works on the approximation that the alpha operation a over b is equal to a + b
							 * This approximation is valid for low layerAlphaInt and low currSumAlpha, hence the if statement
							 * 11 was selected as the currMaxAlpha cutoff since it is the highest integer alpha n for which round(n over n) = 2n
							 * 127 was selected as the currSumAlpha cutoff since it is the highest integer alpha n for which round(1 over n) = n + 1
							 */
							(layerColours zip premultipliedErrors).forEachIndexed { i, (initialColour, initialError) ->
								val premultipliedValue = initialColour.toDouble() * layerAlphaInt / 255
								val premultipliedValueError = premultipliedValue.roundToInt() - premultipliedValue
								val valueErrorInt = (initialError + premultipliedValueError).roundToInt()
								premultipliedErrors[i] += premultipliedValueError - valueErrorInt
								layerColours[i] = (initialColour - (valueErrorInt * 255 / layerAlphaInt)).coerceIn(0, 255)
							}
						}
						val layerColour = Color.argb(layerAlphaInt, layerColours[0], layerColours[1], layerColours[2])
						ShadowRectSpec(layerColour, cornerRadius, inset.left + it, inset.top + it, inset.right + it, inset.bottom + it)
					}
					else {
						null
					}
				}.filterNotNull()
			}
		}
	}

	/**
	 * Calculate the approximate value of the error function erf([x])
	 * See https://en.wikipedia.org/wiki/Error_function#Numerical_approximations
	 * Uses [erfApproxPositive] along with the odd symmetry of the error function to calculate the error function for any value on the real number line
	 *
	 * @param x the value to calculate the error function of
	 */
	private fun erfApprox(x: Double): Double = if (x < 0) {
		-erfApproxPositive(-x)
	}
	else {
		erfApproxPositive(x)
	}

	/**
	 * Calculate the approximate value of the error function erf([x]) FOR POSITIVE X
	 * Values will be incorrect if x is negative
	 * See https://en.wikipedia.org/wiki/Error_function#Numerical_approximations
	 *
	 * @param x the POSITIVE value to calculate the error function of
	 */
	private fun erfApproxPositive(x: Double): Double {
		val a1 = 0.278393
		val a2 = 0.230389
		val a3 = 0.000972
		val a4 = 0.078108
		return (1f - 1f / ((1.0 + a1 * x + a2 * (x.pow(2)) + a3 * (x.pow(3)) + a4 * (x.pow(4))).pow(4)))
	}
}