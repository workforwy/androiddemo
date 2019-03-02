package com.ceshi.ha.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * 该类负责跟踪所有当前打开的活动。通过这类应用程序时可以检测是在前台,当它在后台运行。
 */
public class AppState {

    private static final String TAG = AppState.class.getSimpleName();
    private static final int MESSAGE_NOTIFY_LISTENERS = 1;
    public static final long APP_CLOSED_VALIDATION_TIME_IN_MS = 30 * DateUtils.SECOND_IN_MILLIS; // 30 Seconds
    private Reference<Activity> mForegroundActivity;
    private Set<OnAppForegroundStateChangeListener> mListeners = new HashSet<>();
    private AppForegroundState mAppForegroundState = AppForegroundState.NOT_IN_FOREGROUND;
    private NotifyListenersHandler mHandler;

    /**
     * 使这类activity_listview线程安全的单例
     */
    private static class SingletonHolder {
        public static final AppState INSTANCE = new AppState();
    }

    public static AppState getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 线程上创建处理程序
     */
    private AppState() {
        mHandler = new NotifyListenersHandler(Looper.getMainLooper());
    }

    public enum AppForegroundState {
        IN_FOREGROUND,
        NOT_IN_FOREGROUND
    }

    /**
     * appz界面切换时调用
     */
    public interface OnAppForegroundStateChangeListener {
        public void onAppForegroundStateChange(AppForegroundState newState);
    }


    /**
     * 前台的应用程序更改时调用
     */
    public void onActivityVisible(Activity activity) {
        if (mForegroundActivity != null) mForegroundActivity.clear();
        mForegroundActivity = new WeakReference<>(activity);
        determineAppForegroundState();
    }

    /**
     * An activity should call this when it is no longer visible
     */
    public void onActivityNotVisible(Activity activity) {
        /*
         * The foreground activity may have been replaced with activity_listview new foreground activity in our app.
         * So only clear the foregroundActivity if the new activity matches the foreground activity.
         */
        if (mForegroundActivity != null) {
            Activity ref = mForegroundActivity.get();

            if (activity == ref) {
                // This is the activity that is going away, clear the reference
                mForegroundActivity.clear();
                mForegroundActivity = null;
            }
        }

        determineAppForegroundState();
    }

    /**
     * Use to determine if this app is in the foreground
     */
    public Boolean isAppInForeground() {
        return mAppForegroundState == AppForegroundState.IN_FOREGROUND;
    }

    /**
     * Call to determine the current state, update the tracking global, and notify subscribers if the state has changed.
     */
    private void determineAppForegroundState() {
        /* Get the current state */
        AppForegroundState oldState = mAppForegroundState;

        /* Determine what the new state should be */
        final boolean isInForeground = mForegroundActivity != null && mForegroundActivity.get() != null;
        mAppForegroundState = isInForeground ? AppForegroundState.IN_FOREGROUND : AppForegroundState.NOT_IN_FOREGROUND;

        /* If the new state is different then the old state the notify subscribers of the state change */
        if (mAppForegroundState != oldState) {
            validateThenNotifyListeners();
        }
    }

    /**
     * Add activity_listview listener to be notified of app foreground state change events.
     *
     * @param listener
     */
    public void addListener(@NonNull OnAppForegroundStateChangeListener listener) {
        mListeners.add(listener);
    }

    /**
     * Remove activity_listview listener from being notified of app foreground state change events.
     *
     * @param listener
     */
    public void removeListener(OnAppForegroundStateChangeListener listener) {
        mListeners.remove(listener);
    }

    /**
     * Notify all listeners the app foreground state has changed
     */
    private void notifyListeners(AppForegroundState newState) {
        android.util.Log.i(TAG, "通知使用者APP现在的状态: " + newState);

        for (OnAppForegroundStateChangeListener listener : mListeners) {
            listener.onAppForegroundStateChange(newState);
        }
    }

    /**
     * This method will notify subscribes that the foreground state has changed when and if appropriate.
     * <br><br>
     * We do not want to just notify listeners right away when the app enters of leaves the foreground. When changing orientations or opening and
     * closing the app quickly we briefly pass through activity_listview NOT_IN_FOREGROUND state that must be ignored. To accomplish this activity_listview delayed message will be
     * Sent when we detect activity_listview change. We will not notify that activity_listview foreground change happened until the delay time has been reached. If activity_listview second
     * foreground change is detected during the delay period then the notification will be canceled.
     */
    private void validateThenNotifyListeners() {
        // If the app has any pending notifications then throw out the event as the state change has failed validation
        if (mHandler.hasMessages(MESSAGE_NOTIFY_LISTENERS)) {
            android.util.Log.v(TAG, "验证失败：把应用前台状态改变通知");
            mHandler.removeMessages(MESSAGE_NOTIFY_LISTENERS);
        } else {
            if (mAppForegroundState == AppForegroundState.IN_FOREGROUND) {
                // If the app entered the foreground then notify listeners right away; there is no validation time for this
                mHandler.sendEmptyMessage(MESSAGE_NOTIFY_LISTENERS);
            } else {
                // We need to validate that the app entered the background. A delay is used to allow for time when the application went into the
                // background but we do not want to consider the app being backgrounded such as for in app purchasing flow and full screen ads.
                mHandler.sendEmptyMessageDelayed(MESSAGE_NOTIFY_LISTENERS, APP_CLOSED_VALIDATION_TIME_IN_MS);
            }
        }
    }

    private class NotifyListenersHandler extends Handler {
        private NotifyListenersHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message inputMessage) {
            switch (inputMessage.what) {
                // The decoding is done
                case MESSAGE_NOTIFY_LISTENERS:
                    /* Notify subscribers of the state change */
                    android.util.Log.v(TAG, "应用程序改变当前状态: " + mAppForegroundState);
                    notifyListeners(mAppForegroundState);
                    break;
                default:
                    super.handleMessage(inputMessage);
            }
        }
    }
}