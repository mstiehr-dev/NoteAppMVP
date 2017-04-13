package com.mstiehr_dev.noteappmvp.platform;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mstiehr_dev.noteappmvp.view.MainActivity;

public class BackgroundService extends Service
{
    private static final String TAG = "BackgroundService";

    private Handler mActivityCallbackHandler;
    private MainActivity.BackgroundCallback callback;

    private final IBinder mBinder = new BackgroundBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class BackgroundBinder extends Binder
    {
        // activity holds a reference to binder

        public void setActivityCallbackHandler(Handler activityCallbackHandler) {
            mActivityCallbackHandler = activityCallbackHandler;
        }

        public void setCallbackRunnable(MainActivity.BackgroundCallback runnable)
        {
            callback = runnable;
        }

        public void doShit()
        {
            Log.d(TAG, "doShit: ");
            mActivityCallbackHandler.post(callback);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
