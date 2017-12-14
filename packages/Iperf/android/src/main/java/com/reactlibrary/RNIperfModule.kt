package com.reactlibrary

import com.facebook.react.bridge.*
import android.widget.Toast
import java.util.HashMap
import com.facebook.react.bridge.ReactMethod





class RNIperfModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
    private val DURATION_SHORT_KEY = "SHORT"
    private val DURATION_LONG_KEY = "LONG"

    override fun getConstants(): MutableMap<String, Any> {
        val constants = HashMap<String, Any>()
        constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT)
        constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG)
        return constants
    }

    @ReactMethod
    fun show(message: String, duration: Int) {
        Toast.makeText(reactApplicationContext, message, duration).show()
    }

    override fun getName(): String {
        return "RNIperf"
    }

    override fun onHostResume() {
    }

    override fun onHostPause() {
    }

    override fun onHostDestroy() {
    }
}
