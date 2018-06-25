package com.practice.sensor;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    private final static String TAG = "hfpanswer";
    private  BroadcastReceiver pauseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //mSate = !mSate;
            Log.w(TAG, "pause Receiver ");

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");



    }

    @Override
    public void onCreate() {
        System.out.println("MyService.onCreate");
        super.onCreate();

        Log.w(TAG, "MyService.onCreate");
        IntentFilter intentFilter = new IntentFilter();
        //设置接收广播的类型
        intentFilter.addAction("com.xcz1899.smart.pause");
        //调用Context的registerReceiver（）方法进行动态注册
        registerReceiver(pauseReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("MyService.onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        System.out.println("MyService.onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("MyService.onUnbind");
        return super.onUnbind(intent);
    }
}
