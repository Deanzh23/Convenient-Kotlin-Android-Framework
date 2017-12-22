package com.dean.fw.convenient.version

import android.content.Context
import android.util.Log
import com.dean.fw.convenient.util.NetworkUtils
import com.dean.fw.convenient.util.PackageUtils
import com.dean.fw.convenient.version.listener.OnCheckVersionListener

/**
 * 版本升级
 *
 * Created by dean on 2017/12/22.
 */
class VersionUpdate(context: Context) {

    lateinit var context: Context

    /**
     * 检查更新的url
     */
    lateinit var checkUpdateURL: String
    /**
     * 本地版本号
     */
    var localVersionCode: Int = 0
    /**
     * 服务器版本号
     */
    var serverVersionCode: Int = 0
    /**
     * apk下载路径
     */
    lateinit var apkDownloadURL: String
    /**
     * 是否强制更新
     */
    var isForceUpdate: Boolean = false
    /**
     * 更新内容信息
     */
    lateinit var updateMessage: String
    /**
     * 是否处于移动网络
     */
    var isMobileNetwork: Boolean = true

    init {
        this.localVersionCode = PackageUtils.getVersionCode(context)
        this.isMobileNetwork = NetworkUtils.isMobileConnected(context)
    }

    /**
     * 是否有新版本
     */
    fun hasNewVersion(): Boolean = serverVersionCode > localVersionCode

    fun checkUpdate(onCheckVersionListener: OnCheckVersionListener) {
        // 网络检查
        if (NetworkUtils.getNetworkState(context) == NetworkUtils.NetworkModelEnum.NETWORK_STATE_NONE) {
            Log.e(VersionUpdate::class.simpleName, "当前没有网络，无法检查版本更新")
            return
        } else if (checkUpdateURL.isEmpty()) {
            Log.e(VersionUpdate::class.simpleName, "没有配置版本更新URL，无法检查版本更新")
            return
        }
    }

}