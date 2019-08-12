package com.ceshi.ha.activity;

import android.app.IntentService;
import android.content.Intent;

/**
 * @创建时间 2019-08-12 13:22
 * @创建者 wy
 * @描述
 */
public class WorkService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WorkService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@androidx.annotation.Nullable Intent intent) {

    }


}
