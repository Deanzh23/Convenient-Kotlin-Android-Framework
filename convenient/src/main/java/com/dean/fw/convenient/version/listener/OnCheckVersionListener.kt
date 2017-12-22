package com.dean.fw.convenient.version.listener

import org.json.JSONObject

/**
 * 版本更新监听器
 *
 * Created by dean on 2017/12/22.
 */
interface OnCheckVersionListener {

    fun onCheck(hasVersionUpdate: Boolean, updateMessage: JSONObject)
}