package com.ceshi.ha.net

import com.freddy.shine.java.ShineKit
import com.freddy.shine.java.config.ShineOptions

class ShineKitUtils {
    fun get(){
        val options = ShineOptions.Builder()
            .setLogEnable(true)
            .setLogTag("FreddyChen")
            .setBaseUrl("https://api.oick.cn/")
//            .setParserCls(CustomParser1::class)
            .build()
        ShineKit.getInstance().init(options)
    }
}