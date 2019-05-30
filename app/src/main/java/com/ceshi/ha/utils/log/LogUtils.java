/**
 * Copyright (c) 1993-2023 AutoNavi, Inc. All rights reserved. This software is
 * the confidential and proprietary information of AutoNavi, Inc.
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with AutoNavi.
 */
package com.ceshi.ha.utils.log;

import android.os.Looper;
import android.util.Log;

/**
 * 日志相关类:默认是测试环境<br>
 * <b>支持：存储Log日志文件到本地。发送Log日志信息到服务器</b>
 *
 * @author yuchao.wang
 * @since 2014-4-23
 */
public class LogUtils extends LogData {
    private static boolean DEVELOPER_MODE = true;
    public static boolean debug = DEVELOPER_MODE;
    public static String tag = LogUtils.class.getSimpleName();

    public static void d(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }

    public static void d(String msg) {
        if (debug)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (debug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (debug)
            Log.e(tag, msg);
    }

    public static void i(String msg) {
        if (debug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (debug)
            Log.v(tag, msg);
    }

    public static void v(String msg) {
        if (debug)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (debug)
            Log.w(tag, getDetailMessage(msg));
          // Log.w(tag, msg);
    }

    public static void w(String msg) {
        if (debug) {
            String[] output = getTagAndDetailMessage(msg);
            Log.w(output[0], output[1]);
        }
          // Log.w(tag, msg);
    }

    public static void e(String msg) {
        if (debug)
            Log.e(tag, msg);
    }


    public static void printStack() {
        if (debug) {
            Throwable throwable = new Throwable();
            Log.w(tag, Log.getStackTraceString(throwable));
        }
    }

    public static void isMainThread() {
        if (debug) {
            Log.w(tag, "isMainThread :" + (Looper.getMainLooper() == Looper.myLooper()));
        }
    }

    public static void checkMainThread() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            throw new RuntimeException("Must be activity_m1 thread");
        }
    }

    /**
     * 分段打印出较长log文本
     *
     * @param log       原log文本
     * @param showCount 规定每段显示的长度
     */
    public static void showLogCompletion(String tag, String log, int showCount) {
        if (debug) {
            if (log.length() > showCount) {
                String show = log.substring(0, showCount);
                Log.i(tag, show + "");
                if ((log.length() - showCount) > showCount) {// 剩下的文本还是大于规定长度
                    String partLog = log.substring(showCount, log.length());
                    showLogCompletion(tag, partLog, showCount);
                } else {
                    String surplusLog = log.substring(showCount, log.length());
                    Log.i(tag, surplusLog + "");
                }
            } else {
                Log.i(tag, log + "");
            }
        }
    }

    /**
     * 得到默认tag【类名】以及信息详情
     *
     * @param message 要显示的信息
     * @return 默认tag【类名】以及信息详情,默认信息详情【类名+方法名+行号+message】
     */
    private static String[] getTagAndDetailMessage(String message) {
        String output[] = new String[2];
        for (StackTraceElement ste : (new Throwable()).getStackTrace()) {
            //栈顶肯定是LogUtil这个类自己
            if (LogUtils.class.getName().equals(ste.getClassName())) {
                continue;
            }
            //栈顶的下一个就是需要调用这个类的地方
            else {
                int b = ste.getClassName().lastIndexOf(".") + 1;
                output[0] = ste.getClassName().substring(b);
                output[1] = output[0] + "->" + ste.getMethodName() + "():" + ste.getLineNumber()
                        + "->" + message;
                break;
            }
        }
        return output;
    }

    /**
     * 得到一个信息的详细的情况【类名+方法名+行号】
     *
     * @param message 要显示的信息
     * @return 一个信息的详细的情况【类名+方法名+行号+message】
     */
    private static String getDetailMessage(String message) {
        String detailMessage = "";
        for (StackTraceElement ste : (new Throwable()).getStackTrace()) {
            //栈顶肯定是LogUtil这个类自己
            if (LogUtils.class.getName().equals(ste.getClassName())) {
                continue;
            }
            //栈顶的下一个就是需要调用这个类的地方[此处取出类名和方法名还有行号]
            else {
                int b = ste.getClassName().lastIndexOf(".") + 1;
                String TAG = ste.getClassName().substring(b);
                detailMessage = TAG + "->" + ste.getMethodName() + "():" + ste.getLineNumber()
                        + "->" + message;
                break;
            }
        }
        return detailMessage;
    }
}
