package com.dean.fw.convenient.application

import android.app.Application
import android.os.StrictMode

/**
 * 框架Application
 *
 * Created by dean on 2017/12/22.
 */
abstract class ConvenientApplication : Application() {

    // app初始化配置和数据完成的Action
    val ACTION_APP_INIT_FINISH = ConvenientApplication::class.simpleName + ".ACTION_APP_INIT_FINISH"
    // app是否初始化完成（默认未完成）
    var isAppInitFinish = false
    // 启动过的Activity
    private lateinit var mHistoryActivityMap: MutableMap<String, Any>

    override fun onCreate() {
        super.onCreate()

        // 解决URI问题
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    /**
     * 版本升级
     */
    fun versionUpdate() {
        Thread(Runnable {
            // 抽象方法，开发人员根据业务自己实现初始化加载的配置和数据
            initConfigAndData()

        }).start()
    }

    /**
     * app初始化抽象方法(* 开发人员不要创建新线程去写自己的业务)
     *
     *
     * 开发人员根据业务自己实现初始化加载的配置和数据
     */
    protected abstract fun initConfigAndData()

}