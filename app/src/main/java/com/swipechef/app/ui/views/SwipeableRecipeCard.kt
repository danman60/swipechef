package com.swipechef.app.ui.views

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import kotlin.math.abs

class SwipeableRecipeCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var onSwipeUpListener: (() -> Unit)? = null
    private var onTapListener: (() -> Unit)? = null

    private val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            onTapListener?.invoke()
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (e1 == null) return false

            val deltaY = e2.y - e1.y
            val deltaX = e2.x - e1.x

            // Check if it's primarily a vertical swipe up
            if (abs(deltaY) > abs(deltaX) && deltaY < -100 && velocityY < -500) {
                performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                animateAddToChosen()
                onSwipeUpListener?.invoke()
                return true
            }

            return false
        }
    })

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    fun setOnSwipeUpListener(listener: () -> Unit) {
        onSwipeUpListener = listener
    }

    fun setOnTapListener(listener: () -> Unit) {
        onTapListener = listener
    }

    private fun animateAddToChosen() {
        // Lift and scale animation
        val scaleUpX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1f, 1.05f)
        val scaleUpY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1f, 1.05f)
        val elevateUp = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, 0f, -20f)

        val liftAnimator = AnimatorSet().apply {
            playTogether(scaleUpX, scaleUpY, elevateUp)
            duration = 150
        }

        // Return to normal animation
        val scaleDownX = ObjectAnimator.ofFloat(this, View.SCALE_X, 1.05f, 1f)
        val scaleDownY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 1.05f, 1f)
        val elevateDown = ObjectAnimator.ofFloat(this, View.TRANSLATION_Y, -20f, 0f)

        val returnAnimator = AnimatorSet().apply {
            playTogether(scaleDownX, scaleDownY, elevateDown)
            duration = 200
        }

        // Complete animation sequence
        val fullAnimation = AnimatorSet().apply {
            playSequentially(liftAnimator, returnAnimator)
        }

        fullAnimation.doOnEnd {
            // Show subtle success indicator
            showSuccessBadge()
        }

        fullAnimation.start()
    }

    private fun showSuccessBadge() {
        // Create a temporary "Chosen" badge
        // This could be enhanced with a custom overlay view
        alpha = 0.7f
        animate()
            .alpha(1f)
            .setDuration(300)
            .start()
    }

    fun animateChooseButton() {
        // Same animation for the Choose button
        animateAddToChosen()
    }
}