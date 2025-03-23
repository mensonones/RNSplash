package com.rnsplash.splashscreen

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import java.util.Collections


class SplashModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext){
    override fun getName() = "NativeSplashModule"

    override fun getConstants(): MutableMap<String?, Any?> {
        return Collections.unmodifiableMap(object : HashMap<String?, Any?>() {
            init {
                put("animationType", animationTypes)
            }

            val animationTypes: MutableMap<String?, Any?>
                get() = Collections.unmodifiableMap(object : HashMap<String?, Any?>() {
                    init {
                        put("none", RCTSplashScreen.UI_ANIMATION_NONE)
                        put("fade", RCTSplashScreen.UI_ANIMATION_FADE)
                        put("scale", RCTSplashScreen.UI_ANIMATION_SCALE)
                    }
                })
        })
    }

    @ReactMethod
    fun hide(options: ReadableMap?) {
        Log.d("NativeSplashModule", "HIDE CALLED")
        var animationType = RCTSplashScreen.UI_ANIMATION_NONE
        var duration = 0
        var delay = 0

        options?.let {
            if (it.hasKey("animationType")) {
                animationType = it.getInt("animationType")
            }
            if (it.hasKey("duration")) {
                duration = it.getInt("duration")
            }
            if (it.hasKey("delay")) {
                delay = it.getInt("delay")
            }
        }

        if (animationType == RCTSplashScreen.UI_ANIMATION_NONE) {
            delay = 0
        }

        Handler(Looper.getMainLooper()).postDelayed({
            RCTSplashScreen.removeSplashScreen(
                currentActivity,
                animationType,
                duration
            )
        }, delay.toLong())
    }
}