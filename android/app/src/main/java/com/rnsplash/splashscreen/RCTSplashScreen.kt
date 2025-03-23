package com.rnsplash.splashscreen

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import com.rnsplash.R
import java.lang.ref.WeakReference

class RCTSplashScreen {

    companion object {
        const val UI_ANIMATION_NONE = 0
        const val UI_ANIMATION_FADE = 1
        const val UI_ANIMATION_SCALE = 2

        private var dialog: Dialog? = null
        private var imageView: ImageView? = null
        private var wr_activity: WeakReference<Activity>? = null

        private val activity: Activity?
            get() = wr_activity?.get()

        fun openSplashScreen(activity: Activity?, isFullScreen: Boolean) {
            if (activity == null) return
            wr_activity = WeakReference(activity)
            val drawableId = getImageId()
            if (dialog?.isShowing == true || drawableId == 0) return

            activity.runOnUiThread {
                if (activity.isFinishing.not()) {
                    val context = activity
                    imageView = ImageView(context)
                    imageView?.layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    imageView?.setImageResource(drawableId)
                    imageView?.scaleType = ImageView.ScaleType.CENTER_CROP

                    dialog = Dialog(
                        context,
                        if (isFullScreen) android.R.style.Theme_Translucent_NoTitleBar_Fullscreen else android.R.style.Theme_Translucent_NoTitleBar
                    )
                    dialog?.setContentView(imageView!!)
                    dialog?.setCancelable(false)
                    dialog?.show()
                }
            }
        }

        fun removeSplashScreen(activity: Activity?, animationType: Int, duration: Int) {
            val currentActivity = activity ?: Companion.activity
            if (currentActivity == null || dialog == null || dialog?.isShowing != true) return

            currentActivity.runOnUiThread {
                val animationSet = AnimationSet(true)
                val animation: Animation

                if (animationType == UI_ANIMATION_SCALE) {
                    val fadeOut = AlphaAnimation(1f, 0f)
                    fadeOut.duration = duration.toLong()
                    animationSet.addAnimation(fadeOut)

                    val scale = ScaleAnimation(
                        1f, 1.5f, 1f, 1.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.65f
                    )
                    scale.duration = duration.toLong()
                    animationSet.addAnimation(scale)
                } else {
                    animation = AlphaAnimation(1f, 0f)
                    animation.duration = if (animationType == UI_ANIMATION_FADE) duration.toLong() else 0
                    animationSet.addAnimation(animation)
                }

                val view = (dialog?.window?.decorView as ViewGroup).getChildAt(0)
                view.startAnimation(animationSet)

                animationSet.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}
                    override fun onAnimationRepeat(animation: Animation) {}
                    override fun onAnimationEnd(animation: Animation) {
                        view.post {
                            dialog?.dismiss()
                            dialog = null
                            imageView = null
                        }
                    }
                })
            }
        }

        private fun getImageId(): Int {
            val splashDrawableId = R.drawable.splash
            return if (splashDrawableId != 0) splashDrawableId else 0
        }
    }
}