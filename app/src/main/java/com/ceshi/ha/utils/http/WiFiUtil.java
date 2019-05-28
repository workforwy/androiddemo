package com.ceshi.ha.utils.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 作者：Administrator on 2015/12/27 14:22
 * 邮箱：906514731@qq.com
 */
public class WiFiUtil {
    /**
     * 判断是否连接WIFI
     *
     * @param context 上下文
     * @return boolean
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * @param context 上下文
     * @return 是否有网络
     */
    public static boolean checkNet(Context context) {

        ConnectivityManager manager
                = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null) {
            return true;
        }
        return false;
    }
}
