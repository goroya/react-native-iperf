package com.reactlibrary

import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.io.IOException
import java.util.*


class RNIperfModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
    companion object {
        val TAG: String = RNIperfModule::class.java.simpleName
        private fun sendEvent(reactContext: ReactContext, eventName: String, params: WritableMap?) {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java).emit(eventName, params)
        }
        private fun sendEventString(reactContext: ReactContext, eventName: String, params: String?) {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java).emit(eventName, params)
        }
    }

    init {
        reactContext.addLifecycleEventListener(this)
    }
    private val reactContext = reactContext

    override fun getConstants(): MutableMap<String, Any> {
        val constants = HashMap<String, Any>()
        return constants
    }

    @ReactMethod
    fun init(promise: Promise) {
        try {
            val map = Arguments.createMap()
            val result = Iperf.init(this.reactContext.assets.open("iperf3"))
            if(result) {
                promise.resolve(map)
            } else {
                promise.reject("INIT_ERROR", "init fail")
            }
        }catch (e: IOException) {
            promise.reject("INIT_ERROR", e.message)
        }
    }

    @ReactMethod
    fun start(cmd: String, promise: Promise) {
        try {
            Iperf.start(cmd, object:IperfCallback {
                override fun onPreExecute() {
                    Log.i(TAG, "onPreExecute")
                    //startCB?.invoke()
                    sendEvent(reactContext, "start", null)
                }
                override fun onProgressUpdate(output: String?) {
                    Log.i(TAG, "onProgressUpdate")
                    sendEventString(reactContext, "output", output)
                }
                override fun onCancelled() {
                    Log.i(TAG, "onCancelled")
                    sendEvent(reactContext, "cancel", null)
                }
                override fun onPostExecute() {
                    Log.i(TAG, "onPostExecute")
                    sendEvent(reactContext, "end", null)
                }

                override fun onError(e: String?) {
                    Log.i(TAG, "onError")
                    sendEventString(reactContext, "error",  e)
                }
            })
            promise.resolve(null)
        } catch (e: Exception){
            Log.i(TAG, e.message)
            sendEventString(reactContext, "error",  e.message)
            promise.reject("START_ERROR", e.message)
        }
    }

    @ReactMethod
    fun cancel(promise: Promise) {
        try {
            Iperf.cancel()
            promise.resolve(null)
        } catch (e: Exception){
            promise.reject("CANCEL_ERROR", e.message)
        }
    }

    override fun getName(): String {
        return "RNIperf"
    }

    override fun onHostResume() {
        Log.i(TAG, "onHostResume")
    }

    override fun onHostPause() {
        Log.i(TAG, "onHostPause")
    }

    override fun onHostDestroy() {
        Log.i(TAG, "onHostDestroy")
    }
}
