package com.ceshi.ha.utils.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * 引用信息类
 *
 * @author wy
 */
public class AppInfoUtil {

    /**
     * Retrieve launcher activity name of the application from the context
     *
     * @param context The context of the application package.
     * @return launcher activity name of this application. From the
     * "android:name" attribute.
     */
    public static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        // All Application must have 1 Activity at least.
        // Launcher activity must be found!
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        //////////////////////另一种实现方式//////////////////////
        // ComponentName componentName = context.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName()).getComponent();
        // return componentName.getClassName();
        //////////////////////另一种实现方式//////////////////////
        return info.activityInfo.name;
    }


    /**
     * @param context 上下文
     * @return apn
     */
    public static String getAPN(Context context) {

        String apn = "";
        ConnectivityManager manager
                = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null) {
            if (ConnectivityManager.TYPE_WIFI == info.getType()) {
                apn = info.getTypeName();
                if (apn == null) {
                    apn = "wifi";
                }
            } else {
                apn = info.getExtraInfo().toLowerCase();
                if (apn == null) {
                    apn = "mobile";
                }
            }
        }
        return apn;
    }


    /**
     * @param context 上下文
     * @return model
     */
    public static String getModel(Context context) {

        return Build.MODEL;
    }

    //
    // public static String getHardware(Context context) {
    // if (getPhoneSDK(context) < 8) {
    // return "undefined";
    // } else {
    // Logger.d(TAG, "hardware:" + Build.HARDWARE);
    // }
    // return Build.HARDWARE;
    // }


    /**
     * @param context context
     * @return MANUFACTURER
     */
    public static String getManufacturer(Context context) {

        return Build.MANUFACTURER;
    }


    /**
     * @param context context
     * @return RELEASE
     */
    public static String getFirmware(Context context) {

        return Build.VERSION.RELEASE;
    }


    /**
     * @return sdkversion
     */
    public static String getSDKVer() {

        return Integer.valueOf(Build.VERSION.SDK_INT).toString();
    }

    /**
     * @return 获取语言
     */
    public static String getLanguage() {

        Locale locale = Locale.getDefault();
        String languageCode = locale.getLanguage();
        if (TextUtils.isEmpty(languageCode)) {
            languageCode = "";
        }
        return languageCode;
    }


    /**
     * @return 获取国家
     */
    public static String getCountry() {

        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();
        if (TextUtils.isEmpty(countryCode)) {
            countryCode = "";
        }
        return countryCode;
    }


    /**
     * @param context context
     * @return imei
     */
    public static String getIMEI(Context context) {

        TelephonyManager mTelephonyMgr
                = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        String imei = mTelephonyMgr.getDeviceId();
        if (TextUtils.isEmpty(imei) || imei.equals("000000000000000")) {
            imei = "0";
        }

        return imei;
    }


    /**
     * @param context context
     * @return imsi
     */
    public static String getIMSI(Context context) {

        TelephonyManager mTelephonyMgr
                = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        if (TextUtils.isEmpty(imsi)) {
            return "0";
        } else {
            return imsi;
        }
    }

    // public static String getLac(Context context) {
    // CellIdInfo cell = new CellIdInfo();
    // CellIDData cData = cell.getCellId(context);
    // return (cData != null ? cData.getLac() : "1");
    // }
    //
    // public static String getCellid(Context context) {
    // CellIdInfo cell = new CellIdInfo();
    // CellIDData cData = cell.getCellId(context);
    // return (cData != null ? cData.getCellid() : "1");
    // }


    /**
     * @param context context
     * @return mcnc
     */
    public static String getMcnc(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(
                Context.TELEPHONY_SERVICE);
        String mcnc = tm.getNetworkOperator();
        if (TextUtils.isEmpty(mcnc)) {
            return "0";
        } else {
            return mcnc;
        }
    }


    /**
     * Get phone SDK version
     *
     * @param mContext mContext
     * @return SDK version
     */
    public static int getPhoneSDK(Context mContext) {

        TelephonyManager phoneMgr
                = (TelephonyManager) mContext.getSystemService(
                Context.TELEPHONY_SERVICE);
        int sdk = 7;
        try {
            sdk = Integer.parseInt(Build.VERSION.SDK);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return sdk;
    }


    /**
     * @param context context
     * @param keyName keyName
     * @return data
     */
    public static Object getMetaData(Context context, String keyName) {

        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(
                            context.getPackageName(),
                            PackageManager.GET_META_DATA);

            Bundle bundle = info.metaData;
            Object value = bundle.get(keyName);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param context context
     * @return AppVersion
     */
    public static String getVersionName(Context context) {

        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            String versionName = pi.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取版本code
     * 也可使用 BuildConfig.VERSION_CODE 替换
     *
     * @param context 上下文
     * @return 版本code
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * @param context context
     * @return SerialNumber
     */
    public static String getSerialNumber(Context context) {

        String serial = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            serial = (String) get.invoke(c, "ro.serialno");
            if (serial == null || serial.trim().length() <= 0) {
                TelephonyManager tManager
                        = (TelephonyManager) context.getSystemService(
                        Context.TELEPHONY_SERVICE);
                serial = tManager.getDeviceId();
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return serial;
    }

}