package com.dean.fw.convenient.network

import com.dean.fw.convenient.network.listener.OnConnectionListener

/**
 * Http请求框架类
 *
 * Created by dean on 2017/12/22.
 */
class ConvenientHttpConnection : DefaultHttpConnection() {

    /**
     * 发送HttpGet请求
     *
     * @param basicURL              基础url
     * @param headerParams          连接头参数集
     * @param urlParams             url参数集
     * @param onConnectionListener  http请求监听器
     */
    fun sendGet(basicURL: String, headerParams: LinkedHashMap<String, Any>, urlParams: Any, onConnectionListener: OnConnectionListener?) {
        super.sendHttpGet(basicURL, headerParams, urlParams, onConnectionListener)
    }

}