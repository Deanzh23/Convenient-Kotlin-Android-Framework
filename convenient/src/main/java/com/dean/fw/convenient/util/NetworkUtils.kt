package com.dean.fw.convenient.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * 网络工具类
 *
 * Created by dean on 2017/12/22.
 */
object NetworkUtils {

    /**
     * 网络模式枚举类型
     */
    enum class NetworkModelEnum {
        /**
         * 移动网络
         */
        NETWORK_STATE_MOBILE,
        /**
         * WIFI网络
         */
        NETWORK_STATE_WIFI,
        /**
         * 没有连接的网络模式
         */
        NETWORK_STATE_NONE
    }

    /**
     * 获取当前网络状态
     *
     * @param context
     * @return NetworkModelEnum
     */
    fun getNetworkState(context: Context): NetworkModelEnum {
        if (isWifiConnected(context))
            return NetworkModelEnum.NETWORK_STATE_WIFI
        else if (isMobileConnected(context))
            return NetworkModelEnum.NETWORK_STATE_MOBILE
        else
            return NetworkModelEnum.NETWORK_STATE_NONE
    }

    /**
     * WIFI是否连接
     *
     * @param context
     * @return 连接/未连接
     */
    fun isWifiConnected(context: Context): Boolean = isConnected(context, ConnectivityManager.TYPE_WIFI)

    /**
     * 移动网络是否连接
     *
     * @param context
     * @return 连接/未连接
     */
    fun isMobileConnected(context: Context): Boolean = isConnected(context, ConnectivityManager.TYPE_MOBILE)

    /**
     * 指定网络模式是否连接
     *
     * @param context
     * @param networkType
     * @return 连接/未连接
     */
    @SuppressLint("MissingPermission")
    fun isConnected(context: Context, networkType: Int): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(networkType)

        return networkInfo != null
    }

}