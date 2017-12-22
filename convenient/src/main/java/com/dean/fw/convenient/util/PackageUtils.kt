package com.dean.fw.convenient.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.File

/**
 * 包信息工具类
 *
 * Created by dean on 2017/12/22.
 */
object PackageUtils {

    /**
     * 获取当前APP的包信息
     *
     * @param context
     * @return 包信息对象
     */
    fun getAppPackInfo(context: Context): PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    /**
     * 获取当前apk版本号
     *
     * @param context
     * @return 版本号
     */
    fun getVersionCode(context: Context): Int {
        return getAppPackInfo(context).versionCode
    }

    /**
     * 获取当前apk的版本名
     *
     * @param context
     * @return 版本名
     */
    fun getVersionName(context: Context): String = getAppPackInfo(context).versionName

    /**
     * 获取当前apk的包名
     *
     * @param context
     * @return 包名
     */
    fun getPackeageName(context: Context): String = getAppPackInfo(context).packageName

    /**
     * 获取当前apk的应用名
     *
     * @param context
     * @return 应用名
     */
    fun getAppName(context: Context): String {
        val packageManager = context.packageManager
        val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)

        return packageManager.getApplicationLabel(applicationInfo).toString()
    }

    /**
     * 获取当前apk的应用图标
     *
     * @param context
     * @return 应用图标
     */
    fun getAppIcon(context: Context): Drawable {
        val packageManager = context.packageManager
        val applicationInfo = packageManager.getApplicationInfo(context.packageName, 0)

        return packageManager.getApplicationIcon(applicationInfo)
    }

    /**
     * 安装APK文件
     *
     * @param context
     * @param apkFile apk文件
     */
    fun installAPK(context: Context, apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

}