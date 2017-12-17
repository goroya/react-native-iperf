package com.reactlibrary

import android.os.AsyncTask
import android.util.Log
import java.io.*
import java.util.*

interface IperfCallback {
    fun onPreExecute() {
    }
    fun onProgressUpdate(output: String?) {
    }
    fun onCancelled() {
    }
    fun onPostExecute() {
    }
    fun onError(e: String?) {
    }
}

class Iperf(val cb: IperfCallback) : AsyncTask<String, String, String>() {
    companion object {
        val TAG: String = Iperf::class.java.simpleName
        var initFlag = false
        var iperf: Iperf? = null
        val iperfPath = "/data/data/com.sample/iperf3"
        fun init(iperfBinary: InputStream): Boolean{
            Log.i(TAG, "init()")
            if(this.initFlag){
                return true
            }
            try {
                FileInputStream(Iperf.iperfPath)
            } catch (e: FileNotFoundException) {
                val out = FileOutputStream(Iperf.iperfPath, false)
                val buf = ByteArray(1024)
                var len = iperfBinary.read(buf)
                while (len > 0) {
                    out.write(buf, 0, len)
                    len = iperfBinary.read(buf)
                }
                iperfBinary.close()
                out.close()
                val processChmod = Runtime.getRuntime().exec("/system/bin/chmod 744 ${Iperf.iperfPath}")
                processChmod.waitFor()
                Log.i(TAG, "init success")
            } catch (e: IOException) {
                return false
            } catch (e: InterruptedException) {
                return false
            } catch (e: Exception) {
                Log.i(TAG, e.message)
                return false
            }
            Iperf.initFlag = true
            Log.i(TAG, "init success AAAA")
            return true
        }
        fun start(opt: String, cb: IperfCallback) {
            Log.i(TAG, "start()")
            Log.i(TAG, Iperf.initFlag.toString())
            if (!Iperf.initFlag){
                Log.i(TAG, "init non")
                return
            }
            if (Iperf.iperf != null){
                Log.i(TAG, "iperf non null")
                return
            }
            Iperf.iperf = Iperf(cb)
            Log.i(TAG, "start exe")
            Iperf.iperf?.execute(opt)
        }
        fun cancel() {
            Log.i(TAG, "cancel()")
            if (!Iperf.initFlag){
                return
            }
            if (Iperf.iperf == null){
                return
            }
            Log.i(TAG, "cancel exec")
            Iperf.iperf?.cancel(true)
            Iperf.iperf = null
        }
    }
    var process: Process? = null
    override fun onPreExecute() {
        Log.i(TAG, "onPreExecute")
        super.onPreExecute()
        Iperf.iperf = null
        this.cb.onPreExecute()
    }

    override fun doInBackground(vararg params: String?): String {
        Log.i(TAG, "doInBackground: ${params[0]}")
        try {
            val inputCommands = params[0]
            val commands = inputCommands?.split(" ")
            val commandList = ArrayList<String>(commands)
            if (commandList[0] == "iperf3") {
                commandList.removeAt(0)
            }
            commandList.add(0, Iperf.iperfPath)
            Log.i(TAG, "CMD: $commandList")
            this.process = ProcessBuilder().command(commandList).redirectErrorStream(true).start()
            val reader = BufferedReader(InputStreamReader(this.process?.inputStream))
            val buffer = CharArray(4096)
            val output = StringBuffer()
            var read = reader.read(buffer)
            while (read > 0) {
                output.append(buffer, 0, read)
                Log.i(TAG, output.toString())
                publishProgress(output.toString())
                output.delete(0, output.length)
                read = reader.read(buffer)
            }
            reader.close()
            this.process?.destroy()
        } catch (e: IOException) {
            Log.i(TAG, e.message)
            if(e.message != null){
                this.cb.onError(e.message)
            }
        }
        finally {
        }
        return ""
    }

    override fun onProgressUpdate(vararg values: String?) {
        Log.i(TAG, "onProgressUpdate")
        Log.i(TAG, values[0])
        super.onProgressUpdate(*values)
        this.cb.onProgressUpdate(values[0])
    }

    override fun onCancelled(result: String?) {
        Log.i(TAG, "onCancelled")
        super.onCancelled(result)
        process?.destroy()
        process?.waitFor()
        this.cb.onCancelled()
    }

    override fun onPostExecute(result: String?) {
        Log.i(TAG, "onPostExecute")
        super.onPostExecute(result)
        process?.destroy()
        process?.waitFor()
        this.cb.onPostExecute()
    }
}