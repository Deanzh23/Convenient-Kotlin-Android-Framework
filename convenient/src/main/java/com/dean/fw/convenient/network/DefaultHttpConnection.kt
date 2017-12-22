package com.dean.fw.convenient.network

import android.util.Log
import com.dean.fw.convenient.network.listener.OnConnectionListener
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Http请求超类
 *
 * Created by dean on 2017/12/22.
 */
class DefaultHttpConnection {

    /**
     * 发送HttpGet请求
     *
     * @param basicURL              基础url
     * @param headerParams          连接头参数集
     * @param urlParams             url参数集
     * @param onConnectionListener  监听器
     */
    protected fun sendHttpGet(basicURL: String, headerParams: Map<String, Any>, urlParams: Any, onConnectionListener: OnConnectionListener?) {
        sendHttpGet(basicURL, headerParams, urlParams, "utf-8", 5000, false, onConnectionListener)
    }

    /**
     * 发送HttpGet请求
     *
     * @param basicURL              基础url
     * @param headerParams          连接头参数集
     * @param urlParams             url参数集
     * @param encoding              编码方式
     * @param connectTimeout        连接超时时间
     * @param isUseCache            是否使用缓存
     * @param onConnectionListener  监听器
     */
    protected fun sendHttpGet(basicURL: String, headerParams: Map<String, Any>, urlParams: Any, encoding: String, connectTimeout: Int, isUseCache: Boolean,
                              onConnectionListener: OnConnectionListener?) {

        var httpURLConnection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var inputStreamReader: InputStreamReader? = null
        var reader: BufferedReader? = null

        try {
            // 配置参数以后的url部分
            val httpUrl = getHttpURL(basicURL, urlParams)
            val url = URL(httpUrl)
            httpURLConnection = url.openConnection() as HttpURLConnection
            // 设置连接规则以及连接头参数
            setHttpURLConnectionConfig(httpURLConnection, "GET", encoding, connectTimeout, isUseCache, headerParams)
            // 发起连接
            httpURLConnection.connect()

            // 接收应答
            val responseCode = httpURLConnection.responseCode

            // 应答"成功"
            if (responseCode == 200) {
                inputStream = httpURLConnection.inputStream
                inputStreamReader = InputStreamReader(inputStream)
                reader = BufferedReader(inputStreamReader)

                // 得到应答信息
                val responseBuilder = StringBuffer()
                reader.forEachLine {
                    responseBuilder.append(it)
                }

                Log.d(DefaultHttpConnection::class.simpleName, "response success --> " + responseBuilder.toString())
                onConnectionListener?.onRequestSuccess(responseBuilder.toString())
            } else {
                Log.e(DefaultHttpConnection::class.simpleName, "response failure --> " + responseCode)
                onConnectionListener?.onRequestFailure(responseCode)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onConnectionListener?.onRequestFailure(-1000)
        } finally {
            try {
                reader?.close()
                inputStreamReader?.close()
                inputStream?.close()
                httpURLConnection?.disconnect()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 获取配置参数后的最终url
     *
     * @param basicURL      基础url
     * @param urlParams     url参数集
     * @return              配置完成后的URL字符串
     */
    private fun getHttpURL(basicURL: String, urlParams: Any): String {
        val urlBuilder = StringBuffer(basicURL)
        var i = 0
        var tempUrlParams: Any

        // 如果参数是由Map传递
        if (urlParams is Map<*, *>) {
            tempUrlParams = urlParams as LinkedHashMap<*, *>

            if (!tempUrlParams.isEmpty()) {
                tempUrlParams.entries.forEach {

                    val key = it.key as String
                    val value = it.value

                    // basic url后的分隔符
                    if (i == 0)
                        urlBuilder.append("?")
                    else
                        urlBuilder.append("&")
                    // 参数key+连接符
                    urlBuilder.append(URLEncoder.encode(key)).append("=")
                    // 参数值
                    if (value is String)
                        urlBuilder.append(URLEncoder.encode(value))
                    else
                        urlBuilder.append(value)

                    i++
                }
            }
        }
        // 如果参数是由List传递
        else if (urlParams is List<*>) {
            tempUrlParams = urlParams as ArrayList<*>

            if (!tempUrlParams.isEmpty()) {
                tempUrlParams.forEach {

                    urlBuilder.append("/")

                    if (it is String)
                        urlBuilder.append(URLEncoder.encode(it))
                    else
                        urlBuilder.append(it)
                }
            }
        }

        return urlBuilder.toString()
    }

    /**
     * 设置连接规则以及连接头参数
     *
     * @param httpURLConnection Http连接对象
     * @param requestMethod     请求方式
     * @param encoding          编码规则
     * @param connectTimeout    连接超时时间
     * @param isUseCache        是否启用缓存
     * @param headerParams      请求头信息
     */
    private fun setHttpURLConnectionConfig(httpURLConnection: HttpURLConnection, requestMethod: String, encoding: String, connectTimeout: Int, isUseCache: Boolean,
                                           headerParams: Map<String, Any>) {

        httpURLConnection.requestMethod = requestMethod
        httpURLConnection.setRequestProperty("encoding", encoding)
        httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
        httpURLConnection.connectTimeout = connectTimeout
        httpURLConnection.useCaches = isUseCache

        // 设置请求头参数
        if (!headerParams.isEmpty())
            headerParams.entries.forEach {
                httpURLConnection.setRequestProperty(it.key, it.value.toString())
            }

        val isGetMethod = requestMethod.toLowerCase() == "get"
        httpURLConnection.doOutput = !isGetMethod
        httpURLConnection.doInput = true
    }

}