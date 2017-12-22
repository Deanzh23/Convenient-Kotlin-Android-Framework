package com.dean.fw.convenient.network.listener

/**
 * 网络请求状态监听器
 *
 * Created by dean on 2017/12/22.
 */
abstract class OnConnectionListener {

    /**
     * 请求成功（返回状态码200）
     * <p>
     * 由框架调用此方法
     *
     * @param response
     */
    fun onRequestSuccess(response: String) {
        onSuccess(response)
        onEnd()
    }

    /**
     * 请求失败（返回状态码非200的其它）
     * <p>
     * 由框架调用此方法
     *
     * @param errorCode
     */
    fun onRequestFailure(errorCode: Int) {
        onFailure(errorCode)
        onEnd()
    }

    /**
     * 请求成功（返回状态码200）
     * <p>
     * 由客户端实现
     *
     * @param response
     */
    abstract fun onSuccess(response: String)

    /**
     * 请求失败（返回状态码非200的其它）
     * <p>
     * 由客户端实现
     *
     * @param errorCode
     */
    abstract fun onFailure(errorCode: Int)

    /**
     * 请求完成
     * <p>
     * 由客户端实现
     */
    abstract fun onEnd()

}